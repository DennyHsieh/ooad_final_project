package team.t4.booking.userck;

import team.t4.booking.db.dbManager;

public class Normal_User_CheckRequest implements CheckRequest{
	
	private String UID;
	private String reservationNo;
	
	public static void main(String[] args) {
		 Normal_User_CheckRequest r = new  Normal_User_CheckRequest("A123","42108669");
		 r.print();
	}

	Normal_User_CheckRequest(String UID, String reservationNo) {
		
		this.UID = UID;
		this.reservationNo = reservationNo;
	}

	public void print() {
		try {
			String[][] searchResult = dbManager.getTransactResult(UID,reservationNo);
			int i = 0;
			 while(i<2 && searchResult[i][0]!=null) {
				 System.out.println("<車次>     <日期>     <起站>    <訖站>    <出發時間>     <到達時間>    <行車時間>    <座位資訊>");
				 
				 
				 System.out.println("["+searchResult[i][6]+"]   "+
						 searchResult[i][1]+"     "+
						 searchResult[i][7]+"      "+searchResult[i][8]+"      "+
						 searchResult[i][3]+"         "+searchResult[i][4]+"        "+
						 searchResult[i][5]+"       "+searchResult[i][10]+" \n");
				 i++;
			 }
		}
		catch (Exception e) {
			System.out.println(e);
		}	
	}
}
