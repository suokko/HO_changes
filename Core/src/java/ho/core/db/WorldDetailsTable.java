package ho.core.db;

import ho.core.model.WorldDetailLeague;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import de.hattrickorganizer.tools.HOLogger;

class WorldDetailsTable extends AbstractTable {

	final static String TABLENAME = "HT_WORLDDETAILS";
	
	WorldDetailsTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[3];
		columns[0]= new ColumnDescriptor("LEAGUE_ID",Types.INTEGER,false,true);
		columns[1]= new ColumnDescriptor("COUNTRY_ID",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("COUNTRYNAME",Types.VARCHAR,false,128);
	}

	
	
	void insertWorldDetailsLeague(WorldDetailLeague league){
		if(league == null)
			return;
		
		StringBuilder statement = new StringBuilder(100);
		statement.append("insert into ").append(getTableName()).append("(");
		for (int i = 0; i < columns.length; i++) {
			statement.append(columns[i].getColumnName());
			if(i<columns.length-1)
				statement.append(",");
		}
		statement.append(") VALUES (");
		statement.append(league.getLeagueId()).append(",");
		statement.append(league.getCountryId()).append(",'");
		statement.append(DBManager.insertEscapeSequences(league.getCountryName())).append("')");
		adapter.equals(statement.toString());
	}
	
	WorldDetailLeague getWorldDetailLeagueByLeagueId(int leagueId){
		StringBuilder statement = new StringBuilder(100);
		statement.append("select * from ").append(getTableName()).append(" where ");
		statement.append(columns[0].getColumnName()).append("=").append(leagueId);
		return createObject(adapter.executeQuery(statement.toString()));
	}
	
	WorldDetailLeague getWorldDetailLeagueByCountryId(int countryId){
		StringBuilder statement = new StringBuilder(100);
		statement.append("select * from ").append(getTableName()).append(" where ");
		statement.append(columns[1].getColumnName()).append("=").append(countryId);
		return createObject(adapter.executeQuery(statement.toString()));
	}
	
	WorldDetailLeague[] getAllWorldDetailLeagues(){
		ArrayList<WorldDetailLeague> tmp = new ArrayList<WorldDetailLeague>();
		StringBuilder statement = new StringBuilder(100);
		statement.append("select * from ").append(getTableName()).append(" order by 1 ");
		ResultSet rs = adapter.executeQuery(statement.toString());
		try {
			while(rs.next()){
				tmp.add(createObject(rs));
			}
		} catch (SQLException e) {
			HOLogger.instance().error(this.getClass(), e);
		}
		return tmp.toArray(new WorldDetailLeague[tmp.size()]);
	}
	
	private WorldDetailLeague createObject(ResultSet rs){
		
		WorldDetailLeague league = new WorldDetailLeague();
		try {
			if(rs.next()){
				league.setLeagueId(rs.getInt(columns[0].getColumnName()));
				league.setCountryId(rs.getInt(columns[1].getColumnName()));
				league.setCountryName(DBManager.deleteEscapeSequences(rs.getString(columns[2].getColumnName())));
			}
		} catch (SQLException ex){
			HOLogger.instance().error(this.getClass(), ex);
		}
		return league;
	}

	
//	@Override
//	protected void insertDefaultValues(){
//		insertWorldDetailsLeague(new WorldDetailLeague(1, "Sweden"));
//		insertWorldDetailsLeague(new WorldDetailLeague(2, "England"));
//		insertWorldDetailsLeague(new WorldDetailLeague(3, "Germany"));
//		insertWorldDetailsLeague(new WorldDetailLeague(4, "Italy"));
//		insertWorldDetailsLeague(new WorldDetailLeague(5, "France"));
//		insertWorldDetailsLeague(new WorldDetailLeague(6, "Mexico"));
//		insertWorldDetailsLeague(new WorldDetailLeague(7, "Argentina"));
//		insertWorldDetailsLeague(new WorldDetailLeague(8, "USA"));
//		insertWorldDetailsLeague(new WorldDetailLeague(9, "Norway"));
//		insertWorldDetailsLeague(new WorldDetailLeague(11,10, "Denmark"));
//		insertWorldDetailsLeague(new WorldDetailLeague(12,11, "Finland"));
//		insertWorldDetailsLeague(new WorldDetailLeague(14,12, "Netherlands"));
//		insertWorldDetailsLeague(new WorldDetailLeague(15,13, "Oceania"));
//		insertWorldDetailsLeague(new WorldDetailLeague(16,22, "Brazil"));
//		insertWorldDetailsLeague(new WorldDetailLeague(17,14, "Canada"));
//		insertWorldDetailsLeague(new WorldDetailLeague(18,17, "Chile"));
//		insertWorldDetailsLeague(new WorldDetailLeague(19,18, "Colombia"));
//		insertWorldDetailsLeague(new WorldDetailLeague(20,27, "India"));
//		insertWorldDetailsLeague(new WorldDetailLeague(21,16, "Ireland"));
//		insertWorldDetailsLeague(new WorldDetailLeague(22,25, "Japan"));
//		insertWorldDetailsLeague(new WorldDetailLeague(23,21, "Peru"));
//		insertWorldDetailsLeague(new WorldDetailLeague(24,26, "Poland"));
//		insertWorldDetailsLeague(new WorldDetailLeague(25,23, "Portugal"));
//		insertWorldDetailsLeague(new WorldDetailLeague(26,15, "Scotland"));
//		insertWorldDetailsLeague(new WorldDetailLeague(27,24, "South Africa"));
//		insertWorldDetailsLeague(new WorldDetailLeague(28,19, "Uruguay"));
//		insertWorldDetailsLeague(new WorldDetailLeague(29,20, "Venezuela"));
//		insertWorldDetailsLeague(new WorldDetailLeague(30,29, "South Korea"));
//		insertWorldDetailsLeague(new WorldDetailLeague(0, ""));
//		}
}
