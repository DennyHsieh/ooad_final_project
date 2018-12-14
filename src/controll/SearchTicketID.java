package controll;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import controll.MysqlExe.RetVal;

/**
 * This class is used to search the ticket by the ticket information.
 * @author Jerry
 */
public class SearchTicketID {
	private String uid;
	private String train_id;
	private int date;
	private int start;
	private int end;

	/**
	 * @param uid User ID.
	 * @param train_id Train ID.
	 * @param date Departure date.
	 * @param start Departure Station.
	 * @param end Destination Station.
	 */
	public SearchTicketID(String uid, String train_id, int date, int start, int end) {
		this.uid = uid;
		this.train_id = train_id;
		this.date = date;
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Search the tickets.
	 */
	public void exec() {
		Vector<String> arr = new Vector<String>();
		RetVal ret = null;
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT DISTINCT code, price FROM tickets WHERE uid=\"%s\" AND train_id=%s AND date=%d AND start=%d AND end=%d",
					uid, train_id, date, start, end
					));
			while (ret.res.next()) {
				int code = ret.res.getInt("code");
				int price = ret.res.getInt("price");
				StringBuffer sb = new StringBuffer();
				sb.append("訂單代號: " + code);
				sb.append(", 定位明細: 未付款 / " + uid + "\n");
				sb.append("總價: " + price + "\n");
				SearchByID search = new SearchByID(uid, Integer.toString(code));
				String details = search.exec(1);

				sb.append("詳細資料:\n" + details + "\n");
				arr.add(sb.toString());
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
		if (arr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "查無紀錄!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(null, String.join("\n", arr), "InfoBox: 查詢訂票",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
}
