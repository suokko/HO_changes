package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;

import de.hattrickorganizer.tools.HOLogger;

public final class TrainingsTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "TRAINING";
	
	protected TrainingsTable(JDBCAdapter  adapter){
		super(TABLENAME, adapter);
	}

	protected void initColumns() {
		columns = new ColumnDescriptor[4];
		columns[0]= new ColumnDescriptor("Week",Types.INTEGER,false);
		columns[1]= new ColumnDescriptor("Year",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("Typ",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("Intensity",Types.INTEGER,false);

	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param training TODO Missing Method Parameter Documentation
	 */
	public void saveTraining(de.hattrickorganizer.model.TrainingPerWeek training) {
		if (training != null) {
			final String[] awhereS = { "Week", "Year" };
			final String[] awhereV = { "" + training.getWeek(), "" + training.getYear()};

			delete( awhereS, awhereV );
			
			String statement = "INSERT INTO "+getTableName()+" ( Week, Year, Typ, Intensity ) VALUES ( ";
			statement += (training.getWeek() + ", " + training.getYear() + ", " + training.getTyp() + ", " + training.getIntensitaet() + " )");

			adapter.executeUpdate(statement);
		}
	}
	
	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public java.util.Vector getTrainingsVector() {
		final java.util.Vector vTrainings = new java.util.Vector();

		final String statement = "SELECT * FROM "+getTableName()+" ORDER BY year, week ASC";

		final ResultSet rs = adapter.executeQuery(statement);

		try {
			if (rs != null) {
				rs.beforeFirst();

				while (rs.next()) {
					vTrainings.add(new de.hattrickorganizer.model.TrainingPerWeek(rs.getInt("week"), rs.getInt("year"), rs.getInt("Typ"), rs.getInt("Intensity")));
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getTraining " + e);
		}

		return vTrainings;
	}
	
}
