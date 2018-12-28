package team.t4.booking.ref;

import team.t4.booking.db.dbManager;
import team.t4.booking.db.dbManager.Station;

/**
 * This request is foe decrease amount of tickets
 * of special transaction which users made.
 */
public class Modify_RefundRequest extends RefundRequest{
	private String UID;
	private String reservationNo;
	private String[][] searchResult;
	private int refundAmount;
	private String type;
	
	Modify_RefundRequest (String UID, String reservationNo) {
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
						 "出發:"+searchResult[i][3]+" "+"到達:"+searchResult[i][4]+" "+
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
	 * Set the decreasing amount.
	 * @param amount The decreasing amount.
	 */
	public void setAmount(int amount) {
		this.refundAmount = amount;
	}
	/**
	 * Set the type of the ticket to be decreased.
	 * @param Selected item number of the printed information.
	 * @return The current amount of the selected type, and total number
	 * of the tickets for now.
	 */
	public int[] setType(String type ,int select) {
		this.type = type;
		int totalNum = Integer.parseInt(searchResult[select-1][12])+
					   Integer.parseInt(searchResult[select-1][13])+
					   Integer.parseInt(searchResult[select-1][14])+
					   Integer.parseInt(searchResult[select-1][15])+
					   Integer.parseInt(searchResult[select-1][16]);
		int typeNum =  type == "numOfNormal"?Integer.parseInt(searchResult[select-1][12]):
					   type == "numOfCollege"?Integer.parseInt(searchResult[select-1][13]):
				       type == "numOfChildren"?Integer.parseInt(searchResult[select-1][14]):	
					   type == "numOfSenior"?Integer.parseInt(searchResult[select-1][15]):Integer.parseInt(searchResult[select-1][16]);
		return new int[] {typeNum,totalNum};   
	}
		
	public void execute(int select) {
		select = select-1;
		try {
			if(searchResult[select][0]!=null) { 
				String date = searchResult[select][1];
				String trip = searchResult[select][2];
				String trainNo = searchResult[select][6];
				Station depart = Station.valueOf(searchResult[select][7]);
				Station destination = Station.valueOf(searchResult[select][8]);
				String[] seats = searchResult[select][10].split(",");
				String concession = searchResult[select][19];
				String direction = dbManager.getDirection(depart, destination);
				dbManager.modifyTicketNumber(reservationNo, refundAmount,type,trip);
				dbManager.modifyPrice(reservationNo,trip,date,depart,destination,trainNo,type,refundAmount,concession);
				dbManager.modifySeatPurchase(refundAmount, searchResult[select][10], reservationNo,trip);
				for (int count = 0; count < refundAmount ; count++) {
					String[] seatInformation = dbManager.reacquireSeatInfo(seats[count],depart,destination);
					dbManager.deleteSeat(seatInformation, trainNo, date);
				}
				if(type.equals("numOfNormal"))
					dbManager.returnEarly(date, direction, trainNo, concession, refundAmount);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
