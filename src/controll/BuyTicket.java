package controll;

import java.sql.SQLException;

import controll.MysqlExe.RetVal;
import data.Seats;
import data.Train;

/**
 * The class is used to record the new bought ticket after the user made the final decision. 
 * @author Jerry
 */
public class BuyTicket {
	private Train train;
	private String uid;
	private int code;
	private int type;
	private int start;
	private int end;
	private int count;
	private int side;
	private int price;

	/**
	 * @param train The train that the user chosed
	 * @param uid User id.
	 * @param code Booking code.
	 * @param startStationIndex Departure station.
	 * @param endStationIndex Destination station.
	 * @param count Ticket count.
	 * @param side The side of the seat.
	 * @param type The type of the ticket.
	 * @param price The price of the ticket.
	 */
	public BuyTicket(Train train, String uid, int code, int startStationIndex, int endStationIndex, int count,
			int side, int type, int price) {
		this.train = train;
		this.uid = uid;
		this.code = code;
		this.type = type;
		start = startStationIndex;
		end = endStationIndex;
		this.count = count;
		this.side = side;
		this.price = price;
	}

	/**
	 * Insert the ticket into the database.
	 * @throws SQLException SQLException
	 */
	public void Commit() throws SQLException {
		String[] seat = new String[count];
		int now = 0;
		RetVal ret = null;
		for (int i = 0; i < Seats.TOTAL_CABIN; i++) {
			// check business
			if (i == Seats.BUSS_CABIN && type != 3) continue;
			if (i != Seats.BUSS_CABIN && type == 3) continue;
			for (String st: Seats.VALID_SEATS[i]) {
				// check side
				if (side == 1 && (st.charAt(2) == 'B' || st.charAt(2) == 'C' || st.charAt(2) == 'D')) continue;
				if (side == 2 && (st.charAt(2) == 'A' || st.charAt(2) == 'B' || st.charAt(2) == 'E')) continue;
				if (now == count) break;
				boolean can = true;
				try {
					ret = MysqlExe.execQuery(String.format(
							"SELECT * FROM tickets WHERE train_id= %d AND date=%d AND seat_id=\"%02d%s\"",
							train.train_id, train.date, i + 1, st
							));
					while (ret.res.next()) can = false;
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
				if (can) seat[now++] = String.format("%02d", i + 1) + st;
			}
		}
		if (train.early < 1.0 && type == 0) type = 4;
		for (String st: seat) {
			System.out.println(String.format(
					"INSERT INTO tickets VALUES "
					+ "(%d, \"%s\", %d, %d, %d, %d, %d, %d, %d, \"%s\", %d, %d)",
					code, uid, train.date, type, start, end, side, price, train.train_id, st, train.t1, train.t2
					));
			MysqlExe.execStmt(String.format(
					"INSERT INTO tickets VALUES "
					+ "(%d, \"%s\", %d, %d, %d, %d, %d, %d, %d, \"%s\", %d, %d)",
					code, uid, train.date, type, start, end, side, price, train.train_id, st, train.t1, train.t2
					));
		}
	}
}
