package team.t4.booking.userck;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class CheckRequestFactory {
	
	public abstract CheckRequest getInstance() throws Exception;
	
	protected boolean dateCheck(String inputdate) { 
		String input = inputdate;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		
		Date nowdate = new Date();
		
		calendar.setTime(nowdate);
		calendar.add(Calendar.DAY_OF_MONTH, 28);
		
		Date limitDate =calendar.getTime();
		try {
			Date reservation = sdf.parse(input);
			System.out.print(sdf.format(reservation));
			long period1 = (limitDate.getTime() - reservation.getTime())/86400000;
			long period2 = (reservation.getTime() - nowdate.getTime())/86400000;
			if((period1 < 0) || (period2 < 0)) {
				return false;
			}
			else
				return true;
		} catch (ParseException e) {
			System.out.print("wrong date type");
			return false;
		}
	}

}
