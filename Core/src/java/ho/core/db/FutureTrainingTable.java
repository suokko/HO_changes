package ho.core.db;

import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.util.HOLogger;
import ho.module.training.FutureTrainingWeek;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public final class FutureTrainingTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "FUTURETRAINING";
	
	protected FutureTrainingTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[5];
		columns[0]= new ColumnDescriptor("TYPE",Types.INTEGER,false);
		columns[1]= new ColumnDescriptor("INTENSITY",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("WEEK",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("SEASON",Types.INTEGER,false);
		columns[4] = new ColumnDescriptor("STAMINATRAININGPART",Types.INTEGER,false);
	}
	
	List<FutureTrainingWeek> getFutureTrainingsVector() {
		final Vector<FutureTrainingWeek> vTrainings = new Vector<FutureTrainingWeek>();
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
					train.setStaminaTrainingPart(rs.getInt("STAMINATRAININGPART"));
					vTrainings.add(train);
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getTraining " + e);
		}
		
		List<FutureTrainingWeek> futures = new ArrayList<FutureTrainingWeek>();

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

		for (int i = 0; i < UserParameter.instance().futureWeeks; i++) {
			
			// calculate the week and season of the future training
			int week = (actualWeek + i) - 1;
			int season = actualSeason + (week / 16);
			week = (week % 16) + 1;
	
			// load the training from DB
			FutureTrainingWeek train = null;

			for (Iterator<FutureTrainingWeek> iter = vTrainings.iterator(); iter.hasNext();) {
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
				train.setStaminaTrainingPart(-1);
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
	void saveFutureTraining(FutureTrainingWeek training) {
		if (training != null) {
			String statement =
				"update "+getTableName()+" set TYPE= " + training.getTyp() + ", INTENSITY=" + training.getIntensitaet() + ", STAMINATRAININGPART=" + training.getStaminaTrainingPart() + " WHERE WEEK=" + training.getWeek() + " AND SEASON=" + training.getSeason();
			int count = adapter.executeUpdate(statement);

			if (count == 0) {
				adapter.executeUpdate("insert into "+getTableName()+" (TYPE, INTENSITY, WEEK, SEASON, STAMINATRAININGPART) values (" //$NON-NLS-1$
				+training.getTyp() + ", " + training.getIntensitaet() + ", " + training.getWeek() + ", " + training.getSeason() + "," + training.getStaminaTrainingPart() + ")");
			}

		}
	}

}
