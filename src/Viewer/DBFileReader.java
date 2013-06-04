/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Viewer;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ntx
 */
public abstract class DBFileReader {
    /**
     * Name of opened file
     */
    protected String fileName;
    /**
     * String specifying document format
     */
    protected String format;
    protected File file;

    /**
     * Count of records in document
     */
    protected int recordsCount;
    /**
     * Number of fields per record
     */
    protected int fieldsCount;
    /**
     * Size of record
     */
    protected int recordSize;
    /**
     * Size of string array at the end of document
     */
    protected int stringsSize;
    /**
     * Holder for string array
     */
    protected Map<Integer, String> strings;
    protected byte[] data; //
    protected boolean loaded;
    
    public DBFileReader() {
        this.strings = new HashMap<>();
        Clear();
    }

    /**
     * Initializes local variables
     */
    protected void Clear()
    {
        fileName = "";
        recordsCount = 0;
        fieldsCount = 0;
        recordSize = 0;
        stringsSize = 0;
        loaded = false;
        strings.clear();
    }
    
    public int getRecordsCount() {
        return recordsCount;
    }

    public int getFieldsCount() {
        return fieldsCount;
    }

    public int getRecordSize() {
        return recordSize;
    }

    public int getStringsSize() {
        return stringsSize;
    }
    
    public File getFile(){
        return file;
    }

    public String getFormat() {
        return format;
    }
    /**
     * Tests if specified format is valid
     * 
     * @param _format String specifying format to be validated
     * @return True if specified format is valid, otherwise false
     */
    protected boolean isValidFormat(String _format)
    {
        if (_format == null)
            return false;
        if (_format.length() != fieldsCount)
            return false;
        for (char b : _format.toCharArray()) {
            switch (b)
            {
                case 'n':
                case 'i':
                case 's':
                case 'f':
                    continue;
                default:
                    return false;
            }
        }
        return true;
    }
    
    /**
     * Sets file format
     * @param _format String describing format
     * @return False if specified format is invalid, otherwise true
     */
    public boolean setFormat(String _format) {
        if (!isValidFormat(_format))
            return false;
        format = _format;
        return true;
    }
    
    public boolean isLodead(){
        return loaded;
    }
    /** 
     * Reads header from document
     * @param input Stream from which data will be red
     * @throws IOException 
     */
    abstract protected void ReadHeader(BufferedInputStream input) throws IOException;
    
