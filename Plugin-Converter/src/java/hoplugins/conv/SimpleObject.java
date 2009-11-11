/*
 * Created on 01.05.2005
 *
 */
package hoplugins.conv;

/**
 * @author Thorsten Dietz
 *
 */
public class SimpleObject {
    private int id;
    private String txt;
    
    protected SimpleObject(int newId, String newTxt){
        id = newId;
        txt = newTxt;
    }
    protected final int getId() {
        return id;
    }
    protected final String getTxt() {
        return txt;
    }
    
    public String toString(){
        return txt;
    }
}
