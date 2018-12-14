package controll;

import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

import controll.MysqlExe.RetVal;

/**
 * This class is used to update the ticket by the booking code.
 * @author Jerry
 */
public class UpdateTicket {
	private String uid;
	private String code;
	private int count;
	
	/**
	 * @param uid User ID.
	 * @param code Booking code.
	 * @param count Ticket count.
	 */
	public UpdateTicket(String uid, String code, int count) {
		this.uid = uid;
		this.code = code;
		this.count = count;
	}
	
	/**
	 * Update the ticket.
	 */
	public void exec() {
		int now_count = 0;
		RetVal ret = null;
		boolean userCheck = true;
		boolean gobackCheck = false;
		int st = 0, ed = 0;
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT * FROM tickets WHERE code=\"%s\"", code
					));
			while (ret.res.next()) {
				String uid = ret.res.getString("uid");
				if (!uid.equals(this.uid)) userCheck = false;
				now_count++;
			}
			ret = MysqlExe.execQuery(String.format(
					"SELECT count(*), start, end FROM tickets WHERE code=\"%s\" GROUP BY start, end", code
					));
			int dir_count = 0;
			while (ret.res.next()) {
				st = ret.res.getInt("start");
				ed = ret.res.getInt("end");
				dir_count++;
			}
			if (dir_count == 2)
				gobackCheck = true;
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
		if (gobackCheck)
			now_count /= 2;
		if (now_count == 0) {
			JOptionPane.showMessageDialog(null, "�z��J���q��N�����~�A�Э��s��J!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!userCheck) {
			JOptionPane.showMessageDialog(null, "�z��J�������ѧO���X���~�A�Э��s��J!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (count < 0 || now_count < count) {
			JOptionPane.showMessageDialog(null, "���Ƥ���!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			MysqlExe.execStmt(String.format("DELETE FROM tickets WHERE code=%s AND start=%d LIMIT %d", code, st, now_count - count));
			if (gobackCheck)
				MysqlExe.execStmt(String.format("DELETE FROM tickets WHERE code=%s AND start=%d LIMIT %d", code, ed, now_count - count));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "��s����!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		JOptionPane.showMessageDialog(null, "��s���\!", "InfoBox: �d�߭q��",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
