package team.t4.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import team.t4.booking.tk.Station;
import team.t4.booking.tk.ConcessionPlan;

public class Price {
	
	/**
	 * Get the total price for a special kind of tickets.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param concessionPlan The concession plan for the tickets.
	 * @param num The number of the tickets of the kind.
	 * @return Total price of the tickets of the kind.
	 */
	public static int priceCalculate(Station depart, Station destination, ConcessionPlan concessionPlan, int num) throws Exception {
		String sql;
		if(depart.getValue()<destination.getValue())
			sql = "SELECT "+depart+" FROM "+concessionPlan.getValue()+" WHERE 車站 = '"+destination+"';";
		else
			sql = "SELECT "+destination+" FROM "+concessionPlan.getValue()+" WHERE 車站 = '"+depart+"';";
		try(Connection conn = connect("Ticket");
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);) {
			rs.next();
			System.out.println("price line 31 :\n\n"+sql+" and the number is "+num);
			return rs.getInt(1)*num;	
		} catch(Exception e) {
			throw new Exception("系統查詢票價發生錯誤");
		}
	}
	/**
	 * Connect to the database with specified name.
	 * @param dbName The name of the database.
	 * @return The connection object to the database.
	 * @throws Exception
	 */
	private static Connection connect(String dbName) throws Exception {
		String url = "jdbc:sqlite:"+dbName+".db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new Exception("連接資料庫失敗");
		}
		return conn;
	}
}
