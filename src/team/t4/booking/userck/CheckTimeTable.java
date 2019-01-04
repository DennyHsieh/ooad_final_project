package team.t4.booking.userck;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import team.t4.booking.db.CheckEmptySeat;
import team.t4.booking.db.Schedule;
import team.t4.booking.tk.ConcessionPlan;
import team.t4.booking.tk.Station;

public class CheckTimeTable {
	

/**
 * Get concession train table with specified data.
 * @param date The date of the trip.
 * @param time Condition time.
 * @param timeCondition The condition for searching,
 * to determine whether arriving time or departing time is emphasized.
 * @param depart The departure.
 * @param destination The arrival.
 * @return String of concession train table.
 * @throws Exception
 */
public String[][] getConcessionTable(String date, String time, String timeCondition, Station depart, Station destination) throws Exception{
	Schedule schedule = new Schedule();
	String direction = schedule.getDirection(depart,destination);
	String[][] train =new String[10][9];
	String[][] trainTable;
	
	if(timeCondition.equals("depart")) {
		trainTable = schedule.getSortedTimeTable(depart, destination, direction, date);
	} else {
		trainTable = schedule.getSortedTimeTableDest(depart, destination, direction, date);
	}
	
	int m=0;
	while (trainTable[m][0]!=null) {
		for(int n=0 ; n<7 ; n++) {
			System.out.println(trainTable[m][n]);
		}
		m++;
		System.out.println();
	}
	
	int count = 0;
	int i = 0;
	CheckEmptySeat checkEmptySeat = new CheckEmptySeat();
		//Find the available trainNumber.
	try {
	while(trainTable[i][0]!=null && count < 10) {	
		//Check if the selected train is available for at least one position.
		if(checkEmptySeat.checkConditionSeatAvail(date,trainTable[i][0],depart,destination,1,"")==false) 
			continue;
		
		String dptime = trainTable[i][5];
		String arrtime = trainTable[i][6];
		System.out.println("dptime = "+trainTable[i][5] + "and arrTime = "+trainTable[i][6]);
		String duration;
		
		
		ConcessionPlan concession = schedule.getEarlyPlan(date,trainTable[i][0],direction,1);
		System.out.println("line64 at checktimetable");
		//If user selects time for departure, then the train which departing time is later will be chosen;
		//If user selects time for arrival, then the train which arriving time is earlier will be chosen;
		boolean condition = (timeCondition.equals("depart"))? 
							!arrtime.equals("") && !dptime.equals("") && dptime.compareTo(time)>=0 :
							!dptime.equals("") && !arrtime.equals("") && arrtime.compareTo(time)<=0;
		System.out.println("line70 at checktimetable");		
		System.out.println("line70 at checktimetable ");		
		try{Double.parseDouble(trainTable[i][0]);}catch (Exception e) {System.out.println("71 at checktimetable");}
		try{concession.toString();}catch (Exception e) {System.out.println("72 at checktimetable"+e.getMessage());}
		boolean haveConcession = (Double.parseDouble(trainTable[i][0])!=1.0 && !concession.toString().equals("無優惠"));
		System.out.println("condition = "+condition);
		System.out.println("haveConcession = " +haveConcession);
	
		
		if(condition && haveConcession) {	
			
			duration = checkEmptySeat.getDuration(dptime,arrtime);
			System.out.println("find~~~1");
			String s = trainTable[i][0];
			System.out.println("find~~~2");
			Double.parseDouble(trainTable[i][1]);
			System.out.println("find~~~3");
			date.substring(4);
			train[count++] = new String[] {trainTable[i][0],(Double.parseDouble(trainTable[i][1])==1.0?null:Double.parseDouble(trainTable[i][1])==0.5?"大學5":Double.parseDouble(trainTable[i][1])==0.7?"大學7":"大學85"),(concession!=null?concession.toString():null),date.substring(4),depart.toString(),destination.toString(),dptime,arrtime,duration};
		}
		i++;
	}} catch (Exception e) {System.out.println(e.getMessage());}
	return train;
}

}
