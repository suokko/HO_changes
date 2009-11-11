/*
 * Created on 20.11.2004
 *
 */
package hoplugins.conv;


import javax.swing.JFileChooser;

/**
 * @author Thorsten Dietz
 *
 */
 final class CFileChooser extends JFileChooser {

     private static CFileChooser fc = new CFileChooser();
	
     private  CFileChooser(){
		super();
		setMultiSelectionEnabled(true);
		setAcceptAllFileFilterUsed(false);
		setFileView(new CFileView());
		setDialogTitle(RSC.getProperty("select_source_file"));
		addChoosableFileFilter(new CFilter(RSC.TYPE_HTCOACH)); 
	    addChoosableFileFilter(new CFilter(RSC.TYPE_HAM));  
	    addChoosableFileFilter(new CFilter(RSC.TYPE_HTFOREVER));
	    addChoosableFileFilter(new CFilter(RSC.TYPE_BUDDY)); 
	}
	
	protected static CFileChooser getInstance(){
	    return fc;
	}
}
