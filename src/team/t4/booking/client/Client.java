package team.t4.booking.client;

import java.util.Scanner;

import team.t4.booking.db.TableCrawler;
import team.t4.booking.ref.AddModify;
import team.t4.booking.ref.CreateModify;
import team.t4.booking.tk.AddReservation;
import team.t4.booking.tk.CreateReservation;
import team.t4.booking.userck.AddCheck;
import team.t4.booking.userck.CreateCheck;

/**
 * Provide interface for user to utilize services of the system.
 * @author LiangChiaLun
 */
public class Client {	
	
	
	public static void main(String[] args) {
		
		
		TableCrawler.checkUpdate(); 
		
		menu();
		
		
	}
	
	/**
	 * Dissipate request factory reference for client
	 * initially.
	 */
	private static AddReservation addNewRes;
	private static AddModify addNewMod;
	private static AddCheck  addNewChe;
	
	/**
	 * Dissipate request object reference for client
	 * initially.
	 */
	private static CreateReservation creRes;
	private static CreateModify creMod;
	private static CreateCheck  creChe;
	
	
	/**
	 * Default Reference to the data to be filled.
	 */
	private static String UID;
	private static String date;
	private static String time;
	private static String dateR;
	private static String timeR;
	private static String depart;	
	private static String destination;
	private static String condition;
	private static String reservationNo;
	/**
	 * Default reference to record the amount of Tickets.
	 */
	private static int numOfNormal;
	private static int numOfChildren;
	private static int numOfSenior;
	private static int numOfChallenged;
	private static int numOfCollege;
	
	/**
	 * To let users input integer in a specified range 
	 * except for zero, and print out the assigned reminder.
	 * When invalid number is input, return to the same function recursively.
	 * @param down The bottom bound of the range.
	 * @param up The upper bound of the range.
	 * @param message The message of the reminder.
	 * @return Valid input integer.
	 */
	public static int scanIntRange(int down, int up, String message) {
		printoutln(message+"\n\n");
		int temp;
		Scanner scanner = new Scanner(System.in);
		try {
			temp = scanner.nextInt();
			if( (temp<down || temp >up) && temp!=0)
				throw new Exception();
			} catch(Exception e) {
				printoutln("請輸入正確的數字哦(๑• . •๑)");
				return scanIntRange(down,up,"");
			}
		return temp;
	}
	/**
	 * To manifest a selection menu to let users decide which 
	 * service is needed.
	 */
	public static void menu() {
		int selection = 0;
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		System.out.println("         您好～～歡迎光臨高鐵系統(ﾉ>ω<)ﾉ\n");
		System.out.println("               ①  一般訂票");
		System.out.println("               ②  大學生訂票");
		System.out.println("               ③  退票/修改資料");
		System.out.println("               ④  查詢資料");
		System.out.println("\n               ⓪  離開系統");
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		selection = scanIntRange(1,4,"");
		if(selection==1) normalRequest();
		if(selection==2) collegeRequest();
		if(selection==3) refundRequest();
		if(selection==4) searchRequest();
		if(selection==0) System.exit(0);
	}
	
	
	/**
	 * To manifest a selection menu to let users decide which 
	 * if the purchased tickets should have the other returning one 
	 * or in special condition.
	 */
	public static void normalRequest() {
		int selection1;
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		System.out.println("           有沒有需要訂來回票呢(๑¯∀¯๑)\n");
		System.out.println("            ①  需要    ②  不需要\n\n\n");
		System.out.println("\n               ⓪  返回上頁");
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		selection1 = scanIntRange(1,2,"");
		if(selection1==0) menu();
		
		int selection2;
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		System.out.println("           有沒有特別訂位需求呢（靠窗或靠走道(๑¯∀¯๑)\n");
		System.out.println("            ①  有    ②  沒有\n\n\n");
		System.out.println("\n               ⓪  返回上頁");
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		if(selection1==0) menu();
		selection2 = scanIntRange(1,2,"");

		if(selection1==1 && selection2==1) normalConditionReturnTicket();
		if(selection1==1 && selection2==2) normalReturnTicket();
		if(selection1==2 && selection2==1) normalConditionTicket();
		if(selection1==2 && selection2==2) normalTicket();
	};
	/**
	 * To let users input text in a specified condition, which
	 * is an descending class of checkCondition, and print out the assigned reminder.
	 * When invalid number is input, return to the same function recursively.
	 * @param condition The condition for input data.
	 * @param message The message of the reminder.
	 * @return Valid input data.
	 */
	public static String textInput(checkCondition condition,String message) {
		String temp;
		Scanner scanner = new Scanner(System.in);
		printoutln("\n"+message+"\n\n");
		try {
			temp = scanner.nextLine();
			if(!condition.check(temp))
				throw new Exception();
			} catch(Exception e) {
				printoutln("請輸入正確的形式(๑• . •๑)");
				return textInput(condition, message);
			}
		return temp;
	}
	/**
	 * Base class for condition classes.
	 */
	public interface checkCondition {
		public abstract boolean check(String text);
	}
	/**
	 * Condition for checking UID form, which is a
	 * character plus 9-bit number.
	 */
	public static class UIDCheck implements checkCondition {
		public boolean check(String UID) {
			if(UID.equals("0"))
				return true;
			try {
				Integer.parseInt(UID.substring(1));
			} catch(Exception e) {
				return false;
			}
			if(UID.length()!=10)
				return false;
			if((int)UID.charAt(0)<65 || (int)UID.charAt(0)>90)
				return false;
			return true;
		}
	}
	/**
	 * Condition for checking date form, which is in a
	 * form of yyyy/mm/dd.
	 */
	public static class DateCheck implements checkCondition {
		
