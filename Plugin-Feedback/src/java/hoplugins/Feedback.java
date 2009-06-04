package hoplugins;

import hoplugins.commons.utils.PluginProperty;
import hoplugins.feedback.constants.FeedbackConstants;
import hoplugins.feedback.dao.FeedbackSettingDAO;
import hoplugins.feedback.dao.FeedbackStatusDAO;
import hoplugins.feedback.model.FeedbackObject;
import hoplugins.feedback.model.Rating;
import hoplugins.feedback.model.Training;
import hoplugins.feedback.model.Transfers;
import hoplugins.feedback.ui.FeedbackMenu;
import hoplugins.feedback.ui.component.OptionPanel;
import hoplugins.feedback.ui.component.StartingPanel;

import java.io.File;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JWindow;

import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.IRefreshable;
/**
 * Universal Feedback plugin to report informations
 *
 * @author flattermann <drake79@users.sourceforge.net>
 */

public class Feedback implements IPlugin, IRefreshable, IOfficialPlugin {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Plugin String name */
    private static final String PLUGIN_NAME = "Feedback";
    private static final String PLUGIN_PACKAGE = "feedback";
    private static IHOMiniModel miniModel;

    //~ Instance fields ----------------------------------------------------------------------------

    /** Plugin version constant */
    public static final double PLUGIN_VERSION = 0.421;

    /** Plugin Id */
    private static final int PLUGIN_ID = 42;

    /** Lists of Feedback Objects, one list per FeedbackType  */
    private List[] feedbackObjects;

    //~ Methods ------------------------------------------------------------------------------------

    public final String getName() {
        return getPluginName() + " " + getVersion();
    }

    public final int getPluginID() {
        return PLUGIN_ID;
    }

    public final String getPluginName() {
        return PLUGIN_NAME;
    }

    public final File[] getUnquenchableFiles() {
        return null;
    }

    public final double getVersion() {
        return PLUGIN_VERSION;
    }

    /**
     * This method is called when new data is available
     * (i.e. after a download, after setting options, after sub skill recalculation...)
     * 
     * It creates the FeedbackObject lists with the upload data
     * 
     * The lists are rebuild only, if a new HRF is available 
     */
    public final void refresh() {
    	miniModel = Commons.getModel();
        final JWindow waitWindow = miniModel.getGUI().createWaitDialog(miniModel.getGUI().getOwner4Dialog());
        waitWindow.show();

        // clear Lists
        feedbackObjects = new Vector[FeedbackConstants.NUM_TYPES];

        Timestamp curHrfDate = miniModel.getBasics().getDatum();
        
        // If new HRF available... 
        // (i.e. a change in the options or a subskill recalc IS NOT sufficient) 
        if (curHrfDate.after(FeedbackSettingDAO.getLastHrfDate())) {
            // If activated and not stopped
            // build a list starting with the last COMPLETED date  
            if (FeedbackSettingDAO.isAutomatic(FeedbackConstants.TYPE_RATING) &&
            		!FeedbackSettingDAO.isStopUpload(FeedbackConstants.TYPE_RATING)) {
            	feedbackObjects[FeedbackConstants.TYPE_RATING] =
            		Rating.rebuildList(FeedbackSettingDAO.getAsTimestamp(
            							"COMPLETED" +FeedbackConstants.TYPE_RATING,
            							new Timestamp(0)));
            }

            // If activated and not stopped, 
            // build a list starting with the last COMPLETED date  
            if (FeedbackSettingDAO.isAutomatic(FeedbackConstants.TYPE_TRAINING) &&
            		!FeedbackSettingDAO.isStopUpload(FeedbackConstants.TYPE_TRAINING)) {
            	feedbackObjects[FeedbackConstants.TYPE_TRAINING] =
            		Training.rebuildList(FeedbackSettingDAO.getAsTimestamp(
            							"COMPLETED" +FeedbackConstants.TYPE_TRAINING,
            							new Timestamp(0)));
            }

            // If activated and not stopped, 
            // build a list starting with the last COMPLETED date  
            if (FeedbackSettingDAO.isAutomatic(FeedbackConstants.TYPE_TRANSFERS) &&
            		!FeedbackSettingDAO.isStopUpload(FeedbackConstants.TYPE_TRANSFERS)) {
            	feedbackObjects[FeedbackConstants.TYPE_TRANSFERS] =
            		Transfers.rebuildList(FeedbackSettingDAO.getAsTimestamp(
            							"COMPLETED" +FeedbackConstants.TYPE_TRANSFERS,
            							new Timestamp(0)));
            }

            // Check, if something needs to be uploaded
            checkLists();
            
        }

        FeedbackSettingDAO.setLastHrfDate(curHrfDate);

        waitWindow.hide();
        waitWindow.dispose();
    }

