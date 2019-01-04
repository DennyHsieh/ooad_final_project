package team.t4.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Train {

	public String[][] getTrainTable(String trainNo, String date)  throws Exception { 
	
		String[][] trainTable = new String[200][6];
		String sql = "SELECT * FROM Train"+trainNo+date+";";		
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql)) {		
			
			int row = 0;
			int col = 0;
			
			while (rs.next()) {
				col = 0;
				while (col < 6) {
					trainTable[row][col] = rs.getString(col+1);
					col++;
				}
				row++;
			}
			return trainTable;
		}catch (Exception e) {
			throw new Exception("系統查詢位置失敗");
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
		public void dropSeatTable(String date) throws Exception{
			String sql  = "DROP TABLE tempSeat";
			try(Connection conn = connect(date);
					Statement stm = conn.createStatement()){
					stm.execute(sql);
			} catch (Exception e) {
				throw new TempSaveException("系統刪除暫存車位資料庫檔案失敗:"+e.getMessage());
			}
		}

		/**
		 * Delete the seat from the train of specified date.
		 * @param seatInfo The seat information of the seat.
		 * @param trainNo The train number of the train.
		 * @param date The date of the trip.
		 * @throws Exception
		 */
		public void deleteSeat(String[] seatInfo, String trainNo, String date) throws Exception {
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
		 * Occupy the seats with specified seat information.
		 * @param seatInfo The seat information.
		 * @param trainNo The train number.
		 * @param date The date of the trip.
		 * @throws Exception
		 */
		public void occupy (String[] seatInfo,String trainNo,String date) throws Exception {
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
				String sql2 = "UPDATE Train"+trainNo+date+" SET "+seatCharc+" = '" + this.bitAdd(12,usage,currentUse)+"' WHERE No = '"+seatNo+"';";
				stm.execute(sql2);
			} catch(Exception e) {
				throw new Exception("系統劃位發生錯誤");
			}
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
}
