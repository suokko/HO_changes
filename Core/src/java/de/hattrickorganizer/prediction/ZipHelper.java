package de.hattrickorganizer.prediction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipHelper
{

    private static ZipFile zipFile = null;

    public ZipHelper(String zipFilename)
        throws Exception
    {
        try
        {
            zipFile = new ZipFile(new File(zipFilename));
            return;
        }
        catch(Exception _ex)
        {
            throw new Exception("The JarFile cannot be located");
        }
    }

    public ZipHelper(File file)
        throws Exception
    {
        try
        {
            zipFile = new ZipFile(file);
            return;
        }
        catch(Exception _ex)
        {
            throw new Exception("The JarFile cannot be located");
        }
    }

    public final boolean extractFile(String s, String dir)
    {
        File file;
        (file = new File(dir)).mkdirs();
        try
        {
            for(Enumeration<? extends ZipEntry> enumeration = zipFile.entries(); enumeration.hasMoreElements();)
            {
                ZipEntry zipentry = enumeration.nextElement();
                String filename;
                if((filename = dir + File.separatorChar + zipentry.getName()).toUpperCase().endsWith(s.toUpperCase()))
                    extractZipEntry(zipentry, filename);
            }

        }
        catch(Exception _ex)
        {
            return false;
        }
        return true;
    }

    public static InputStream getFile(String s)
    {
    	try {
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            ZipEntry zipentry;
            String s1;
            while(enumeration.hasMoreElements()) 
                if((s1 = (zipentry = (ZipEntry)enumeration.nextElement()).getName()).toUpperCase().endsWith(s.toUpperCase()))
                    return zipFile.getInputStream(zipentry);			
		} catch (Exception e) {
			// TODO: handle exception
		}
        return null;
    }

    public static void close()
    {
        try
        {
            zipFile.close();
            return;
        }
        catch(IOException _ex)
        {
            return;
        }
    }

    public final boolean unzip(String toDir)
    {
        File file;
        (file = new File(toDir)).mkdirs();
        try
        {
            ZipEntry zipentry;
            String filename;
            for(Enumeration<? extends ZipEntry> enumeration = zipFile.entries(); 
            	enumeration.hasMoreElements(); 
            	extractZipEntry(zipentry, filename))
            {
                zipentry = (ZipEntry)enumeration.nextElement();
                filename = toDir + File.separatorChar + zipentry.getName();
            }

            zipFile.close();
        }
        catch(Exception _ex)
        {
            return false;
        }
        return true;
    }

    private static void extractZipEntry (ZipEntry zipentry, String s)
        throws IOException, FileNotFoundException
    {
        File file;
        if(!(file = new File(s)).getParentFile().exists())
            file.getParentFile().mkdirs();
        if(zipentry.isDirectory())
            file.mkdir();
        if(!file.exists())
            file.createNewFile();
        InputStream inputstream = zipFile.getInputStream(zipentry);
        byte buffer[] = new byte[2048];
        if(!file.isDirectory())
        {
            FileOutputStream fileoutputstream = new FileOutputStream(file);
            for(int i = 0; (i = inputstream.read(buffer)) != -1;)
                fileoutputstream.write(buffer, 0, i);

            fileoutputstream.flush();
            fileoutputstream.close();
            inputstream.close();
        }
    }

    public static Enumeration<? extends ZipEntry> getFileList()
    {
        return zipFile.entries();
    }

}