    /**
     * Check the FeedbackObject lists and upload the data if the lists are not empty
     */
    public void checkLists () {
    	for (int i=0; i < feedbackObjects.length; i++) {
    		if (feedbackObjects[i] != null && feedbackObjects[i].size() > 0) {
    			boolean uploadOk = uploadFeedbackList(feedbackObjects[i]);
    			if (uploadOk) {
    				FeedbackSettingDAO.setTimestamp("COMPLETED" +i, miniModel.getBasics().getDatum());
    			}
    		}
    	}
    }
    
    /**
     * This method is called on HO startup
     */
    public final void start(IHOMiniModel hoMiniModel) {
        setMiniModel(hoMiniModel);
        PluginProperty.loadPluginProperties(PLUGIN_PACKAGE);
        hoMiniModel.getGUI().addOptionPanel(getPluginName(), new OptionPanel());
        if (!FeedbackSettingDAO.isStarted()) {
          JOptionPane.showMessageDialog(hoMiniModel.getGUI().getOwner4Dialog(),
                                        new StartingPanel(), "Info",
                                        JOptionPane.PLAIN_MESSAGE);
          FeedbackSettingDAO.setStarted();
        }
        hoMiniModel.getGUI().addMenu(FeedbackMenu.createMenu(hoMiniModel));
        hoMiniModel.getGUI().registerRefreshable(this);
        // TODO just for testing: Force refresh/upload at startup
//        refresh();
    }

    /**
     * Upload a single Feedback Object
     * 
     * @param fo
     * @return
     */
    private int upload(FeedbackObject fo) {
        try {
            // Create URL and open it
            String url = fo.createUrl();
//            System.out.println ("Opening: "+url);
        	if (url != null) {
        		String result = Commons.getModel().getDownloadHelper()
    				.getUsalWebPage(url,false);
        		if (result.toLowerCase().equals("success") ||
        				result.toLowerCase().equals("skipped"))
        			return FeedbackConstants.UPLOAD_OK;
        		else if (result.toLowerCase().equals("stop"))
        			return FeedbackConstants.UPLOAD_STOP;
        		else
        			System.out.println("Result='"+result+"'");
        	}
		} catch (Exception e) {
			System.out.println ("Feedback.upload: Exception catched");
			e.printStackTrace();
		}
		return FeedbackConstants.UPLOAD_FAILED;
    }

    /**
     * Upload a list of FeedbackObjects to the server
     * 
     * @param feedbackObjects
     * @return
     */
    private boolean uploadFeedbackList (List feedbackObjects) {
    	boolean allUploaded = true;
        for (Iterator iter = feedbackObjects.iterator(); iter.hasNext();) {
            final FeedbackObject fo = (FeedbackObject) iter.next();

            if (!FeedbackStatusDAO.isUploaded(fo)) {
            	int uploadStatus = upload(fo);
                if (uploadStatus == FeedbackConstants.UPLOAD_OK) {
                	System.out.println ("FeedbackUpload OK: "+fo.getFeedbackType()+"."+fo.getFeedbackId());
                    FeedbackStatusDAO.setUploaded(fo);
                } else if (uploadStatus == FeedbackConstants.UPLOAD_STOP) {
                	System.out.println ("FeedbackUpload STOPPED for type "+fo.getFeedbackType());
                	FeedbackSettingDAO.setStopUpload(fo.getFeedbackType(), true);
                	// Skip the other uploads of this type
                	return false;
                } else {
                	System.out.println ("FeedbackUpload FAILED: "+fo.getFeedbackType()+"."+fo.getFeedbackId());
                	System.out.println ("Request was: "+fo.createUrl());
                	allUploaded = false;
                }
            }
        }
        return allUploaded;
    }

	public static IHOMiniModel getMiniModel() {
		return miniModel;
	}

	public static void setMiniModel(IHOMiniModel miniModel) {
		Feedback.miniModel = miniModel;
	}

}
