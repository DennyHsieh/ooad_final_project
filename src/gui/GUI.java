package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JInternalFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JPanel panelMain;
	private JPanel panelBooking;
	private JPanel panelRefund;
	private JPanel panelCheck;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("高鐵訂票系統");
		frame.setBounds(100, 100, 894, 543);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.setResizable(false);
		
		final JPanel panelMain = new JPanel();
		frame.getContentPane().add(panelMain, "name_3039236733700");
		panelMain.setLayout(null);
		panelMain.setVisible(true);
		
		final JPanel panelBooking = new JPanel();
		frame.getContentPane().add(panelBooking, "name_2756131448400");
		panelBooking.setLayout(null);
		panelBooking.setVisible(false);
		
		final JPanel panelRefund = new JPanel();
		frame.getContentPane().add(panelRefund, "name_2762253138900");
		panelRefund.setLayout(null);
		
		final JPanel panelCheck = new JPanel();
		frame.getContentPane().add(panelCheck, "name_2765710027600");
		panelCheck.setLayout(null);
		
		JButton btnBookingButton = new JButton("");
		btnBookingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelMain.setVisible(false);
				panelBooking.setVisible(true);
				
			}
		});
		btnBookingButton.setForeground(SystemColor.controlHighlight);
		btnBookingButton.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\DRAFT2\\src\\gui\\bleu.png"));
		btnBookingButton.setBounds(107, 160, 193, 50);
		panelMain.add(btnBookingButton);
		
		JButton btnRefundButton = new JButton("");
		btnRefundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRefund.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		btnRefundButton.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\DRAFT2\\src\\gui\\orange.png"));
		btnRefundButton.setBounds(355, 160, 178, 50);
		panelMain.add(btnRefundButton);
		
		JButton btnCheckButton = new JButton("");
		btnCheckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		btnCheckButton.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\DRAFT2\\src\\gui\\vert.png"));
		btnCheckButton.setBounds(596, 160, 178, 50);
		panelMain.add(btnCheckButton);
		
		JLabel IconLabel = new JLabel("");
		IconLabel.setIcon(new ImageIcon(GUI.class.getResource("/gui/1200x630wa.png")));
		IconLabel.setBounds(82, 10, 68, 96);
		panelMain.add(IconLabel);
		
		JLabel Instruction = new JLabel("請選擇以下操作:");
		Instruction.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		Instruction.setBounds(107, 116, 201, 26);
		panelMain.add(Instruction);
		
		JLabel MainBbgnLabel = new JLabel("");
		MainBbgnLabel.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\DRAFT2\\src\\gui\\train.png"));
		MainBbgnLabel.setBounds(298, 264, 317, 192);
		panelMain.add(MainBbgnLabel);
		
		
		
		JLabel book_Icon_1 = new JLabel("New label");
		book_Icon_1.setIcon(new ImageIcon(GUI.class.getResource("/gui/1200x630wa.png")));
		book_Icon_1.setBounds(46, 41, 66, 67);
		panelBooking.add(book_Icon_1);
		
		JLabel book_inst_1 = new JLabel("歡迎進入訂票系統");
		book_inst_1.setBounds(54, 137, 122, 22);
		panelBooking.add(book_inst_1);
		
		JLabel book_inst_2 = new JLabel("使用者ID");
		book_inst_2.setBounds(54, 184, 66, 15);
		panelBooking.add(book_inst_2);
		
		textField = new JTextField();
		textField.setBounds(130, 181, 96, 21);
		panelBooking.add(textField);
		textField.setColumns(10);
		
		JLabel book_inst_3 = new JLabel("起訖車站");
		book_inst_3.setBounds(54, 231, 58, 15);
		panelBooking.add(book_inst_3);
		
		JLabel book_inst_4 = new JLabel("去/回程");
		book_inst_4.setBounds(54, 280, 66, 15);
		panelBooking.add(book_inst_4);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("單程票");
		rdbtnNewRadioButton.setBounds(119, 276, 70, 23);
		panelBooking.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("去回票");
		rdbtnNewRadioButton_1.setBounds(191, 276, 107, 23);
		panelBooking.add(rdbtnNewRadioButton_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"A", "B"}));
		comboBox.setBounds(130, 228, 75, 21);
		panelBooking.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"A", "B"}));
		comboBox_1.setBounds(227, 228, 71, 21);
		panelBooking.add(comboBox_1);
		
		JLabel book_inst_5 = new JLabel("車廂種類");
		book_inst_5.setBounds(55, 366, 57, 15);
		panelBooking.add(book_inst_5);
		
		JLabel book_inst_6 = new JLabel("座位喜好");
		book_inst_6.setBounds(54, 406, 66, 15);
		panelBooking.add(book_inst_6);
		
		JLabel book_inst_7 = new JLabel("去程時間");
		book_inst_7.setBounds(54, 323, 58, 15);
		panelBooking.add(book_inst_7);
		
		JRadioButton radioButton = new JRadioButton("標準");
		radioButton.setBounds(119, 362, 70, 23);
		panelBooking.add(radioButton);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("商務");
		rdbtnNewRadioButton_2.setBounds(191, 362, 107, 23);
		panelBooking.add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("無");
		rdbtnNewRadioButton_3.setBounds(119, 402, 57, 23);
		panelBooking.add(rdbtnNewRadioButton_3);
		
		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("走道");
		rdbtnNewRadioButton_4.setBounds(130, 402, 72, 23);
		panelBooking.add(rdbtnNewRadioButton_4);
		
		JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("靠窗");
		rdbtnNewRadioButton_5.setBounds(266, 402, 66, 23);
		panelBooking.add(rdbtnNewRadioButton_5);
		
		JLabel book_inst_8 = new JLabel("乘客人數");
		book_inst_8.setBounds(457, 184, 58, 15);
		panelBooking.add(book_inst_8);
		
		JLabel book_inst_9 = new JLabel("全票");
		book_inst_9.setBackground(SystemColor.textHighlight);
		book_inst_9.setBounds(467, 216, 40, 15);
		panelBooking.add(book_inst_9);
		
		JLabel book_inst_10 = new JLabel("敬老");
		book_inst_10.setBackground(SystemColor.textHighlight);
		book_inst_10.setBounds(467, 257, 40, 15);
		panelBooking.add(book_inst_10);
		
		JLabel book_inst_11 = new JLabel("大學生");
		book_inst_11.setBackground(SystemColor.textHighlight);
		book_inst_11.setBounds(457, 299, 51, 15);
		panelBooking.add(book_inst_11);
		
		JLabel book_inst_12 = new JLabel("孩童");
		book_inst_12.setBackground(SystemColor.textHighlight);
		book_inst_12.setBounds(605, 216, 41, 15);
		panelBooking.add(book_inst_12);
		
		JLabel book_inst_13 = new JLabel("愛心");
		book_inst_13.setBackground(SystemColor.textHighlight);
		book_inst_13.setBounds(605, 257, 41, 15);
		panelBooking.add(book_inst_13);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox_2.setSelectedIndex(0);
		comboBox_2.setBounds(517, 213, 75, 21);
		panelBooking.add(comboBox_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox_3.setSelectedIndex(0);
		comboBox_3.setBounds(656, 216, 75, 21);
		panelBooking.add(comboBox_3);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox_4.setSelectedIndex(0);
		comboBox_4.setBounds(517, 254, 75, 21);
		panelBooking.add(comboBox_4);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox_5.setSelectedIndex(0);
		comboBox_5.setBounds(517, 296, 75, 21);
		panelBooking.add(comboBox_5);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox_6.setSelectedIndex(0);
		comboBox_6.setBounds(656, 257, 75, 21);
		panelBooking.add(comboBox_6);
		
		JButton button = new JButton("回上一頁");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelBooking.setVisible(false);
			}
		});
		button.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		button.setBounds(697, 400, 114, 62);
		panelBooking.add(button);
		
		
		
		JButton modify = new JButton("減少人數");
		modify.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		modify.setBounds(65, 187, 114, 62);
		panelRefund.add(modify);
		
		JButton delete = new JButton("取消行程");
		delete.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		delete.setBounds(210, 187, 114, 62);
		panelRefund.add(delete);
		
		JButton menu2 = new JButton("回上一頁");
		menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelRefund.setVisible(false);
			}
		});
		menu2.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		menu2.setBounds(697, 400, 114, 62);
		panelRefund.add(menu2);
		
		JLabel label = new JLabel("請選擇服務：");
		label.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label.setBounds(65, 136, 130, 25);
		panelRefund.add(label);
		panelRefund.setVisible(false);
		
		
		
		JButton timetable = new JButton("查詢時刻表");
		timetable.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		timetable.setBounds(99, 180, 114, 62);
		panelCheck.add(timetable);
		
		JButton concession = new JButton("查詢優惠車次");
		concession.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		concession.setBounds(244, 180, 114, 62);
		panelCheck.add(concession);
		
		JButton transaction = new JButton("查詢訂位紀錄");
		transaction.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		transaction.setBounds(389, 180, 114, 62);
		panelCheck.add(transaction);
		
		JButton transactionNoRN = new JButton("查詢訂位代號");
		transactionNoRN.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		transactionNoRN.setBounds(534, 180, 114, 62);
		panelCheck.add(transactionNoRN);
		
		JButton menu3 = new JButton("回上一頁");
		menu3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelCheck.setVisible(false);
			}
		});
		menu3.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		menu3.setBounds(697, 400, 114, 62);
		panelCheck.add(menu3);
		
		JLabel label_1 = new JLabel("請選擇服務：");
		label_1.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label_1.setBounds(99, 129, 130, 25);
		panelCheck.add(label_1);
		panelCheck.setVisible(false);
	}
}
