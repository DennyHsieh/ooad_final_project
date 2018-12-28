
package team.t4.booking.db;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import team.t4.booking.db.SearchException.TypeOfSearch;
/**
 * Provide methods for system to manipulate data in database,
 * and common fashion of data processing.
 * @author LiangChiaLun
 */
public class dbManager {

	/**
	 * Delete the data base with specified date.
	 * @param date Specified dataBase
	 */
	public static void deleteDataBase(String date) {
		String projectPath = System.getProperty("user.dir");
				File file = new File(projectPath + "/"+date+".db");
				if(file.delete())
					System.out.println(file.getName() + " is deleted!");
				else
					System.out.println("Delete operation is failed.");
	}
	
	/**
	 * Create a bitset with a specified length with a 1-bitset surrounded by 0-bitsets.
	 * @param length The specified length of the bitset.
	 * @param end1 One of the ends of the range of the 1-bit-group.
	 * @param end2 The other ends of the range of the 1-bit-group.
	 * @return The created bitset.
	 */
	private static String bitCreate(int length, int end1, int end2) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = '0';
		}
		for(int j = (end1<end2)?end1:end2 ; j<=((end1>end2)?end1:end2) ; j++) {
			output[j]='1';
		}
		return new String(output);
	}
	/**
	 * Create a 0-bitset with specified length.
	 * @param length Specified length of the set.
	 * @return The created bitset.
	 */
	private static String bitCreate(int length) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = '0';
		}
		return new String(output);
	}
	/**
	 * Implement Substract operation for two bitset with specified length.
	 * @param length Specified length of the sets.
	 * @param bit1 Bitset minuend.
	 * @param bit2 Bitset subtrahend.
	 * @return Result of substract operation.
	 */
	private static String bitSubtract(int length,String bit1,String bit2) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = Integer.toString(Integer.parseInt(String.valueOf(bit1.charAt(i)))-Integer.parseInt(String.valueOf(bit2.charAt(i)))).charAt(0);
		}
		return new String(output);
	}
	/**
	 * Implement Add operation for two bitset with specified length.
	 * @param length Specified length of the sets.
	 * @param bit1 One of the Bitsets of operation.
	 * @param bit2 The other Bitset of operation.
	 * @return Result of Add operation.
	 */
	private static String bitAdd(int length,String bit1,String bit2) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = Integer.toString((Integer.parseInt(String.valueOf(bit1.charAt(i)))+Integer.parseInt(String.valueOf(bit2.charAt(i))))).charAt(0);
		}
		return new String(output);
	}
	/**
	 * Implement AND operation for two bitset with specified length.
	 * @param length Specified length of the sets.
	 * @param bit1 One of the Bitsets of operation.
	 * @param bit2 The other Bitset of operation.
	 * @return Result of OR operation.
	 */
	private static String bitAND(int length,String bit1,String bit2) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = (bit1.charAt(i)>='1'&& bit2.charAt(i)>='1')?'1':'0';
		}
		return new String(output);
	}
	/**
	 * Create a bitset with specified length, and with value 1 assaigned to selected positions,
	 * and 0 assigned to others. 
	 * @param length Specified length of the bitset.
	 * @param positionOne Selected positions for 1-bit.
	 * @return The created bitset.
	 */
	private static String bitOneCreate(int length, int... positionOne ) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = '0';
		}
		for(int j=0; j<positionOne.length ; j++) {
			output[positionOne[j]]='1';
		}
		return new String(output);
	}
	/**
	 * This enum grasps all the station name to which a unique value is assigned into a group.
	 */
	public enum Station {
		南港(1),台北(2),板橋(3),桃園(4),新竹(5),苗栗(6),
		台中(7),彰化(8),雲林(9),嘉義(10),台南(11),左營(12);
		private int value;
		private Station(int value) {
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}
		
	}
	
	/**
	 * Save a complete transaction into the database.
	 * @param UID User identification.
	 * @param trip To mark whether the trip is outbound or inbound.
	 * @param date The date of the trip.
	 * @param departTime The depart time of the trip.
	 * @param arriveTime The arrive time of the trip.
	 * @param duration The duration of the trip.
	 * @param trainNo The train number of the train which clients take.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param price The total price of the tickets for the trip.
	 * @param seat The seat place for clients.
	 * @param numOfNormal The number of normaltickets.
	 * @param numOfCollege The number of collegetickets.
	 * @param numOfChildren The number of childrentickets.
	 * @param numOfSenior The number of seniortickets.
	 * @param numOfChallenged The number of challengedtickets.
	 * @param reservationNo The reservation number, which is a random 8-digits number.
	 * @throws Exception 
	 */
	public static void saveTransaction (String UID, String trip,String date, String departTime, String arriveTime,
				String duration, String trainNo, Station depart, Station destination, int price, String seat, String earlyConcession,
				int numOfNormal,int numOfCollege, int numOfChildren, int numOfSenior, int numOfChallenged, int reservationNo) throws Exception {
		String sql = "INSERT INTO Transact "+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try(Connection conn = connect("Ticket");
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString (1,UID);
			pstmt.setString (2,date);
			pstmt.setString (3,trip);
			pstmt.setString (4,departTime);
			pstmt.setString (5,arriveTime);
			pstmt.setString (6,duration);
			pstmt.setString (7,trainNo);
			pstmt.setString (8,depart.toString());
			pstmt.setString (9,destination.toString());
			pstmt.setInt (10,price);
			pstmt.setString (11,seat);
			pstmt.setString (12,"普通車廂");
			pstmt.setInt (13,numOfNormal);
			pstmt.setInt (14,numOfCollege);
			pstmt.setInt (15,numOfChildren);
			pstmt.setInt (16,numOfSenior);
			pstmt.setInt (17,numOfChallenged);
			double ran=0;
			while(ran<0.1) {
				ran = Math.random();
			}
			pstmt.setInt (18,reservationNo);
			pstmt.setString (19,"未付款（付款期限：發車前30分鐘)");
			pstmt.setString (20,earlyConcession);
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception("資料儲存失敗");
		}
	}
	/**
	 * Print success message when the reservation succeeds.
	 */
	public static void printSuccess(String UID, String trip,String date, String departTime, String arriveTime,
			String duration, String trainNo, Station depart, Station destination, int price, String earlyConcession,
			int numOfNormal,int numOfCollege, int numOfChildren, int numOfSenior, int numOfChallenged, int reservationNo) {
		System.out.println(trip+": ");
		System.out.println("從 "+depart.toString()+" 到 "+destination.toString());
		System.out.println("出發日期： "+date);
		System.out.println("出發時間： "+departTime);
		System.out.println("抵達時間： "+arriveTime);
		System.out.println("總時長：   "+duration);
		System.out.println("車次：     "+trainNo); 
		System.out.println("一般票"+numOfNormal+"張, "+"大學生票"+numOfCollege+"張, "+"兒童票"+numOfChildren+"張, "+"敬老票"+numOfSenior+"張, "+"愛心票"+numOfChallenged+"張。");	
	}
	/**
	 * Create a reservation number, which is a random 8-digits number.
	 * @return Reservation number.
	 */
	public static int getReservationNo() {
		double ran=0;
		while(ran<0.1) {
			ran = Math.random();
		}
		return (int)(ran*(Math.pow(10, 8))+1);
	}
	/**
	 * Get the string type of the seat information in Chinese.
	 * @param seatInfo
	 * @return Seat information in Chinese.
	 */
	public static String seatToString(String seatInfo[][]) {
		String buffer="";
		for(int i=0 ; i<seatInfo.length ; i++) {
			buffer += (seatInfo[i][0].substring(1,2)+"車"+(seatInfo[i][0].substring(2,3).equals("0")?"":"1")+(seatInfo[i][0].substring(3))+seatInfo[i][1]+(i!=seatInfo.length-1?",":""));
		}
		return buffer;
	}
	/**
	 * Save the seatTable temporarily.
	 * @param date The date of the trip.
	 * @param trainNo Train number.
	 * @throws Exception 
	 */
	public static void saveSeatTable(String date, String trainNo) throws Exception{
		String sql = "CREATE TABLE IF NOT EXISTS tempSeat (" + 
				" No text PRIMARY KEY, A text, B text, C text ,D text ,E text)";
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement()){
			stm.execute(sql);
			sql = "INSERT INTO tempSeat SELECT * FROM Train"+trainNo+date;
			stm.execute(sql);
		}catch (Exception e) {
			throw new TempSaveException("系統暫存車位資料庫檔案失敗:"+e.getMessage());
		}	
	}
	/**
	 * Recover the seat table to the last statement.
	 * @param date The date of the trip.
	 * @param trainNo The train number.
	 * @throws Exception 
	 */
	public static void recoverSeatTable(String date, String trainNo) throws Exception{
		String sql  = "DROP TABLE Train"+trainNo+date;
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement()){
			stm.execute(sql);
			sql = "ALTER TABLE tempSeat RENAME TO Train"+trainNo+date;
			stm.execute(sql);
		} catch (Exception e) {
			throw new TempSaveException("系統恢復車位資料庫檔案失敗:"+e.getMessage());
		}
	}
	/**
	 * Delete the temporarily saved seatTable.
	 * @param date The date of the trip.
	 * @param trainNo The train number.
	 * @throws Exception
	 */
	public static void dropSeatTable(String date) throws Exception{
		String sql  = "DROP TABLE tempSeat";
		try(Connection conn = connect(date);
				Statement stm = conn.createStatement()){
				stm.execute(sql);
		} catch (Exception e) {
			throw new TempSaveException("系統刪除暫存車位資料庫檔案失敗:"+e.getMessage());
		}
	}
	
	/**
	 * Occupy the seats with specified seat information.
	 * @param seatInfo The seat information.
	 * @param trainNo The train number.
	 * @param date The date of the trip.
	 * @throws Exception
	 */
	public static void occupy (String[] seatInfo,String trainNo,String date) throws Exception {
		// In the form : seatNo/seatCharc/usage.
		// e.g 0103/A/000011111100
		String seatNo = seatInfo[0];
		String seatCharc = seatInfo[1];
		String usage = seatInfo[2];
		String sql = "SELECT "+seatCharc+" FROM "+"Train"+trainNo+date +" WHERE No = '"+seatNo+"';";
		
		try (Connection conn = connect(date);
			 Statement stm = conn.createStatement();
			 ResultSet rs = stm.executeQuery(sql)) {
			String currentUse = rs.getString(1);
			String sql2 = "UPDATE Train"+trainNo+date+" SET "+seatCharc+" = '"+bitAdd(12,usage,currentUse)+"' WHERE No = '"+seatNo+"';";
			stm.execute(sql2);
		} catch(Exception e) {
			throw new Exception("系統劃位發生錯誤");
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
	public static void sellEarly(String date, String direction, String trainNo, String earlyConcession,int ticketNum) throws Exception{
		
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
	 * Save the timetable information temporarily.
	 * @param date The date of the trip.
	 * @param direction The rail direction of the trip.
	 * @throws Exception
	 */
	public static void saveTimeTable(String date, String direction) throws Exception{
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
		try(Connection conn = connect(date);
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
	public static void recoverTimeTable(String date, String direction) throws Exception {
		
		String sql  = "DROP TABLE "+direction+date;
		try(Connection conn = connect(date);
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
	public static void dropTimeTable(String date) throws Exception {
		
		String sql  = "DROP TABLE tempTime";
		try(Connection conn = connect(date);
				Statement stm = conn.createStatement()){
				stm.execute(sql);
		} catch (Exception e) {
			throw new TempSaveException("系統刪除暫存時刻表資料庫檔案失敗:"+e.getMessage());
		}
	}
	/**
	 * Check if there are seats available in the train in a particular condition.
	 * @param date The date of the trip.
	 * @param trainNo The train number.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param num The number of clients.
	 * @param condition The special condition of the seat requirement.
	 * @return Whether there are seats available.
	 * @throws Exception
	 */
	private static boolean checkConditionSeatAvail(String date, String trainNo,Station depart, Station destination, int num, String condition) throws Exception{
		int availNum=0;		
		
		String sql = "SELECT * FROM Train"+trainNo+date+";";		
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql)) {		
			
				while(rs.next()) {
					
					if(rs.getString(1).substring(0,2).equals("06"))
						continue;
					
					for(int i=0 ; i<5 ; i++) {
						
						if(condition=="aisle")
							if(i==0 || i==1 || i==4) continue;
						
						if(condition=="window")
							if(i==1 || i==2 || i==3) continue;
						
						String usage = rs.getString(i+2);
						//Check if the seat is available.
						if(checkSingleSeat(depart,destination,usage) == true) {
							availNum+=1;
							if(availNum==num) 
								return true;
						}
					}	
				}
				return false;
		}catch (Exception e) {
			throw new Exception("系統查詢位置失敗");
		}
	}
	/**
	 * Get the available seat information in a particular condition.
	 * @param date The date of the trip.
	 * @param trainNo The train number.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param num The number of the clients.
	 * @param condition The particular condition
	 * @return The seat information in a compression form.
	 */
	public static String[][] getConditionSeatInfo(String date, String trainNo, Station depart, Station destination, int num, String condition) throws Exception{
		String seatInfo[][] = new String[num][3]; // In the form : seatInfo[x] = { seatNo,seatCharc,usage }		
		String sql = "SELECT * FROM Train"+trainNo+date+";";		
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql)) {
				int count=0;
				while(rs.next()) {
					if(rs.getString(1).substring(0,2).equals("06"))
						continue;
					for(int i=0 ; i<5 ; i++) {
					if(condition.equals("aisle")) {
						if(i==0 || i==1 || i==4) continue;
					}
					else if(condition.equals("window")) {
						if(i==1 || i==2 || i==3) continue;
					}
						String usage = rs.getString(i+2);
						//Check if the seat is available.
						if(checkSingleSeat(depart,destination,usage) == true) {
							seatInfo[count] = new String[] {rs.getString(1),rs.getMetaData().getColumnName(i+2),bitCreate(12,depart.getValue()-1,destination.getValue()-1)};
							count++;
							if(count==num) 
								return seatInfo;
						}
					}	
				}	
			return null;
		}catch (Exception e) {
			throw new Exception("系統取得座位資訊失敗");
		}
	}
	
	/**
	 * Check whether the specified seat is suitable with the requirement.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param usage The current using statement of the seat.
	 * @return Whether the seat is suitable.
	 */
	private static boolean checkSingleSeat(Station depart, Station destination, String usage) {
		int[] endpair = new int[] {depart.getValue()-1,destination.getValue()-1};
		String user = bitCreate(12,endpair[0],endpair[1]);
		if(usage.equals("0"))
			return false;
		String check = bitAND(12, user, usage);
		if(check.equals(bitCreate(12)) || 
			check.equals(bitOneCreate(12,endpair[0])) || 
			check.equals(bitOneCreate(12,endpair[1]))) {
			return true;
		}
		if(!check.equals(user) && check.equals(bitOneCreate(12,endpair[0],endpair[1]))){
			return true;
		}
		return false;
	}
	/**
	 * Get the rail direction of the departure and destination.
	 * @param depart
	 * @param destination
	 * @return The rail direction.
	 */
	public static String getDirection (Station depart, Station destination) {
		if(destination.getValue()-depart.getValue()<=0) {
			return "North";
		}
		else {
			return "South";
		}
	}
	/**
	 * This enum grasps all the concession plans with values which are
	 * names of tables in the database into a group.
	 */
	public enum ConcessionPlan {
		大學85("College85Price"),
		大學7("College7Price"),
		大學5("College5Price"),
		優惠("ConcessionPrice"),
		早鳥9("Early9Price"),
		早鳥8("Early8Price"),
		早鳥65("Early65Price"),
		無優惠("NormalPrice");
		
		public String getValue() {
			return new String(this.value);
		}
		private ConcessionPlan(String value) {
			this.value = value;
		}		
		private String value;
	}
	/**
	 * Get the total price for a special kind of tickets.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param concession The concession plan for the tickets.
	 * @param num The number of the tickets of the kind.
	 * @return Total price of the tickets of the kind.
	 */
	public static int priceCalculate(Station depart, Station destination, ConcessionPlan concession, int num) throws Exception {
		String sql;
		if(depart.getValue()<destination.getValue())
			sql = "SELECT "+depart+" FROM "+concession.getValue()+" WHERE 車站 = '"+destination+"';";
		else
			sql = "SELECT "+destination+" FROM "+concession.getValue()+" WHERE 車站 = '"+depart+"';";
		try(Connection conn = connect("Ticket");
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);) {
			rs.next();
			return rs.getInt(1)*num;	
		} catch(Exception e) {
			throw new Exception("系統查詢票價發生錯誤");
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
	private static ConcessionPlan getEarlyPlan(String date, String trainNo, String direction, int numOfNormal) throws Exception {
		String sql = "SELECT 早鳥65,早鳥8,早鳥9 from "+direction+date+" WHERE 車次 = '"+trainNo+"';";
		try(Connection conn = connect(date);
				Statement stmt = conn.createStatement();
				ResultSet rs =stmt.executeQuery(sql); ) {
		return rs.getInt(1)>=numOfNormal?ConcessionPlan.早鳥65:rs.getInt(2)>=numOfNormal?ConcessionPlan.早鳥8:rs.getInt(3)>=numOfNormal?ConcessionPlan.早鳥9:null;
		} catch (Exception e) {
			throw new Exception("早鳥資訊查詢失敗");
		}
	}
	/**
	 * Return the train duration in specific string format .
	 * @param dptime Specified departure time.
	 * @param arrtime Specified arrival time.
	 * @return train duration.
	 */
	private static String getDuration(String dptime, String arrtime) {
		if(dptime.substring(3).compareTo(arrtime.substring(3))<0) {
			 return (Integer.parseInt(arrtime.substring(0,2))-Integer.parseInt(dptime.substring(0,2)))
					+":"+String.valueOf((Integer.parseInt(arrtime.substring(3))-Integer.parseInt(dptime.substring(3)))+100).substring(1);
		}
		else {
			return  (Integer.parseInt(arrtime.substring(0,2))-Integer.parseInt(dptime.substring(0,2))-1)
					+":"+String.valueOf(((Integer.parseInt(arrtime.substring(3))+60)-Integer.parseInt(dptime.substring(3)))+100).substring(1);
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
	/**
	 * Get a transaction record string array from database with specified 
	 * UID and reservation number. 
	 * @param UID User id.
	 * @param reservationNo The reservation number of the transaction.
	 * @return Transaction records of String array.
	 * @throws Exception if the UID of reservation number is not found, throw an exception.
	 */
	public static String[][] getTransactResult(String UID, String reservationNo) throws Exception {
		
		String[][] buffer = new String[2][20];
		String sql = "SELECT * FROM 'Transact' WHERE UID = '"+UID+"';";
		try (Connection conn = connect("Ticket");
			 Statement stm = conn.createStatement();
			 ResultSet rs =stm.executeQuery(sql)){
			if (rs.next()) {
				int numOfRow = 0;
				do {
					if (rs.getString(18).equals(reservationNo)) {
						for (int numOfCol = 0 ; numOfCol<20 ; numOfCol++) {
							buffer[numOfRow][numOfCol] = rs.getString(numOfCol+1);
						}
						numOfRow++;					
					}
				} while(rs.next() && numOfRow < 2);
				if (buffer[0][0]!=null)
					return buffer;
				else
					throw new SearchException(SearchException.TypeOfSearch.ResvNowrong);
			} else {
				throw new SearchException(SearchException.TypeOfSearch.UIDwrong);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * Get a transaction record without reservation number, instead, search the record by
	 * the departure,the  destination and the date of the trip. 
	 * @param UID User id.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param date The date of the trip.
	 * @return Transaction records of String array.
	 * @throws Exception if the record with corresponding data does not exists, throw an Exception.
	 */
	public static String[][] getTransactResultWithoutRN(String UID, Station depart, Station destination, String date, String trainNo) throws Exception {

		String[][] buffer = new String[10][20];
		String sql = "SELECT * FROM 'Transact' WHERE UID = '"+UID+
					"' AND depart = '"+depart.toString()+
					"' AND destination = '"+destination.toString()+
					"' AND trainNo = '"+trainNo+
					"' AND date = '"+date+"';";
		try (Connection conn = connect("Ticket");
			 Statement stm = conn.createStatement();
			 ResultSet rs =stm.executeQuery(sql)){
			int numOfRow = 0;
			if (rs.next()) {
				do {
				for (int numOfCol = 0 ; numOfCol<20 ; numOfCol++) {
						buffer[numOfRow][numOfCol] = rs.getString(numOfCol+1);
				}
				numOfRow ++;
				} while (rs.next() && numOfRow < 10);
				return buffer;
			} else {
				throw new SearchException(SearchException.TypeOfSearch.NotFound);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Modify ticket number of the transaction data in the database.
	 * @param reservationNo The reservation number of the transaction.
	 * @param refundAmount The ticket amount to be decreased.
	 * @param ticketType The decreased ticket type.
	 * @param trip Specifying whether the transaction is of returning tickets.
	 * @throws Exception 
	 */
	public static void modifyTicketNumber(String reservationNo, int refundAmount, String ticketType, String trip) throws Exception{
		String sql = "UPDATE transact SET "+ticketType+" = "+ticketType+" - "+refundAmount+
					" WHERE reservationNo = '"+reservationNo+
					"' AND trip = '"+trip+"';";
		try(Connection conn = connect("Ticket");
			Statement stm = conn.createStatement();) {
			stm.execute(sql);
		} catch( Exception e) {
			throw new Exception("系統更改交易資料時發生錯誤");
		}
	}
	
	/**
	 * Modify the total price of the transaction.
	 * @param reservationNo The transaction reservation number.
	 * @param trip Whether the transaction is of returning tickets.
	 * @param date The date of the trip.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param trainNo The train number of the trip.
	 * @param type The type of the tickets relative to the modified price.
	 * @param num The decreasing number of the relative tickets.
	 * @param earlyConcession The early concession of transaction.
	 * @throws Exception
	 */
	public static void modifyPrice(String reservationNo, String trip, 
			String date, Station depart, Station destination, String trainNo,
			 String type, int num, String earlyConcession) throws Exception {
		
		ConcessionPlan concession = type == "numOfNormal"?earlyConcession==null?ConcessionPlan.無優惠:ConcessionPlan.valueOf(earlyConcession):
							    type == "numOfCollege"?concession = getCollegePlan(date, trainNo, getDirection(depart,destination)):ConcessionPlan.優惠; 	
		int minusPrice = priceCalculate(depart, destination, concession, num);
		String sql = "UPDATE transact SET price = price - "+minusPrice+
				" WHERE reservationNo = "+reservationNo+";"+
				"' AND trip = '"+trip+"';";
		try(Connection conn = connect("Ticket");
				Statement stm = conn.createStatement();) {
				stm.execute(sql);
			} catch( Exception e) {
				throw new Exception("系統更改交易資料時發生錯誤");
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
	private static ConcessionPlan getCollegePlan(String date, String trainNo, String direction) throws Exception {
			String sql = "SELECT 大學 from "+direction+date+" WHERE 車次 = '"+trainNo+"';";
			try(Connection conn = connect(date);
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
	 * Delete the seat from the train of specified date.
	 * @param seatInfo The seat information of the seat.
	 * @param trainNo The train number of the train.
	 * @param date The date of the trip.
	 * @throws Exception
	 */
	public static void deleteSeat(String[] seatInfo, String trainNo, String date) throws Exception {
		String seatNo = seatInfo[0];
		String seatCharacter = seatInfo[1];
		String usage = seatInfo[2];
		String currentUse = "";
		String sql = "SELECT "+seatCharacter+" FROM Train"+trainNo+date+" WHERE No = '"+seatNo+"';";
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement();
			ResultSet rst = stm.executeQuery(sql)){
			currentUse = rst.getString(1);
		} catch( Exception e) {
			throw new Exception("系統刪除座位時發生錯誤");
		}
		String newUse = bitSubtract(12, currentUse, usage);
		sql = "UPDATE Train"+trainNo+date+" SET "+seatCharacter+"='"+newUse+"' WHERE No = '"+seatNo+"';";
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement()){
			stm.execute(sql);
		} catch( Exception e) {
				throw new Exception("系統刪除座位時發生錯誤");
		}
	};
	
	/**
	 * Release tickets quota of the early ticket.
	 * @param date The date of the ticket bought.
	 * @param direction The direction of the trip.
	 * @param trainNo The train number of the trip.
	 * @param earlyConcession The concession plan of the ticket.
	 * @param ticketNum The number of the ticket to be released.
	 * @throws Exception
	 */
	public static void returnEarly(String date, String direction, String trainNo, String earlyConcession,int ticketNum) throws Exception{
		if(earlyConcession == null)
			return;
		String sql = "UPDATE "+direction+date+" SET "+earlyConcession+" = "+earlyConcession+" + "+ticketNum+" WHERE 車次 = '"+trainNo+"';";
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement();) {
			stm.execute(sql);
			} catch (Exception e) {
				throw new Exception("系統回收早鳥優惠售票失敗");
		}
	}
	
	/**
	 * Modify the seat information of the transaction in the data base.
	 * @param refundAmount The decreasing amount of tickets.
	 * @param originSeats The original seat information of the seats.
	 * @param reservationNo The reservation number of the transaction.
	 * @param trip Whether the transaction is of returning tickets.
	 * @throws Exception
	 */
	public static void modifySeatPurchase (int refundAmount, String originSeats, String reservationNo, String trip) throws Exception{
		String newSeats = originSeats;
		for(int count = 0 ; count<refundAmount ; count++) {
			newSeats = newSeats.substring(originSeats.indexOf(",")+1);
		}
		String sql = "UPDATE Transact SET seat = '"+newSeats+"' WHERE  reservationNo = '"+reservationNo+"' AND trip = '"+trip+"';";
		try(Connection conn = connect("Ticket");
				Statement stm = conn.createStatement();) {
				stm.execute(sql);
			} catch( Exception e) {
				throw new Exception("系統更改交易資料時發生錯誤");
		}	
	}
	
	/**
	 * To delete the whole record of the transaction.
	 * @param reservationNo The reservation number of the transaction.
	 * @param trip Whether the transaction is of returning tickets.
	 * @throws Exception
	 */
	public static void deleteRecord(String reservationNo, String trip) throws Exception{
		String sql = "DELETE FROM Transact WHERE reservationNo = '"+reservationNo+"' AND trip = '"+trip+"';";
		try(Connection conn = connect("Ticket");
				Statement stm = conn.createStatement();) {
				stm.execute(sql);
			} catch( Exception e) {
				throw new Exception("系統刪除交易資料時發生錯誤");
		}	
	}
	
	/**
	 * To convert seat information in chinese to data that
	 * system is easy to utilized.
	 * @param seatInChinese Seat information in chainese.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @return Converted seat information in string type.
	 */
	public static String[] reacquireSeatInfo(String seatInChinese, Station depart, Station destination) {
		String rowNum;
		if (Integer.parseInt(seatInChinese.substring(seatInChinese.indexOf("車")+1,seatInChinese.length()-1))>10) {
			rowNum = seatInChinese.substring(seatInChinese.indexOf("車")+1,seatInChinese.length()-1);
		}else { 
			rowNum = "0"+seatInChinese.substring(seatInChinese.indexOf("車")+1,seatInChinese.length()-1);
		}
		return new String[] { "0"+seatInChinese.substring(0,seatInChinese.indexOf("車"))+rowNum, 
			   seatInChinese.substring(seatInChinese.length()-1),
			   bitCreate(12, depart.getValue() - 1, destination.getValue() - 1)};
	}
	
/**
 * Get the time table in south direction of assigned date.	
 * @param date The date of the timetable.
 * @return String of timetable
 */
	public static String[][] getSouthTimeTable(String date) {
		
		String sql = "SELECT * FROM  South"+date+" ORDER BY 南港;";
		try(Connection conn = connect(date);
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
public static String[][] getNorthTimeTable(String date) {
		
		String sql = "SELECT * FROM  North"+date+" ORDER BY 左營;";
		try(Connection conn = connect(date);
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
 * Get concession train table with specified data.
 * @param date The date of the trip.
 * @param time Condition time.
 * @param timeCondition The condition for searching,
 * to determine whether arriving time or departing time is emphasized.
 * @param depart The departure.
 * @param destination The arrival.
 * @return String of concession train table.
 * @throws Exception
 */
public static String[][] getConcessionTable(String date, String time, String timeCondition, Station depart, Station destination) throws Exception{
	
	String direction = getDirection(depart,destination);
	String[][] train =new String[10][9];
	
	int count=0;
	String sql;
	if(timeCondition == "depart") {
		sql = "SELECT 車次,大學,早鳥65,早鳥8,早鳥9,"+depart+","+destination+
				" from "+direction+date+" ORDER BY "+depart+";";
	} else {
		sql = "SELECT 車次,大學,早鳥65,早鳥8,早鳥9,"+depart+","+destination+
				" from "+direction+date+" ORDER BY "+destination+";";
	}
	
	try(Connection conn = connect(date);
			Statement stmt = conn.createStatement();
			ResultSet rs =stmt.executeQuery(sql); ) {
		//Find the available trainNumber.
		while(rs.next() && count<10) {	
			//Check if the selected train is available for at least one position.
			if(checkConditionSeatAvail(date,rs.getString(1),depart,destination,1,"")==false) 
				continue;
			String dptime = rs.getString(6);
			String arrtime = rs.getString(7);
			String duration;
			ConcessionPlan concession = getEarlyPlan(date,rs.getString(1),direction,1);
			//If user selects time for departure, then the train which departing time is later will be chosen;
			//If user selects time for arrival, then the train which arriving time is earlier will be chosen;
			boolean condition = (timeCondition.equals("depart"))? 
								dptime.compareTo(time)>=0 && !arrtime.equals("") && !dptime.equals(""): 
								arrtime.compareTo(time)<=0 && !dptime.equals("") && !arrtime.equals("");
			boolean haveConcession = (rs.getDouble(2)!=1 && concession.toString()!="無優惠");
			if(condition && haveConcession) {	
				duration = getDuration(dptime,arrtime);
				train[count++] = new String[] {rs.getString(1),(rs.getDouble(2)==1.0?null:rs.getDouble(2)==0.5?"大學5":rs.getDouble(2)==0.7?"大學7":"大學85"),(concession!=null?concession.toString():null),date.substring(4),depart.toString(),destination.toString(),dptime,arrtime,duration};
			}
		}
	} catch(Exception e) {
		throw new Exception("查詢車次時發生錯誤："+e.getMessage());
	}	
	return train;
}
	
}
