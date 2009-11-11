package hoplugins.conv;




import java.io.File;

import javax.swing.filechooser.FileFilter;


final class CFilter extends FileFilter {
     
    private byte type = RSC.TYPE_BUDDY;
    
    protected CFilter(byte newType){
        type = newType;
    }

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        if(type == RSC.TYPE_BUDDY && RSC.isBuddyFile(f)) 
            return true;
        if (type == RSC.TYPE_HAM && RSC.isHAMFile(f)) 
            return true;
        if (type == RSC.TYPE_HTFOREVER && RSC.isHTForverFile(f))
            return true;
        if (type == RSC.TYPE_HTCOACH && RSC.isHTCoachFile(f))
            return true;
        return false;
    }

    public String getDescription() {
        String value = "?";
        switch(type){
        	case RSC.TYPE_BUDDY:value =  "Hattrick Buddy"; break;
        	case RSC.TYPE_HAM:value =  "Hattrick Assistent Manager"; break;
        	case RSC.TYPE_HTFOREVER:value =  "HT Forever"; break;
        	case RSC.TYPE_HTCOACH:value =  "HT Coach Professional"; break;
        }
        return value;
    }
    
    protected byte getType(){
        return type;
    }
    
}
