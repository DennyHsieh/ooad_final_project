package team.t4.booking.db;

import team.t4.booking.tk.Station;
import team.t4.booking.tk.ConcessionPlan;

public class CheckEmptySeat {
	
	
	/**
	 * To get the information of available trains in particular condition.
	 * @param date The date of the trip.
	 * @param time The earliest time for departure.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param num The number of the clients.
	 * @param numOfNormal The number of normal tickets.
	 * @param condition Particular seat requirement.
	 * @return Information of available trains.
	 * @throws Exception
	 */
	public  String[][] getTrainAvail(String date, String time, Station depart, Station destination, int num, int numOfNormal, String condition) throws Exception {
			
			String direction = getDirection(depart,destination);
			
			String[][] train =new String[10][9];
			String[][] table = new String[200][7];
			
			// create schedule class to get the sorted time table.
			Schedule schedule = new Schedule();
			table = schedule.getSortedTimeTable(depart, destination, direction, date);
			
			int i = 0;
			int count = 0;	
			
			while (table[i][0] != null && count < 10) { // while there are still trains to be checked and the train that
													   //  have been checked are less than ten.
				
				if(checkConditionSeatAvail(date, table[i][0], depart, destination, num, condition) == false) 
					continue;
				
				String dptime =  table[i][5]; // depart time.
				String arrtime = table[i][6]; // arrive time.
				String duration; 
				String collegePlan; 
				String earlyPlan;
				
				// If the train departs at the departure later than the specified time 
				// and will also arrive at the destination.
				if (dptime.compareTo(time) >= 0 && !arrtime.equals("")) {	
					
					// get the trip duration.
					duration = getDuration(dptime,arrtime);
					
					// get the college concession plan message.
					double collegeDis = Double.parseDouble(table[i][1]);
					collegePlan = collegeDis == 1.0 ? null : collegeDis == 0.5 ? "大學5" : collegeDis ==0.7 ? "大學7" : "大學85" ;
					
					// get the early concession plan message.
					ConcessionPlan earlyDis = schedule.getEarlyPlan(date,table[i][0],direction,numOfNormal);
					earlyPlan = (earlyDis != null ? earlyDis.toString() : null);
					
					train[count++] = new String[] {	table[i][0], // train number.
												  collegePlan, // college concession.
												  earlyPlan,	 // early concession.
												  date.substring(4), // date.
												  depart.toString(), // departure.
												  destination.toString(), // destination.
												  dptime, // departure time.
												  arrtime, //  arrival time.
												  duration // duration.
												  };
				}
				i++;
			}
		return train;
	}
	
	
	/**
	 * Return the train duration in specific string format .
	 * @param dptime Specified departure time.
	 * @param arrtime Specified arrival time.
	 * @return train duration.
	 */
	public  String getDuration(String dptime, String arrtime) {
		if(dptime.substring(3).compareTo(arrtime.substring(3))<0) {
			 return (Integer.parseInt(arrtime.substring(0,2))-Integer.parseInt(dptime.substring(0,2)))
					+":"+String.valueOf((Integer.parseInt(arrtime.substring(3))-Integer.parseInt(dptime.substring(3)))+100).substring(1);
		}
		else {
			return  (Integer.parseInt(arrtime.substring(0,2))-Integer.parseInt(dptime.substring(0,2))-1)
					+":"+String.valueOf(((Integer.parseInt(arrtime.substring(3))+60)-Integer.parseInt(dptime.substring(3)))+100).substring(1);
		}	
	}
	
	/**
	 * Check if there are seats available in the train in a particular condition.
	 * @param date The date of the trip.
	 * @param trainNo The train number.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param num The number of clients.
	 * @param condition The special condition of the seat requirement.
	 * @return Whether there are seats available.
	 * @throws Exception
	 */
	public boolean checkConditionSeatAvail(String date, String trainNo,Station depart, Station destination, int num, String condition) throws Exception {
		
		int availNum = 0; // how many seats have been found.	
		String[][] trainTable = new String[200][6]; // the inner situation of the train.
		
		Train train = new Train();
		trainTable = train.getTrainTable(trainNo, date);
		
		int i = 0;
		while (trainTable[i][0] != null) {
			
			if(trainTable[i][0].substring(0,2).equals("06")) // business class car.
				continue;
			
			for(int seat = 0 ; seat < 5 ; seat++) {
				
				if(condition=="aisle")
					if(seat == 0 || seat == 1 || seat == 4) continue;
				
				if(condition=="window")
					if(seat == 1 || seat == 2 || seat == 3) continue;
				
				String usage = trainTable[i][seat+1]; // record the seat situation.
				
				//Check if the seat is available.
					if(checkSingleSeat(depart,destination,usage) == true) {
						availNum += 1; // find another available seat!
						if( availNum == num) // the seats are sufficient. 
							return true;
					}
			}	
			i++;
		}
		return false;
	}
	
