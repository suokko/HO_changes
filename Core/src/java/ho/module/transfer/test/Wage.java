package ho.module.transfer.test;

import ho.core.db.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Wage {

	private int age;
	private int wage;

	public Wage() {
	}

	public Wage(int age, int wage) {
		this.age = age;
		this.wage = wage;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWage() {
		return wage;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

	public static List<Wage> getWages(int playerID) {
		List<Wage> wages = new ArrayList<Wage>();

		String query = "SELECT age, gehalt FROM Spieler WHERE spielerid=" + playerID
				+ " GROUP BY age, gehalt";
		ResultSet rs = DBManager.instance().getAdapter().executeQuery(query);
		try {
			while (rs.next()) {
				wages.add(new Wage(rs.getInt("age"), rs.getInt("gehalt") / 10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wages;
	}

}
