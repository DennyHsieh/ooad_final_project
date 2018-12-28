package team.t4.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.t4.booking.db.dbManager.ConcessionPlan;
import team.t4.booking.db.dbManager.Station;

public class checkEmptySeat {
	
	/**
	 * To get the college concession with special data.
	 * @param date The date of the trip.
	 * @param trainNo The train number of the trip.
	 * @param direction The direction of the trip.
	 * @return The concession plan of the train.
	 * @throws Exception
	 */
	private ConcessionPlan getCollegePlan(String date, String trainNo, String direction) throws Exception {
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
	 * To get the information of available trains in particular condition.
	 * @param date The date of the trip.
	 * @param time The earliest time for departure.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param num The number of the clients.
	 * @param numOfNormal The number of normal tickets.
	 * @param condition Particular seat requirement.
	 * @return Information of available trains.
	 * @throws Exception
	 */
	public  String[][] getConditionTrainAvail(String date, String time, Station depart, Station destination, int num, int numOfNormal, String condition) throws Exception {
			String direction = getDirection(depart,destination);
			String[][] train =new String[10][9];
			int count=0;

			String sql = "SELECT 車次,大學,早鳥65,早鳥8,早鳥9,"+depart+","+destination+
						" from "+direction+date+" ORDER BY "+depart+";";
			try(Connection conn = connect(date);
					Statement stmt = conn.createStatement();
					ResultSet rs =stmt.executeQuery(sql); ) {
				//Find the available trainNumber.
				while(rs.next() && count<10) {	
					//Check if the selected train is available.
					if(checkConditionSeatAvail(date,rs.getString(1),depart,destination,num,condition)==false) 
						continue;
					
					String dptime = rs.getString(6);
					String arrtime = rs.getString(7);
					String duration;
					//If the train departs at the departure later than the specified time 
					//and will also arrive at the destination.
					if(dptime.compareTo(time)>=0 && !arrtime.equals("")) {	
						duration = getDuration(dptime,arrtime);
						ConcessionPlan concession = getEarlyPlan(date,rs.getString(1),direction,numOfNormal);
						train[count++] = new String[] {rs.getString(1),(rs.getDouble(2)==1.0?null:rs.getDouble(2)==0.5?"大學5":rs.getDouble(2)==0.7?"大學7":"大學85"),(concession!=null?concession.toString():null),date.substring(4),depart.toString(),destination.toString(),dptime,arrtime,duration};
				}
			}
		} catch(Exception e) {
			throw new Exception("查詢車次時發生錯誤："+e.getMessage());
		}	
		return train;
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
	private  ConcessionPlan getEarlyPlan(String date, String trainNo, String direction, int numOfNormal) throws Exception {
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
	private  String getDuration(String dptime, String arrtime) {
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
	private boolean checkConditionSeatAvail(String date, String trainNo,Station depart, Station destination, int num, String condition) throws Exception{
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
	public String[][] getConditionSeatInfo(String date, String trainNo, Station depart, Station destination, int num, String condition) throws Exception{
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
	private boolean checkSingleSeat(Station depart, Station destination, String usage) {
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
	public String getDirection (Station depart, Station destination) {
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
	 * Get the string type of the seat information in Chinese.
	 * @param seatInfo
	 * @return Seat information in Chinese.
	 */
	public String seatToString(String seatInfo[][]) {
		String buffer="";
		for(int i=0 ; i<seatInfo.length ; i++) {
			buffer += (seatInfo[i][0].substring(1,2)+"車"+(seatInfo[i][0].substring(2,3).equals("0")?"":"1")+(seatInfo[i][0].substring(3))+seatInfo[i][1]+(i!=seatInfo.length-1?",":""));
		}
		return buffer;
	}
	
	/**
	 * Create a bitset with a specified length with a 1-bitset surrounded by 0-bitsets.
	 * @param length The specified length of the bitset.
	 * @param end1 One of the ends of the range of the 1-bit-group.
	 * @param end2 The other ends of the range of the 1-bit-group.
	 * @return The created bitset.
	 */
	private String bitCreate(int length, int end1, int end2) {
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
	private String bitCreate(int length) {
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
	private String bitSubtract(int length,String bit1,String bit2) {
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
	private String bitAdd(int length,String bit1,String bit2) {
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
	private String bitAND(int length,String bit1,String bit2) {
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
	private String bitOneCreate(int length, int... positionOne ) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = '0';
		}
		for(int j=0; j<positionOne.length ; j++) {
			output[positionOne[j]]='1';
		}
		return new String(output);
	}
}
