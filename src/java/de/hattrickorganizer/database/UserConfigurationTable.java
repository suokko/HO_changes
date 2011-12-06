package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import plugins.IUserConfiguration;
import de.hattrickorganizer.tools.HOLogger;
/**
 * The Table UserConfiguration contain all User properties.
 * CONFIG_KEY = Primary Key, fieldname of the class
 * CONFIG_VALUE = value of the field, save as VARCHAR. Convert to right datatype if loaded
 * 
 * @since 1.36
 *
 */
final class UserConfigurationTable extends AbstractTable {
	final static String TABLENAME = "USERCONFIGURATION";

	protected UserConfigurationTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[2];
		columns[0] = new ColumnDescriptor("CONFIG_KEY", Types.VARCHAR, false, true, 50);
		columns[1] = new ColumnDescriptor("CONFIG_VALUE", Types.VARCHAR, true, 256);
	}


	private void insert(String key, String value) {
		StringBuffer sql = new StringBuffer(130);
		sql.append("INSERT INTO ");
		sql.append(getTableName());
		sql.append("(");
		sql.append(columns[0].getColumnName());
		sql.append(",");
		sql.append(columns[1].getColumnName());
		sql.append(") VALUES (");
		sql.append("'");
		sql.append(key);
		sql.append("','");
		sql.append(value);
		sql.append("')");
		adapter.executeUpdate(sql.toString());
	}

	/**
	 * Update a key in the user configuration
	 * if the key does not exist yet, insert it
	 * @param key
	 * @param value
	 */
	void update(String key, String value) {
		final StringBuffer updateSQL = new StringBuffer(80);
		updateSQL.append("UPDATE ");
		updateSQL.append(getTableName());
		updateSQL.append(" SET ");
		updateSQL.append(columns[1].getColumnName());
		updateSQL.append(" = '");
		updateSQL.append(value);
		updateSQL.append("' WHERE ");
		updateSQL.append(columns[0].getColumnName());
		updateSQL.append(" = '");
		updateSQL.append(key);
		updateSQL.append("'");
		// Try to update the key in the DB
		int updated = adapter.executeUpdate(updateSQL.toString());
		if (updated == 0)
			// Key not yet in DB -> insert key/value
			insert(key, value);
	}

	private String getStringValue(String key) {
		String value = null;
		final StringBuffer sql = new StringBuffer(100);
		sql.append("SELECT * FROM ");
		sql.append(getTableName());
		sql.append(" WHERE ");
		sql.append(columns[0].getColumnName());
		sql.append(" = '");
		sql.append(key);
		sql.append("'");

		final ResultSet rs = adapter.executeQuery(sql.toString());
		try {
			if (rs.next()) {
				value = rs.getString(columns[1].getColumnName());
			}
			rs.close();
		} catch (SQLException e) {
			HOLogger.instance().log(getClass(), e);
		}

		return value;
	}


	int getDBVersion() {
		int version = 0;
		try {
			//      	 in the next version we have to change statement!!!

			final ResultSet rs = adapter.executeQuery("SELECT CONFIG_VALUE FROM " + TABLENAME + " WHERE CONFIG_KEY = 'DBVersion'");

			if ((rs != null) && rs.first()) {
				version = rs.getInt(1);
				rs.close();
			}
			
		} catch (Exception e) {
			try {
				HOLogger.instance().log(getClass(), "Old DB version.");
				final ResultSet rs = adapter.executeQuery("SELECT DBVersion FROM UserParameter");
				if ((rs != null) && rs.first()) {
					version = rs.getInt(1);
					rs.close();
				}
				
			} catch (Exception e1) {
				HOLogger.instance().log(getClass(), e1);
			}
		}
		return version;
	}

	/**
	 * Get the last HO release where we have completed successfully a config update
	 * @return	the ho version of the last conf update
	 */
	double getLastConfUpdate() {
		double version = 0;
		try {
			final ResultSet rs = adapter.executeQuery("SELECT CONFIG_VALUE FROM " + TABLENAME + " WHERE CONFIG_KEY = 'LastConfUpdate'");

			if ((rs != null) && rs.first()) {
				version = rs.getDouble(1);
				rs.close();
			}
			
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), e);
		}
		return version;
	}

	/**
	 * update/ insert method
	 * @param obj
	 */
	void store(IUserConfiguration obj) {
		final HashMap<String, String> values = obj.getValues();
		final Set<String> keys = values.keySet();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			String key = iter.next();
			update(key, (values.get(key) != null) ? values.get(key).toString() : "");
		}		
	}

	/**
	 * 
	 * @param obj
	 */
	void load(IUserConfiguration obj) {
		final HashMap<String,String> values = new HashMap<String,String>();
		final Set<String> keys = obj.getValues().keySet();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			String key = iter.next();
			String value = getStringValue(key);
			values.put(key, (value != null) ? value : obj.getValues().get(key));
		}
		obj.setValues(values);
	}

}
