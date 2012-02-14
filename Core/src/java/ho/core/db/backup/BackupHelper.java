// %2388812641:de.hattrickorganizer.tools.backup%
package ho.core.db.backup;

import ho.core.db.User;
import ho.core.file.ExampleFileFilter;

import java.io.File;
import java.util.Calendar;

import de.hattrickorganizer.tools.HOLogger;


/**
 * HSQL DB zipper
 *
 * @author Thorsten Dietz
 */
public class BackupHelper {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * zip and delete db
     *
     * @param dbDirectory 
     */
	public static void backup(File dbDirectory) {
		  Calendar now = Calendar.getInstance();

		  if(! dbDirectory.exists())
			  return;

		  HOZip zOut = null;
		    try {
		    	zOut = new HOZip(dbDirectory+File.separator+"db_"+User.getCurrentUser().getName()+"-"+now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH)+".zip");

		      File[] files = dbDirectory.listFiles();
		      for (int i = 0; i < files.length; i++) {
				if(files[i].getName().endsWith(".script")
						|| files[i].getName().endsWith(".data")
						|| files[i].getName().endsWith(".backup")
						|| files[i].getName().endsWith(".properties")){
					zOut.addFile(files[i]);
				}
			}
		      
		      zOut.closeArchive();
		    }
		    catch(Exception e) {
		       HOLogger.instance().log(BackupHelper.class,e);
		    }

		  deleteOldFiles(dbDirectory);  
	  }

	/**
     * delete old zip files, which are out of backuplevel
     *
     * @param dbDirectory directory of the database
     */
	 private static void deleteOldFiles(File dbDirectory){
			 File toDelete = null;
			 ExampleFileFilter filter = new ExampleFileFilter("zip");
			 filter.setIgnoreDirectories(true);
			 File[] files = dbDirectory.listFiles(filter); 
			 if(files.length>User.getCurrentUser().getBackupLevel()){
				 for (int i = 0; i < files.length; i++) {
					if(i == 0 || (toDelete != null && toDelete.lastModified() > files[i].lastModified())){
						toDelete = files[i];
					}	 
				}
				if (toDelete != null)
					toDelete.delete();
			 }
		  }
	 
    
}
