package team.t4.booking.ref;

import java.sql.Connection;
import java.sql.Statement;

import team.t4.booking.db.CheckEmptySeat;
import team.t4.booking.db.Schedule;
import team.t4.booking.db.Train;
import team.t4.booking.db.Transaction;
import team.t4.booking.tk.ConcessionPlan;
import team.t4.booking.tk.Station;

public class Modify {
	
	public void DeleteSelect (String[][]searchResult, int select, String reservationNo) throws Exception {
		try {
			select = select-1;
			if(searchResult[select][0]!=null) { 
				String date = searchResult[select][1];
				String trainNo = searchResult[select][6];
				String trip = searchResult[select][2];
				Station depart = Station.valueOf(searchResult[select][7]);
				Station destination = Station.valueOf(searchResult[select][8]);
				String[] seats = searchResult[select][10].split(",");
				int numOfNormal = Integer.parseInt(searchResult[select][12]);
				int numOfCollege = Integer.parseInt(searchResult[select][13]);
				int numOfChildren = Integer.parseInt(searchResult[select][14]);
				int numOfSenior = Integer.parseInt(searchResult[select][15]);
				int numOfChallenged = Integer.parseInt(searchResult[select][16]);
				int numOfTotal = numOfNormal + numOfCollege + numOfChildren + numOfSenior + numOfChallenged;
				String concession = searchResult[select][19];
				Schedule schedule = new Schedule();
				String direction = schedule.getDirection(depart, destination);
				
				System.out.print("modify 36");
				Transaction transaction = new Transaction();
				transaction.deleteRecord(reservationNo, trip);
				
				Train train = new Train();
				for (int count = 0; count < numOfTotal; count++) {
					String[] seatInformation = this.reacquireSeatInfo(seats[count],depart,destination);
					train.deleteSeat(seatInformation, trainNo, date);
				}
				System.out.print("modify 45");
				if (numOfNormal!=0)
					schedule.returnEarly(date, direction, trainNo, concession, numOfNormal);
				System.out.print("modify 48");
			}
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public void modifySelect(int select,String searchResult[][],String reservationNo, int refundAmount,String type) {
		try { System.out.println("modify 56");
			if(searchResult[select][0]!=null) { 
				String date = searchResult[select][1];
				String trip = searchResult[select][2];
				String trainNo = searchResult[select][6];
				Station depart = Station.valueOf(searchResult[select][7]);  System.out.println("modify 61");
				Station destination = Station.valueOf(searchResult[select][8]);
				String[] seats = searchResult[select][10].split(",");  System.out.println("modify 63");
				String concession = searchResult[select][19];
				Schedule schedule = new Schedule();
				String direction = schedule.getDirection(depart, destination);
				System.out.println("modify 67");
				Transaction transaction = new Transaction();
				
				transaction.modifyTicketNumber(reservationNo, refundAmount,type,trip);
				transaction.modifyPrice(reservationNo,trip,date,depart,destination,trainNo,type,refundAmount,concession);
				transaction.modifySeatPurchase(refundAmount, searchResult[select][10], reservationNo,trip);
				
				System.out.println("modify 74");
				for (int count = 0; count < refundAmount ; count++) {
					String[] seatInformation = this.reacquireSeatInfo(seats[count],depart,destination);
					Train train = new Train();
					train.deleteSeat(seatInformation, trainNo, date);
				}
				if(type.equals("numOfNormal"))
					schedule.returnEarly(date, direction, trainNo, concession, refundAmount);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	public String[] reacquireSeatInfo(String seatInChinese, Station depart, Station destination) {
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
}
