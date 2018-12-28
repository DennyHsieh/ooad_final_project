package team.t4.booking.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
/**
 * This class provided only one public method to check
 * whether the data need updating, for example, deleting 
 * old databases and add new databases , which containing
 * time table that is obtained from the web.
 */
public class TableCrawler {
	
	private final static String projectPath =  System.getProperty("user.dir");
	private final String earlyFile = ReadFile(projectPath + "/earlyDiscount.json");
	private final String collegeFile = ReadFile(projectPath + "/universityDiscount.json");
	
	/**
	 * Check if the data need updating. That is,
	 * delete old files that is not worth reserving,
	 * and crawl new necessary data.
	 */
	public static void checkUpdate() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		
		Date date = new Date();
		calendar.setTime(date);
		String checkDate = sdFormat.format(calendar.getTime());
		String[] recorder = new String[30];
		String lastLoggin="";
		int	recItr = 0;
		
		String sql = "SELECT loggin FROM record;";
		try (Connection conn = connect("Ticket");
			 Statement stmt = conn.createStatement();
			 ResultSet rst = stmt.executeQuery(sql)) {
			 lastLoggin = rst.getString(1);
			 
			if(lastLoggin != checkDate) {
				sql = "UPDATE record SET loggin = "+checkDate+";";
				stmt.execute(sql);
				if(lastLoggin == null)
					lastLoggin = checkDate;
			}
		} catch (Exception e) {
			if(e.getMessage() == "ResultSet closed") {
				try(Connection conn = connect("Ticket");
					Statement stmt = conn.createStatement();){
					sql = "INSERT INTO record (loggin) VALUES( "+checkDate+");";	
					stmt.execute(sql);
					lastLoggin = checkDate;
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
			else
			System.out.println(e.getMessage());
		}
		
		Date lastDate;
		try {
			lastDate = sdFormat.parse(lastLoggin);
		} catch (ParseException e) {
			lastDate = date;
		}
		Long day = (date.getTime() - lastDate.getTime())/86400000;
		for(int i=0 ; i<day ; i++) {
			calendar.add(Calendar.DATE,-1);
			deleteDataBase(sdFormat.format(calendar.getTime()));
		}
		calendar.add(Calendar.DATE,day.intValue());
		
		for (int i=0 ; i<=28 ; i++) {
			checkDate = sdFormat.format(calendar.getTime());
			if(!(new File(projectPath+"/"+checkDate+".db").exists())) {
				recorder[recItr++] = checkDate;
				System.out.println("file name : "+checkDate+".db does not exist");
			}
			calendar.add(Calendar.DATE,1);
		}
		if(recorder[0]!=null) {
			TableCrawler crawler = new TableCrawler();
			for (int i=0 ; i<recItr ; i++) {		
				crawler.updateData(recorder[i]);
			}
			crawler = null;
			System.out.println("Completed.");
		}
		else {
			System.out.println("Nothing need updating");
			return;
		}
	}
	
	/**
	 * Crawl the needed data of the specified date.
	 * @param date Specified date.
	 */
	private void updateData (String date) {
		createNewTimeTable("South",date);
		createNewTimeTable("North",date);
		inputTimeTable("South", date);
		inputTimeTable("North", date);
		String[] trainNos = getTrainNumbers(date);
		int i=0;
		while(true) {
			if(trainNos[i]==null)
				break;
			createTrainSeats(trainNos[i],date);
			initialTrainSeats(trainNos[i],date);
			i++;	
		}
	}
	
	
	/**
	 * Delete the data base with specified date.
	 * @param date Specified dataBase
	 */
	private static void deleteDataBase(String date) {
			try{
				File file = new File(projectPath + "/"+date+".db");
				if(file.delete())
					System.out.println(file.getName() + " is deleted!");
				else
					System.out.println("Delete operation is failed.");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}
	
	/**
	 * Initialize the train seats data with the specified train number and date.
	 * @param trainNo Specified train number.
	 * @param date Specified date.
	 */
	private void initialTrainSeats(String trainNo,String date ) {
		String sql ="ATTACH DATABASE 'Ticket.db' AS '"+date+".db' ;";
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement()){
			stm.execute(sql);
			sql = "INSERT INTO Train"+trainNo+date+" SELECT * FROM TrainSeatSample";
			stm.execute(sql);
			sql = "DETACH database'"+date+".db'";
			stm.execute(sql);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Get all the train numbers of the date.
	 * @param date The specified date.
	 * @return String array for the train numbers of the specified date.
	 */
	private String[]  getTrainNumbers(String date) {
		String[] trains = new String[300];
		int j=0;
		for(int i=0 ; i<2 ; i++) {
			String sql;
			if(i==0)
				sql = "SELECT 車次 FROM "+"South"+date;
			else
				sql = "SELECT 車次 FROM "+"North"+date;
			try (Connection conn = connect(date);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					trains[j++] = rs.getString(1);
				} 
			}catch (SQLException e) {
				System.out.println(e.getMessage());
				return null;
			}
		}return trains;
	}
	
	/**
	 * Create a new blank trains seat table with specified train number and date.
	 * @param trainNo Specified train number.
	 * @param date Specified date.
	 */
	private void createTrainSeats(String trainNo,String date) {
		String sql = "CREATE TABLE IF NOT EXISTS "+"Train"+trainNo+date+"("
				+ "No text PRIMARY KEY,"
				+ "A text,"
				+ "B text,"
				+ "C text,"
				+ "D text,"
				+ "E text);";
		try(Connection conn = connect(date);
			Statement stm = conn.createStatement();) {
			stm.execute(sql);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Fill in the timetable with crawled data form the official website 
	 * and concession information, which is saved as json file at the local place.
	 * @param direction The rail direction for trains.
	 * @param date The date when the timetable is valid.
	 */
	private void inputTimeTable(String direction, String date) {
	
		String url = "http://www.thsrc.com.tw/tw/TimeTable/DailyTimeTable/"+date;
		 Document doc;
		 try{
			doc = Jsoup.connect(url).get();
			Element table = direction.equals("South")?doc.select("table").get(3):doc.select("table").get(5);
			Iterator<Element> item = table.select("td").iterator();	
			for(int i=0 ; i<13 ; i++) { item.next(); }
			int iterator=0;
			String[][] data = new String[200][13];
			Double[] college = new Double[200];
			int[][] early = new int[200][3];
	
			while(item.hasNext()) {
				String content = item.next().text(); 
				if (iterator%13==1) {
						college[iterator/13] = FindCollege(toWeek(date),data[iterator/13][0]);
						for (int i=0 ; i<3 ; i++) {
						early[iterator/13][i] = FindEarly(toWeek(date),data[iterator/13][0])[i];}
					}
				data[iterator/13][iterator%13] = content;
				iterator++;
			}
			
			for(int i=0 ;;i++) {
				if(data[i][0]!=null) {
				insertTimeTable(direction+date,date,data[i],college[i],early[i]);
				System.out.println();}
				else break;
			}
		 } catch (Exception e) {
			 System.out.println(e.getMessage());
		 }
	}
	
	/**
	 * Get early concession information with specified day of week and the train number.
	 * @param dayOfWeek The day of week.
	 * @param trainNo The specified train number.
	 * @return An integer array with available ticket number 
	 * 		   respectively for 35% off, 20%off and 10%off.
	 */
	private int[] FindEarly(String dayOfWeek, String trainNo) {
		int[] tickets = {0,0,0};
		JSONArray trainArray = new JSONObject(earlyFile).getJSONArray("DiscountTrains");
		JSONObject trainData;
		try {
			for(int i=0;;i++) {
				if(trainArray.getJSONObject(i).getString("TrainNo").equals(trainNo)) {
					trainData = trainArray.getJSONObject(i).getJSONObject("ServiceDayDiscount");
					break;
				}
			}
		} catch(Exception e) {
			return tickets;
		}
		try {
			if(trainData.getJSONArray(dayOfWeek).getJSONObject(0).getDouble("discount") == 0.65) 
				for(int i=0 ; i<3 ; i++) 
				{ tickets[i] = trainData.getJSONArray(dayOfWeek).getJSONObject(i).getInt("tickets");}
			else 
				for(int i=0 ; i<2 ; i++) 
				{ tickets[i+1] = trainData.getJSONArray(dayOfWeek).getJSONObject(i).getInt("tickets");}	
			return tickets;
		} catch (Exception e) {
			return tickets;
		}
	}
	
	/**
	 * Get college concession information with specified day of week and the train number.
	 * @param dayOfWeek The day of week.
	 * @param trainNo The specified train number.
	 * @return the concession plan for the specified information.
	 */
	private double FindCollege(String dayOfWeek, String trainNo) {
		double tickets = 1;
		JSONArray trainArray = new JSONObject(collegeFile).getJSONArray("DiscountTrains");
		JSONObject trainData;
		System.out.println("DATA FOR "+trainNo+" : ");
		try {
			for(int i=0;;i++) {
				if(trainArray.getJSONObject(i).getString("TrainNo").equals(trainNo)) {
					trainData = trainArray.getJSONObject(i).getJSONObject("ServiceDayDiscount");
					break;
				}
			}
		} catch(Exception e) {
			System.out.println("no such train");
			return tickets;
		}
		return trainData.getDouble(dayOfWeek);
	}
	
	/**
	 * Get the day of a week of specified date .
	 * @param date Specified date.
	 * @return The day of a week in English.
	 */
	private static String toWeek(String date) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cld = Calendar.getInstance();
		cld.setTime(sdf.parse(date));
		switch(cld.get(Calendar.DAY_OF_WEEK)) {
			case 1 :return "Sunday";			
			case 2 :return "Monday";			
			case 3 :return "Tuesday";			
			case 4 :return "Wednesday";				
			case 5 :return "Thursday";			
			case 6 :return "Friday";			
			case 7 :return "Saturday";		
			default: return null;	
		}
	}
	
	/**
	 * Read external file such as json files row by row.
	 * @param path The read file path.
	 * @return Text content of the file.
	 */
	 private String ReadFile(String path) {
		 	File file = new File(path);
	        BufferedReader reader = null;
	        String laststr = "";     
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            while ((tempString = reader.readLine()) != null) 
	            { laststr = laststr + tempString; }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) 
	                try 
	            		{ reader.close();} 
	            		catch (IOException e1) 
	            		{ e1.printStackTrace(); }       
	        }
	        return laststr;
	    }
	 
	 	/**
	 	 * Connect to the data base with specified database name.
	 	 * @param dbName Name for the connected database.
	 	 * @return Connection object to the specified database.
	 	 */
		private static Connection connect(String dbName) {
			String url = "jdbc:sqlite:"+dbName+".db";
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(url);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return conn;
		}
		
		/**
		 * Create a new timetable with specified date and direction.
		 * @param date The date when the timetable is valid.
		 * @param direction The rail direction for trains.
		 */
		private void createNewTimeTable(String direction,String date) {
			String sql;
			if(direction == "North") {
				sql = "CREATE TABLE IF NOT EXISTS "+direction+date+"("
						+ "車次 text ,"
						+ "大學 real,"
						+ "早鳥65 int,"
						+ "早鳥8 int,"
						+ "早鳥9 int,"
						+ "左營 text,"
						+ "台南 text,"
						+ "嘉義 text,"
						+ "雲林 text,"
						+ "彰化 text,"
						+ "台中 text,"
						+ "苗栗 text,"
						+ "新竹 text,"
						+ "桃園 text,"
						+ "板橋 text,"
						+ "台北 text,"
						+ "南港 text);";
			}
			else {
				sql = "CREATE TABLE IF NOT EXISTS "+direction+date+"("
						+ "車次 text ,"
						+ "大學 real,"
						+ "早鳥65 int,"
						+ "早鳥8 int,"
						+ "早鳥9 int,"
						+ "南港 text,"
						+ "台北 text,"
						+ "板橋 text,"
						+ "桃園 text,"
						+ "新竹 text,"
						+ "苗栗 text,"
						+ "台中 text,"
						+ "彰化 text,"
						+ "雲林 text,"
						+ "嘉義 text,"
						+ "台南 text,"
						+ "左營 text);";}
			try (Connection conn = connect(date);
					Statement stmt = conn.createStatement() ) {
						stmt.execute(sql);
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		

		
		
		/**
		 * Insert the data into timetable for one row.
		 * @param tableName Table where data are inserted.
		 * @param date The date when the timetable is valid.
		 * @param timedata Time Data for the timetable.
		 * @param college Concession information for college concession.
		 * @param early Concession information for early concession.
		 */
		private  void insertTimeTable (String tableName,String date,String[] timedata,double college,int[] early) {
			String sql = "INSERT INTO "+tableName+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try (Connection conn = connect(date); 
				 PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString (1,timedata[0]);
				pstmt.setDouble (2,college);
				pstmt.setInt (3,early[0]);
				pstmt.setInt (4,early[1]);
				pstmt.setInt (5,early[2]);
				pstmt.setString (6,timedata[1]);
				pstmt.setString (7,timedata[2]);
				pstmt.setString (8,timedata[3]);
				pstmt.setString (9,timedata[4]);
				pstmt.setString (10,timedata[5]);
				pstmt.setString (11,timedata[6]);
				pstmt.setString (12,timedata[7]);
				pstmt.setString (13,timedata[8]);
				pstmt.setString (14,timedata[9]);
				pstmt.setString (15,timedata[10]);
				pstmt.setString (16,timedata[11]);
				pstmt.setString (17,timedata[12]);
				pstmt.executeUpdate();
				System.out.println("input data success.");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		

}
