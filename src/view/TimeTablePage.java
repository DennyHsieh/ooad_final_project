package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controll.MysqlExe;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;
import data.Station;

/**
 * This class is the UI of the time table.
 * @author Jerry
 */
public class TimeTablePage {

	private JFrame frame;
	private JTable table_down;
	private JTable table_up;
	private DefaultTableModel model_up;
	private DefaultTableModel model_down;

	/**
	 * Launch the application.
	 * @param args No args.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeTablePage window = new TimeTablePage();
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
	 * Get time table down from mysql.
	 * @param date date
	 */
	public void setTimeDown(String date) {
		model_down.setRowCount(0);
		MysqlExe.RetVal ret = null;
		try {
			ret = MysqlExe.execQuery("SELECT * FROM timeTable_down WHERE date = " + date);
			while (ret.res.next()) {
				Vector<String> row = new Vector<String>();
				row.add(ret.res.getString(2));
				for (int i = 0; i < 12; i++) {
					int time = ret.res.getInt(i + 3);
					String input = String.format("%02d:%02d", time / 100, time % 100);
					if (time == -1) input = "*";
					row.add(input);
				}
				model_down.addRow(row);
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
	}	/**
	 * Get time table up from mysql.
	 * @param date date
	 */
	public void setTimeUp(String date) {
		model_up.setRowCount(0);
		MysqlExe.RetVal ret = null;
		try {
			ret = MysqlExe.execQuery("SELECT * FROM timeTable_up WHERE date = " + date);
			while (ret.res.next()) {
				Vector<String> row = new Vector<String>();
				row.add(ret.res.getString(2));
				for (int i = 0; i < 12; i++) {
					int time = ret.res.getInt(i + 3);
					String input = String.format("%02d:%02d", time / 100, time % 100);
					if (time == -1) input = "*";
					row.add(input);
				}
				model_up.addRow(row);
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
	}
	/**
	 * Create the application.
	 */
	public TimeTablePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 472);
		frame.getContentPane().setLayout(null);
		
		JLabel downLabel = new JLabel("\u5357\u4E0B");
		downLabel.setFont(downLabel.getFont().deriveFont(downLabel.getFont().getStyle() | Font.BOLD));
		downLabel.setBounds(10, 10, 46, 15);
		frame.getContentPane().add(downLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 35, 864, 176);
		frame.getContentPane().add(scrollPane);
		
		JLabel upLabel = new JLabel("\u5317\u4E0A");
		upLabel.setFont(upLabel.getFont().deriveFont(upLabel.getFont().getStyle() | Font.BOLD));
		upLabel.setBounds(10, 221, 46, 15);
		frame.getContentPane().add(upLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 246, 864, 176);
		frame.getContentPane().add(scrollPane_1);

		// time table
		model_up = new DefaultTableModel(); 
		model_down = new DefaultTableModel(); 
		model_up.addColumn("車次");
		model_down.addColumn("車次");
		for (int i = 0; i < Station.CHI_NAME.length; i++) {
			model_down.addColumn(Station.CHI_NAME[i]);
			model_up.addColumn(Station.CHI_NAME[Station.CHI_NAME.length - i - 1]);
		}
		
		table_down = new JTable(model_down);
		scrollPane.setViewportView(table_down);
		table_up = new JTable(model_up);
		scrollPane_1.setViewportView(table_up);

		table_down.getTableHeader().setReorderingAllowed(false);
		table_up.getTableHeader().setReorderingAllowed(false);
	}
}
