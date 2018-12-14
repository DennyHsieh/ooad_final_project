package controll;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import org.json.*;

import data.Seats;

/**
 * The class is used to parser the early discount value from the given json file.
 * @author Jerry
 */
public class EarlyParser {
	/**
	 * Read the json file.
	 * @return file content.
	 * @throws FileNotFoundException FileNotFoundException.
	 */
	public static String readJSON() throws FileNotFoundException {
		StringBuffer str = new StringBuffer();
		InputStream f = new FileInputStream("data/earlyDiscount.json");
		Scanner sc = new Scanner(f);
		while (sc.hasNext()) {
			str.append(sc.next() + " ");
		}
		sc.close();
		return str.toString();
	}
	
	/**
	 * Parser the json file.
	 * @throws SQLException SQLException.
	 * @throws FileNotFoundException FileNotFoundException.
	 */
	public static void initialize() throws SQLException, FileNotFoundException {
		MysqlExe.execStmt("DELETE FROM earlyDiscount");
		String file = readJSON();
		JSONObject obj = new JSONObject(file);
		JSONArray arr = obj.getJSONArray("DiscountTrains");
		for (int i = 0; i < arr.length(); i++) {
			String train_id = arr.getJSONObject(i).getString("TrainNo");
			JSONObject disc = arr.getJSONObject(i).getJSONObject("ServiceDayDiscount");
			for (int id = 0; id < 7; id++) {
				String weekday = Seats.WEEKDAYS[id];
				Object discObj = disc.get(weekday); 
				if (discObj.getClass() == org.json.JSONArray.class) {
					for (int j = 0; j < ((JSONArray) discObj).length(); j++) {
						double discount = ((JSONArray) discObj).getJSONObject(j).getDouble("discount");
						if (discount < 1.0e-9 || discount == 1.0) continue;
						int tickets = ((JSONArray) discObj).getJSONObject(j).getInt("tickets");
						System.out.println(String.format("%s, %d, %f, %d", train_id, id, discount, tickets));
						MysqlExe.execStmt(String.format("INSERT INTO earlyDiscount VALUES (%s, %d, %f, %d)",
								train_id, id, discount, tickets));
					}
				}
				else {
					double discount = disc.getDouble(weekday);
					if (discount < 1.0e-9 || discount == 1.0) continue; 
					System.out.println(String.format("%s, %d, %f, %d", train_id, id, discount, Seats.TOTAL_SEATS));
					MysqlExe.execStmt(String.format("INSERT INTO earlyDiscount VALUES (%s, %d, %f, %d)",
							train_id, id, discount, Seats.TOTAL_SEATS));
				}
				
			}
			//mysqlExe.execStmt(String.format("INSERT INTO studentDiscount VALUES (%s)", String.join(", ", tot)));
		}
	}
	/**
	 * Main function to parse student discount json file.
	 * @param args args
	 */
	public static void main(String[] args) {
		System.out.println("Parsing...");
		try {
			initialize();
			System.out.println("Seccessed!");
		} catch (FileNotFoundException e) {
			System.out.println("Error during reading the file.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error during initialization");
			e.printStackTrace();
		}
	}


}
