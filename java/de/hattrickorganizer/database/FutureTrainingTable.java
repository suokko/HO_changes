package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import plugins.IFutureTrainingManager;
import plugins.IFutureTrainingWeek;
import de.hattrickorganizer.model.FutureTrainingWeek;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;

public final class FutureTrainingTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "FUTURETRAINING";
	
	protected FutureTrainingTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}

	protected void initColumns() {
		columns = new ColumnDescriptor[4];
		columns[0]= new ColumnDescriptor("TYPE",Types.INTEGER,false);
		columns[1]= new ColumnDescriptor("INTENSITY",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("WEEK",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("SEASON",Types.INTEGER,false);
	}
	
	public List getFutureTrainingsVector() {
		final java.util.Vector vTrainings = new java.util.Vector();
		String query = "select * from "+getTableName();
		ResultSet rs = adapter.executeQuery(query);

		try {
			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					FutureTrainingWeek train = new FutureTrainingWeek();			
					train.setTyp(rs.getInt("TYPE")); 
					train.setIntensitaet(rs.getInt("INTENSITY")); 
					train.setWeek(rs.getInt("WEEK"));
					train.setSeason(rs.getInt("SEASON"));						
					vTrainings.add(train);
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getTraining " + e);
		}
		
		List futures = new ArrayList();

		int actualSeason = HOVerwaltung.instance().getModel().getBasics().getSeason();
		int actualWeek = HOVerwaltung.instance().getModel().getBasics().getSpieltag();

		// We are in the middle where season has not been updated!
		try {
			if (HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate().after(HOVerwaltung.instance().getModel()
																				 .getXtraDaten()
																				 .getSeriesMatchDate())) {
			actualWeek++;

				if (actualWeek == 17) {
					actualWeek = 1;
					actualSeason++;
				}
			}
		} catch (Exception e1) {
			// Null when first time HO is launched		
		}

		for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
			
			// calculate the week and season of the future training
			int week = (actualWeek + i) - 1;
			int season = actualSeason + (week / 16);
			week = (week % 16) + 1;
	
			// load the training from DB
			IFutureTrainingWeek train = null;

			for (Iterator iter = vTrainings.iterator(); iter.hasNext();) {
				FutureTrainingWeek tmp = (FutureTrainingWeek) iter.next();
				if ((tmp.getWeek() == week) && (tmp.getSeason() == season)) {
					train = tmp;
					break;
				}
			}

			// if not found create it and saves it
			if (train == null) {
				train = new FutureTrainingWeek();
				train.setWeek(week);
				train.setSeason(season);
				train.setIntensitaet(-1);
				train.setTyp(-1);
				saveFutureTraining(train);				
			}
			futures.add(train);
		}		

		return futures;
	}
	
	/**
	 * TODO Missing Method Documentation
	 *
	 * @param training TODO Missing Method Parameter Documentation
	 */
	public void saveFutureTraining(IFutureTrainingWeek training) {
		if (training != null) {
			String statement =
				"update "+getTableName()+" set TYPE= " + training.getTyp() + ", INTENSITY=" + training.getIntensitaet() + " WHERE WEEK=" + training.getWeek() + " AND SEASON=" + training.getSeason();
			int count = adapter.executeUpdate(statement);

			if (count == 0) {
				adapter.executeUpdate("insert into "+getTableName()+" (TYPE, INTENSITY, WEEK, SEASON) values (" //$NON-NLS-1$
				+training.getTyp() + ", " + training.getIntensitaet() + ", " + training.getWeek() + ", " + training.getSeason() + ")");
			}

		}
	}

}
