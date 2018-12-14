package controll;

import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JComboBox;

import controll.MysqlExe.RetVal;
import data.Seats;
import data.Station;
import data.Train;

/**
 * This class is used to search the train that fit the user's constrain.
 * All candidates will be added to the comboBox, the user will need to
 * choose the train tickets they want later.
 * @author Jerry
 */
public class SearchCandidate {
	public Vector<Train> can;
	public final String direction[] = {"timeTable_down", "timeTable_up"};
	
	/**
	 * Initialize the vector of the possible train.
	 */
	public SearchCandidate () {
		can = new Vector<Train>(); 
	}
	
	/**
	 * Search the train that fit the user's constrain.
	 * @param box The comboBox in the mainUI.
	 * @param date The date of the ticket.
	 * @param start Departure station.
	 * @param end Destination station.
	 * @param t1 Depart time.
	 * @param t2 Arrive time.
	 * @param count Ticket count.
	 * @param side The side of the seat.
	 * @param type The type of the ticket.
	 * @param early A boolean that decide if the ticket has the early discount.
	 */
	public void search(JComboBox<String> box, String date, int start, int end, int t1, int t2, int count, int side, int type, int early) {
		can.clear();
		box.removeAllItems();
		int weekday = Seats.getWeekDay(date);
		String direction = null;
		if (start < end) direction = this.direction[0];
		else {
			direction = this.direction[1];
		}
		
		Vector<Train> arr = new Vector<Train>();
		RetVal ret = null;
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT * FROM %s WHERE date = %s AND %s != -1 AND %s != -1 AND %s >= %d AND %s <= %d",
					direction, date.replace("/", ""), Station.ENG_NAME[start], Station.ENG_NAME[end], Station.ENG_NAME[start], t1, Station.ENG_NAME[end], t2
					));
			while (ret.res.next()) {
				int id = ret.res.getInt("train_id");
				int st1 = ret.res.getInt(Station.ENG_NAME[start]);
				int st2 = ret.res.getInt(Station.ENG_NAME[end]);
				arr.add(new Train(id, Integer.parseInt(date.replace("/", "")), st1, st2, 0, 0));
			}
			ret.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret.conn != null) ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// Start check ticket
		for (Train o: arr) {
			// check discount
			double Ediscount = 1.0;
			double Sdiscount = 1.0;
			int Ecount = 0;
			int Scount = 0;
			int tot = 0, s1 = 0, s2 = 0;
			try {
				// check sold tickets
				ret = MysqlExe.execQuery(String.format(
						"SELECT * FROM tickets WHERE ticketsType = 4 AND train_id = %d AND date = %s",
						o.train_id, date.replace("/", "")
						));
				while (ret.res.next()) {
					Ecount++;
				}
				ret.conn.close();
				ret = MysqlExe.execQuery(String.format(
						"SELECT * FROM tickets WHERE ticketsType = 1 AND train_id = %d AND date = %s",
						o.train_id, date.replace("/", "")
						));
				while (ret.res.next()) {
					Scount++;
				}
				ret.conn.close();
				// check discount tickets
				ret = MysqlExe.execQuery(String.format(
						"SELECT * FROM earlyDiscount WHERE train_id = %d AND weekday = %d ORDER BY earlyD",
						o.train_id, weekday
						));
				while (ret.res.next()) {
					int cnt = ret.res.getInt("earlyT");
					double dis = ret.res.getDouble("earlyD");
					if (cnt >= Ecount + count) {
						Ediscount = dis;
						break;
					} else {
						Ecount -= cnt;
					}
				}
				ret.conn.close();
				ret = MysqlExe.execQuery(String.format(
						"SELECT * FROM studentDiscount WHERE train_id = %d AND weekday = %d ORDER BY studentD",
						o.train_id, weekday
						));
				while (ret.res.next()) {
					int cnt = ret.res.getInt("studentT");
					double dis = ret.res.getDouble("studentD");
					if (cnt >= Scount + count) {
						Sdiscount = dis;
						break;
					} else {
						Scount -= cnt;
					}
				}
				ret.conn.close();
				// check side
				ret = MysqlExe.execQuery(String.format(
						"SELECT COUNT(*), side FROM allSeat WHERE business=%d GROUP BY side",
						type == 3 ? 1: 0
						));
				while (ret.res.next()) {
					int cnt = ret.res.getInt(1);
					int sd = ret.res.getInt(2);
					tot += cnt;
					if (sd == 1) s1 += cnt;
					if (sd == 2) s2 += cnt;
				}
				ret.conn.close();
				ret = MysqlExe.execQuery(String.format(
						"SELECT COUNT(*), seats FROM `tickets` WHERE ticketsType %s 3 AND date=%s GROUP BY seats",
						type == 3 ? "=": "!=", date.replace("/", "")
						));
				while (ret.res.next()) {
					int cnt = ret.res.getInt(1);
					int sd = ret.res.getInt(2);
					tot -= cnt;
					if (sd == 1) s1 -= cnt;
					if (sd == 2) s2 -= cnt;
				}
				ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ret.conn != null) ret.conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// No student discount
			if (Sdiscount == 1.0 && type == 1) continue;
			// No seat
			if (side == 0 && tot - count < 0) continue;
			if (side == 1 && s1 - count < 0) continue;
			if (side == 2 && s2 - count < 0) continue;
			StringBuffer possible = new StringBuffer();
			possible.append("班次" + o.train_id + ": ");
			if (early == 1 && type == 0 && Ediscount < 1.0) 
				possible.append("早鳥折數 " + Double.toString(Ediscount) + ",");
			if (type == 1 && Sdiscount < 1.0) 
				possible.append("學生折數 " + Double.toString(Sdiscount) + ",");
			possible.append(date);
			possible.append(", 起/訖站 " + Station.CHI_NAME[start] + "->" + Station.CHI_NAME[end]);
			possible.append(", 出發/到達時間 " + o.t1 + "->" + o.t2);
			possible.append(", 行車時間 " + Seats.getDur(o.t1, o.t2));
			can.add(new Train(o.train_id, o.date, o.t1, o.t2, Ediscount, Sdiscount));
			box.addItem(possible.toString());
//			System.out.println(possible);
		}
	}
}
