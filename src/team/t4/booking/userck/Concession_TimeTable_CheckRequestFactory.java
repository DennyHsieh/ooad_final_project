package team.t4.booking.userck;

import team.t4.booking.db.dbManager.Station;
import team.t4.booking.tk.FillException;

public class Concession_TimeTable_CheckRequestFactory extends CheckRequestFactory {
	private String date;
	private String time;
	private String timeCondition;
	private String depart;
	private String destination;
	
	public Concession_TimeTable_CheckRequestFactory (String date, String time, String timeCondition, String depart, String destination) {
			this.date = date = date.split("/")[0]+date.split("/")[1]+date.split("/")[2];
			this.time = time;
			this.timeCondition  = timeCondition;
			this.depart = depart;
			this.destination = destination;
		}
	public Concession_TimeTable_CheckRequest getInstance() throws Exception{
		if (date=="" || time=="" || timeCondition =="" || depart == "" || destination == "") {
			throw new FillException("欄位不得空白");
		}
		if (depart.equals(destination)) {
			throw new FillException("出發地與目的地不可相同");
		}
		if (dateCheck(date) == false) {
			throw new FillException("查詢失敗 " + this.date.substring(0,4)+"/"+this.date.substring(4,6)+"/"+this.date.substring(6,8) + "尚未有時刻表供查詢" );
		}
		return new Concession_TimeTable_CheckRequest(date,time,timeCondition,depart,destination);
	}
	
}
