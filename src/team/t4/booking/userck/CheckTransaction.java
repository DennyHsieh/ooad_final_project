package team.t4.booking.userck;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import team.t4.booking.db.Transaction;
import team.t4.booking.tk.Station;

public class CheckTransaction {
	
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
	public String[][] getTransactResultWithoutRN(String UID, Station depart, Station destination, String date, String trainNo) throws Exception {
		

		Transaction transaction = new Transaction();
		String[][] record = transaction.getTransactResult(UID); //50*20

		String[][] buffer = new String[10][20]; 
		
		System.out.println("checkTransaction line 31 below should print buffer for getTransactResult");
		for (int m = 0 ; m <3 ; m++) {
			for (int n = 0 ; n< 5 ; n++) {
				System.out.print(record[m][n]+" ");
			}
			System.out.println();
		}
		
		
		int i = 0; 
		int numOfRow = 0;
		while (record[i][0] != null && i<50) {
			System.out.println("checkTransaction line 43 and i  = "+i);
			System.out.println("checkTansaction line 44 \n"
					+ "date = "+ record[i][1]+"    trainNo = "+record[i][6]+ "depart = "+ record[i][7]+" destination = "+record[i][8]);
			if ( date.equals(record[i][1]) && 
				 trainNo.equals(record[i][6]) && 
				 depart.toString().equals(record[i][7]) && 
				 destination.toString().equals(record[i][8])){
				
				for (int numOfCol = 0 ; numOfCol<20 ; numOfCol++) {
					buffer[numOfRow][numOfCol] = record[i][numOfCol];
				}
				numOfRow ++;
			}
			i++;
		}
		if (buffer[0][0] != null) {
			System.out.println("checkTransaction line 55");
			return buffer;
		}
		else 
			throw new Exception("查詢結果錯誤");
	}

	public String[][] getTransactWithRn(String UID, String reservationNo) throws Exception {
		Transaction transaction = new Transaction();

		String[][] record = transaction.getTransactResult(UID); // String[50]*[20]
		String[][] output = new String[2][20];
		int i = 0;
		int row = 0;
		while (record[i][0] != null && row < 2) {
			if (record[i][17].equals(reservationNo)) {	
				output[row++] = record[i]; // return the record corresponding to the UID.
			}
			i++;
		}
		if (output[0][0] != null)
			return output;
		else
			throw new Exception("查無訂位資料");
	} 
}
