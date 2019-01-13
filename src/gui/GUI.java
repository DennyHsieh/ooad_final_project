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
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JInternalFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ActionMap;
import javax.swing.ComboBoxModel;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSlider;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import team.t4.booking.tk.AddReservation;
import team.t4.booking.tk.CreateReservation;

import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;


public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JPanel panelMain;
	private JPanel panelBooking;
	private JPanel panelRefund;
	private JPanel panelCheck;
	private JTextField jTextFieldDate;
	private Integer JOneWayclicktimes = 0;
	private Integer JRroundtripclicktimes = 0;
	private JTextField jTextFieldDate2;
	private AddReservation addNewRes;
			
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
		super();
		initialize();
		/*datePicker = new JDatePickerDialog(this, true, "Demo"); // arg1=Interactive frame; arg2=mode, arg3=title  
        datePicker.registerTF(jTextFieldDate);  
        datePicker.applyDateFormat(new SimpleDateFormat("yyyy-MM-dd"));*/
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("高鐵訂票系統");
		frame.setBounds(10, -29, 894, 543);
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
		
		final JPanel panelRefund = new JPanel();
		frame.getContentPane().add(panelRefund, "name_2762253138900");
		panelRefund.setLayout(null);
		
		final JPanel panelCheck = new JPanel();
		frame.getContentPane().add(panelCheck, "name_2765710027600");
		panelCheck.setLayout(null);
		
		final JPanel panelReturn = new JPanel();
		frame.getContentPane().add(panelReturn, "name_247753624742900");
		panelReturn.setLayout(null);
		
		JButton btnBookingButton = new JButton("");
		btnBookingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelMain.setVisible(false);
				panelBooking.setVisible(true);
				
			}
		});
		btnBookingButton.setForeground(SystemColor.controlHighlight);
		btnBookingButton.setIcon(new ImageIcon(GUI.class.getResource("/gui/bleu.png")));
		btnBookingButton.setBounds(107, 160, 193, 50);
		panelMain.add(btnBookingButton);
		
		JButton btnRefundButton = new JButton("");
		btnRefundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRefund.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		btnRefundButton.setIcon(new ImageIcon(GUI.class.getResource("/gui/orange.png")));
		btnRefundButton.setBounds(355, 160, 178, 50);
		panelMain.add(btnRefundButton);
		
		JButton btnCheckButton = new JButton("");
		btnCheckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		btnCheckButton.setIcon(new ImageIcon(GUI.class.getResource("/gui/vert.png")));
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
		
		JLabel label_3 = new JLabel("回程時間");
		label_3.setBounds(252, 323, 58, 15);
		panelBooking.add(label_3);
		label_3.setVisible(false);
		
		JLabel labeldate2 = new JLabel("回程日期");
		labeldate2.setBounds(368, 280, 58, 15);
		panelBooking.add(labeldate2);
		labeldate2.setVisible(false);
		
		jTextFieldDate2 = new JTextField();
		jTextFieldDate2.setColumns(10);
		jTextFieldDate2.setVisible(false);
		jTextFieldDate2.setBounds(429, 277, 46, 21);
		panelBooking.add(jTextFieldDate2);
		
		
		JRadioButton JRoneway = new JRadioButton("單程票");
		JRoneway.setSelected(true);
		JRoneway.setBounds(119, 276, 70, 23);
		panelBooking.add(JRoneway);
		JRadioButton JRroundtrip = new JRadioButton("去回票");
		JRroundtrip.setBounds(191, 276, 66, 23);
		panelBooking.add(JRroundtrip);
		
		JComboBox departhour = new JComboBox();
		departhour.setBounds(130, 320, 46, 21);
		panelBooking.add(departhour);
		departhour.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		
		JComboBox departminute = new JComboBox();
		departminute.setBounds(199, 320, 43, 21);
		panelBooking.add(departminute);
		departminute.setModel(new DefaultComboBoxModel(new String[] {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}));
		
		JComboBox arrivehour = new JComboBox();
		arrivehour.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		arrivehour.setBounds(320, 320, 41, 21);
		panelBooking.add(arrivehour);
		arrivehour.setVisible(false);
		
		JLabel label_4 = new JLabel(":");
		label_4.setBounds(368, 323, 16, 15);
		panelBooking.add(label_4);
		label_4.setVisible(false);
		
		JComboBox arriveminute = new JComboBox();
		arriveminute.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		arriveminute.setBounds(387, 320, 41, 21);
		panelBooking.add(arriveminute);
		arriveminute.setVisible(false);
		JRoneway.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOneWayclicktimes += 1;
				if(JRoneway.isSelected()) {
					JRroundtrip.setSelected(false);
					label_3.setVisible(false);
					arrivehour.setVisible(false);
					arriveminute.setVisible(false);
					label_4.setVisible(false);
					labeldate2.setVisible(false);
					jTextFieldDate2.setVisible(false);
				}
				else {
					JRoneway.setSelected(true);
				}
			}
		});
		
		JRroundtrip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JRroundtripclicktimes += 1;
				if(JRroundtrip.isSelected()) {
					JRoneway.setSelected(false);
					label_3.setVisible(true);
					arrivehour.setVisible(true);
					arriveminute.setVisible(true);
					label_4.setVisible(true);
					labeldate2.setVisible(true);
					jTextFieldDate2.setVisible(true);
				}
				else {
					JRroundtrip.setSelected(true);
				}
			}
		});
		
		JComboBox comboinit = new JComboBox();
		comboinit.setToolTipText("起點");
		comboinit.setModel(new DefaultComboBoxModel(new String[] {"南港", "台北", "板橋", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南", "左營"}));
		comboinit.setSelectedIndex(0);
		comboinit.setBounds(130, 228, 75, 21);
		panelBooking.add(comboinit);
		
		JComboBox combodes = new JComboBox();
		combodes.setToolTipText("終點");
		combodes.setModel(new DefaultComboBoxModel(new String[] {"南港", "台北", "板橋", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南", "左營"}));
		combodes.setBounds(227, 228, 71, 21);
		panelBooking.add(combodes);
		
		JLabel book_inst_5 = new JLabel("車廂種類");
		book_inst_5.setBounds(55, 366, 57, 15);
		panelBooking.add(book_inst_5);
		
		JLabel book_inst_6 = new JLabel("座位喜好");
		book_inst_6.setBounds(54, 406, 66, 15);
		panelBooking.add(book_inst_6);
		
		JLabel book_inst_7 = new JLabel("去程時間");
		book_inst_7.setBounds(54, 323, 58, 15);
		panelBooking.add(book_inst_7);
		
		JRadioButton JBstandardcarriage = new JRadioButton("標準");
		JBstandardcarriage.setSelected(true);
		JBstandardcarriage.setBounds(119, 362, 70, 23);
		panelBooking.add(JBstandardcarriage);
		
		JRadioButton JBbusinesscarriage = new JRadioButton("商務");
		JBbusinesscarriage.setBounds(191, 362, 107, 23);
		panelBooking.add(JBbusinesscarriage);
		
		JBstandardcarriage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JBbusinesscarriage.setSelected(false);
			}
		});
		
		JBbusinesscarriage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JBstandardcarriage.setSelected(false);
			}
		});
		
		JRadioButton JBprefernone = new JRadioButton("無");
		JBprefernone.setSelected(true);
		JBprefernone.setBounds(119, 402, 57, 23);
		panelBooking.add(JBprefernone);
		
		JRadioButton JBpreferaisle = new JRadioButton("走道");
		JBpreferaisle.setBounds(191, 402, 72, 23);
		panelBooking.add(JBpreferaisle);
		
		JRadioButton JBpreferwindow = new JRadioButton("靠窗");
		JBpreferwindow.setBounds(273, 402, 66, 23);
		panelBooking.add(JBpreferwindow);
		
		JBprefernone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JBpreferaisle.setSelected(false);
				JBpreferwindow.setSelected(false);
			}
		});
		
		JBpreferaisle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JBprefernone.setSelected(false);
				JBpreferwindow.setSelected(false);
			}
		});
		
		JBpreferwindow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JBprefernone.setSelected(false);
				JBpreferaisle.setSelected(false);
			}
		});
		
		int selected[] = new int [2];
		selected[0] = -1;
		selected[1] = -1;
		JList list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Integer index = list.getSelectedIndex();
				selected[0] = index;
				//newResv = new CreateReservation(index);
				//System.out.print(index);
				
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(SystemColor.info);
		list.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		list.setSelectedIndex(0);
		list.setBounds(178, 141, 542, 272);
		panelReturn.add(list);
		panelCheck.setVisible(false);
		
		JLabel book_inst_8 = new JLabel("乘客人數");
		book_inst_8.setBounds(516, 184, 58, 15);
		panelBooking.add(book_inst_8);
		
		JLabel book_inst_9 = new JLabel("全票");
		book_inst_9.setBackground(SystemColor.textHighlight);
		book_inst_9.setBounds(516, 216, 40, 15);
		panelBooking.add(book_inst_9);
		
		JLabel book_inst_10 = new JLabel("敬老");
		book_inst_10.setBackground(SystemColor.textHighlight);
		book_inst_10.setBounds(516, 257, 40, 15);
		panelBooking.add(book_inst_10);
		
		JLabel book_inst_11 = new JLabel("大學生");
		book_inst_11.setBackground(SystemColor.textHighlight);
		book_inst_11.setBounds(516, 299, 51, 15);
		panelBooking.add(book_inst_11);
		
		JLabel book_inst_12 = new JLabel("孩童");
		book_inst_12.setBackground(SystemColor.textHighlight);
		book_inst_12.setBounds(651, 216, 41, 15);
		panelBooking.add(book_inst_12);
		
		JLabel book_inst_13 = new JLabel("愛心");
		book_inst_13.setBackground(SystemColor.textHighlight);
		book_inst_13.setBounds(651, 257, 41, 15);
		panelBooking.add(book_inst_13);
		
		JSpinner spfullfare = new JSpinner();
		spfullfare.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spfullfare.setBounds(581, 213, 40, 22);
		panelBooking.add(spfullfare);
		
		JSpinner spelderyfare = new JSpinner();
		spelderyfare.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spelderyfare.setBounds(581, 254, 40, 22);
		panelBooking.add(spelderyfare);
		
		JSpinner spstudent = new JSpinner();
		spstudent.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spstudent.setBounds(581, 296, 40, 22);
		panelBooking.add(spstudent);
		
		JSpinner sphalfare = new JSpinner();
		sphalfare.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		sphalfare.setBounds(712, 213, 40, 22);
		panelBooking.add(sphalfare);
		
		JSpinner spdisabledfare = new JSpinner();
		spdisabledfare.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spdisabledfare.setBounds(712, 254, 40, 22);
		panelBooking.add(spdisabledfare);
		
		JButton submitbuton = new JButton("");
		submitbuton.addActionListener(new ActionListener() {
			private String textFieldValue;
			private Integer spi1 ;
			private Integer spi2 ;
			private Integer spi3 ;
			private Integer spi4 ;
			private Integer spi5 ;
			private String stationfrom;
			private String stationtogo;
			private Boolean timemakesense;
			public void actionPerformed(ActionEvent arg0) {
				textFieldValue = textField.getText();
				spi1 = (Integer)spfullfare.getValue();
				spi2 = (Integer)sphalfare.getValue();
				spi3 = (Integer)spelderyfare.getValue();
				spi4 = (Integer)spdisabledfare.getValue();
				spi5 = (Integer)spstudent.getValue();
				stationfrom = comboinit.getSelectedItem().toString();
				stationtogo = combodes.getSelectedItem().toString();
				Integer departurehour = Integer.parseInt(departhour.getSelectedItem().toString());
				Integer departureminute = Integer.parseInt(departminute.getSelectedItem().toString());
				if(JRroundtrip.isSelected()) {
					Integer arrivalhour = Integer.parseInt(arrivehour.getSelectedItem().toString());
					Integer arrivalminute = Integer.parseInt(arriveminute.getSelectedItem().toString());
					timemakesense = departurehour*60 + departureminute < arrivalhour*60 + arrivalminute;
				}
				else {
					timemakesense = false;
				}
				boolean success = true;
				if(textFieldValue.isEmpty()) {
					JOptionPane.showMessageDialog(null, "使用者ID不可為空");
					success = false;
				}
				if(spi1+spi2+spi3+spi4+spi5 == 0) {
					JOptionPane.showMessageDialog(null, "票數不可為零");
					success = false;
				}
				if(stationfrom.equals(stationtogo)) {
					JOptionPane.showMessageDialog(null, "起站不能等於迄站");
					success = false;
				}
				if(!timemakesense && JRroundtrip.isSelected()) {
					JOptionPane.showMessageDialog(null, "去程時間不能超過回程時間");
					success = false;
				}
				if(success) {
					String hr = departhour.getSelectedItem().toString();
					String mn = departminute.getSelectedItem().toString();
					String cond1 = (JBpreferaisle.isSelected())?"aisle":"window";
					String timeR = (JRroundtrip.isSelected())?arrivehour.getSelectedItem().toString()+':'+arrivehour.getSelectedItem().toString():"";
					String dateR = (JRroundtrip.isSelected())?jTextFieldDate2.getText():"";
					addNewRes = new AddReservation(textFieldValue,jTextFieldDate.getText(),hr+':'+mn,stationfrom,
							stationtogo,dateR,timeR,cond1,spi5,spi1,spi2,spi3,spi4);
					
					try {
						CreateReservation creRes = addNewRes.getInstance();
						list.setModel(new AbstractListModel() {
							String[] values  = creRes.checkVacancy();
							public int getSize() {
								return values.length;
							}
							public Object getElementAt(int index) {
								return values[index];
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
					panelBooking.setVisible(false);
					panelReturn.setVisible(true);
				}
			}
		});
		
		JButton returnbutton = new JButton("");
		returnbutton.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\DRAFT2\\src\\gui\\violet.jpg"));
		returnbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelBooking.setVisible(false);
			}
		});
		returnbutton.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		returnbutton.setBounds(683, 406, 168, 56);
		panelBooking.add(returnbutton);
		submitbuton.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\DRAFT2\\src\\gui\\confirm.png"));
		submitbuton.setBounds(478, 406, 168, 56);
		panelBooking.add(submitbuton);
		
		JLabel lblNewLabel = new JLabel(":");
		lblNewLabel.setBounds(180, 323, 9, 15);
		panelBooking.add(lblNewLabel);
		
		JLabel labeldate = new JLabel("去程日期");
		labeldate.setBounds(257, 272, 53, 30);
		panelBooking.add(labeldate);
		
		jTextFieldDate = new JTextField();
		jTextFieldDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Table();
				//datePicker.setVisible(true); 
			}
		});
		jTextFieldDate.setBounds(315, 277, 46, 21);
		panelBooking.add(jTextFieldDate);
		jTextFieldDate.setColumns(10);
		
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
		
		JButton menu2 = new JButton("");
		menu2.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\DRAFT2\\src\\gui\\violet.jpg"));
		menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelRefund.setVisible(false);
			}
		});
		menu2.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		menu2.setBounds(633, 409, 178, 53);
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
		
		JButton menu3 = new JButton("");
		menu3.setIcon(new ImageIcon("C:\\Users\\admin\\Desktop\\DRAFT2\\src\\gui\\violet.jpg"));
		menu3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelCheck.setVisible(false);
			}
		});
		menu3.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		menu3.setBounds(650, 400, 161, 47);
		panelCheck.add(menu3);
		
		JLabel label_1 = new JLabel("請選擇服務：");
		label_1.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label_1.setBounds(99, 129, 130, 25);
		panelCheck.add(label_1);
		
		JLabel lblNewLabel_1 = new JLabel("查詢結果");
		lblNewLabel_1.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		lblNewLabel_1.setBounds(405, 60, 81, 24);
		panelReturn.add(lblNewLabel_1);
		
		JLabel label_2 = new JLabel("車次");
		label_2.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_2.setBounds(205, 104, 44, 24);
		panelReturn.add(label_2);
		
		JLabel label_5 = new JLabel("備註");
		label_5.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_5.setBounds(264, 104, 44, 24);
		panelReturn.add(label_5);
		
		JLabel label_7 = new JLabel("發車站-終點站");
		label_7.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_7.setBounds(382, 104, 134, 24);
		panelReturn.add(label_7);
		
		JLabel label_8 = new JLabel("日期");
		label_8.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_8.setBounds(328, 104, 44, 24);
		panelReturn.add(label_8);
		
		JLabel label_9 = new JLabel("開車時間\t");
		label_9.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_9.setBounds(526, 104, 81, 24);
		panelReturn.add(label_9);
		
		JLabel label_6 = new JLabel("抵達時間\t");
		label_6.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_6.setBounds(615, 104, 81, 24);
		panelReturn.add(label_6);
		
		
	}
	
	public class DateLabelFormatter extends AbstractFormatter {

	    private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }

	}
	
	private void Table() {
		UtilDateModel model = new UtilDateModel();
		model.setDate(11,01,2019);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	}
}
