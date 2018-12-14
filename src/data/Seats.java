package data;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import controll.MysqlExe;

/**
 * This class record some constant value and provide some useful methods.
 * @author Jerry
 */
public class Seats {
	public static final int FETCH_DAYS = 28;
	public static final String[] WEEKDAYS = {
		"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
	};
	public static final String[] TICKET_TYPE = {
			"標準", "學生", "愛心/孩童/敬老", "商務" //, "早鳥"
		};
	public static final String[] TICKET_TYPE_2 = {
			"標準", "學生", "愛心/孩童/敬老", "商務", "早鳥"
		};
	public static final String[] SEAT_TYPE = {
			"隨機", "靠窗", "靠走道"
		};
		
	
	public static final int BUSS_CABIN = 5;	// 0 base
	public static final int TOTAL_CABIN = 9;
	public static final int TOTAL_SEATS = 733; 
	public static final int CABIN_SEATS[] = {63, 96, 88, 96, 83, 66, 57, 96 ,88};
	public static final String[][] VALID_SEATS;
	
	public static final int WINDOW = 1;	
	public static final int SIDE = 2;
	
	static {
		// initialize valid seats
		VALID_SEATS = new String[TOTAL_CABIN][];
		ArrayList<String> tmp = new ArrayList<String>();
		// cabin 1, 3, 5, 9
		tmp.clear();
		tmp.addAll(Arrays.asList("01A", "01B", "01C"));
		for (int i = 2; i <= 13; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[0] = tmp.toArray(new String[tmp.size()]);
		for (int i = 14; i <= 17; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[4] = tmp.toArray(new String[tmp.size()]);
		for (int i = 18; i <= 18; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[2] = tmp.toArray(new String[tmp.size()]);
		VALID_SEATS[8] = tmp.toArray(new String[tmp.size()]);
		// cabin 2, 4, 8
		tmp.clear();
		for (int i = 1; i <= 20; i++) {
			for (int j = 0; j < 3; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		for (int i = 2; i <= 19; i++) {
			for (int j = 3; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[1] = tmp.toArray(new String[tmp.size()]);
		VALID_SEATS[3] = tmp.toArray(new String[tmp.size()]);
		VALID_SEATS[7] = tmp.toArray(new String[tmp.size()]);
		// cabin 6
		tmp.clear();
		tmp.addAll(Arrays.asList("01D", "01E"));
		for (int i = 2; i <= 17; i++) {
			for (int j = 0; j < 5; j++) {
				if (j == 1) continue;
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[5] = tmp.toArray(new String[tmp.size()]);
		// cabin 7
		tmp.clear();
		for (int i = 1; i <= 11; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		tmp.addAll(Arrays.asList("12D", "12E"));
		//tmp.addAll(Arrays.asList("NO1", "NO2", "NO3", "NO4"));
		VALID_SEATS[6] = tmp.toArray(new String[tmp.size()]);		
	}
	
	/**
	 * Convert the date format.
	 * @param date The date whose format is "YYYY/MM/DD".
	 * @return The integer format.
	 */
	public static int getDateInt(String date) {
		date = date.replace("/", "");
		return Integer.parseInt(date);
		
	}
	
	/**
	 * Convert the date to the weekday.
	 * @param date The date whose format is "YYYY/MM/DD".
	 * @return The week day.
	 */
	public static int getWeekDay(int date) {
		SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd");
		Date dat = null;
		try {
			dat = format1.parse(Integer.toString(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(dat);
		return (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;
	}
	
	/**
	 * Convert the date to the weekday.
	 * @param date The date whose format is integer.
	 * @return The week day.
	 */
	public static int getWeekDay(String date) {
		SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd");
		Date dat = null;
		try {
			dat = format1.parse(date.replace("/", ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(dat);
		return (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;
	}
	
	/**
	 * Get the duration by the given two time.
	 * @param t1 Start time.
	 * @param t2 End time.
	 * @return Duration.
	 */
	public static int getDur(int t1, int t2) {
		int h1 = t1 / 100;
		int m1 = t1 % 100;
		int h2 = t2 / 100;
		int m2 = t2 % 100;
		
		int time = (h2 * 60 + m2) - (h1 * 60 + m1);
		int hh = time / 60;
		int mm = time % 60;
		return hh * 100 + mm;
	}
	
	/**
	 * This method is used to test the class. 
	 * @param args No args.
	 * @throws SQLException SQLException
	 */
	public static void main(String[] args) throws SQLException {
		for (int i = 0; i < 9; i++) {
			for (String seat: VALID_SEATS[i]) {
				int side = 0;
				int business = 0;
				if (seat.charAt(2) == 'A' || seat.charAt(2) == 'E') side = 1;
				if (seat.charAt(2) == 'C' || seat.charAt(2) == 'D') side = 2;
				if (i == 5) business = 1;
//				mysqlExe.setDebug(1);
				MysqlExe.execStmt(String.format("INSERT INTO allSeat VALUES (\"%02d%s\", %d, %d)", i + 1, seat, side, business));
			}
			System.out.println(VALID_SEATS[i].length);
			for (String st: VALID_SEATS[i]) System.out.print(st + " ");
			System.out.println("");
		}
	}	
}
