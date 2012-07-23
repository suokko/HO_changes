// %2201242558:de.hattrickorganizer.logik%
package ho.core.training;

import ho.core.db.DBManager;
import ho.core.db.JDBCAdapter;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.misc.Basics;
import ho.core.util.HOLogger;
import ho.core.util.Helper;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


/**
 * Class that extract data from Database and contains the list of TrainingPerWeek objects, one for
 * each week in the database.
 * 
 *
 * @author humorlos, Dragettho, thetom, seb04, blaghaid
 */
public class TrainingWeekManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static TrainingWeekManager m_clInstance;

    /** TrainingWeeks */
    private List<TrainingPerWeek> m_vTrainings;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of TrainingsManager
     */
    private TrainingWeekManager() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the instance of TrainingWeekManager.
     * 
     * @return instance of TrainingWeekManager
     */
    public static TrainingWeekManager instance() {
        if (m_clInstance == null) {
            m_clInstance = new TrainingWeekManager();
        }

        return m_clInstance;
    }

    
    /**
     * returns the current Training List
     *
     * @return Training List, List of TrainingPerWeek
     */
    public List<TrainingPerWeek> getTrainingList() {
    	if (m_vTrainings == null) {
    		
    		m_vTrainings = generateTrainingList();
    	}
    	return m_vTrainings;
    }
    
    
    /**
     * Forces a new training list to be generated, and returns the result.
     * 
     * @return The list of Team Training Weeks. 
     */
    public List<TrainingPerWeek> refreshTrainingList() {
    	m_vTrainings = generateTrainingList();
    	return m_vTrainings;
    }
    
    
    /**
     * Returns the TrianingPerWeek for the week with the given nextTrainingDate.
     * 
     * @param nextTrainingDate The timestamp of the next training found in a hrf
     *
     * @return TrainingPerWeek for the given timestamp, or null if none found.
     */
    public TrainingPerWeek getTrainingWeek(Timestamp nextTrainingDate) {
        
    	TrainingPerWeek currentCandidate = null;
    	
    	for (TrainingPerWeek tpw : m_vTrainings) {
    		if (nextTrainingDate.after(tpw.getNextTrainingDate())) {
    			return currentCandidate;
    		}
    		currentCandidate = tpw;
    	}
    	HOLogger.instance().error(getClass(), "No training week found for date:  " + nextTrainingDate);
    	return null;
    }

    
    /** Returns a list of TraingPerWeek, one for each week since the first hrf.
     * 
     * @param overrides An input list of TrainingPerWeek that will override the info for the same
     * 		week based on hrf content.
     * @return The list of TrainingPerWeek.
     */
    private static List<TrainingPerWeek> generateTrainingList() {
    	
    	List<TrainingPerWeek> output = fetchTrainingListFromHrf();
    	output = washTrainingList(output);
    	output = updateWithOverrides(output, DBManager.instance().getTrainingOverrides());
    	output = updateHattrickDates(output);
    	return output;
    }
    
    
    /** Adjusts the content of items in trainingList where an item in overrides is present for the same
     *  year and week. Apart from this the return is trainingList. Only week and year fields are used from the overrides,
     *  in addition to training settings.
     * 
     *  Both lists are assumed to be sorted on date, with the first training date first, but overrides does not have
     *  to include every training, and can be just a list of a few differences. 
     * 
     * @param trainingList A sorted list of all trainingWeeks.
     * @param overrides A list of overrides for values in the trainingList.
     * @return the adjusted trainingList.
     */
    private static List<TrainingPerWeek> updateWithOverrides (List<TrainingPerWeek> trainingList
    																, List<TrainingPerWeek> overrides) {
    	// This will break badly if they are not sorted with first date first.
    	
    	TrainingPerWeek tpw = null;
    	Iterator<TrainingPerWeek> iter = trainingList.iterator();
    	
    	// do for each override.
    	for (TrainingPerWeek over : overrides) {
    		// Search forwards through the trainingList, continuing from where we left off.
    		while (iter.hasNext()) {
    			tpw = iter.next();
    			if ((tpw != null) 
    					&& (tpw.getWeek() == over.getWeek())
    					&& (tpw.getYear() == over.getYear())) {
 
    				tpw.setStaminaPart(over.getStaminaPart());
    				tpw.setTrainingIntensity(over.getTrainingIntensity());
    				tpw.setTrainingType(over.getTrainingType());
    				break;
    			}
    		}
    	}
    	
    	return trainingList;
    }

    
    /** This one filters the list so that there is one per week. If multiple hrfs a week, the last one is used.
     * 	If there is a week with no hrf, a new will be inserted with content identical to the old one, and with a
     *  next training date one week after the previous one.
     *  
     *  Objects in the input are included in the output, and can be modified by this function.
     * 
     * @param input list to be filtered
     * @return the filtered list
     */
    private static List<TrainingPerWeek> washTrainingList(List<TrainingPerWeek> input) {
    	
    	ArrayList<TrainingPerWeek> output = new ArrayList<TrainingPerWeek>();
    	TrainingPerWeek old = null;
    	Calendar previousTraining = Calendar.getInstance(Locale.UK);
    	previousTraining.setFirstDayOfWeek(Calendar.SUNDAY);
    	
    	Calendar actualTraining = Calendar.getInstance(Locale.UK);
    	actualTraining.setFirstDayOfWeek(Calendar.SUNDAY);
    	
    	for (TrainingPerWeek tpw: input) {
    		
    		if (tpw == null) {
    			continue;
    		}
    		
    		if (old == null) {
    			// first item
    			old = tpw;
    			Calendar cal = Calendar.getInstance(Locale.UK);
    			cal.setTime(tpw.getNextTrainingDate());
    			cal.add(Calendar.WEEK_OF_YEAR, -1);
    			tpw.setTrainingDate(new Timestamp(cal.getTimeInMillis()));
    			output.add(tpw);
    	
    			previousTraining.setTime(tpw.getNextTrainingDate());
    			continue;
    		}
    		
    		actualTraining.setTime(tpw.getNextTrainingDate());
    		
    		if (!actualTraining.after(previousTraining)) {
    			// The same week, update to the contents of the newer item.
    			// old should point at the last item entered.
    			old.setStaminaPart(tpw.getStaminaPart());
    			old.setTrainingIntensity(tpw.getTrainingIntensity());
    			old.setTrainingType(tpw.getTrainingType());
    			old.setAssistants(tpw.getAssistants());
    			old.setHrfId(tpw.getHrfId());
    			continue;
    		}
    	
    		
    		
   			while (actualTraining.after(previousTraining)) {
   				
   				// Advance the previous date one week.
   				previousTraining.add(Calendar.WEEK_OF_YEAR, 1);
   				
   				if (actualTraining.after(previousTraining)) {
   					// We have a skipped week. Create a new item
   					
   			       int trainWeek = previousTraining.get(Calendar.WEEK_OF_YEAR);
	               int trainYear= previousTraining.get(Calendar.YEAR);

	               if (old == null) {
	            	   // Should never, ever, happen, but makes the compiler shut up.
	            	   continue;
	               }
	               
	               TrainingPerWeek newTpw = new TrainingPerWeek(trainWeek, trainYear
	            		   						, old.getTrainingType()
	            		   						, old.getTrainingIntensity()
	            		   						, old.getStaminaPart());
   					newTpw.setNextTrainingDate(new Timestamp(previousTraining.getTimeInMillis()));
   					newTpw.setHrfId(-1);
   					newTpw.setTrainingDate(old.getNextTrainingDate());
   					newTpw.setAssistants(old.getAssistants());
   					
   					int htWeek = old.getHattrickWeek() + 1;
   					if (htWeek == 17) {
   						newTpw.setHattrickWeek(1);
   						newTpw.setHattrickSeason(1 + old.getHattrickSeason());
   					} else {
   						newTpw.setHattrickWeek(htWeek);
   						newTpw.setHattrickSeason(old.getHattrickSeason());
   					}
   					
   					old = newTpw;
   					output.add(newTpw);
   					// The previous date is already set at the start of loop.
   					
   				} else {
   					// We found our current date, add this object. 
   					// The previousDate is correct already.
   					tpw.setTrainingDate(old.getNextTrainingDate());
   					old = tpw;
   					output.add(tpw);
   				}
   			}     				
    	}
    	
    	return output;
    }
    
    /**
     * Fetches a TrainingPerWeek for each hrf in the database. This could be 0, 1, or more per week.
     * 
     * @return A list of TrainingPerWeek
     */
    private static List<TrainingPerWeek> fetchTrainingListFromHrf() {
    	try {
    		HOModel model = HOVerwaltung.instance().getModel();
    		Basics basics = model.getBasics();
    		List<TrainingPerWeek> output = new ArrayList<TrainingPerWeek>();
    		String sql = " SELECT TRAININGSART, TRAININGSINTENSITAET, STAMINATRAININGPART, " +
    					"HRF_ID, DATUM, TRAININGDATE, COTRAINER FROM HRF,TEAM,XTRADATA,VEREIN WHERE " + 
    					"HRF.HRF_ID=TEAM.HRF_ID and HRF.HRF_ID=XTRADATA.HRF_ID and HRF.HRF_ID=VEREIN.HRF_ID " +
    					"ORDER BY DATUM";
    	
	    	 final JDBCAdapter ijdbca = DBManager.instance().getAdapter();
	    	 final ResultSet rs = ijdbca.executeQuery(sql);
	         rs.beforeFirst();
	
	         int lastTrainWeek = -1;
	
	         boolean isFirstTrain = true;
	         int trainIntensity = 0;
	         int trainStaminaTrainPart = 0;
	         int hrfId = 0;
	         int trainType = 0;
	         int assistants = 0;
	         Timestamp tStamp = null;
	         Calendar calendar = null;
	         int trainWeek = 0;
	         int trainYear = 0;
	         int weeks = 0;
	         Timestamp nextTraining = null;
	         
	         Calendar now = Calendar.getInstance(Locale.UK);
	         now.setTime(model.getXtraDaten().getTrainingDate());
	         Calendar tswDate = Calendar.getInstance(Locale.UK);
	         
	         
	         while (rs.next()) {
	        	 trainType = rs.getInt("TRAININGSART");
	             if (trainType != -1) {
	                 trainIntensity = rs.getInt("TRAININGSINTENSITAET");
	                 trainStaminaTrainPart = rs.getInt("STAMINATRAININGPART");
	                 hrfId = rs.getInt("HRF_ID");
	                 tStamp = rs.getTimestamp("DATUM");
	                 nextTraining = rs.getTimestamp("TRAININGDATE");
	                 assistants = rs.getInt("COTRAINER");
	                 
	                 calendar = Calendar.getInstance(Locale.UK);
	         		 calendar.setFirstDayOfWeek(Calendar.SUNDAY);
	         		 calendar.setTime(nextTraining);
	                 
	                 trainWeek = calendar.get(Calendar.WEEK_OF_YEAR);
	                 trainYear = calendar.get(Calendar.YEAR);
	                 
	                 TrainingPerWeek tpw = new TrainingPerWeek(trainWeek, trainYear, 
	                		 					trainType, trainIntensity, trainStaminaTrainPart);
	                 
	                 tpw.setNextTrainingDate(nextTraining);
	                 tpw.setHrfId(hrfId);
	                 tpw.setAssistants(assistants);
	                 output.add(tpw);
	             }
	         }
         
	         return output;
    	} catch (Exception e) {
	        HOLogger.instance().log(null,e);
	
	        final StackTraceElement[] aste = e.getStackTrace();
	        String msg = "";
	
	        for (int j = 0; j < aste.length; j++) {
	            msg = msg + "\n" + aste[j];
	        }
	
	        Helper.showMessage(null, e.getMessage(), "DatenreiheA",
	                                                   javax.swing.JOptionPane.INFORMATION_MESSAGE);
	        Helper.showMessage(null, msg, "DatenreiheB",
	                                                   javax.swing.JOptionPane.INFORMATION_MESSAGE);
    	}
         
    	return null;
    }
    
    
    /**
     * Updates the input list objects with hattrick season, hattrick week, and previousHrf.
     *
     * @param input A list of TrainingPerWeek. Sorted on date.
     *
     * @return The input list.
     */
    private static List<TrainingPerWeek> updateHattrickDates(List<TrainingPerWeek> input) {
    	HOModel hom = HOVerwaltung.instance().getModel();
    	Basics bas = hom.getBasics();
        int actualSeason = bas.getSeason();
        int actualWeek = bas.getSpieltag();
        int trainNumber = input.size();
        try {
            // We are in the middle where season has not been updated!
            if (hom.getXtraDaten().getTrainingDate().after(hom.getXtraDaten().getSeriesMatchDate())) {
                actualWeek++;
                if (actualWeek == 17) {
                    actualWeek = 1;
                    actualSeason++;
                }
            }
        } catch (Exception e) {
        	HOLogger.instance().log(null,"TrainingsWeekManager.updateHattrickDates: " + e);
            return input;
        }
        TrainingPerWeek train = null;
        HattrickDate htDate = null;
        for (int index = 0; index < trainNumber; index++) {
            train = input.get(index);
            htDate = calculateByDifference(actualSeason, actualWeek, trainNumber - index);
            train.setHattrickSeason(htDate.getSeason());
            train.setHattrickWeek(htDate.getWeek());
            train.setHrfId(train.getHrfId());
            train.setPreviousHrfId(DBManager.instance().getPreviousHRF(train.getHrfId()));
         }
        return input;
    }

    
    
    /**
     * Method that calculate the HT date for past week before
     *
     * @param actualSeason Actual Hattrick Season
     * @param actualWeek Actual Hattrick Week
     * @param pastWeek How many week to go back
     *
     * @return Hattrick Date
     */
    private static HattrickDate calculateByDifference(int actualSeason, int actualWeek, int pastWeek) {
        final HattrickDate date = new HattrickDate();
        actualWeek = (actualWeek - pastWeek) - 1;

        if (actualWeek <= 0) {
            final int a = Math.abs(actualWeek / 16) + 1;
            actualSeason = actualSeason - a;
            actualWeek = actualWeek + (a * 16);
        }
        date.setSeason(actualSeason + (actualWeek / 16));
        date.setWeek((actualWeek % 16) + 1);
        return date;
    }
}
