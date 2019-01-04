package team.t4.booking.userck;

import team.t4.booking.db.Schedule;
import team.t4.booking.tk.Station;

public class CreateCheck {
	
	private String UID = "";
	private String reservationNo = "";
	private Station destination;
	private Station depart;
	private String date = "";
	private String trainNo = "";
	private String checkDate = "";
	private String checkTime = "";
	private String timeCondition = "";
	private Station checkDepart;
	private Station checkDestination;
	
	CreateCheck (String UID, String reservationNo, 
							String destination, String depart, 
							String date, String trainNo,
							String checkDate, String checkTime,
							String timeCondition, String checkDepart,
							String checkDestination) {
		
		
		this.UID = UID;
		this.reservationNo = reservationNo;
		if(!depart.equals(""))
			this.depart = Station.valueOf(depart);
		if(!destination.equals(""))
			this.destination = Station.valueOf(destination);
		this.date = date;
		this.trainNo = trainNo;
		this.checkDate = checkDate;
		this.checkTime = checkTime;
		this.timeCondition = timeCondition;
		if(!checkDepart.equals(""))
			this.checkDepart = Station.valueOf(checkDepart);
		if(!checkDestination.equals(""))
			this.checkDestination = Station.valueOf(checkDestination);
	}

	public void checkWithRn() {
		
		CheckTransaction checkTransaction = new CheckTransaction();
		
		try {
			String[][] searchResult = checkTransaction.getTransactWithRn(UID,reservationNo);
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
	
	public void checkWithoutRn() {
		try {
			System.out.println("createCheck line 71");
			CheckTransaction checkTransaction = new CheckTransaction();
			System.out.println("createCheck line 73");
			String[][] searchResult = checkTransaction.getTransactResultWithoutRN(UID,depart,destination,date,trainNo);
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
	
	public void checkNormalTable() {
		
		Schedule schedule = new Schedule();
		String[][] south = schedule.getSouthTimeTable(checkDate);
		String[][] north = schedule.getNorthTimeTable(checkDate);
		
		int row = 0;	
		System.out.println("\n\n");
		System.out.println("<<南下列車>>"+"\n");
		System.out.println("<車次>    <南港站>   <台北站>   <板橋站>   <桃園站>   <新竹站>   <苗栗站>   <台中站>   <彰化站>   <雲林站>   <嘉義站>   <台南站>   <左營站>");
		System.out.println("");
		while(row < south.length && south[row][0]!=null) {
			System.out.print("["+south[row][0]+"]    ");	
			for(int i=1; i<south[row].length ; i++) {
				if(south[row][i].equals(""))
					System.out.print("          ");
				else
					System.out.print(south[row][i]+"     ");	
			}
			System.out.println("\n");
			row++;
		}
		
		row = 0;	
		System.out.println("\n\n");
		System.out.println("<<北上列車>>"+"\n");
		System.out.println("<車次>    <左營站>   <台南站>   <嘉義站>   <雲林站>   <彰化站>   <台中站>   <苗栗站>   <新竹站>   <桃園站>   <板橋站>   <台北站>   <南港站>");
		System.out.println("");
		while(row < north.length && north[row][0]!=null) {
			System.out.print("["+north[row][0]+"]    ");	
			for(int i=1; i<north[row].length ; i++) {
				if(north[row][i].equals(""))
					System.out.print("          ");
				else
					System.out.print(north[row][i]+"     ");	
			}
			System.out.println("\n");
			row++;
		}
		
	}
	
	public void checkConcessionTable() {
		try {			
			CheckTimeTable checkTimeTable = new CheckTimeTable();
			String[][] info = checkTimeTable.getConcessionTable(checkDate,checkTime,timeCondition,checkDepart,checkDestination);
			
			System.out.println("info[0][0] = "+info[0][0]);
			System.out.println("info[1][0] = "+info[1][0]);
			if(info[0][0]!=null) {
				int trainCount = 0;
				System.out.println(" <車次>     <大學生折數>   <早鳥折數>     <起站>   <訖站>   <出發時間>  <到達時間>  <行車時間>"+"\n");
				while( trainCount < 10 && info[trainCount][0]!=null) {
					System.out.print(" ["+info[trainCount][0]+"]     "+
							(info[trainCount][1]==null?"             ":" "+info[trainCount][1]+"折      ")+
							(info[trainCount][2]==null?"              ":(" "+info[trainCount][2])+((info[trainCount][2].length()==4)?"折      ":"折       "))+
							info[trainCount][4]+"     "+
							info[trainCount][5]+"      "+
							info[trainCount][6]+"      "+
							info[trainCount][7]+"      "+
							info[trainCount][8]);
					System.out.println("\n");
					trainCount++;
				}
			} else {
				System.out.println("查無相關車次");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
