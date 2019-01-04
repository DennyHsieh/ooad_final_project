package team.t4.booking.db;

public class Fortest {
	
	public static void main (String[] args) {
		
		String[][] a = {{"1","2","3","4","5"},{"6","7","8","9","10"}};
		String[][] b = new String[2][5];
		b[0][3] = null;
		b[0] = a[0];
		
		for (String e : b[1]) {
			System.out.println(e);
		}
		
		
	}

}
