package team.t4.booking.tk;

import java.util.Scanner;

import team.t4.booking.db.TempSaveException;
import team.t4.booking.db.checkEmptySeat;
import team.t4.booking.db.dbManager;
import team.t4.booking.db.dbManager.ConcessionPlan;
import team.t4.booking.db.dbManager.Station;

public class CreateReservation {
	

	protected String UID=""; 		//每個人的身分證
	protected String date="";		//搭車日期
	protected String time="";
	private String dateR="";
	private String timeR="";
	
	protected Station depart;	//出發地
	protected Station destination;	//抵達地
	
	protected String condition="";
	
	protected int numOfNormal=0;		//全票票數
	protected int numOfChildren=0;	//兒童票票數
	protected int numOfSenior=0;		//敬老票票數
	protected int numOfChallenged=0;	//愛心票票數
	protected int numOfCollege=0;	//大學生票數
	
	CreateReservation(String UID, String date, String time, String depart, String destination, 
			String dateR, String timeR, String condition, int numOfCollege, int numOfNormal,int numOfChildren,int numOfSenior,int numOfChallenged) {
		
		this.UID = UID;
		this.date = date;
		this.time = time;
		this.depart = Station.valueOf(depart);
		this.destination = Station.valueOf(destination);
		this.dateR = dateR;
		this.timeR = timeR;
		this.condition = condition;
		
		this.numOfCollege = numOfCollege;
		this.numOfNormal = numOfNormal;
		this.numOfChildren = numOfChildren;
		this.numOfSenior = numOfSenior;
		this.numOfChallenged = numOfChallenged ;
		
	}
	private String seatOperation(String trainNo, int num, String date) throws Exception {
		String seats[][] = dbManager.getConditionSeatInfo(date, trainNo, depart, destination,num,condition);
		String seat = dbManager.seatToString(seats);
		for(int i=0 ; i<num ;i++) {
			dbManager.occupy (seats[i],trainNo,date);
		}	
		return seat;
	}
	public void checkVacancy() {
		if(dateR.equals("")) {
			try {	
				
				checkEmptySeat seatChecker = new checkEmptySeat();
				String[][] info = seatChecker.getConditionTrainAvail(date,time,depart,destination,
					numOfCollege+numOfNormal+numOfChildren+numOfChallenged,numOfNormal,condition);
		
				if(info[0][0]!=null) {
			
					int i = 0;
					while(i<10 && info[i][0]!=null) {
						System.out.println();
						System.out.print("("+(i+1)+") "+info[i][0]+" "+(info[i][1]==null?"     ":info[i][1])+" "+(info[i][2]==null?"     ":info[i][2])+" "+info[i][3].substring(0,2)+"/"+info[i][3].substring(2)+" "+info[i][4]+" "+info[i][5]+" "+info[i][6]+" "+info[i][7]+" "+info[i][8]);
						System.out.println();
						i++;
					}
			
					int go;
					System.out.println("要選哪一台車?");
					Scanner scanner = new Scanner(System.in);
					int reservationNo = dbManager.getReservationNo();
					addList(info[ go = (scanner.nextInt()-1) ],"去程",date,reservationNo);
					
					System.out.println("訂票成功！您的訂票資料如下\n");
					successMsg(info[go],"去程",date,reservationNo);
					System.out.println("\n總價：       "+ (getTotalPrice(info[go],date)) );
					System.out.println("訂位代號：    "+reservationNo);
				} else {
				System.out.println("去程查無可購票車次");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		else {	
			try {
				String[][] info;
				String[][] info2;
				checkEmptySeat seatChecker = new checkEmptySeat();
				info = seatChecker.getConditionTrainAvail(date,time,depart,destination,
						numOfCollege+numOfNormal+numOfChildren+numOfChallenged,numOfNormal,condition);
				info2 = seatChecker.getConditionTrainAvail(dateR,timeR,destination,depart,
						numOfCollege+numOfNormal+numOfChildren+numOfChallenged,numOfNormal,condition);
			
				if(info[0][0]!=null && info2[0][0]!=null) {

					int i = 0;
					System.out.println();
					System.out.println("去程： \n");
					while(i<10 && info[i][0]!=null) {
						System.out.print("("+(i+1)+") "+info[i][0]+" "+(info[i][1]==null?"     ":info[i][1])+" "+(info[i][2]==null?"     ":info[i][2])+" "+info[i][3].substring(0,2)+"/"+info[i][3].substring(2)+" "+info[i][4]+" "+info[i][5]+" "+info[i][6]+" "+info[i][7]+" "+info[i][8]);
						System.out.println();
						i++;
					}
				
					System.out.println("\n");
					System.out.println("回程： \n");
					i=0;
					while(i<10 && info2[i][0]!=null) {
						System.out.print("("+(i+1)+") "+info2[i][0]+" "+(info2[i][1]==null?"     ":info2[i][1])+" "+(info2[i][2]==null?"     ":info2[i][2])+" "+info2[i][3].substring(0,2)+"/"+info2[i][3].substring(2)+" "+info2[i][4]+" "+info2[i][5]+" "+info2[i][6]+" "+info2[i][7]+" "+info2[i][8]);
						System.out.println();
						i++;
					}
					
					System.out.println("去程要選哪一台車?");
					Scanner scanner = new Scanner(System.in);
					int reservationNo = dbManager.getReservationNo();
					int go;
					int back;
					addList(info[go = (scanner.nextInt()-1)],"去程",date,reservationNo);
					System.out.println("回程要選哪一台車?");
				
					Station temp = depart;
					depart = destination;
					destination = temp;
					addList(info2[back = (scanner.nextInt()-1) ],"回程",dateR,reservationNo);
					destination = depart;
					depart = temp;
					
					System.out.println("訂票成功！您的訂票資料如下\n");
					successMsg(info[go],"去程",date,reservationNo);
					successMsg(info2[back],"回程",dateR,reservationNo);
					System.out.println("\n總價：       "+ (getTotalPrice(info[go],date) + getTotalPrice(info2[back],date)));
					System.out.println("訂位代號：    "+reservationNo);
				
				} else {
					System.out.println("去程或回程查無可訂購之車次");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	private int getTotalPrice(String[] trainInfo,String date) throws Exception{
		int price=0;
		if(numOfNormal!=0) {
			if(trainInfo[2]!=null) {
			price+=dbManager.priceCalculate(depart,destination,ConcessionPlan.valueOf(trainInfo[2]),numOfNormal);
			}
			else {
			price+=dbManager.priceCalculate(depart,destination,ConcessionPlan.valueOf("無優惠"),numOfNormal);
			}
		}
		if(numOfCollege!=0) {
			if(trainInfo[1]!=null) {
				price+=dbManager.priceCalculate(depart,destination,ConcessionPlan.valueOf(trainInfo[1]),numOfCollege);
			}	
			else {
				price+=dbManager.priceCalculate(depart,destination,ConcessionPlan.valueOf("無優惠"),numOfCollege);
			}
		}
		if(numOfChildren + numOfSenior + numOfChallenged!=0)
			price+=dbManager.priceCalculate(depart,destination,ConcessionPlan.valueOf("優惠"),numOfChildren+numOfSenior+numOfChallenged);
		return price;
	}
	private void addList(String[] trainInfo, String trip, String date, int reservationNo) {
		int num = numOfNormal+numOfCollege+numOfChildren+numOfSenior+numOfChallenged;
		try {
			dbManager.saveTimeTable(date, dbManager.getDirection(depart, destination));
			dbManager.saveSeatTable(date, trainInfo[0]);

			dbManager.saveTransaction (UID, trip, date, trainInfo[6], trainInfo[7],
					trainInfo[8], trainInfo[0],depart,destination,getTotalPrice(trainInfo,date),seatOperation(trainInfo[0],num,date), trainInfo[2],
					 numOfNormal, numOfCollege, numOfChildren, numOfSenior, numOfChallenged, reservationNo);
			if(trainInfo[2]!=null) {
				dbManager.sellEarly(date,dbManager.getDirection(depart, destination),trainInfo[0],trainInfo[2],numOfNormal);
			}
			dbManager.dropTimeTable(date);
			dbManager.dropSeatTable(date);
		} catch (TempSaveException e) {
			System.out.println(e.getMessage());
			dbManager.deleteDataBase(date);
			System.exit(0);
		}
		catch (Exception e) {
			try {
				dbManager.recoverTimeTable(date,dbManager.getDirection(depart, destination));
				dbManager.recoverSeatTable(date,trainInfo[0]);
			} catch (Exception e2){
				System.out.println(e2.getMessage());
				return;
			}
			System.out.print(e.getMessage());
			return;
		}
}
	private void successMsg(String[] trainInfo, String trip, String date, int reservationNo) {
	int num = numOfNormal+numOfCollege+numOfChildren+numOfSenior+numOfChallenged;
	try {   	
		if(trip.equals("去程"))
			dbManager.printSuccess(UID, trip, date, trainInfo[6], trainInfo[7],
				trainInfo[8], trainInfo[0],depart,destination,getTotalPrice(trainInfo,date), trainInfo[2],
				numOfNormal, numOfCollege, numOfChildren, numOfSenior, numOfChallenged, reservationNo);
		else
			dbManager.printSuccess(UID, trip, date, trainInfo[6], trainInfo[7],
					trainInfo[8], trainInfo[0],destination,depart,getTotalPrice(trainInfo,date), trainInfo[2],
					numOfNormal, numOfCollege, numOfChildren, numOfSenior, numOfChallenged, reservationNo);
	} catch (Exception e) {
		System.out.print(e.getMessage());
	}
}
}
