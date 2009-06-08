package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import de.hattrickorganizer.gui.model.HOColumnModel;
import de.hattrickorganizer.gui.model.UserColumn;
import de.hattrickorganizer.gui.model.UserColumnFactory;
import de.hattrickorganizer.tools.HOLogger;


public class UserColumnsTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "USERCOLUMNS";

	protected UserColumnsTable(JDBCAdapter  adapter){
		super(TABLENAME, adapter);
	}


	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[4];
		columns[0]= new ColumnDescriptor("COLUMN_ID",	Types.INTEGER,false,true);
		columns[1]= new ColumnDescriptor("MODELL_INDEX",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("TABLE_INDEX",	Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("COLUMN_WIDTH",Types.INTEGER,true);
	}


	public void saveModel(HOColumnModel model){

		adapter.executeUpdate("DELETE FROM USERCOLUMNS WHERE COLUMN_ID BETWEEN "+(model.getId()*1000)+" AND "+((model.getId()+1)*1000));

		final StringBuffer sql = new StringBuffer(100);
		final StringBuffer values = new StringBuffer(20);
		sql.append("INSERT INTO ");
		sql.append(getTableName());
		sql.append("(");
		sql.append(columns[0].getColumnName());
		sql.append(",");
		sql.append(columns[1].getColumnName());
		sql.append(",");
		sql.append(columns[2].getColumnName());
		sql.append(",");
		sql.append(columns[3].getColumnName());
		sql.append(") VALUES (");

		UserColumn[] dbcolumns = model.getColumns();
		for (int i = 0; i < dbcolumns.length; i++) {
			if (model.getId()==2 && dbcolumns[i].getId() == UserColumnFactory.ID) {
				dbcolumns[i].setDisplay(true); // force ID column
			}
			if(dbcolumns[i].isDisplay()){
				values.append((model.getId()*1000)+dbcolumns[i].getId());
				values.append(",");
				values.append(i);
				values.append(",");
				values.append(dbcolumns[i].getIndex());
				values.append(",");
				values.append(dbcolumns[i].getPreferredWidth());
				values.append(")");
				adapter.executeUpdate(sql.toString()+values);
				values.delete(0,values.length());
			} // if
		}
	}

	public void insertDefault(HOColumnModel model){

		adapter.executeUpdate("DELETE FROM USERCOLUMNS WHERE COLUMN_ID BETWEEN "+(model.getId()*1000)+" AND "+((model.getId()+1)*1000));

		final StringBuffer sql = new StringBuffer(100);
		final StringBuffer values = new StringBuffer(20);
		sql.append("INSERT INTO ");
		sql.append(getTableName());
		sql.append("(");
		sql.append(columns[0].getColumnName());
		sql.append(",");
		sql.append(columns[1].getColumnName());
		sql.append(",");
		sql.append(columns[2].getColumnName());
		sql.append(",");
		sql.append(columns[3].getColumnName());
		sql.append(") VALUES (");

		UserColumn[] dbcolumns = model.getColumns();
		for (int i = 0; i < dbcolumns.length; i++) {
				dbcolumns[i].setIndex(i);
				values.append((model.getId()*1000)+dbcolumns[i].getId());
				values.append(",");
				values.append(i);
				values.append(",");
				values.append(dbcolumns[i].getIndex());
				values.append(",");
				values.append(dbcolumns[i].getPreferredWidth());
				values.append(")");

			adapter.executeUpdate(sql.toString()+values);
			values.delete(0,values.length());
		}
	}
	public void loadModel(HOColumnModel model){
		int modelIndex 	= 0;
		int tableIndex 	= 0;
		int width		= 10;

		final StringBuffer sql = new StringBuffer(100);
		int count = 0;
		sql.append("SELECT * ");
		sql.append(" FROM ");
		sql.append(getTableName());
		sql.append(" WHERE ");
		sql.append(columns[0].getColumnName());
		sql.append(" BETWEEN ");
		sql.append((model.getId()*1000));
		sql.append(" AND ");
		sql.append(((model.getId()+1)*1000));
//		sql.append(" ORDER BY  ");
//		sql.append( columns[2].getColumnName() );
//		sql.append("  ");
		ResultSet rs = adapter.executeQuery(sql.toString());
		UserColumn[] dbcolumns = model.getColumns();
		try {

			while(rs.next()){

				modelIndex 	= rs.getInt(columns[1].getColumnName());
				tableIndex 	= rs.getInt(columns[2].getColumnName());
				width 		= rs.getInt(columns[3].getColumnName());

				dbcolumns[modelIndex].setIndex(tableIndex);
				dbcolumns[modelIndex].setDisplay(true);
				dbcolumns[modelIndex].setPreferredWidth(width);


				count++;
			}

			if(count == 0){
				insertDefault( model );
				loadModel( model );
			}
			rs.close();

		} catch (SQLException e) {
			HOLogger.instance().log(getClass(),e);
		}

	}

}
