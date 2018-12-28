package team.t4.booking.userck;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import team.t4.booking.tk.FillException;

public class TimeTable_CheckRequestFactory extends CheckRequestFactory{
	
	
	private String date;
	public TimeTable_CheckRequestFactory(String date) {
		this.date = date.split("/")[0]+date.split("/")[1]+date.split("/")[2];
	}
	
	public TimeTable_CheckRequest getInstance() throws Exception {
		if (date=="") {
			throw new FillException("欄位不得空白");
		}
		if (dateCheck(date) == false) {
			throw new FillException("print 失敗，<" + this.date.substring(0,4)+"/"+this.date.substring(4,6)+"/"+this.date.substring(6,8) + ">尚未有時刻表供查詢" );
		}
		return new TimeTable_CheckRequest(date);
	}
}
