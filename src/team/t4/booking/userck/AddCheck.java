package team.t4.booking.userck;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import team.t4.booking.tk.Station;

public class AddCheck {

	private String UID = "";
	private String reservationNo = "";
	private String destination = "";
	private String depart = "";
	private String date = "";
	private String trainNo = "";
	private String checkDate = "";
	private String checkTime = "";
	private String timeCondition = "";
	private String checkDepart = "";
	private String checkDestination = "";
	
	public AddCheck (  String UID, String reservationNo, 
										    String destination, String depart, 
											String date, String trainNo,
											String checkDate, String checkTime,
											String timeCondition, String checkDepart,
											String checkDestination) {
		

		this.UID = UID;
		this.reservationNo = reservationNo;
		this.depart = depart;
		this.destination = destination;
		this.date = date.replace("/", "");

		this.trainNo = trainNo;
		this.checkDate = checkDate.replace("/", "");
		this.checkTime = checkTime;
		this.timeCondition = timeCondition;
		this.checkDepart = checkDepart;
		this.checkDestination = checkDestination;
		
	}
	public CreateCheck getInstance() throws Exception {
		if(!depart.equals("")) {
			if (depart.equals(destination)) {
				throw new Exception("出發地與目的地不可相同");
			}
		}
		if(!checkDate.equals(""))
			if (dateCheck(checkDate) == false) {
				throw new Exception("查詢失敗 " + this.checkDate.substring(0,4)+"/"+this.checkDate.substring(4,6)+"/"+this.checkDate.substring(6,8) + "尚未有時刻表供查詢" );
			}
		
		return new CreateCheck( UID, reservationNo, destination,  depart, date,  trainNo,checkDate, checkTime, timeCondition,  checkDepart, checkDestination);
	}
	
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
