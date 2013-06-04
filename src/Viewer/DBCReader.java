/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Viewer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidParameterException;

/**
 *
 * @author ntx
 */

public class DBCReader extends DBFileReader{

    public DBCReader(){
        super();
    }
    
    @Override
    public void Open(File file, String _format) throws IOException, InvalidParameterException
    {
        Clear();
        strings.clear();
        this.file = file;
        if (!file.exists() || !file.canRead())
            throw new InvalidParameterException("File " + fileName + " does not exists");

        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
        if (file.length() < 20)
            throw new InvalidParameterException("File is not a DBC File");
        ReadHeader(stream);
        if (_format != null && !isValidFormat(_format))
            throw new InvalidParameterException("Invalid format");
        data = new byte[recordSize * recordsCount];
        byte[] stringData = new byte[stringsSize];
        stream.read(data);
        stream.read(stringData);
        BufferedInputStream stringStream = new BufferedInputStream(new ByteArrayInputStream(stringData));
        int position = 0;
        int lastPos = 0;
        int x;
        String tmp = new String();
        while ((x = stringStream.read()) != -1)
        {
            position++;
            if (x == 0)
            {
                strings.put(lastPos, tmp);
                tmp = new String();
                lastPos = position;
                continue;
            }
            tmp += (char)x;
        }
        if (_format == null)
            AutoDetectFormat();
        else
            format = _format;
        loaded = true;
    }

    @Override
    protected void ReadHeader(BufferedInputStream input) throws IOException
    {
        byte [] b = new byte[4];
        input.read(b);
        if (!(new String(b)).equals("WDBC"))
            throw new InvalidParameterException("File is not a DBC File");
        input.read(b);
        recordsCount = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        input.read(b);
        fieldsCount = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        input.read(b);
        recordSize = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        input.read(b);
        stringsSize = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    @Override
    public String getStatisticsMessage() {
        return "Fields Count: " + getFieldsCount() + 
               "\nRecords Count: " + getRecordsCount() + 
               "\nSize of Record: " + getRecordSize() + 
               "\nStrings Size: " + getStringsSize();
    }
}
