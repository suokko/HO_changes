package ho.core.db;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import de.hattrickorganizer.model.TrainingPerWeek;
import de.hattrickorganizer.tools.HOLogger;

final class TrainingsTable extends AbstractTable {
	final static String TABLENAME = "TRAINING";
	
	protected TrainingsTable(JDBCAdapter  adapter){
		super(TABLENAME, adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[5];
		columns[0]= new ColumnDescriptor("Week",Types.INTEGER,false);
		columns[1]= new ColumnDescriptor("Year",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("Typ",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("Intensity",Types.INTEGER,false);
		columns[4]= new ColumnDescriptor("StaminaTrainingPart",Types.INTEGER,false);

	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param training TODO Missing Method Parameter Documentation
	 */
	void saveTraining(de.hattrickorganizer.model.TrainingPerWeek training) {
		if (training != null) {
			final String[] awhereS = { "Week", "Year" };
			final String[] awhereV = { "" + training.getWeek(), "" + training.getYear()};

			delete( awhereS, awhereV );
			
			String statement = "INSERT INTO "+getTableName()+" ( Week, Year, Typ, Intensity, StaminaTrainingPart ) VALUES ( ";
			statement += (training.getWeek() + ", " + training.getYear() + ", " + training.getTyp() + ", " + training.getIntensitaet() + ", " + training.getStaminaTrainingPart() + " )");

			adapter.executeUpdate(statement);
		}
	}
	
	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	Vector<TrainingPerWeek> getTrainingsVector() {
		final Vector<TrainingPerWeek> vTrainings = new Vector<TrainingPerWeek>();

		final String statement = "SELECT * FROM "+getTableName()+" ORDER BY year, week ASC";

		final ResultSet rs = adapter.executeQuery(statement);

		try {
			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					vTrainings.add(new TrainingPerWeek(rs.getInt("week"), rs.getInt("year"), rs.getInt("Typ"), rs.getInt("Intensity"), rs.getInt("StaminaTrainingPart")));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getTraining " + e);
		}

		return vTrainings;
	}
	
}
