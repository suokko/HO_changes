package hoplugins.specialEvents;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileLogger
{

    DataOutputStream dos;

    public FileLogger()
    {
        dos = null;
        try
        {
            dos = new DataOutputStream(new FileOutputStream("selog.txt", true));
        }
        catch(FileNotFoundException filenotfoundexception) { }
//        catch(IOException ioexception) { }
    }

    public synchronized void log(String msg)
    {
        try
        {
            dos.writeBytes(msg);
        }
        catch(FileNotFoundException filenotfoundexception) { }
        catch(IOException ioexception) { }
    }

    public void closeLog()
    {
        try
        {
            dos.close();
        }
        catch(IOException ioexception) { }
    }
}