		public boolean check(String date) {
			if(date.equals("0"))
				return true;
			String[] input = date.split("/");
			if(input.length != 3)
				return false;
			if(input[0].length()!=4 || input[1].length()!=2 || input[2].length()!=2)
				return false;
			for(int i=0 ; i<3 ; i++) {
				for(int j=0 ; j<input[i].length() ; j++)
				try{
					Integer.parseInt(input[i].substring(j,j+1));
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * Condition for checking time form, which is in a
	 * form of hh::mm.
	 */
	public static class TimeCheck implements checkCondition {
		
		public boolean check(String time) {
			if(time.equals("0"))
						return true;
			String[] input = time.split(":");
			if(input.length != 2)
				return false;
			if(input[0].length() !=2 || input[1].length() !=2) 
				return false;
			for(int i=0 ; i<2 ; i++) {
				for(int j=0 ; j<input[i].length() ; j++)
				try{
					Integer.parseInt(input[i].substring(j, j+1));
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		}
	}
	/**
	 * Provide a selection menu for user to select the location
	 * with specific number, and pop out a reminder with specified message.
	 * @param message Reminder message.
	 * @return Selected Location.
	 */
	public static String selectLocation(String message) {
		int selection;
		printoutln("\n"+message);
		System.out.println("①南港站  ②台北站  ③板橋站   ④桃園站  ⑤新竹站  ⑥苗栗站 \n"
				+ "⑦台中站  ⑧彰化站  ⑨雲林站  ⑩嘉義站   ⑪台南站  ⑫左營站 \n");
		selection = scanIntRange(1, 12,"");
		if(selection == 0)
				return "0";
		
		return  selection == 1?"南港":
			    selection == 2?"台北":
		   		selection == 3?"板橋":
		   		selection == 4?"桃園":
		   		selection == 5?"新竹":
		   		selection == 6?"苗栗":
		   		selection == 7?"台中":
		   		selection == 8?"彰化":
		   		selection == 9?"雲林":
		   		selection == 10?"嘉義":
		   		selection == 11?"台南":"左營";
	}
	/**
	 * Provide a selection menu for user to select the condition for seat
	 * with specific number, and pop out a reminder with specified message.
	 * @param message Reminder message.
	 * @return Selected Condition.
	 */
	public static String selectCondition(String message) {
		int selection;
		printoutln("\n"+message);
		System.out.println("①靠窗  ②靠走道 \n");
		selection = scanIntRange(1, 2,"");
		if(selection == 0 ) 
					return "0";
		return  selection == 1?"window":"aisle";
	}
	
	/**
	 * Print message word by word in a way of typewriter,
	 * and in a new line, specially.
	 * @param message Message to be printed.
	 */
	public static void printoutln(String message) {
		for(int i=0 ; i<message.length() ; i++) {
			System.out.print(message.charAt(i));
			try {
				Thread.sleep(20);
			} catch (Exception e) {
			}
		}
		System.out.println();
	}
	
	/**
	 * Print message word by word in a way of typewriter.
	 * @param message Message to be printed.
	 */
	public static void printout(String message) {
		for(int i=0 ; i<message.length() ; i++) {
			System.out.print(message.charAt(i));
			try {
				Thread.sleep(20);
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * Provide a interface for users to fill in the data
	 * and buy tickets which are in special seat condition and
	 * have return tickets, either they want.
	 */
	public static void normalConditionReturnTicket() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		printout("請輸入您的行程資料 輸入0離開 (◕ܫ◕)\n");
		
		UID = textInput(new UIDCheck(),"請輸入身分證字號");
		if(UID.equals("0"))   menu();
		
		depart = selectLocation("請選擇出發站");
		if(depart.equals("0"))   menu();
		
		destination = selectLocation("請選擇到達站");
		if(destination.equals("0"))   menu();
		
		date = textInput(new DateCheck(),"請輸入去程日期（年/月/日) (例：2018/08/07)");
		if(date.equals("0"))   menu();
		
		time =textInput(new TimeCheck(),"請輸入去程出發時間（時:分) (例：05:20)");
		if(time.equals("0"))   menu();
		
		dateR = textInput(new DateCheck(),"請輸入回程日期（年/月/日)");
		if(dateR.equals("0"))   menu();
		
		timeR = textInput(new TimeCheck(),"請輸入回程出發時間（時:分)"); 
		if(timeR.equals("0"))   menu();
		
		condition = selectCondition("請選擇靠窗或靠走道座位");
		if(condition.equals("0"))   menu();
		
		printout("請輸入您的購票資料 輸入88離開 (◕ܫ◕)\n");
		
		numOfNormal = scanIntRange(0, 10,"請輸入一般票票數");
		if(numOfNormal == 88) menu();
		
		numOfChildren =  scanIntRange(0, 10,"請輸入兒童票票數");
		if(numOfChildren == 88) menu();
		
		numOfSenior = scanIntRange(0, 10,"請輸入敬老票票數");
		if(numOfSenior == 88) menu();
		
		numOfChallenged = scanIntRange(0, 10,"請輸入愛心票票數");
		if(numOfChallenged == 88) menu();
		
		addNewRes = new AddReservation(UID,date,time,depart,destination, 
				dateR,timeR,condition, 0,numOfNormal,numOfChildren,numOfSenior,numOfChallenged);
		try {
			creRes = addNewRes.getInstance();
			creRes.checkVacancy();
			Thread.sleep(500);
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			normalConditionReturnTicket(); 
		}
	};
	
	/**
	 * Provide a interface for users to fill in the data
	 * and buy tickets which have return tickets they want.
	 */
	public static void normalReturnTicket() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		printout("請輸入您的行程資料 輸入0離開 (◕ܫ◕)\n");
		
		UID = textInput(new UIDCheck(),"請輸入身分證字號");
		if(UID.equals("0"))   menu();
		
		depart = selectLocation("請選擇出發站");
		if(depart.equals("0"))   menu();
		
		destination = selectLocation("請選擇到達站");
		if(destination.equals("0"))   menu();
		
		date = textInput(new DateCheck(),"請輸入去程日期（年/月/日) (例：2018/08/07)");
		if(date.equals("0"))   menu();
		
		time =textInput(new TimeCheck(),"請輸入去程出發時間（時:分) (例：05:20)");
		if(time.equals("0"))   menu();
		
		dateR = textInput(new DateCheck(),"請輸入回程日期（年/月/日)");
		if(dateR.equals("0"))   menu();
		
		timeR = textInput(new TimeCheck(),"請輸入回程出發時間（時:分)"); 
		if(timeR.equals("0"))   menu();
		
		printout("請輸入您的購票資料 輸入88離開 (◕ܫ◕)\n");
		
		numOfNormal = scanIntRange(0, 10,"請輸入一般票票數");
		if(numOfNormal == 88) menu();
		
		numOfChildren =  scanIntRange(0, 10,"請輸入兒童票票數");
		if(numOfChildren == 88) menu();
		
		numOfSenior = scanIntRange(0, 10,"請輸入敬老票票數");
		if(numOfSenior == 88) menu();
		
		numOfChallenged = scanIntRange(0, 10,"請輸入愛心票票數");
		if(numOfChallenged == 88) menu();
		
		addNewRes = new AddReservation(UID,date,time,depart,destination, 
				dateR,timeR,"", 0,numOfNormal,numOfChildren,numOfSenior,numOfChallenged); 
		try {
			creRes = addNewRes.getInstance();
			creRes.checkVacancy();
			Thread.sleep(500);
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			normalConditionReturnTicket(); 
		}
	}
	
	/**
	 * Provide a interface for users to fill in the data
	 * and buy tickets in special seat condition they want.
	 */
	public static void normalConditionTicket() {
		
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		printout("請輸入您的行程資料 輸入0離開 (◕ܫ◕)\n");
		
		UID = textInput(new UIDCheck(),"請輸入身分證字號");
		if(UID.equals("0"))   menu();
		
		depart = selectLocation("請選擇出發站");
		if(depart.equals("0"))   menu();
		
		destination = selectLocation("請選擇到達站");
		if(destination.equals("0"))   menu();
		
		date = textInput(new DateCheck(),"請輸入去程日期（年/月/日) (例：2018/08/07)");
		if(date.equals("0"))   menu();
		
		time =textInput(new TimeCheck(),"請輸入去程出發時間（時:分) (例：05:20)");
		if(time.equals("0"))   menu();
		
		condition = selectCondition("請選擇靠窗或靠走道座位");
		if(condition.equals("0"))   menu();
		
		printout("請輸入您的購票資料 輸入88離開 (◕ܫ◕)\n");
		
		numOfNormal = scanIntRange(0, 10,"請輸入一般票票數");
		if(numOfNormal == 88) menu();
		
		numOfChildren =  scanIntRange(0, 10,"請輸入兒童票票數");
		if(numOfChildren == 88) menu();
		
		numOfSenior = scanIntRange(0, 10,"請輸入敬老票票數");
		if(numOfSenior == 88) menu();
		
		numOfChallenged = scanIntRange(0, 10,"請輸入愛心票票數");
		if(numOfChallenged == 88) menu();
		
		addNewRes = new AddReservation(UID,date,time,depart,destination, 
				"","",condition, 0,numOfNormal,numOfChildren,numOfSenior,numOfChallenged); 
		try {
			creRes = addNewRes.getInstance();
			creRes.checkVacancy();
			Thread.sleep(500);
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			normalConditionReturnTicket(); 
		} 
	}
	
	/**
	 * Provide a interface for users to fill in the data
	 * and buy tickets hey want.
	 */
	public static void normalTicket() {
		
	System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
	printout("請輸入您的行程資料 輸入0離開 (◕ܫ◕)\n");
	
	UID = textInput(new UIDCheck(),"請輸入身分證字號");
	if(UID.equals("0"))   menu();
	
	depart = selectLocation("請選擇出發站");
	if(depart.equals("0"))   menu();
	
	destination = selectLocation("請選擇到達站");
	if(destination.equals("0"))   menu();
	
	date = textInput(new DateCheck(),"請輸入去程日期（年/月/日) (例：2018/08/07)");
	if(date.equals("0"))   menu();
	
	time =textInput(new TimeCheck(),"請輸入去程出發時間（時:分) (例：05:20)");
	if(time.equals("0"))   menu();
	
	printout("請輸入您的購票資料 輸入88離開 (◕ܫ◕)\n");
	
	numOfNormal = scanIntRange(0, 10,"請輸入一般票票數");
	if(numOfNormal == 88) menu();
	
	numOfChildren =  scanIntRange(0, 10,"請輸入兒童票票數");
	if(numOfChildren == 88) menu();
	
	numOfSenior = scanIntRange(0, 10,"請輸入敬老票票數");
	if(numOfSenior == 88) menu();
	
	numOfChallenged = scanIntRange(0, 10,"請輸入愛心票票數");
	if(numOfChallenged == 88) menu();
	
	addNewRes = new AddReservation(UID,date,time,depart,destination, 
			"","","", 0,numOfNormal,numOfChildren,numOfSenior,numOfChallenged); 
	try {
		creRes = addNewRes.getInstance();
		creRes.checkVacancy();
		Thread.sleep(500);
		menu();
	} catch (Exception e) {
		printout(e.getMessage()+"\n\n");
		normalConditionReturnTicket(); 
	} 
		
	};
	
	
	
	/**
	 * To manifest a selection menu to let users decide which 
	 * if the purchased tickets containing college tickets
	 * should have the other returning one or in special condition.
	 */
	public static void collegeRequest() {
		int selection1;
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		System.out.println("           有沒有需要訂來回票呢(๑¯∀¯๑)\n");
		System.out.println("            ①  需要    ②  不需要\n\n\n");
		System.out.println("\n               ⓪  返回上頁");
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		selection1 = scanIntRange(1,2,"");
		if(selection1==0) menu();
		
		int selection2;
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		System.out.println("           有沒有特別訂位需求呢（靠窗或靠走道(๑¯∀¯๑)\n");
		System.out.println("            ①  有    ②  沒有\n\n\n");
		System.out.println("\n               ⓪  返回上頁");
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		if(selection1==0) menu();
		selection2 = scanIntRange(1,2,"");

		if(selection1==1 && selection2==1) collegeConditionReturnTicket();
		if(selection1==1 && selection2==2) collegeReturnTicket();
		if(selection1==2 && selection2==1) collegeConditionTicket();
		if(selection1==2 && selection2==2) collegeTicket();
	};
	
	
	/**
	 * Provide a interface for users to fill in the data
	 * and buy tickets containing college type ones having
	 * return tickets and in a special condition they want.
	 */
	public static void collegeConditionReturnTicket() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		printout("請輸入您的行程資料 輸入0離開 (◕ܫ◕)\n");
		
		UID = textInput(new UIDCheck(),"請輸入身分證字號");
		if(UID.equals("0"))   menu();
		
		depart = selectLocation("請選擇出發站");
		if(depart.equals("0"))   menu();
		
		destination = selectLocation("請選擇到達站");
		if(destination.equals("0"))   menu();
		
		date = textInput(new DateCheck(),"請輸入去程日期（年/月/日) (例：2018/08/07)");
		if(date.equals("0"))   menu();
		
		time = textInput(new TimeCheck(),"請輸入去程出發時間（時:分) (例：05:20)");
		if(time.equals("0"))   menu();
		
		dateR = textInput(new DateCheck(),"請輸入回程日期（年/月/日)");
		if(dateR.equals("0"))   menu();
		
		timeR = textInput(new TimeCheck(),"請輸入回程出發時間（時:分)"); 
		if(timeR.equals("0"))   menu();
		
		condition = selectCondition("請選擇靠窗或靠走道座位");
		if(condition.equals("0"))   menu();
		
		printout("請輸入您的購票資料 輸入88離開 (◕ܫ◕)\n");
		
		numOfCollege = scanIntRange(0, 10,"請輸入大學生票數");
		while (numOfCollege == 0) 
			numOfCollege = scanIntRange(0, 10,"大學生票數不得為0");
		if(numOfCollege == 88) menu();
		
		numOfNormal = scanIntRange(0, 10,"請輸入一般票票數");
		if(numOfNormal == 88) menu();
		
		numOfChildren =  scanIntRange(0, 10,"請輸入兒童票票數");
		if(numOfChildren == 88) menu();
		
		numOfSenior = scanIntRange(0, 10,"請輸入敬老票票數");
		if(numOfSenior == 88) menu();
		
		numOfChallenged = scanIntRange(0, 10,"請輸入愛心票票數");
		if(numOfChallenged == 88) menu();
		
		
		addNewRes = new AddReservation(UID,date,time,depart,destination, 
				dateR,timeR,condition, numOfCollege,numOfNormal,numOfChildren,numOfSenior,numOfChallenged); 
		try {
			creRes = addNewRes.getInstance();
			creRes.checkVacancy();
			Thread.sleep(500);
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			normalConditionReturnTicket(); 
		} 
	};
	
	/**
	 * Provide a interface for users to fill in the data
	 * and buy tickets containing college type ones having
	 * return tickets they want.
	 */
	public static void collegeReturnTicket() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		printout("請輸入您的行程資料 輸入0離開 (◕ܫ◕)\n");
		
		UID = textInput(new UIDCheck(),"請輸入身分證字號");
		if(UID.equals("0"))   menu();
		
		depart = selectLocation("請選擇出發站");
		if(depart.equals("0"))   menu();
		
		destination = selectLocation("請選擇到達站");
		if(destination.equals("0"))   menu();
		
		date = textInput(new DateCheck(),"請輸入去程日期（年/月/日) (例：2018/08/07)");
		if(date.equals("0"))   menu();
		
		time =textInput(new TimeCheck(),"請輸入去程出發時間（時:分) (例：05:20)");
		if(time.equals("0"))   menu();
		
		dateR = textInput(new DateCheck(),"請輸入回程日期（年/月/日)");
		if(dateR.equals("0"))   menu();
		
		timeR = textInput(new TimeCheck(),"請輸入回程出發時間（時:分)"); 
		if(timeR.equals("0"))   menu();
		
		printout("請輸入您的購票資料 輸入88離開 (◕ܫ◕)\n");
		
		numOfCollege = scanIntRange(0, 10,"請輸入大學生票數");
		while (numOfCollege == 0) 
			numOfCollege = scanIntRange(0, 10,"大學生票數不得為0");
		if(numOfCollege == 88) menu();
		
		numOfNormal = scanIntRange(0, 10,"請輸入一般票票數");
		if(numOfNormal == 88) menu();
		
		numOfChildren =  scanIntRange(0, 10,"請輸入兒童票票數");
		if(numOfChildren == 88) menu();
		
		numOfSenior = scanIntRange(0, 10,"請輸入敬老票票數");
		if(numOfSenior == 88) menu();
		
		numOfChallenged = scanIntRange(0, 10,"請輸入愛心票票數");
		if(numOfChallenged == 88) menu();
		
		addNewRes = new AddReservation(UID,date,time,depart,destination, 
				dateR,timeR,"", numOfCollege,numOfNormal,numOfChildren,numOfSenior,numOfChallenged); 
		try {
			creRes = addNewRes.getInstance();
			creRes.checkVacancy();
			Thread.sleep(500);
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			normalConditionReturnTicket(); 
		}	
	}
	/**
	 * Provide a interface for users to fill in the data
	 * and buy tickets containing college type ones which 
	 * are in special seat condition.
	 */
	public static void collegeConditionTicket() {
		
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		printout("請輸入您的行程資料 輸入0離開 (◕ܫ◕)\n");
		
		UID = textInput(new UIDCheck(),"請輸入身分證字號");
		if(UID.equals("0"))   menu();
		
		depart = selectLocation("請選擇出發站");
		if(depart.equals("0"))   menu();
		
		destination = selectLocation("請選擇到達站");
		if(destination.equals("0"))   menu();
		
		date = textInput(new DateCheck(),"請輸入去程日期（年/月/日) (例：2018/08/07)");
		if(date.equals("0"))   menu();
		
		time =textInput(new TimeCheck(),"請輸入去程出發時間（時:分) (例：05:20)");
		if(time.equals("0"))   menu();
		
		condition = selectCondition("請選擇靠窗或靠走道座位");
		if(condition.equals("0"))   menu();
		
		printout("請輸入您的購票資料 輸入88離開 (◕ܫ◕)\n");
		
		numOfCollege = scanIntRange(0, 10,"請輸入大學生票數");
		while (numOfCollege == 0) 
			numOfCollege = scanIntRange(0, 10,"大學生票數不得為0");
		if(numOfCollege == 88) menu();
		
		numOfNormal = scanIntRange(0, 10,"請輸入一般票票數");
		if(numOfNormal == 88) menu();
		
		numOfChildren =  scanIntRange(0, 10,"請輸入兒童票票數");
		if(numOfChildren == 88) menu();
		
		numOfSenior = scanIntRange(0, 10,"請輸入敬老票票數");
		if(numOfSenior == 88) menu();
		
		numOfChallenged = scanIntRange(0, 10,"請輸入愛心票票數");
		if(numOfChallenged == 88) menu();
		
		addNewRes = new AddReservation(UID,date,time,depart,destination, 
				"","",condition, numOfCollege,numOfNormal,numOfChildren,numOfSenior,numOfChallenged); 
		try {
			creRes = addNewRes.getInstance();
			creRes.checkVacancy();
			Thread.sleep(500);
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			normalConditionReturnTicket(); 
		} 
	}
	/**
	 * Provide a interface for users to fill in the data
	 * and buy tickets containing college type ones.
	 */
	public static void collegeTicket() {
		
			System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
			printout("請輸入您的行程資料 輸入0離開 (◕ܫ◕)\n");
			
			UID = textInput(new UIDCheck(),"請輸入身分證字號");
			if(UID.equals("0"))   menu();
			
			depart = selectLocation("請選擇出發站");
			if(depart.equals("0"))   menu();
			
			destination = selectLocation("請選擇到達站");
			if(destination.equals("0"))   menu();
			
			date = textInput(new DateCheck(),"請輸入去程日期（年/月/日) (例：2018/08/07)");
			if(date.equals("0"))   menu();
			
			time =textInput(new TimeCheck(),"請輸入去程出發時間（時:分) (例：05:20)");
			if(time.equals("0"))   menu();
			
			printout("請輸入您的購票資料 輸入88離開 (◕ܫ◕)\n");
			
			numOfCollege = scanIntRange(0, 10,"請輸入大學生票數");
			while (numOfCollege == 0) 
				numOfCollege = scanIntRange(0, 10,"大學生票數不得為0");
			
			if(numOfCollege == 88) menu();
			
			numOfNormal = scanIntRange(0, 10,"請輸入一般票票數");
			if(numOfNormal == 88) menu();
			
			numOfChildren =  scanIntRange(0, 10,"請輸入兒童票票數");
			if(numOfChildren == 88) menu();
			
			numOfSenior = scanIntRange(0, 10,"請輸入敬老票票數");
			if(numOfSenior == 88) menu();
			
			numOfChallenged = scanIntRange(0, 10,"請輸入愛心票票數");
			if(numOfChallenged == 88) menu();
			
			addNewRes = new AddReservation(UID,date,time,depart,destination, 
					"","","",numOfCollege,numOfNormal,numOfChildren,numOfSenior,numOfChallenged); 
			try {
				creRes = addNewRes.getInstance();
				creRes.checkVacancy();
				Thread.sleep(500);
				menu();
			} catch (Exception e) {
				printout(e.getMessage()+"\n\n");
				normalConditionReturnTicket(); 
			} 			
	};

	
	/**
	 * Provide a  selection menu for users to choose to
	 *  modify the transaction they have made or delete it.
	 */
	public static void refundRequest() {
		int selection;
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		System.out.println("             請問需要哪一種服務呢(๑¯∀¯๑)\n");
		System.out.println("            ①  減少人數    ②  取消行程\n\n\n");
		System.out.println("\n                 ⓪  返回上頁");
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		selection = scanIntRange(1,2,"");
		if(selection == 0) menu();
		if(selection == 1 ) modify();
		if(selection == 2 ) delete();
	};
	
	/**
	 * Let users to select types of tickets by numbers.
	 * And print out a reminder with specified message.
	 * @param message Reminder Message.
	 * @return Ticket type.
	 */
	public static String selectType(String message) {
		int selection;
		printoutln("\n"+message);
		System.out.println("①一般票  ②大學生票  ③兒童票  ④敬老票  ⑤愛心票 \n");
		selection = scanIntRange(1, 5,"");
		if(selection == 0 ) 
					return "0";
		
		return selection == 1?"numOfNormal":
			   selection == 2?"numOfCollege":
			   selection == 3?"numOfChildren":
			   selection == 4?"numOfSenior":"numOfChallenged";
				
	}
	/**
	 * Provide a menu for users to fill in data and 
	 * modify The transaction.
	 */
	public static void modify() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		UID = textInput(new UIDCheck(),"請輸入您的身分證字號\n");
		reservationNo = textInput(new RNCheck(),"請輸入訂位代號");
		addNewMod = new AddModify(UID, reservationNo); 
		try {
			creMod = addNewMod.getInstance();
			creMod.checkModify();
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			modify();
		} 
	};
	
	/**
	 * Provide a menu for users to fill in data and 
	 * delete The transaction.
	 */
	public static void delete() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		UID = textInput(new UIDCheck(),"請輸入您的身分證字號\n");
		reservationNo = textInput(new RNCheck(),"請輸入訂位代號");
		addNewMod = new AddModify (UID, reservationNo); 
		try {
			creMod = addNewMod.getInstance();
			System.out.println("client 881 ");
			creMod.checkDelete();	
			printoutln("刪除成功");
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			delete();
		} 
		
	};
	/**
	 * Provide a selection menu for users to choose what 
	 * to be searched by system.
	 */
	public static void searchRequest() {
		int selection;
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		System.out.println("          請問需要哪一種服務呢(๑¯∀¯๑)\n");
		System.out.println("       ①  查詢時刻表     ②  查詢優惠車次\n");
		System.out.println("       ③  查詢訂位紀錄   ④  查詢訂位代號\n\n\n");
		System.out.println("\n               ⓪  返回上頁");
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		selection = scanIntRange(1,4,"");
		if(selection == 0) menu();
		if(selection == 1 ) timetable();
		if(selection == 2 ) concession();
		if(selection == 3 ) transaction();
		if(selection == 4 ) transactionNoRN();
	};
	
	/**
	 * Provide a selection menu for user to select 
	 * which is the time condition for searching, and 
	 * print out a reminder of specified message.
	 * @param message Reminder Message
	 * @return the search condition of time.
	 */
	public static String selectTimeCondition(String message) {
		int selection;
		printoutln("\n"+message);
		System.out.println("①出發  ②抵達 \n");
		selection = scanIntRange(1, 2,"");
		if(selection == 0 ) 
					return "0";
		return  selection == 1?"depart":"arrive";
	}
	
	/**
	 * To find the transaction without reservation numbers.
	 */
	public static void transactionNoRN() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		UID = textInput(new UIDCheck(),"請輸入您的身分證字號\n");
		date = textInput(new DateCheck(),"請輸入訂票之車程日期\n");
		depart = selectLocation("請選擇出發地點");
		destination = selectLocation("請選擇抵達地點");
		printout("請輸入訂票之車次號碼");
		String trainNo = new Scanner(System.in).nextLine();
		addNewChe= new AddCheck(UID, "",destination,depart, 
				date,trainNo,"","","","","");
		try {
			creChe = addNewChe.getInstance();
			printoutln("client line 940");
			creChe.checkWithoutRn();
			printoutln("按Enter鍵繼續 ฅ●ω●ฅ");
			new Scanner(System.in).nextLine();
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			timetable();
		} 
	}
	
	/**
	 * The condition for checking reservation numbers, which
	 * are in a form of 8-bit number.
	 */
	public static class RNCheck implements checkCondition {
		
		public boolean check(String RN) {
			if(RN.length() != 8)
					return false;
			try {
				Integer.parseInt(RN);
			} catch(Exception e) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * Provide a menu for users to find the transaction with UID
	 * and reservation number.
	 */
	public static void transaction() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		UID = textInput(new UIDCheck(),"請輸入您的身分證字號\n");
		reservationNo = textInput(new RNCheck(),"請輸入訂位代號");
		addNewChe = new AddCheck(UID, reservationNo,"","","","","","","","",""); 
		try {
			creChe = addNewChe.getInstance();
			creChe.checkWithRn();
			printoutln("按Enter鍵繼續 ฅ●ω●ฅ");
			new Scanner(System.in).nextLine();
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			transaction();
		}
	}
	
	/**
	 * Provide a menu for searching trains of special date with concessions.
	 */
	public static void concession() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		date = textInput(new DateCheck(),"請輸入欲查詢之日期 (Ex:2018/08/08)\n");
		condition = selectTimeCondition("請問要根據車次出發時間是抵達時間查詢呢");
		time = textInput(new TimeCheck(),"請輸入條件時間 (Ex:05:20)\n");
		depart = selectLocation("請選擇出發地點");
		destination = selectLocation("請選擇抵達地點");
		addNewChe = new AddCheck("","","","","","",date,time,condition,depart,destination);
		try {
			creChe = addNewChe.getInstance();
			creChe.checkConcessionTable();
			printoutln("按Enter鍵繼續 ฅ●ω●ฅ");
			new Scanner(System.in).nextLine();
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			timetable();
		}	
	}
	
	/**
	 * Provide a menu for user to find timetable of special date.
	 */
	public static void timetable() {
		System.out.println("▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n");
		date = textInput(new DateCheck(),"請輸入欲查詢之日期(◕ܫ◕)\n");
		addNewChe = new AddCheck("","","","","","",date,"","","","");
		try {
			creChe = addNewChe.getInstance();
			creChe.checkNormalTable();
			printoutln("按Enter鍵繼續 ฅ●ω●ฅ");
			new Scanner(System.in).nextLine();
			menu();
		} catch (Exception e) {
			printout(e.getMessage()+"\n\n");
			timetable();
		} 
	}
	
}

