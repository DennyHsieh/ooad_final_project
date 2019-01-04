package team.t4.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import team.t4.booking.tk.Station;
import team.t4.booking.tk.ConcessionPlan;

public class Schedule {
		
	
	public String[][] getSortedTimeTable(Station depart, Station destination, String direction, String date) throws Exception {
		String sql = "SELECT 車次,大學,早鳥65,早鳥8,早鳥9,"+depart+","+destination+
					" from "+direction+date+" ORDER BY "+depart+";";
		try(Connection conn = connect(date);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql); ) {
			
			String[][] buffer = new String[200][7];
			
			int row = 0;
			while(rs.next()) {
				for (int col = 0 ; col < 7 ; col++) {
					buffer[row][col] = rs.getString(col+1);
				}
				row++;
			} 
			return buffer;
		} catch(Exception e) {
			throw new Exception("查詢車次時發生錯誤："+e.getMessage());
		}
	}
	
	public String[][] getSortedTimeTableDest(Station depart, Station destination, String direction, String date) throws Exception {
		String sql = "SELECT 車次,大學,早鳥65,早鳥8,早鳥9,"+depart+","+destination+
					" from "+direction+date+" ORDER BY "+destination+";";
		try(Connection conn = connect(date);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql); ) {
			
			String[][] buffer = new String[200][7];
			
			int row = 0;
			int col = 0;
			
			while(rs.next()) {
				col = 0;
				while(col < 7) {
					buffer[row][col] = rs.getString(col+1);
					col++;
				}
				row++;
			} 
			return buffer;
		} catch(Exception e) {
			throw new Exception("查詢車次時發生錯誤："+e.getMessage());
		}
	}
		
	
	

	/**
	 * Get the time table in south direction of assigned date.	
	 * @param date The date of the timetable.
	 * @return String of timetable
	 */
		public String[][] getSouthTimeTable(String date) {
			
			String sql = "SELECT * FROM  South"+date+" ORDER BY 南港;";
			try(Connection conn = this.connect(date);
				Statement stm = conn.createStatement();
				ResultSet rst = stm.executeQuery(sql);) {
				
				String[][] buffer = new String[200][13];
				int row = 0;
				int col = 0;
				
				while(rst.next()) {
					buffer[row][0] = rst.getString(1);
					for (col=1 ; col<13 ; col++) {
						buffer[row][col] = rst.getString(col+5);
					}
					row++;
				} return buffer;
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
		
	/**
	 * Get the time table in north direction of assigned date.	
	 * @param date The date of the timetable.
	 * @return String of timetable
	 */
	public String[][] getNorthTimeTable(String date) {
			
			String sql = "SELECT * FROM  North"+date+" ORDER BY 左營;";
			try(Connection conn = this.connect(date);
				Statement stm = conn.createStatement();
				ResultSet rst = stm.executeQuery(sql);) {
				
				String[][] buffer = new String[200][13];
				int row = 0;
				int col = 0;
				
				while(rst.next()) {
					buffer[row][0] = rst.getString(1);
					for (col=1 ; col<13 ; col++) {
						buffer[row][col] = rst.getString(col+5);
					}
					row++;
				} return buffer;
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
	
	/**
	 * Connect to the database with specified name.
	 * @param dbName The name of the database.
	 * @return The connection object to the database.
	 * @throws Exception
	 */
	private Connection connect(String dbName) throws Exception {
		String url = "jdbc:sqlite:"+dbName+".db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new Exception("連接資料庫失敗");
		}
		return conn;
	}


/**
 * Save the timetable information temporarily.
 * @param date The date of the trip.
 * @param direction The rail direction of the trip.
 * @throws Exception
 */
public void saveTimeTable(String date, Station depart, Station destination) throws Exception{
	String direction = this.getDirection(depart, destination);
	String sql="";
	if (direction.equals("North"))
	    sql = "CREATE TABLE IF NOT EXISTS tempTime ("
			+ "車次 text ,"+ "大學 real,"+ "早鳥65 int,"+ "早鳥8 int,"+ "早鳥9 int,"
			+ "左營 text,"+ "台南 text,"+ "嘉義 text,"+ "雲林 text,"+ "彰化 text,"
			+ "台中 text,"+ "苗栗 text,"+ "新竹 text,"+ "桃園 text,"+ "板橋 text,"
			+ "台北 text,"	+ "南港 text);";
	else
		sql = "CREATE TABLE IF NOT EXISTS tempTime ("
			+ "車次 text ,大學 real,早鳥65 int,早鳥8 int,早鳥9 int,"
			+ "南港 text,台北 text,板橋 text,桃園 text,新竹 text,"
			+ "苗栗 text,台中 text,彰化 text,雲林 text,嘉義 text,"
			+ "台南 text,左營 text);";
	try(Connection conn = this.connect(date);
			Statement stm = conn.createStatement()){
			stm.execute(sql);
			sql = "INSERT INTO tempTime SELECT * FROM "+direction+date;
			stm.execute(sql);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			throw new TempSaveException("系統暫存時刻表資料庫檔案失敗:"+e.getMessage());
		}	
}
/**
 * Recover the timetable to the last statement.
 * @param date The date of the trip.
 * @param direction The rail direction of the trip.
 * @throws Exception 
 */
public void recoverTimeTable(String date, Station depart, Station destination) throws Exception {
	
	String direction = this.getDirection(depart, destination);
	String sql  = "DROP TABLE "+direction+date;
	try(Connection conn = this.connect(date);
		Statement stm = conn.createStatement()){
		stm.execute(sql);
		sql = "ALTER TABLE tempTime RENAME TO "+direction+date;
		stm.execute(sql);
	} catch (Exception e) {
		throw new TempSaveException("系統恢復時刻表資料庫檔案失敗:"+e.getMessage());
	}
}
/**
 * Delete the temporarily saved timeTable.
 * @param date The date of the trip.
 * @throws Exception 
 */
public void dropTimeTable(String date) throws Exception {
	
	String sql  = "DROP TABLE tempTime";
	try(Connection conn = this.connect(date);
			Statement stm = conn.createStatement()){
			stm.execute(sql);
	} catch (Exception e) {
		throw new TempSaveException("系統刪除暫存時刻表資料庫檔案失敗:"+e.getMessage());
	}
}


/**
 * Decrease the amount of the tickets of early-concession.
 * @param date The date of the trip.
 * @param direction The rail direction of the trip.
 * @param trainNo The train number.
 * @param earlyConcession The plan for the early-concession.
 * @param ticketNum The number of the tickets to be sold.
 * @throws Exception
 */
public void sellEarly(String date, Station depart, Station destination, String trainNo, String earlyConcession,int ticketNum) throws Exception{
	String direction = this.getDirection(depart, destination);
	String sql = "SELECT "+earlyConcession+" FROM "+direction+date+" WHERE 車次 = '"+trainNo+"' ;";
	
	try(Connection conn = connect(date);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);) {
		rs.next();
		rs.getInt(1);	
		sql = "UPDATE "+direction+date+" SET "+earlyConcession+" = "+(rs.getInt(1)-ticketNum)+" WHERE 車次 = '"+trainNo+"';";
		stm.execute(sql);
		} catch (Exception e) {
			throw new Exception("系統早鳥優惠售票失敗"+e.getMessage());
	}
	
}

/**
 * Get the early-concession plan of specific condition.
 * @param date The date of the trip.
 * @param trainNo The train number.
 * @param direction The rail direction of the trip.
 * @param numOfNormal The number of normal ticket. 
 * @return The early-concession plan.
 * @throws Exception 
 */
public ConcessionPlan getEarlyPlan(String date, String trainNo, String direction, int numOfNormal) throws Exception {
	String sql = "SELECT 早鳥65,早鳥8,早鳥9 from "+direction+date+" WHERE 車次 = '"+trainNo+"';";
	try(Connection conn = connect(date);
			Statement stmt = conn.createStatement();
			ResultSet rs =stmt.executeQuery(sql); ) {
	return rs.getInt(1)>=numOfNormal?ConcessionPlan.早鳥65:rs.getInt(2)>=numOfNormal?ConcessionPlan.早鳥8:rs.getInt(3)>=numOfNormal?ConcessionPlan.早鳥9:ConcessionPlan.無優惠;
	} catch (Exception e) {
		throw new Exception("早鳥資訊查詢失敗");
	}
}

/**
 * To get the college concession with special data.
 * @param date The date of the trip.
 * @param trainNo The train number of the trip.
 * @param direction The direction of the trip.
 * @return The concession plan of the train.
 * @throws Exception
 */
ConcessionPlan getCollegePlan(String date, String trainNo, String direction) throws Exception {
		String sql = "SELECT 大學 from "+direction+date+" WHERE 車次 = '"+trainNo+"';";
		try(Connection conn = this.connect(date);
				Statement stmt = conn.createStatement();
				ResultSet rs =stmt.executeQuery(sql); ) {
		return rs.getDouble(1)==1?ConcessionPlan.無優惠:
			   rs.getDouble(1)==0.85?ConcessionPlan.大學85:
			   rs.getDouble(1)==0.7?ConcessionPlan.大學7:ConcessionPlan.大學5;
		} catch (Exception e) {
			throw new Exception("大學生優惠資訊查詢失敗");
		}
}

/**
 * Get the rail direction of the departure and destination.
 * @param depart
 * @param destination
 * @return The rail direction.
 */
public String getDirection (Station depart, Station destination) {
	if(destination.getValue()-depart.getValue()<=0) {
		return "North";
	}
	else {
		return "South";
	}
}

/**
 * Release tickets quota of the early ticket.
 * @param date The date of the ticket bought.
 * @param direction The direction of the trip.
 * @param trainNo The train number of the trip.
 * @param earlyConcession The concession plan of the ticket.
 * @param ticketNum The number of the ticket to be released.
 * @throws Exception
 */
public void returnEarly(String date, String direction, String trainNo, String earlyConcession,int ticketNum) throws Exception{
	if(earlyConcession == null)
		return;
	String sql = "UPDATE "+direction+date+" SET "+earlyConcession+" = "+earlyConcession+" + "+ticketNum+" WHERE 車次 = '"+trainNo+"';";
	System.out.println(sql);
	System.out.println("schedule306");
	try(Connection conn = this.connect(date);
		Statement stm = conn.createStatement();) {
		stm.execute(sql);
		} catch (Exception e) {
			System.out.println("error in schedule 311 exception!!"+e.getMessage());
			throw new Exception("系統回收早鳥優惠售票失敗");
	}
}

}


