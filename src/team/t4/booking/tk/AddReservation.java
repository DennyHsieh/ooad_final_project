package team.t4.booking.tk;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import team.t4.booking.tk.*;

public class AddReservation {

	protected String UID;
	
	protected String date;
	protected String time;
	protected String dateR;
	protected String timeR;
	
	protected String depart;
	protected String destination ;
	
	protected int numOfNormal;
	protected int numOfChildren;
	protected int numOfSenior;
	protected int numOfChallenged;
	protected String condition;
	
	protected int numOfCollege;
		
	public AddReservation(String UID, String date, String time, String depart, String destination, 
			String dateR, String timeR, String condition,int numOfCollege, int numOfNormal,int numOfChildren,int numOfSenior,int numOfChallenged) {
		
			this.UID = UID;
			this.date = date.replace("/","");
			this.time = time;
			this.depart = depart;
			this.destination = destination;
			this.dateR = dateR.replace("/","");
			this.timeR = timeR;
			this.condition = condition;
			
			this.numOfCollege = numOfCollege;
			this.numOfNormal = numOfNormal;
			this.numOfChildren = numOfChildren;
			this.numOfSenior = numOfSenior;
			this.numOfChallenged = numOfChallenged ;
	}
	
	public CreateReservation getInstance() throws Exception{
		
		/*
		if (depart.equals(destination)) {
			throw new Exception("出發地與目的地不可相同");
		}
		
		if(dateR.equals("")) {
			if(numOfCollege+numOfNormal+numOfChildren+numOfSenior+numOfChallenged >10) {
				throw new Exception("票數不得超過10張");
			}
		} else {
			if(numOfCollege+numOfNormal+numOfChildren+numOfSenior+numOfChallenged >5) {
				throw new Exception("來回票數各別不得超過5張");
			}
			if (dateR.compareTo(date)<0) {
				throw new Exception("去程不可晚於回程");
			}
			if (dateR.compareTo(date)==0) {
				if (timeR.compareTo(time)<=0)
						throw new Exception("去程不可晚於回程");
			}
			if (dateCheck(dateR) == false) {
				throw new Exception("訂票失敗 " + this.dateR.substring(0,4)+"/"+this.dateR.substring(4,6)+"/"+this.dateR.substring(6,8) + "未能預約" );
			}
		}
		if (dateCheck(date) == false) {
			throw new Exception("訂票失敗 " + this.date.substring(0,4)+"/"+this.date.substring(4,6)+"/"+this.date.substring(6,8) + "未能預約" );
		}
		*/
		System.out.println(UID+date+time+depart+destination+dateR+timeR+condition+
				numOfCollege+ numOfNormal+numOfChildren+numOfSenior+numOfChallenged);
		return new CreateReservation(UID,date,time,depart,destination,dateR,timeR,condition,
						numOfCollege, numOfNormal,numOfChildren,numOfSenior,numOfChallenged);
	};

	private boolean dateCheck(String inputdate) { 
		String input = inputdate;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		
		Date nowdate = new Date();
		
		calendar.setTime(nowdate);
		calendar.add(Calendar.DAY_OF_MONTH, 28);
		
		Date limitDate =calendar.getTime();
		try {
			Date reservation = sdf.parse(input);
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
