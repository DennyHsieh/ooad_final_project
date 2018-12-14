package view;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controll.MysqlExe;
import data.Seats;
import data.Station;

import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 * This class is used to show the discount time table.
 * @author Jerry
 */
public class DiscountTablePage {
	private JFrame frame;
	private DefaultTableModel model;
	private JTable table;
	public final String dirTable[] = {"timeTable_down", "timeTable_up"};

	/**
	 * Launch the application.
	 * @param args No args.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiscountTablePage window = new DiscountTablePage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Show the frame.
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * Set title.
	 * @param title title
	 */
	public void setTitle(String title) {
		frame.setTitle(title);
	}

	/**
	 * Create the application.
	 */
	public DiscountTablePage() {
		initialize();
	}
	
	/**
	 * @param date Departure date.
	 * @param direction Go north or south.
	 * @param startTime Departure time.
	 * @param endTime Arrival time.
	 * @param startStation Departure station.
	 * @param endStation Destination station.
	 * @throws SQLException SQLException
	 * @throws ParseException ParseException
	 */
	public void showDiscount(int date, int direction, int startTime, int endTime, int startStation, int endStation) throws SQLException, ParseException {
//		System.out.println(startStation);
//		System.out.println(endStation);
		int weekday = Seats.getWeekDay(date);
		String start = Station.ENG_NAME[startStation];
		String end = Station.ENG_NAME[endStation];
		String startC = Station.CHI_NAME[startStation];
		String endC = Station.CHI_NAME[endStation];
		if (direction == 1) {
			startStation = Station.ENG_NAME.length - 1 - startStation;
			endStation = Station.ENG_NAME.length - 1 - endStation;
		}
		model.setRowCount(0);
		MysqlExe.RetVal ret = null;
		try {
			ret = MysqlExe.execQuery(String.format(
					"Select * FROM %s WHERE date = %s AND %s != -1 AND %s != -1 AND %s >= %d AND %s <= %d AND train_id IN "
					+ "(SELECT train_id FROM earlyDiscount WHERE weekday = %d UNION SELECT train_id FROM studentDiscount WHERE weekday = %d)"
						, dirTable[direction], date, start, end, start, startTime, end, endTime, weekday, weekday));
			while (ret.res.next()) {
				String train_id = ret.res.getString("train_id");
				int st = ret.res.getInt(start);
				int ed = ret.res.getInt(end);
				StringBuffer early = new StringBuffer();
				StringBuffer student = new StringBuffer();
				
				// get discount by train id
				MysqlExe.RetVal rsDis = null;
				try {
					rsDis = MysqlExe.execQuery(String.format("SELECT * FROM earlyDiscount WHERE weekday = %d AND train_id = %s", weekday, train_id));
					while (rsDis.res.next()) {
						if (early.length() > 0) early.append(" / ");
						early.append(rsDis.res.getString("earlyD"));
					}
					rsDis.conn.close();
					rsDis = MysqlExe.execQuery(String.format("SELECT * FROM studentDiscount WHERE weekday = %d AND train_id = %s", weekday, train_id));
					while (rsDis.res.next()) {
						if (student.length() > 0) student.append(" / ");
						student.append(rsDis.res.getString("studentD") + "");
					}
					rsDis.conn.close();
				} catch (SQLException e) {
					if (rsDis.conn != null) rsDis.conn.close();
					throw e;
				}
				
				// add new row 
				int duration = (ed / 100 * 60 + ed % 100) - (st / 100 * 60 + st % 100);
				Vector<String> row = new Vector<String>();
				row.add(train_id);
				row.add(startC);
				row.add(endC);
				row.add(student.toString());
				row.add(early.toString());
				row.add(String.format("%02d:%02d", st / 100, st % 100));
				row.add(String.format("%02d:%02d", ed / 100, ed % 100));
				row.add(String.format("%02d:%02d", duration / 60, duration % 60));
				model.addRow(row);
			}
		} catch (SQLException e) {
			if (ret.conn != null) ret.conn.close();
			throw e;
		}
		if (model.getRowCount() == 0) {
			frame.setVisible(false);
			JOptionPane.showMessageDialog(null, "無優惠車票!", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		// create table model
		model = new DefaultTableModel();
		model.addColumn("車次");
		model.addColumn("起站");
		model.addColumn("訖站");
		model.addColumn("大學生折數");
		model.addColumn("早鳥折數");
		model.addColumn("出發時間");
		model.addColumn("抵達時間");
		model.addColumn("行車時間");
		frame.setBounds(100, 100, 750, 432);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
	}
}
