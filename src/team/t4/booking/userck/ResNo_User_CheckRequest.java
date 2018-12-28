package team.t4.booking.userck;

import team.t4.booking.db.dbManager;
import team.t4.booking.db.dbManager.Station;

public class ResNo_User_CheckRequest implements CheckRequest {
	
	public static void main(String[] args) {
		 ResNo_User_CheckRequest r = new  ResNo_User_CheckRequest("A123","板橋","台中","20180712","0609");
		 r.print();
	}

	
	private String UID;
	private Station destination;
	private Station depart;
	private String date;
	private String trainNo;
	
	ResNo_User_CheckRequest(String UID, String depart, String destination, String date, String trainNo) {
		this.UID = UID;
		this.depart = Station.valueOf(depart);
		this.destination = Station.valueOf(destination);
		this.date = date;
		this.trainNo = trainNo;
	}
	
	@Override
	public void print() {
		try {
			String[][] searchResult = dbManager.getTransactResultWithoutRN(UID,depart,destination,date,trainNo);
			int i = 0;
			 while(i<10 && searchResult[i][0]!=null) {
				 System.out.println("<訂位代號>               <交易狀態>               <UID>        "
				 		+ "<去回程>     <車次>     <日期>     <起站>    <訖站>    <出發時間>     <到達時間>    <行車時間>    <座位資訊>");
				 
				 
				 System.out.println(
						 searchResult[i][17]+"      "+
						 searchResult[i][18]+"     "+
						 searchResult[i][0]+"       "+
						 searchResult[i][2]+"       "+
						 "["+searchResult[i][6]+"]   "+
						 searchResult[i][1]+"    "+
						 searchResult[i][7]+"      "+searchResult[i][8]+"       "+
						 searchResult[i][3]+"         "+searchResult[i][4]+"        "+
						 searchResult[i][5]+"       "+searchResult[i][10]);
				 System.out.println("<車廂>                   <票種數量>                                <總價>");
				 System.out.println(
						 searchResult[i][11]+"   "+
						 "一般票:"+searchResult[0][12]+"張 "+
						 "大學生票:"+searchResult[0][13]+"張 "+
						 "兒童票:"+searchResult[0][14]+"張 "+
						 "敬老票:"+searchResult[0][15]+"張 "+
						 "愛心票:"+searchResult[0][16]+"張      "+
						 searchResult[0][9]+"元"+"\n");
				 i++;
			 }
		}
		catch (Exception e) {
			System.out.println(e);
		}	
		
	}


}
