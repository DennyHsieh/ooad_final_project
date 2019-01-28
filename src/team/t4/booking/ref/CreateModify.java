package team.t4.booking.ref;

import java.util.Scanner;

import team.t4.booking.db.Transaction;
import team.t4.booking.userck.CheckTransaction;

/**
 * This request is foe decrease amount of tickets
 * of special transaction which users made.
 */
public class CreateModify {
	
	private String UID="";
	private String reservationNo="";
	private String[][] searchResult;
	private int refundAmount=0;
	private String type="";
	
	CreateModify (String UID, String reservationNo) {
		this.UID = UID;
		this.reservationNo = reservationNo;
	}
	
	
	public String[] checkModify() throws Exception {
		String[] result = new String[2];
		try {
			CheckTransaction checkTransaction = new CheckTransaction();
			searchResult = checkTransaction.getTransactWithRn(UID, reservationNo);
			int i = 0;
			 while(i<2 && searchResult[i][0]!=null) {
				 result[i] = searchResult[i][6]+" "+
						 searchResult[i][1]+" "+
						 searchResult[i][7]+" 到 "+searchResult[i][8]+" "+
						 "出發:"+searchResult[i][3]+" "+"到達:"+searchResult[i][4]+" "+
						 searchResult[i][5]+searchResult[i][10]+" \n"+
						 "一般票:"+searchResult[i][12]+"張 "+
						 "大學生票:"+searchResult[i][13]+"張 "+
						 "兒童票:"+searchResult[i][14]+"張 "+
						 "敬老票:"+searchResult[i][15]+"張 "+
						 "愛心票:"+searchResult[i][16]+"張 "+
						 "總價:"+searchResult[i][9];
				 i++;
			 }
			 if(i== 0) {
					throw new Exception();
				}
			 return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public void executeModify(int selection, String type, int reduce) {
		
		// numOfCollege numOfNormal numOfSenior numOfChallenged numOfChildren
//		int select = 0;
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("請選擇要修改的交易紀錄");
//		select = (scanner.nextInt()-1);
		
		// type number, totalnumber 
//		int[] Num = this.setType(selectType("請選擇要減少人數的票種"),select);
//		System.out.println("createModify 60 int[] Num = "+Num[0]+Num[1]);
//		while (Num[0] == 0 ) {
//			System.out.println("不可選擇票種為零的項目");
//			Num = this.setType(selectType("請選擇要減少人數的票種"),select);
//		}
//		while (Num[0] == Num[1] && Num[0] == 1 ) {
//			System.out.println("不可選擇刪除後總票數為零的種類，若欲刪除交易資料，請使用刪除功能");
//			Num = this.setType(selectType("請選擇要減少人數的票種"),select);
//		}
		
//		System.out.println("請輸入要減少的數量");
//		int reduce = scanner.nextInt();
//		while (Num[0] - reduce < 0 ) {
//			System.out.println("不可選擇刪除後小於零的數字");
//			System.out.println("請輸入要減少的數量");
//			reduce = scanner.nextInt();
//		}
		this.setAmount(reduce);
		System.out.println("createModify 78");
		Modify modify = new Modify();
		modify.modifySelect(selection, searchResult, reservationNo, reduce, type);
	}
	


	public String[] checkDelete() throws Exception {
		String[] result = new String[2];
		try {
			CheckTransaction checkTransaction = new CheckTransaction();
			searchResult = checkTransaction.getTransactWithRn(UID, reservationNo);
			System.out.print("checkmodify 81 ");
			int i = 0;
			 while(i<2 && searchResult[i][0]!=null) {
				 result[i]=searchResult[i][6]+" "+
						 searchResult[i][1]+" "+
						 searchResult[i][7]+" 到 "+searchResult[i][8]+" "+
						 "出發:"+searchResult[0][3]+" "+"到達:"+searchResult[i][4]+" "+
						 searchResult[i][5]+searchResult[i][10]+" \n"+
						 "一般票:"+searchResult[i][12]+"張 "+
						 "大學生票:"+searchResult[i][13]+"張 "+
						 "兒童票:"+searchResult[i][14]+"張 "+
						 "敬老票:"+searchResult[i][15]+"張 "+
						 "愛心票:"+searchResult[i][16]+"張 "+
						 "總價:"+searchResult[i][9];
				 i++;
			 }
			 return result;	 
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
		
	public void excuteDelete(int selection) {
		try {
			Modify modify = new Modify();
			modify.DeleteSelect(searchResult, selection, reservationNo);
			searchResult = null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Let users to select types of tickets by numbers.
	 * And print out a reminder with specified message.
	 * @param message Reminder Message.
	 * @return Ticket type.
	 */
	public String selectType(String message) {
		Scanner scanner = new Scanner(System.in);
		int selection;
		System.out.println("\n"+message);
		System.out.println("①一般票  ②大學生票  ③兒童票  ④敬老票  ⑤愛心票 \n");
		selection = scanner.nextInt();
		if(selection == 0 ) 
					return "0";
		System.out.println("createModify 126");
		return selection == 1?"numOfNormal":
			   selection == 2?"numOfCollege":
			   selection == 3?"numOfChildren":
			   selection == 4?"numOfSenior":"numOfChallenged";
				
	}
	/**
	 * Set the decreasing amount.
	 * @param amount The decreasing amount.
	 */
	public void setAmount(int amount) {
		this.refundAmount = amount;
	}
	/**
	 * Set the type of the ticket to be decreased.
	 * @param Selected item number of the printed information.
	 * @return The current amount of the selected type, and total number
	 * of the tickets for now.
	 */
	public int[] setType(String type ,int select) {
		this.type = type;
		System.out.println("createModify 148 type = "+this.type);
		System.out.println("select ="+select);
		System.out.println("searchResult[select-1][0] = "+searchResult[select][0]);

		int totalNum = Integer.parseInt(searchResult[select][12])+
					   Integer.parseInt(searchResult[select][13])+
					   Integer.parseInt(searchResult[select][14])+
					   Integer.parseInt(searchResult[select][15])+
					   Integer.parseInt(searchResult[select][16]);
		System.out.println("createModify 154 ");
		int typeNum =  type == "numOfNormal"?Integer.parseInt(searchResult[select][12]):
					   type == "numOfCollege"?Integer.parseInt(searchResult[select][13]):
				       type == "numOfChildren"?Integer.parseInt(searchResult[select][14]):	
					   type == "numOfSenior"?Integer.parseInt(searchResult[select][15]):Integer.parseInt(searchResult[select-1][16]);
		return new int[] {typeNum,totalNum};   
	}
	
}
