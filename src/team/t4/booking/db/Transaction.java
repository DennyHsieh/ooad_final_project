package team.t4.booking.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.t4.booking.tk.ConcessionPlan;
import team.t4.booking.tk.Station;

public class Transaction {



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
	public void saveTransaction (String UID, String trip,String date, String departTime, String arriveTime,
				String duration, String trainNo, Station depart, Station destination, int price, String seat, String earlyConcession,
				int numOfNormal,int numOfCollege, int numOfChildren, int numOfSenior, int numOfChallenged, String reservationNo) throws Exception {
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
			pstmt.setString (18,reservationNo);
			pstmt.setString (19,"未付款（付款期限：發車前30分鐘)");
			pstmt.setString (20,earlyConcession);
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception("資料儲存失敗");
		}
	}
	
	/**
	 * Get a transaction record string array from database with specified UID.
	 * @param UID User id.
	 * @param reservationNo The reservation number of the transaction.
	 * @return Transaction records of String array.
	 * @throws Exception if the UID of reservation number is not found, throw an exception.
	 */
	public String[][] getTransactResult(String UID) throws Exception {
		
		String[][] buffer = new String[50][20]; // save the transaction result.
		String sql = "SELECT * FROM 'Transact' WHERE UID = '"+UID+"';";
		

		try (Connection conn = connect("Ticket");
			 Statement stm = conn.createStatement();
			 ResultSet rs =stm.executeQuery(sql)) {
			
			int numOfRow = 0;
			
			
			while (rs.next() && numOfRow < 50) {
				
				for (int numOfCol = 0 ; numOfCol < 20 ; numOfCol++) {
					buffer[numOfRow][numOfCol] = rs.getString(numOfCol+1);
				}
				numOfRow++;		
			}
			
			if (buffer[0][0]!=null)
				return buffer;
			else 
				throw new Exception("身分證字號錯誤");
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
	public void modifyTicketNumber(String reservationNo, int refundAmount, String ticketType, String trip) throws Exception{
		String sql = "UPDATE transact SET "+ticketType+" = "+ticketType+" - "+refundAmount+
					" WHERE reservationNo = '"+reservationNo+
					"' AND trip = '"+trip+"';";
		System.out.println("transaction 125 " +sql);
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
	public void modifyPrice(String reservationNo, String trip, 
			String date, Station depart, Station destination, String trainNo,
			 String type, int num, String earlyConcession) throws Exception {
		Schedule schedule = new Schedule();
		ConcessionPlan concession = type == "numOfNormal"?earlyConcession==null?ConcessionPlan.無優惠:team.t4.booking.tk.ConcessionPlan.valueOf(earlyConcession):
							    type == "numOfCollege"?concession = schedule.getCollegePlan(date, trainNo, schedule.getDirection(depart,destination)):ConcessionPlan.優惠; 	
		Price price = new Price();
		int minusPrice = price.priceCalculate(depart, destination, concession, num);
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
	 * To delete the whole record of the transaction.
	 * @param reservationNo The reservation number of the transaction.
	 * @param trip Whether the transaction is of returning tickets.
	 * @throws Exception
	 */
	public void deleteRecord(String reservationNo, String trip) throws Exception{
		String sql = "DELETE FROM Transact WHERE reservationNo = '"+reservationNo+"' AND trip = '"+trip+"';";
		try(Connection conn = connect("Ticket");
				Statement stm = conn.createStatement();) {
				stm.execute(sql);
			} catch( Exception e) {
				throw new Exception("系統刪除交易資料時發生錯誤");
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
	public void modifySeatPurchase (int refundAmount, String originSeats, String reservationNo, String trip) throws Exception{
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
	 * Delete the data base with specified date.
	 * @param date Specified dataBase
	 */
	public void deleteDataBase(String date) {
		String projectPath = System.getProperty("user.dir");
				File file = new File(projectPath + "/"+date+".db");
				if(file.delete())
					System.out.println(file.getName() + " is deleted!");
				else
					System.out.println("Delete operation is failed.");
	}
}
