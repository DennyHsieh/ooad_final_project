package team.t4.booking.userck;

import team.t4.booking.db.dbManager;
import team.t4.booking.db.dbManager.Station;

public class Concession_TimeTable_CheckRequest implements CheckRequest{
	
	private String date;
	private String time;
	private String timeCondition;
	private Station depart;
	private Station destination;
	
	Concession_TimeTable_CheckRequest (String date, String time, String timeCondition, String depart, String destination) {
		
		this.date = date;
		this.time = time;
		this.timeCondition = timeCondition;
		this.depart = Station.valueOf(depart);
		this.destination = Station.valueOf(destination);
	}
	
	public void print() {
		try {			
			String[][] info = dbManager.getConcessionTable(date,time,timeCondition,depart,destination);
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