    /**
     * Checks if string fields specified by format contains references to valid strings
     * @param tmpFormat 
     */
    private void CheckStringsExistence(char [] tmpFormat)
    {
        for (int b = 1; b < fieldsCount; ++b)
            if (tmpFormat[b] == 's')
            {
                boolean n = true;
                for (int i = 0; i < recordsCount; ++i)
                {
                    int intNum = ByteBuffer.wrap(data, i * recordSize + b * 4, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
                    if (strings.get(intNum) == null)
                    {
                        tmpFormat[b] = 'i';
                        break;
                    }
                    else 
                        if (intNum != 0) 
                            n = false;
                }
                if (n)
                    tmpFormat[b] = 'i';
             }
    }
    /**
     * Automatically detects format based on statistical type values
     * @throws IOException 
     */
    protected void AutoDetectFormat() throws IOException
    {
        char [] tmpFormat = new char[fieldsCount];
        Arrays.fill(tmpFormat, 's');
        tmpFormat[0] = 'n';
        int usedRows = 50;
        if (usedRows > recordsCount)
            usedRows = recordsCount;
        for (int i = 0; i < usedRows; ++i)
        {
            for (int b = 1; b < fieldsCount; ++b)
            {
                int intNum = ByteBuffer.wrap(data, i * recordSize + b * 4, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
                float floatNum = ByteBuffer.wrap(data, i * recordSize + b * 4, 4).order(ByteOrder.LITTLE_ENDIAN).getFloat();

                if ((strings.get(intNum) != null || intNum == 0 ) && tmpFormat[b] == 's')
                    tmpFormat[b] = 's';
                else
                {
                    if ((Math.abs(Math.log10(floatNum)) < 10 && Math.abs(Math.log10(intNum)) > 7) || 
                         (intNum == 0 && tmpFormat[b] == 'f')) // Statistical check
                        tmpFormat[b] = 'f';
                    else
                        tmpFormat[b] = 'i';
                        
                }
            }
            
        }
        CheckStringsExistence(tmpFormat);
        format = new String(tmpFormat);
    }
    
    /**
     * Abstract method for document processing
     * @param file Path to document
     * @param _format Specifies format which will be used to read data, if null format will be autodetected
     * @throws IOException
     * @throws InvalidParameterException 
     */
    abstract public void Open(File file, String _format) throws IOException, InvalidParameterException;
    
    /**
     * Fills specified table with data from opened document
     * @param table JTable in which data will be inserted
     * @param bar Progress bar to show progress on
     * @param colSelector JCombobox to fill col IDs for further searching
     */
    public void FillTable(JTable table, JProgressBar bar, JComboBox colSelector)
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnCount(0);
        model.setRowCount(0);
        colSelector.removeAllItems();
        for (int j = 0; j < fieldsCount; ++j)
        {
            model.addColumn(j == 0 ? "Id" : ("Col " + j));
            colSelector.addItem(j == 0 ? "Id" : ("Col " + j));
        }
        bar.setMinimum(0);
        bar.setMaximum(recordsCount);
        bar.setValue(0);
        for (int i = 0; i < recordsCount; ++i)
        {
            Object[] rowData = new Object[fieldsCount];
            for (int j = 0; j < fieldsCount; ++j)
            {
                ByteBuffer buffer = ByteBuffer.wrap(data, i * recordSize + j * 4, 4).order(ByteOrder.LITTLE_ENDIAN);
                switch (format.charAt(j))
                {
                    case 'n':
                    case 'i':
                        rowData[j] = buffer.getInt();
                        break;
                    case 'f':
                        rowData[j] = buffer.getFloat();
                        break;
                    case 's':
                        rowData[j] = strings.get(buffer.getInt());
                        break;
                }
            }
            model.addRow(rowData);
            bar.setValue(i + 1);
            bar.repaint();
        }
    }

    private String formatRow(int row, char separator)
    {
        String ret =  new String("");
        for (int j = 0; j < fieldsCount; ++j)
        {
            ByteBuffer buffer = ByteBuffer.wrap(data, row * recordSize + j * 4, 4).order(ByteOrder.LITTLE_ENDIAN);
            switch (format.charAt(j))
            {
                case 'n':
                case 'i':
                {
                    ret += Integer.toString(buffer.getInt());
                    break;
                }
                case 'f':
                {
                    ret += Float.toString(buffer.getFloat());
                    break;
                }
                case 's':
                {
                    int value = buffer.getInt();
                    String tmp = strings.get(value);
                    ret += '"' + strings.get(value).replace("\\", "\\\\").replace("\"", "\\\"") + '"';
                    break;
                }
            }
            if (j != fieldsCount - 1)
                ret += separator;
        }
        return ret;
    }
    
    /**
     * Function for data export to CSV file
     * @param output File in which will data will be written
     */
    public void ExportToCSV(File output) throws IOException
    {
        if (output == null)
            throw new InvalidParameterException("Invalid filename");
        BufferedWriter stream = new BufferedWriter(new FileWriter(output));
        for (int i = 0; i < recordsCount; ++i)
        {
            stream.write(formatRow(i, ';'));
            stream.write('\n');
        }
        stream.close();
    }
    
    /**
     * Function for table creation based on format of document
     * @param stream Stream in which data will be written
     * @param tableName Name of a new table
     * @throws IOException 
     */
    private void CreateTableByFormat(BufferedWriter stream, String tableName) throws IOException
    {
        stream.write("CREATE TABLE `" + tableName + "` (\n");
        for (int j = 0; j < fieldsCount; ++j)
        {
            switch (format.charAt(j))
            {
                case 'n':
                    stream.write("Id int unsigned not null primary key" + (j != fieldsCount - 1 ? "," : "") + '\n');
                    break;
                case 'i':
                    stream.write("col" + j + " int not null" + (j != fieldsCount - 1 ? "," : "") + '\n');
                    break;
                case 'f':
                    stream.write("col" + j + " float not null" + (j != fieldsCount - 1 ? "," : "") + '\n');
                    break;
                case 's':
                    stream.write("col" + j + " text not null" + (j != fieldsCount - 1 ? "," : "") + '\n');
                    break;
            }
        }
        stream.write(");");
    }
    
    /**
     * Method for data export into SQL File
     * @param output File in which data will be written
     * @param tableName Name of a table in which data will be inserted
     * @param createTable If true file will begin with "CREATE TABLE" statement
     * @param exportData If true data will be exported
     * @param singleRowExport If true single INSERT statement will be generated
     * @param rowsPerInsert Number of rows per INSERT, ignored when singleRowExport is in effect
     * @throws IOException 
     */
    public void ExportToSQL(File output, String tableName, boolean createTable, boolean exportData, boolean singleRowExport, int rowsPerInsert) throws IOException
    {
        if (output == null)
            throw new InvalidParameterException("Invalid filename");
        BufferedWriter stream = new BufferedWriter(new FileWriter(output));
        if (createTable)
        {
            CreateTableByFormat(stream, tableName);
            if (exportData)
                stream.write("\n\n");
        }
        if (exportData)
        {
            int totalCount = recordsCount;
            while (totalCount != 0)
            {
                int insertCount = recordsCount;
                if (!singleRowExport)
                {
                    insertCount = rowsPerInsert;
                    if (insertCount > totalCount)
                        insertCount = totalCount;
                }
                stream.write("INSERT INTO `" + tableName + "` VALUES \n");
                for (int  i = 0; i < insertCount; ++i)
                {
                    stream.write('(');
                    stream.write(formatRow(i, ','));
                    stream.write(')' + (i != insertCount - 1 ? ",\n" : ";\n"));
                }
                totalCount -= insertCount;
                if (totalCount != 0)
                    stream.write("\n\n");
            }
        }
        stream.close();
    }
    
    
    abstract public String getStatisticsMessage();
}
