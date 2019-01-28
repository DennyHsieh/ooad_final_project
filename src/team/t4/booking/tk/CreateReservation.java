package team.t4.booking.tk;

import java.awt.Font;
import java.awt.SystemColor;
import java.util.Scanner;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import team.t4.booking.db.CheckEmptySeat;
import team.t4.booking.tk.Station;
import team.t4.booking.userck.CheckTransaction;
import team.t4.booking.tk.ConcessionPlan;


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
	
	private String[][] info;
	private String[][] infoR;// train information for return trip.
	
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
		
		System.out.println("the ticket number is :(college normal children senior challenged) "+numOfCollege+numOfNormal+numOfChildren+numOfSenior+numOfChallenged);
		
	}
	
	
	public String[] checkVacancy() {
		String[] trainInfo = new String[20];
		if (dateR.equals("")) { // If there is no return trip.
			try {	
				System.out.println("createReservation line 57");
				/*
				 * get the information of the available train.
				 */
				CheckEmptySeat checkEmptySeat = new CheckEmptySeat();
				info = null;
				info = checkEmptySeat.getTrainAvail(date, time, depart, destination,
					numOfCollege+numOfNormal+numOfChildren+numOfChallenged, numOfNormal, condition);
		
				/**
				 * The information below should be implemented in GUI funciton.
				 */
				if(info[0][0]!=null) {
					int i = 0;
					while(i<10 && info[i][0] != null) {
 						trainInfo[i] = "("+(i+1)+") "+info[i][0]+" "+(info[i][1]==null?"     ":info[i][1])+" "+(info[i][2]==null?"     ":info[i][2])+" "+info[i][3].substring(0,2)+"/"+info[i][3].substring(2)+" "+info[i][4]+" "+info[i][5]+" "+info[i][6]+" "+info[i][7]+" "+info[i][8];
						i++;
					}
				}
			} catch(Exception e) {
				System.out.println(e.getMessage());
				info = null;
			}
				return trainInfo;
		} else {	
			try {
				CheckEmptySeat seatChecker = new CheckEmptySeat();
				info = null;
				infoR = null;
				info = seatChecker.getTrainAvail(date, time, depart, destination,
						numOfCollege+numOfNormal+numOfChildren+numOfChallenged, numOfNormal, condition);
				infoR = seatChecker.getTrainAvail(dateR,timeR,destination,depart,
						numOfCollege+numOfNormal+numOfChildren+numOfChallenged, numOfNormal, condition);
			//
				/**
				 * The information below should be implemented in GUI funciton.
				 */
				if(info[0][0]!=null && infoR[0][0]!=null) {
					int i = 0;
					while (i < 10 && info[i][0] != null) {				
						trainInfo[i] = "("+(i+1)+") "+info[i][0]+" "+(info[i][1]==null?"     ":info[i][1])+" "+(info[i][2]==null?"     ":info[i][2])+" "+info[i][3].substring(0,2)+"/"+info[i][3].substring(2)+" "+info[i][4]+" "+info[i][5]+" "+info[i][6]+" "+info[i][7]+" "+info[i][8];
						i++;
					}
					i = 10;
					while (i  < 20 && infoR[i][0] != null) {
						trainInfo[i] = "("+(i+1)+") "+infoR[i][0]+" "+(infoR[i][1]==null?"     ":infoR[i][1])+" "+(infoR[i][2]==null?"     ":infoR[i][2])+" "+infoR[i][3].substring(0,2)+"/"+infoR[i][3].substring(2)+" "+infoR[i][4]+" "+infoR[i][5]+" "+infoR[i][6]+" "+infoR[i][7]+" "+infoR[i][8];
						i++;
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				info = null;
				infoR = null;
			}
		}
		return trainInfo;
	}
	
	
	/**
	 * 
	 * 
	 * @param selection
	 * @return
	 */
	
	
	public String saveAndOutput(int[] selection) { //int[2] selection selection[0] =  go selection[1] = back defult = -1
		String[] returnResult = new String[2];
		String reservationNo="";
		try {
			System.out.println("createReservaiton139");
			CheckEmptySeat checkEmptySeat = new CheckEmptySeat();
			int go = selection[0];
			int back = selection[1];
			System.out.println("the go is "+go);
			System.out.println("createReservation143");
			System.out.println("print the informatino!!"+info[0][0]+"  "+info[0][1]+"   ");
			reservationNo = getReservationNo();
			String[][] seats = checkEmptySeat.getConditionSeatInfo(date, info[go][0], depart, destination, numOfCollege+numOfNormal+numOfChildren+numOfChallenged, condition);
			// Save the transaction into the database.
			addList(info[go], seats, "去程", reservationNo);
			
			if (selection[1] != -1) {
				Station temp = depart;
				depart = destination;
				destination = temp;
				seats = checkEmptySeat.getConditionSeatInfo(date, info[back][0], depart, destination, numOfCollege+numOfNormal+numOfChildren+numOfChallenged, condition);
				addList(infoR[back], seats, "回程",reservationNo);			
				destination = depart;
				depart = temp;
			}
			
			CheckTransaction checkTransaction = new CheckTransaction();
			String[][] transactionResult = checkTransaction.getTransactWithRn(UID, reservationNo);
			
			int findNum = 1;
			if (selection[1] != -1)
				findNum++;
			for (int n = 0 ; n < findNum ; n ++) {
				returnResult[n] = transactionResult[n][2]+"		"+ 
					
						(transactionResult[n][1].substring(4,6)+"/"+transactionResult[0][1].substring(6)+"		")+ 					
						(transactionResult[n][6]+"		")+ 
						(transactionResult[n][7]+"		")+ 											
						(transactionResult[n][8]+"		")+ 
						(transactionResult[n][3]+"		")+  
						(transactionResult[n][4]+"		")+  
						(transactionResult[n][12]+"		")+ 
						(transactionResult[n][13]+"		")+ 	
						(transactionResult[n][14]+"		")+ 			
						(transactionResult[n][15]+"		")+				
						(transactionResult[n][16]+"		")+ 			
						(transactionResult[n][9]+"		"); 
			}
			
			
		//return returnResult; //returnResult[0] go result [1] back result
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//return returnResult;
		return reservationNo;
	}

//					CheckTransaction checkTransaction = new CheckTransaction();
//					String[][] transactionResult = checkTransaction.getTransactWithRn(UID, reservationNo);
//					System.out.println("訂票成功！您的訂位明細如下");
//					System.out.println("行程		日期		車次		起程站		到達站		出發時間		到達時間		全票		大學生		敬老		孩童		愛心		小計");
//					for (int n = 0 ; n < 1 ; n ++) {
//						System.out.print(transactionResult[n][2]+"		"); // go or return.
//							
//						System.out.print(transactionResult[n][1].substring(4,6)+"/"+transactionResult[0][1].substring(6)+"		"); // date ex:01/25.
//									
//						System.out.print(transactionResult[n][6]+"		"); // Train number.
//											
//						System.out.print(transactionResult[n][7]+"		"); // departure.
//													
//						System.out.print(transactionResult[n][8]+"		"); // destination.
//													
//						System.out.print(transactionResult[n][3]+"		");  // departTime.
//					
//						System.out.print(transactionResult[n][4]+"		");  // arriveTime.
//					
//						System.out.print(transactionResult[n][12]+"		"); // normal ticket number
//					
//						System.out.print(transactionResult[n][13]+"		"); // college ticket number
//					
//						System.out.print(transactionResult[n][14]+"		"); // children ticket number
//					
//						System.out.print(transactionResult[n][15]+"		"); // senior ticket number
//						
//						System.out.print(transactionResult[n][16]+"		"); // challenged ticket number
//					
//						System.out.println(transactionResult[n][9]+"		"); //total price.
//					}
//				} else {
//				System.out.println("去程查無可購票車次");
//				}
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//			}
//		}
//		else {	
//			try {
//				String[][] info;
//				String[][] infoR; // train information for return trip.
//				CheckEmptySeat seatChecker = new CheckEmptySeat();
//				info = seatChecker.getTrainAvail(date, time, depart, destination,
//						numOfCollege+numOfNormal+numOfChildren+numOfChallenged, numOfNormal, condition);
//				infoR = seatChecker.getTrainAvail(dateR,timeR,destination,depart,
//						numOfCollege+numOfNormal+numOfChildren+numOfChallenged, numOfNormal, condition);
//			//
//				/**
//				 * The information below should be implemented in GUI funciton.
//				 */
//				if(info[0][0]!=null && infoR[0][0]!=null) {
//					int i = 0;
//					System.out.println();
//					System.out.println("去程： \n");
//					while (i < 10 && info[i][0] != null) {
//						System.out.print("("+(i+1)+") "+info[i][0]+" "+(info[i][1]==null?"     ":info[i][1])+" "+(info[i][2]==null?"     ":info[i][2])+" "+info[i][3].substring(0,2)+"/"+info[i][3].substring(2)+" "+info[i][4]+" "+info[i][5]+" "+info[i][6]+" "+info[i][7]+" "+info[i][8]);
//						System.out.println();
//						i++;
//					}
//				
//					System.out.println("\n");
//					System.out.println("回程： \n");
//					i = 0;
//					while (i  < 10 && infoR[i][0] != null) {
//						System.out.print("("+(i+1)+") "+infoR[i][0]+" "+(infoR[i][1]==null?"     ":infoR[i][1])+" "+(infoR[i][2]==null?"     ":infoR[i][2])+" "+infoR[i][3].substring(0,2)+"/"+infoR[i][3].substring(2)+" "+infoR[i][4]+" "+infoR[i][5]+" "+infoR[i][6]+" "+infoR[i][7]+" "+infoR[i][8]);
//						System.out.println();
//						i++;
//					}
//					System.out.println("createReservation line 154");
//					Scanner scanner = new Scanner(System.in);
//					String reservationNo = this.getReservationNo();
//					
//					System.out.println("去程要選哪一台車?");
//					int go = (scanner.nextInt()-1);
//					
//					String[][] seats = seatChecker.getConditionSeatInfo(date, info[go][0], depart, destination, numOfCollege+numOfNormal+numOfChildren+numOfChallenged, condition);
//					addList(info[go], seats, "去程",reservationNo);
//					
//					System.out.println("回程要選哪一台車?");
//					int back = (scanner.nextInt()-1);
//					
//					Station temp = depart;
//					depart = destination;
//					destination = temp;
//					
//					seats = seatChecker.getConditionSeatInfo(date, info[back][0], depart, destination, numOfCollege+numOfNormal+numOfChildren+numOfChallenged, condition);
//					addList(infoR[back], seats, "回程",reservationNo);
//					
//					
//					destination = depart;
//					depart = temp;
//					
//					
//					CheckTransaction checkTransaction = new CheckTransaction();
//					String[][] transactionResult = checkTransaction.getTransactWithRn(UID, reservationNo);
//					System.out.println("訂票成功！您的訂位明細如下");
//					System.out.println("行程		日期		車次		起程站		到達站		出發時間		到達時間		全票		大學生		敬老		孩童		愛心		小計");
//					for (int n = 0 ; n < 2 ; n ++) {
//						System.out.print(transactionResult[n][2]+"		"); // go or return.
//							
//						System.out.print(transactionResult[n][1].substring(4,6)+"/"+transactionResult[0][1].substring(6)+"		"); // date ex:01/25.
//									
//						System.out.print(transactionResult[n][6]+"		"); // Train number.
//											
//						System.out.print(transactionResult[n][7]+"		"); // departure.
//													
//						System.out.print(transactionResult[n][8]+"		"); // destination.
//													
//						System.out.print(transactionResult[n][3]+"		");  // departTime.
//					
//						System.out.print(transactionResult[n][4]+"		");  // arriveTime.
//					
//						System.out.print(transactionResult[n][12]+"		"); // normal ticket number
//					
//						System.out.print(transactionResult[n][13]+"		"); // college ticket number
//					
//						System.out.print(transactionResult[n][14]+"		"); // children ticket number
//					
//						System.out.print(transactionResult[n][15]+"		"); // senior ticket number
//						
//						System.out.print(transactionResult[n][16]+"		"); // challenged ticket number
//					
//						System.out.println(transactionResult[n][9]+"		"); //total price.
//					}
//				
//				} else {
//					System.out.println("去程或回程查無可訂購之車次");
//				}
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//			}
//		}
//	}
	
	private void addList(String[] trainInfo, String[][] seats, String trip, String reservationNo) {
		
		AddList addList = new AddList();
		addList.saveData (UID, depart, destination, trainInfo, seats, trip, date,reservationNo,
					    numOfNormal, numOfCollege, numOfChildren, numOfSenior, numOfChallenged);
	}
	
	/**
	 * Create a reservation number, which is a random 8-digits number.
	 * @return Reservation number.
	 */
	public String getReservationNo() {
		double ran=0;
		while(ran<0.1) {
			ran = Math.random();
		}
		return String.valueOf((int)(ran*(Math.pow(10, 8))+1));
	}
}
