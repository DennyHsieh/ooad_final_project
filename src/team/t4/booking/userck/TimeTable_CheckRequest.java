package team.t4.booking.userck;

import team.t4.booking.db.dbManager;

public class TimeTable_CheckRequest implements CheckRequest {
	
	private String date;
	
	public TimeTable_CheckRequest(String date) {
		this.date = date;
	}
	
	public void print() {
		
		String[][] south = dbManager.getSouthTimeTable(date);
		String[][] north = dbManager.getNorthTimeTable(date);
		
		int row = 0;	
		System.out.println("\n\n");
		System.out.println("<<南下列車>>"+"\n");
		System.out.println("<車次>    <南港站>   <台北站>   <板橋站>   <桃園站>   <新竹站>   <苗栗站>   <台中站>   <彰化站>   <雲林站>   <嘉義站>   <台南站>   <左營站>");
		System.out.println("");
		while(row < south.length && south[row][0]!=null) {
			System.out.print("["+south[row][0]+"]    ");	
			for(int i=1; i<south[row].length ; i++) {
				if(south[row][i].equals(""))
					System.out.print("          ");
				else
					System.out.print(south[row][i]+"     ");	
			}
			System.out.println("\n");
			row++;
		}
		
		row = 0;	
		System.out.println("\n\n");
		System.out.println("<<北上列車>>"+"\n");
		System.out.println("<車次>    <左營站>   <台南站>   <嘉義站>   <雲林站>   <彰化站>   <台中站>   <苗栗站>   <新竹站>   <桃園站>   <板橋站>   <台北站>   <南港站>");
		System.out.println("");
		while(row < north.length && north[row][0]!=null) {
			System.out.print("["+north[row][0]+"]    ");	
			for(int i=1; i<north[row].length ; i++) {
				if(north[row][i].equals(""))
					System.out.print("          ");
				else
					System.out.print(north[row][i]+"     ");	
			}
			System.out.println("\n");
			row++;
		}
		
		
	}
}
