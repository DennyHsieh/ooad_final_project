package team.t4.booking.tk;
import team.t4.booking.db.Price;
import team.t4.booking.db.Schedule;
import team.t4.booking.db.TempSaveException;
import team.t4.booking.db.Train;
import team.t4.booking.db.Transaction;

public class AddList {
	
	public void saveData ( String UID, Station depart, Station destination, String[] trainInfo, String[][] seats, String trip, String date, String reservationNo,
						    int numOfNormal, int numOfCollege, int numOfChildren, int numOfSenior, int numOfChallenged) {
		
		int num = numOfNormal + numOfCollege + numOfChildren + numOfSenior + numOfChallenged;
		
		Schedule schedule = new Schedule();
		Train train = new Train();
		Transaction transaction = new Transaction();
		
		try {
			schedule.saveTimeTable(date,  depart, destination);
			train.saveSeatTable(date, trainInfo[0]);
			
			transaction.saveTransaction (UID, trip, date, trainInfo[6], trainInfo[7],
					trainInfo[8], trainInfo[0],depart,destination,getTotalPrice(depart, destination, trainInfo,date, numOfCollege, numOfNormal, numOfChildren, numOfSenior, numOfChallenged),seatOperation(seats, trainInfo[0],num,date), trainInfo[2],
					 numOfNormal, numOfCollege, numOfChildren, numOfSenior, numOfChallenged, reservationNo);
			
			if(trainInfo[2]!=null) {
				schedule.sellEarly(date, depart, destination, trainInfo[0], trainInfo[2], numOfNormal);
			}
			
			schedule.dropTimeTable(date);
			train.dropSeatTable(date);
		} catch (TempSaveException e) {
			System.out.println(e.getMessage());
			transaction.deleteDataBase(date);
			System.exit(0);
		}
		catch (Exception e) {
			try {
				schedule.recoverTimeTable(date, depart, destination);
				train.recoverSeatTable(date,trainInfo[0]);
			} catch (Exception e2){
				return;
			}
			return;
		}
	}
	
	private String seatOperation(String[][] seats, String trainNo, int num, String date) throws Exception {
		String seat = this.seatToString(seats);
		Train train = new Train();
		for(int i=0 ; i<num ;i++) {
			train.occupy(seats[i],trainNo,date);
		}	
		return seat;
	}
	

	private int getTotalPrice(Station depart, Station destination, String[] trainInfo, String date, 
								int numOfCollege,int numOfNormal, int numOfChildren, int numOfSenior, int numOfChallenged) throws Exception{
		
		Price price = new Price();
		
		int totalPrice = 0;
		if(numOfNormal!=0) {
			if(trainInfo[2]!=null) {
				totalPrice += Price.priceCalculate(depart, destination, ConcessionPlan.valueOf(trainInfo[2]), numOfNormal);
			}
			else {
				totalPrice += Price.priceCalculate(depart,destination,ConcessionPlan.valueOf("無優惠"),numOfNormal);
			}
		}
		
		if(numOfCollege!=0) {
			if(trainInfo[1]!=null) {
				totalPrice += Price.priceCalculate(depart,destination,ConcessionPlan.valueOf(trainInfo[1]),numOfCollege);
			}	
			else {
				totalPrice += Price.priceCalculate(depart,destination,ConcessionPlan.valueOf("無優惠"),numOfCollege);
			}
		}
		if(numOfChildren + numOfSenior + numOfChallenged!=0)
			totalPrice += Price.priceCalculate(depart,destination,ConcessionPlan.valueOf("優惠"),numOfChildren+numOfSenior+numOfChallenged);
		return totalPrice;
	}
	
	/**
	 * Get the string type of the seat information in Chinese.
	 * @param seatInfo
	 * @return Seat information in Chinese.
	 */
	public static String seatToString(String seatInfo[][]) {
		String buffer="";
		for(int i=0 ; i<seatInfo.length ; i++) {
			buffer += (seatInfo[i][0].substring(1,2)+"車"+(seatInfo[i][0].substring(2,3).equals("0")?"":"1")+(seatInfo[i][0].substring(3))+seatInfo[i][1]+(i!=seatInfo.length-1?",":""));
		}
		return buffer;
	}
}
