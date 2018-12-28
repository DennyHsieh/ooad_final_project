package team.t4.booking.ref;

import team.t4.booking.db.dbManager;
import team.t4.booking.db.dbManager.Station;
/**
 * This request is for deleting transactions in
 * database. 
 */
public class Delete_RefundRequest extends RefundRequest{

	/**
	 * Create the request object.
	 * @param UID The UID of transaction.
	 * @param reservationNo Reservation number of the transaction.
	 */
	Delete_RefundRequest (String UID, String reservationNo) {
		this.UID = UID;
		this.reservationNo = reservationNo;
	}

	public int print() {
		try {
			searchResult = dbManager.getTransactResult(UID,reservationNo);
			int i = 0;
			 while(i<2 && searchResult[i][0]!=null) {
				 System.out.println(searchResult[i][6]+" "+
						 searchResult[i][1]+" "+
						 searchResult[i][7]+" 到 "+searchResult[i][8]+" "+
						 "出發:"+searchResult[0][3]+" "+"到達:"+searchResult[i][4]+" "+
						 searchResult[i][5]+searchResult[i][10]+" \n"+
						 "一般票:"+searchResult[i][12]+"張 "+
						 "大學生票:"+searchResult[i][13]+"張 "+
						 "兒童票:"+searchResult[i][14]+"張 "+
						 "敬老票:"+searchResult[i][15]+"張 "+
						 "愛心票:"+searchResult[i][16]+"張 "+
						 "總價:"+searchResult[i][9]);
				 i++;
			 }
			 
			 return i;
		}
		catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	/**
	 * For this subclass, this method do nothing.
	 */
	public void setAmount(int amount) {
	}
	/**
	 * For this subclass, this method do nothing.
	 */
	public int[] setType(String type, int select) {
		return null;
	}
		
	public void execute(int select) {
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
				String direction = dbManager.getDirection(depart, destination);
		 
				dbManager.deleteRecord(reservationNo, trip);
				for (int count = 0; count < numOfTotal; count++) {
					String[] seatInformation = dbManager.reacquireSeatInfo(seats[count],depart,destination);
					dbManager.deleteSeat(seatInformation, trainNo, date);
				}
				if (numOfNormal!=0)
					dbManager.returnEarly(date, direction, trainNo, concession, numOfNormal);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
