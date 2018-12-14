package controll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

/**
 * This class is used to parse the online time table. 
 * @author Jerry
 */
public class TimeTableParser {

	public Document doc;
	public final String direction[] = {"timeTable_down", "timeTable_up"};
	
	/**
	 * Request the time table html file.
	 * @param url target url
	 * @throws IOException IOException
	 */
	public void getDoc(String url) throws IOException {
		doc = Jsoup.connect(url).get();
	}
	
	/**
	 * Parse the time table and insert to mysql.
	 * @param date update date.
	 * @param dir go south or north.
	 * @throws SQLException SQLException
	 */
	public void parseDate(String date, int dir) throws SQLException {
		try {
			Elements table = doc.select("table[bgcolor$=#CCCCCC]");
			Elements down_tables = table.get(dir).getElementsByTag("tr");
			for (Element row: down_tables) {
				// get train_id
				String train_id = row.getElementsByClass("text_orange_link").html();
				if (train_id.equals("")) continue;
				
				// get time
				//System.out.println("Train id: " + train_id);
				ArrayList<String> timeArray = new ArrayList<String>();
				Elements stations = row.getElementsByClass("text_grey2");
	
				timeArray.add(date);
				timeArray.add(train_id);
				for (Element station: stations) {
					String timeStr = station.html();
					if (timeStr.equals("")) timeStr = "-1";
					else timeStr = timeStr.replace(":", "");
					timeArray.add(timeStr);
				}
				
				String sql = String.format("INSERT INTO %s VALUES (%s)", direction[dir], String.join(", ", timeArray));
				MysqlExe.execStmt(sql);
			}	
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No available train. dir = " + dir);
		}
	}
	
	/**
	 * Update the time table of date.
	 * @param date update date.
	 * @throws SQLException SQLException
	 * @throws IOException IOException
	 */
	public void updateDay(String date) throws SQLException, IOException {
		System.out.println("Parsing date " + date);
		MysqlExe.RetVal ret= MysqlExe.execQuery("SELECT COUNT(*) FROM timeTable_down WHERE date = " + date);
		ret.res.next();
		int count = ret.res.getInt(1);
		if (count > 0) {
			System.out.println("Data is already in the DB.");
			return;
		}
		ret.conn.close();
		MysqlExe.execStmt("DELETE FROM timeTable_down WHERE date = " + date);
		MysqlExe.execStmt("DELETE FROM timeTable_up WHERE date = " + date);		
		getDoc("http://www.thsrc.com.tw/tw/TimeTable/DailyTimeTable/" + date);
		parseDate(date, 0);
		parseDate(date, 1);	
		System.out.println("Successed");
	}
	
//	public static void main(String[] args) throws SQLException, IOException {
//		TimeTableParser T = new TimeTableParser();
//		T.updateDay("20180527");
//	}

}
