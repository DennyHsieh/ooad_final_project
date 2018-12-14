package controll;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import controll.MysqlExe.RetVal;
import data.Seats;
import data.Station;

/**
 * This class is used to search the ticket by the booking code.
 * @author Jerry
 */
public class SearchByID {
	private String uid;
	private String code;
	
	/**
	 * @param uid User ID.
	 * @param code Booking code.
	 */
	public SearchByID(String uid, String code) {
		this.uid = uid;
		this.code = code;
	}

	/**
	 * Search the ticket.
	 * @param tostr Return string or pop the message box.
	 * @return The search result.
	 */
	public String exec(int tostr) {
		Vector<String> arr = new Vector<String>();
		RetVal ret = null;
		boolean userCheck = true;
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT * FROM tickets WHERE code=\"%s\"", code
					));
			while (ret.res.next()) {
				String uid = ret.res.getString("uid");
				if (!uid.equals(this.uid)) userCheck = false;
				int train_id = ret.res.getInt("train_id");
				int date = ret.res.getInt("date");
				int st = ret.res.getInt("start");
				int ed = ret.res.getInt("end");
				int t1 = ret.res.getInt("start_time");
				int t2 = ret.res.getInt("end_time");
				int dur = Seats.getDur(t1, t2);
				String type = Seats.TICKET_TYPE_2[ret.res.getInt("ticketsType")];
				String seat = ret.res.getString("seat_id");
				StringBuffer sb = new StringBuffer();
				sb.append("����: " + train_id);
				sb.append(", ���: " + date);
				sb.append(", �_/�W��: " + Station.CHI_NAME[st] + "->" + Station.CHI_NAME[ed]);
				sb.append(", �X�o/��F�ɶ�: " + t1 + "->" + t2);
				sb.append(", �樮�ɶ�: " + dur);
				sb.append(", �y��: " + seat);
				sb.append(", ����: " + type);
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
			JOptionPane.showMessageDialog(null, "�z��J���q��N�����~�A�Э��s��J!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return "";
		}
		if (!userCheck) {
			JOptionPane.showMessageDialog(null, "�z��J�������ѧO���X���~�A�Э��s��J!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return "";
		}
		if (tostr == 1)
			return String.join("\n", arr);
		else
			JOptionPane.showMessageDialog(null, String.join("\n", arr), "InfoBox: �d�߭q��",
				JOptionPane.INFORMATION_MESSAGE);
		return "";
	}
}
