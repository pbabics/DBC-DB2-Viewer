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
public class DB2Reader extends DBFileReader {
    
    protected int tableHash;
    protected int build;
    protected int unk;
    protected int unk2;
    protected int unk3;
    protected int maxIndex;
    protected int locales;
    
    public DB2Reader(){
        super();
    }

    @Override
    protected void Clear() {
        super.Clear();
        tableHash = 0;
        build = 0;
        unk = 0;
        unk2 = 0;
        unk3 = 0;
        maxIndex = 0;
        locales = 0;
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
            throw new InvalidParameterException("File is not a DB2 File");
        ReadHeader(stream);
        if (_format != null && !isValidFormat(_format))
            throw new InvalidParameterException("Invalid format");
        
        if (maxIndex != 0)
        {
            int diff = maxIndex - unk2 + 1;
            stream.skip(diff * 4 + diff * 2);
        }
        
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
        if (!(new String(b)).equals("WDB2"))
            throw new InvalidParameterException("File is not a DB2 File");
        input.read(b);
        recordsCount = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        input.read(b);
        fieldsCount = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        input.read(b);
        recordSize = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        input.read(b);
        stringsSize = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        input.read(b);
        tableHash = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        input.read(b);
        build = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        input.read(b);
        unk = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        if (build > 12880)
        {
            input.read(b);
            unk2 = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
            input.read(b);
            maxIndex = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
            input.read(b);
            locales = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
            input.read(b);
            unk3 = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
        }
    }

    @Override
    public String getStatisticsMessage() {
        return "Fields Count: " + getFieldsCount() + 
               "\nRecords Count: " + getRecordsCount() + 
               "\nSize of Record: " + getRecordSize() + 
               "\nStrings Size: " + getStringsSize() +
               "\nTable hash: " + tableHash +
               "\nBuild: " + build +
               "\nMax index:" + maxIndex;
    }
}