	/**
	 * Get the available seat information in a particular condition.
	 * @param date The date of the trip.
	 * @param trainNo The train number.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param num The number of the clients.
	 * @param condition The particular condition
	 * @return The seat information in a compression form.
	 */
	public String[][] getConditionSeatInfo(String date, String trainNo, Station depart, Station destination, int num, String condition) throws Exception {
		
		String seatInfo[][] = new String[num][3]; // In the form : seatInfo[x] = { seatNo,seatCharc,usage }	
		String trainTable[][] = new String[200][6]; // The inner situation of the train.
		int availNum = 0; // how many seats have been found.
		
		Train train = new Train();
		trainTable = train.getTrainTable(trainNo, date);
		
		
		int i = 0;
		while (trainTable[i][0] != null) {
			
			if(trainTable[i][0].substring(0,2).equals("06")) // business class car.
				continue;
			
			for(int seat = 0 ; seat < 5 ; seat++) {
				
				if(condition=="aisle")
					if(seat == 0 || seat == 1 || seat == 4) continue;
				
				if(condition=="window")
					if(seat == 1 || seat == 2 || seat == 3) continue;
				
				String usage = trainTable[i][seat+1]; // record the seat situation.
				
				//Check if the seat is available.
				if(checkSingleSeat(depart, destination, usage) == true) {
					seatInfo[availNum] = new String[] { trainTable[i][0], // train number.
													   (char)(seat + (int)'A') + "", // character of the seat.
													   bitCreate(12,depart.getValue()-1, destination.getValue()-1) //usage.
													  };
					availNum += 1; // find another available seat!
					
					if( availNum == num) // the seats are sufficient. 
						return seatInfo;
				}
			}	
			i++;
		}
		return null;
	}
	
	/**
	 * Check whether the specified seat is suitable with the requirement.
	 * @param depart The departure of the trip.
	 * @param destination The destination of the trip.
	 * @param usage The current using statement of the seat.
	 * @return Whether the seat is suitable.
	 */
	private boolean checkSingleSeat(Station depart, Station destination, String usage) {
		int[] endpair = new int[] {depart.getValue()-1,destination.getValue()-1};
		String user = bitCreate(12,endpair[0],endpair[1]);
		if(usage.equals("0"))
			return false;
		String check = bitAND(12, user, usage);
		if(check.equals(bitCreate(12)) || 
			check.equals(bitOneCreate(12,endpair[0])) || 
			check.equals(bitOneCreate(12,endpair[1]))) {
			return true;
		}
		if(!check.equals(user) && check.equals(bitOneCreate(12,endpair[0],endpair[1]))){
			return true;
		}
		return false;
	}
	/**
	 * Get the rail direction of the departure and destination.
	 * @param depart
	 * @param destination
	 * @return The rail direction.
	 */
	public String getDirection (Station depart, Station destination) {
		if(destination.getValue()-depart.getValue()<=0) {
			return "North";
		}
		else {
			return "South";
		}
	}

	/**
	 * Create a bitset with a specified length with a 1-bitset surrounded by 0-bitsets.
	 * @param length The specified length of the bitset.
	 * @param end1 One of the ends of the range of the 1-bit-group.
	 * @param end2 The other ends of the range of the 1-bit-group.
	 * @return The created bitset.
	 */
	private String bitCreate(int length, int end1, int end2) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = '0';
		}
		for(int j = (end1<end2)?end1:end2 ; j<=((end1>end2)?end1:end2) ; j++) {
			output[j]='1';
		}
		return new String(output);
	}
	/**
	 * Create a 0-bitset with specified length.
	 * @param length Specified length of the set.
	 * @return The created bitset.
	 */
	private String bitCreate(int length) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = '0';
		}
		return new String(output);
	}
	/**
	 * Implement Substract operation for two bitset with specified length.
	 * @param length Specified length of the sets.
	 * @param bit1 Bitset minuend.
	 * @param bit2 Bitset subtrahend.
	 * @return Result of substract operation.
	 */
	private String bitSubtract(int length,String bit1,String bit2) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = Integer.toString(Integer.parseInt(String.valueOf(bit1.charAt(i)))-Integer.parseInt(String.valueOf(bit2.charAt(i)))).charAt(0);
		}
		return new String(output);
	}
	/**
	 * Implement AND operation for two bitset with specified length.
	 * @param length Specified length of the sets.
	 * @param bit1 One of the Bitsets of operation.
	 * @param bit2 The other Bitset of operation.
	 * @return Result of OR operation.
	 */
	private String bitAND(int length,String bit1,String bit2) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = (bit1.charAt(i)>='1'&& bit2.charAt(i)>='1')?'1':'0';
		}
		return new String(output);
	}
	/**
	 * Create a bitset with specified length, and with value 1 assaigned to selected positions,
	 * and 0 assigned to others. 
	 * @param length Specified length of the bitset.
	 * @param positionOne Selected positions for 1-bit.
	 * @return The created bitset.
	 */
	private String bitOneCreate(int length, int... positionOne ) {
		char[] output = new char[length];
		for(int i=0 ; i<length ; i++) {
			output[i] = '0';
		}
		for(int j=0; j<positionOne.length ; j++) {
			output[positionOne[j]]='1';
		}
		return new String(output);
	}
}
