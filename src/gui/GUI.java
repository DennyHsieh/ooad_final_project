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
import javax.swing.JScrollPane;
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
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import team.t4.booking.client.Client.UIDCheck;
import team.t4.booking.db.TableCrawler;
import team.t4.booking.ref.AddModify;
import team.t4.booking.ref.CreateModify;
import team.t4.booking.tk.AddReservation;
import team.t4.booking.tk.CreateReservation;
import team.t4.booking.userck.AddCheck;
import team.t4.booking.userck.CreateCheck;

import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;


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
	private CreateReservation creRes;
	private AddModify addNewMod;
	private CreateModify creMod;
	private AddCheck addNewChe;
	private CreateCheck creChe;
	private JTextField ID;
	private JTextField ReservationNo;
	private JTextField UID_Modify;
	private JTextField ResNo_Modify;
	private JTextField keyID_transation;
	private JTextField reserveID_transation;
	private JTextField keyID_transactionNoRN;
	private JTextField textFieldtrainID_transactionNoRN;
	private int[] selected;
	private String textFieldValue;
	private Integer spi1 ;
	private Integer spi2 ;
	private Integer spi3 ;
	private Integer spi4 ;
	private Integer spi5 ;
	private String stationfrom;
	private String stationtogo;
	private boolean clickedcounter = false;
	
	/**
	 * File relative path string.
	 */
	private static Properties properties = System.getProperties();
	private static String relativelyPath = properties.getProperty("user.dir");
	
	
	/**
	 * @wbp.nonvisual location=-35,-31
	 */
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GUI 	windowSearch = new GUI(true);
		GUI windowMain = new GUI(false);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					windowSearch.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		TableCrawler.checkUpdate(); 
		windowSearch.frame.dispose();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI windowMain = new GUI(false);	
					windowMain.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(boolean load) {
		super();
		if(load == true) {
			initializeSearch();
		}
		else {
			initialize();
		}
		/*datePicker = new JDatePickerDialog(this, true, "Demo"); / arg1=Interactive frame; arg2=mode, arg3=title  
        datePicker.registerTF(jTextFieldDate);  
        datePicker.applyDateFormat(new SimpleDateFormat("yyyy-MM-dd"));*/
	}
	private void initializeSearch() {
			frame = new JFrame();
			frame.setTitle("Updating data...");
			frame.setBounds(50,50,200,200);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
			JLabel label = new JLabel("Updating Data...");
			label.setBounds(50,10,200,100);;
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.add(label);
			frame.add(panel, BorderLayout.CENTER);
			
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
		
		final JPanel panelBooking_1 = new JPanel();
		frame.getContentPane().add(panelBooking_1, "name_2756131448400");
		panelBooking_1.setLayout(null);
		
		final JPanel panelReturn = new JPanel();
		frame.getContentPane().add(panelReturn, "name_247753624742900");
		panelReturn.setLayout(null);
		JList list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Integer index = list.getSelectedIndex();
				selected = new int[2];
				selected[0] = index;
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(SystemColor.info);
		list.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		list.setSelectedIndex(0);
		list.setBounds(178, 141, 542, 272);
		panelReturn.add(list);
		
		JLabel book_Icon_1 = new JLabel("New label");
		book_Icon_1.setIcon(new ImageIcon(GUI.class.getResource("/gui/1200x630wa.png")));
		book_Icon_1.setBounds(46, 41, 66, 67);
		panelBooking_1.add(book_Icon_1);
		
		JLabel book_inst_1 = new JLabel("歡迎進入訂票系統");
		book_inst_1.setBounds(54, 137, 122, 22);
		panelBooking_1.add(book_inst_1);
		
		JLabel book_inst_2 = new JLabel("身分證字號");
		book_inst_2.setBounds(54, 184, 66, 15);
		panelBooking_1.add(book_inst_2);
		
		textField = new JTextField();
		textField.setBounds(130, 181, 96, 21);
		panelBooking_1.add(textField);
		textField.setColumns(10);
		
		JLabel book_inst_3 = new JLabel("起訖車站");
		book_inst_3.setBounds(54, 231, 58, 15);
		panelBooking_1.add(book_inst_3);
		
		JLabel book_inst_4 = new JLabel("去/回程");
		book_inst_4.setBounds(54, 280, 66, 15);
		panelBooking_1.add(book_inst_4);
		
		JLabel label_3 = new JLabel("回程時間");
		label_3.setBounds(252, 323, 58, 15);
		panelBooking_1.add(label_3);
		label_3.setVisible(false);
		
		JLabel labeldate2 = new JLabel("回程日期");
		labeldate2.setBounds(368, 280, 58, 15);
		panelBooking_1.add(labeldate2);
		labeldate2.setVisible(false);
		
		jTextFieldDate2 = new JTextField();
		jTextFieldDate2.setColumns(10);
		jTextFieldDate2.setVisible(false);
		jTextFieldDate2.setBounds(429, 277, 46, 21);
		panelBooking_1.add(jTextFieldDate2);
		
		
		JRadioButton JRoneway = new JRadioButton("單程票");
		JRoneway.setSelected(true);
		JRoneway.setBounds(119, 276, 70, 23);
		panelBooking_1.add(JRoneway);
		JRadioButton JRroundtrip = new JRadioButton("去回票");
		JRroundtrip.setBounds(191, 276, 66, 23);
		panelBooking_1.add(JRroundtrip);
		
		JComboBox departhour = new JComboBox();
		departhour.setBounds(130, 320, 46, 21);
		panelBooking_1.add(departhour);
		departhour.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		
		JComboBox departminute = new JComboBox();
		departminute.setBounds(199, 320, 43, 21);
		panelBooking_1.add(departminute);
		departminute.setModel(new DefaultComboBoxModel(new String[] {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}));
		
		JComboBox arrivehour = new JComboBox();
		arrivehour.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		arrivehour.setBounds(320, 320, 41, 21);
		panelBooking_1.add(arrivehour);
		arrivehour.setVisible(false);
		
		JLabel label_4 = new JLabel(":");
		label_4.setBounds(368, 323, 16, 15);
		panelBooking_1.add(label_4);
		label_4.setVisible(false);
		
		JComboBox arriveminute = new JComboBox();
		arriveminute.setModel(new DefaultComboBoxModel(new String[] {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}));
		arriveminute.setBounds(387, 320, 41, 21);
		panelBooking_1.add(arriveminute);
		arriveminute.setVisible(false);
		JRoneway.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
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
					label_3.setVisible(true);
					arrivehour.setVisible(true);
					arriveminute.setVisible(true);
					label_4.setVisible(true);
					labeldate2.setVisible(true);
					jTextFieldDate2.setVisible(true);
				}
			}
		});
		
		
		JRroundtrip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
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
					label_3.setVisible(false);
					arrivehour.setVisible(false);
					arriveminute.setVisible(false);
					label_4.setVisible(false);
					labeldate2.setVisible(false);
					jTextFieldDate2.setVisible(false);
				}
			}
		});
		
		JComboBox comboinit = new JComboBox();
		comboinit.setToolTipText("起點");
		comboinit.setModel(new DefaultComboBoxModel(new String[] {"南港", "台北", "板橋", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南", "左營"}));
		comboinit.setSelectedIndex(0);
		comboinit.setBounds(130, 228, 75, 21);
		panelBooking_1.add(comboinit);
		
		JComboBox combodes = new JComboBox();
		combodes.setToolTipText("終點");
		combodes.setModel(new DefaultComboBoxModel(new String[] {"南港", "台北", "板橋", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南", "左營"}));
		combodes.setBounds(227, 228, 71, 21);
		panelBooking_1.add(combodes);
		
		JLabel book_inst_5 = new JLabel("車廂種類");
		book_inst_5.setBounds(55, 366, 57, 15);
		panelBooking_1.add(book_inst_5);
		
		JLabel book_inst_6 = new JLabel("座位喜好");
		book_inst_6.setBounds(54, 406, 66, 15);
		panelBooking_1.add(book_inst_6);
		
		JLabel book_inst_7 = new JLabel("去程時間");
		book_inst_7.setBounds(54, 323, 58, 15);
		panelBooking_1.add(book_inst_7);
		
		JRadioButton JBstandardcarriage = new JRadioButton("標準");
		JBstandardcarriage.setSelected(true);
		JBstandardcarriage.setBounds(119, 362, 70, 23);
		panelBooking_1.add(JBstandardcarriage);
		
		JRadioButton JBbusinesscarriage = new JRadioButton("商務");
		JBbusinesscarriage.setBounds(191, 362, 107, 23);
		panelBooking_1.add(JBbusinesscarriage);
		
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
		panelBooking_1.add(JBprefernone);
		
		JRadioButton JBpreferaisle = new JRadioButton("走道");
		JBpreferaisle.setBounds(191, 402, 72, 23);
		panelBooking_1.add(JBpreferaisle);
		
		JRadioButton JBpreferwindow = new JRadioButton("靠窗");
		JBpreferwindow.setBounds(273, 402, 66, 23);
		panelBooking_1.add(JBpreferwindow);
		
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
		
		JLabel book_inst_8 = new JLabel("乘客人數");
		book_inst_8.setBounds(516, 184, 58, 15);
		panelBooking_1.add(book_inst_8);
		
		JLabel book_inst_9 = new JLabel("全票");
		book_inst_9.setBackground(SystemColor.textHighlight);
		book_inst_9.setBounds(516, 216, 40, 15);
		panelBooking_1.add(book_inst_9);
		
		JLabel book_inst_10 = new JLabel("敬老");
		book_inst_10.setBackground(SystemColor.textHighlight);
		book_inst_10.setBounds(516, 257, 40, 15);
		panelBooking_1.add(book_inst_10);
		
		JLabel book_inst_11 = new JLabel("大學生");
		book_inst_11.setBackground(SystemColor.textHighlight);
		book_inst_11.setBounds(516, 299, 51, 15);
		panelBooking_1.add(book_inst_11);
		
		JLabel book_inst_12 = new JLabel("孩童");
		book_inst_12.setBackground(SystemColor.textHighlight);
		book_inst_12.setBounds(651, 216, 41, 15);
		panelBooking_1.add(book_inst_12);
		
		JLabel book_inst_13 = new JLabel("愛心");
		book_inst_13.setBackground(SystemColor.textHighlight);
		book_inst_13.setBounds(651, 257, 41, 15);
		panelBooking_1.add(book_inst_13);
		
		JSpinner spfullfare = new JSpinner();
		spfullfare.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spfullfare.setBounds(581, 213, 40, 22);
		panelBooking_1.add(spfullfare);
		
		JSpinner spelderyfare = new JSpinner();
		spelderyfare.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spelderyfare.setBounds(581, 254, 40, 22);
		panelBooking_1.add(spelderyfare);
		
		JSpinner spstudent = new JSpinner();
		spstudent.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spstudent.setBounds(581, 296, 40, 22);
		panelBooking_1.add(spstudent);
		
		JSpinner sphalfare = new JSpinner();
		sphalfare.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		sphalfare.setBounds(712, 213, 40, 22);
		panelBooking_1.add(sphalfare);
		
		JSpinner spdisabledfare = new JSpinner();
		spdisabledfare.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spdisabledfare.setBounds(712, 254, 40, 22);
		panelBooking_1.add(spdisabledfare);
		
		JButton submitbuton = new JButton("");
		submitbuton.addActionListener(new ActionListener() {
			
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
					int DepartureDate = (int)jTextFieldDate.getText().charAt(8)*10 + jTextFieldDate.getText().charAt(9);
					int ArrivialDate = (int)jTextFieldDate2.getText().charAt(8)*10 + jTextFieldDate2.getText().charAt(9);
					timemakesense |= (DepartureDate < ArrivialDate);
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
				if(!timemakesense&&JRroundtrip.isSelected()) {
					JOptionPane.showMessageDialog(null, "去程時間不能超過回程時間");
					success = false;
				}
				if(success) {
					String hr = departhour.getSelectedItem().toString();
					String mn = departminute.getSelectedItem().toString();
					String cond1 = (JBpreferaisle.isSelected())?"aisle":"";
					cond1 = (JBpreferwindow.isSelected())?"window":"";
					String timeR = (JRroundtrip.isSelected())?arrivehour.getSelectedItem().toString()+':'+arrivehour.getSelectedItem().toString():"";
					String dateR = (JRroundtrip.isSelected())?jTextFieldDate2.getText():"";
					addNewRes = new AddReservation(textFieldValue,jTextFieldDate.getText(),hr+':'+mn,stationfrom,
							stationtogo,dateR,timeR,cond1,spi5,spi1,spi2,spi3,spi4);
					
					try {
						creRes = addNewRes.getInstance();
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
					panelBooking_1.setVisible(false);
					panelReturn.setVisible(true);
					clickedcounter = false;
				}
			}
		});
		
		JButton returnbutton = new JButton("");
		returnbutton.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		returnbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelBooking_1.setVisible(false);
			}
		});
		returnbutton.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		returnbutton.setBounds(683, 406, 168, 56);
		panelBooking_1.add(returnbutton);
		submitbuton.setIcon(new ImageIcon(relativelyPath+"/src/gui/confirm.png"));
		submitbuton.setBounds(478, 406, 168, 56);
		panelBooking_1.add(submitbuton);
		
		JLabel lblNewLabel = new JLabel(":");
		lblNewLabel.setBounds(180, 323, 9, 15);
		panelBooking_1.add(lblNewLabel);
		
		JLabel labeldate = new JLabel("去程日期");
		labeldate.setBounds(257, 272, 53, 30);
		panelBooking_1.add(labeldate);
		
		jTextFieldDate = new JTextField();
		jTextFieldDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Table();
//				/datePicker.setVisible(true); 
			}
		});
		jTextFieldDate.setBounds(315, 277, 46, 21);
		panelBooking_1.add(jTextFieldDate);
		jTextFieldDate.setColumns(10);
		
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
		
		final JPanel panelRefund = new JPanel();
		frame.getContentPane().add(panelRefund, "name_2762253138900");
		panelRefund.setLayout(null);
		
		JPanel panelCancel = new JPanel();
		panelCancel.setLayout(null);
		frame.getContentPane().add(panelCancel, "name_313701673413300");
		
		JLabel label_13 = new JLabel("訂位資料取消");
		label_13.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_13.setBounds(374, 28, 211, 39);
		panelCancel.add(label_13);
		
		JLabel label_14 = new JLabel("請輸入訂位識別碼:");
		label_14.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label_14.setBounds(99, 175, 170, 25);
		panelCancel.add(label_14);
		
		JLabel label_15 = new JLabel("身份證字號 :");
		label_15.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label_15.setBounds(99, 248, 128, 25);
		panelCancel.add(label_15);
		
		JLabel label_16 = new JLabel("訂位代號 :");
		label_16.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label_16.setBounds(99, 308, 97, 25);
		panelCancel.add(label_16);
		
		ID = new JTextField();
		ID.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		ID.setColumns(10);
		ID.setBounds(237, 247, 117, 27);
		panelCancel.add(ID);
		
		ReservationNo = new JTextField();
		ReservationNo.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		ReservationNo.setColumns(10);
		ReservationNo.setBounds(237, 307, 117, 27);
		panelCancel.add(ReservationNo);
		
		final JPanel panelSelectReservToCancel = new JPanel();
		frame.getContentPane().add(panelSelectReservToCancel, "name_314320031617900");
		panelSelectReservToCancel.setLayout(null);
		
		JLabel label_17 = new JLabel("請選擇欲取消的交易 :");
		label_17.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_17.setBounds(339, 28, 281, 39);
		panelSelectReservToCancel.add(label_17);
		
		JList listToCancel = new JList();
		listToCancel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listToCancel.setSelectedIndex(0);
		listToCancel.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		listToCancel.setBackground(SystemColor.info);
		listToCancel.setBounds(175, 98, 542, 272);
		panelSelectReservToCancel.add(listToCancel);
		
		JButton CancelReservConfirm = new JButton("");
		CancelReservConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = listToCancel.getSelectedIndex()+1;
				creMod.excuteDelete(selection);
				JOptionPane.showMessageDialog(null, "已成功刪除訂票!");
			}
		});
		CancelReservConfirm.setIcon(new ImageIcon(relativelyPath+"/src/gui/confirm.png"));
		CancelReservConfirm.setBounds(478, 406, 168, 56);
		panelSelectReservToCancel.add(CancelReservConfirm);
		
		JButton returnToSelectResToCancel = new JButton("");
		returnToSelectResToCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSelectReservToCancel.setVisible(false);
				panelCancel.setVisible(true);
			}
		});
		returnToSelectResToCancel.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		returnToSelectResToCancel.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		returnToSelectResToCancel.setBounds(683, 406, 168, 56);
		panelSelectReservToCancel.add(returnToSelectResToCancel);
		
		
		
		JButton login = new JButton("");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/*if(uid_resno_error_detection(ID.getText(), ReservationNo.getText())) {
					addNewMod = new AddModify (ID.getText(), ReservationNo.getText()); 
				try {
					creMod = addNewMod.getInstance();
					listToCancel.setModel(new AbstractListModel() {
						String[] values  = creMod.checkDelete();
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
				*/
				JOptionPane.showMessageDialog(null,"退票成功!");
				panelMain.setVisible(true);
				panelCancel.setVisible(false);
				
			}
		});
		login.setIcon(new ImageIcon(relativelyPath+"/src/gui/confirm.png"));
		login.setBounds(478, 406, 168, 56);
		panelCancel.add(login);
		
		JButton returnToRefund = new JButton("");
		returnToRefund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCancel.setVisible(false);
				panelRefund.setVisible(true);
			}
		});
		returnToRefund.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		returnToRefund.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		returnToRefund.setBounds(683, 406, 168, 56);
		panelCancel.add(returnToRefund);
		
		final JPanel panelModify = new JPanel();
		frame.getContentPane().add(panelModify, "name_307963157522700");
		panelModify.setLayout(null);
		
		JPanel panelSelectReservToModify = new JPanel();
		frame.getContentPane().add(panelSelectReservToModify, "name_322703150856200");
		panelSelectReservToModify.setLayout(null);
		
		JList listToModify = new JList();
		listToModify.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listToModify.setSelectedIndex(0);
		listToModify.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		listToModify.setBackground(SystemColor.info);
		listToModify.setBounds(66, 98, 542, 272);
		panelSelectReservToModify.add(listToModify);
		
		JButton confirmModify = new JButton("");
		confirmModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(uid_resno_error_detection(UID_Modify.getText(),ResNo_Modify.getText())) {
					addNewMod = new AddModify(UID_Modify.getText(), ResNo_Modify.getText()); 
					try {
						creMod = addNewMod.getInstance();
						listToModify.setModel(new AbstractListModel() {
							String[] values  = creMod.checkModify();
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
					panelSelectReservToModify.setVisible(true);
					panelCancel.setVisible(false);
				}
			}
		});
		confirmModify.setIcon(new ImageIcon(GUI.class.getResource("/gui/confirm.png")));
		confirmModify.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		confirmModify.setBounds(478, 406, 168, 56);
		panelModify.add(confirmModify);
		
		JButton back = new JButton("");
		
			back.setIcon(new ImageIcon(GUI.class.getResource("/gui/violet.jpg")));
			back.setBounds(683, 406, 168, 56);
			panelModify.add(back);
			
			JLabel label_18 = new JLabel("請輸入訂位識別碼:");
			label_18.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
			label_18.setBounds(99, 175, 170, 25);
			panelModify.add(label_18);
			
			JLabel label_19 = new JLabel("身份證字號 :");
			label_19.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
			label_19.setBounds(99, 248, 128, 25);
			panelModify.add(label_19);
			
			JLabel label_20 = new JLabel("訂位代號 :");
			label_20.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
			label_20.setBounds(99, 308, 97, 25);
			panelModify.add(label_20);
			
			JLabel label_21 = new JLabel("減少訂位人數");
			label_21.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
			label_21.setBounds(374, 28, 211, 39);
			panelModify.add(label_21);
			
			UID_Modify = new JTextField();
			UID_Modify.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
			UID_Modify.setColumns(10);
			UID_Modify.setBounds(237, 247, 117, 27);
			panelModify.add(UID_Modify);
			
			ResNo_Modify = new JTextField();
			ResNo_Modify.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
			ResNo_Modify.setColumns(10);
			ResNo_Modify.setBounds(237, 307, 117, 27);
			panelModify.add(ResNo_Modify);
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panelRefund.setVisible(true);
					panelModify.setVisible(false);
				}
			});
		
		
		
		JLabel label_22 = new JLabel("請選擇欲修改的交易 :");
		label_22.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_22.setBounds(339, 28, 281, 39);
		panelSelectReservToModify.add(label_22);
		
		
		
		JComboBox typeToModify = new JComboBox();
		typeToModify.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		typeToModify.setModel(new DefaultComboBoxModel(new String[] {"一般票", "大學票", "敬老票", "愛心票", "兒童票"}));
		typeToModify.setBounds(668, 170, 88, 30);
		panelSelectReservToModify.add(typeToModify);
		
		JComboBox NumToModify = new JComboBox();
		NumToModify.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"}));
		NumToModify.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		NumToModify.setBounds(668, 283, 88, 30);
		panelSelectReservToModify.add(NumToModify);
		
		JButton ModifyReservConfirm = new JButton("");
		ModifyReservConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = listToModify.getSelectedIndex();
				String type = "";
				int reduce = NumToModify.getSelectedIndex()+1;
				if (typeToModify.getSelectedItem().toString().equals("一般票")) type = "numOfNormal";
				if (typeToModify.getSelectedItem().toString().equals("大學票")) type = "numOfCollege";
				if (typeToModify.getSelectedItem().toString().equals("愛心票")) type = "numOfChallenged";
				if (typeToModify.getSelectedItem().toString().equals("敬老票")) type = "numOfSenior";
				if (typeToModify.getSelectedItem().toString().equals("兒童票")) type = "numOfChildren";
			
				creMod.executeModify(selection, type, reduce);
			}
		});
		ModifyReservConfirm.setIcon(new ImageIcon(relativelyPath+"/src/gui/confirm.png"));
		ModifyReservConfirm.setBounds(478, 406, 168, 56);
		panelSelectReservToModify.add(ModifyReservConfirm);
		
		JButton returnToSelectReservToModify = new JButton("");
		returnToSelectReservToModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSelectReservToModify.setVisible(false);
				panelCancel.setVisible(true);
			}
		});
		returnToSelectReservToModify.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		returnToSelectReservToModify.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		returnToSelectReservToModify.setBounds(683, 406, 168, 56);
		panelSelectReservToModify.add(returnToSelectReservToModify);
		
		JLabel label_29 = new JLabel("請選擇欲減少的票種 : ");
		label_29.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_29.setBounds(641, 108, 281, 39);
		panelSelectReservToModify.add(label_29);
		
		JLabel label_30 = new JLabel("請選擇欲減少的數量 : ");
		label_30.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_30.setBounds(641, 221, 281, 39);
		panelSelectReservToModify.add(label_30);
		
		final JPanel panelCheck = new JPanel();
		frame.getContentPane().add(panelCheck, "name_2765710027600");
		panelCheck.setLayout(null);
		panelCheck.setVisible(false);
		
		JButton btnBookingButton = new JButton("");
		btnBookingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelMain.setVisible(false);
				panelBooking_1.setVisible(true);
				
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
		MainBbgnLabel.setIcon(new ImageIcon(relativelyPath+"/src/gui/train.png"));
		MainBbgnLabel.setBounds(298, 264, 317, 192);
		panelMain.add(MainBbgnLabel);
		
		JButton SubmittReturnButton = new JButton("");
		SubmittReturnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clickedcounter = !clickedcounter ;
				if(JRroundtrip.isSelected()) {
					if(clickedcounter) {
						list.clearSelection();
						textFieldValue = textField.getText();
						spi1 = (Integer)spfullfare.getValue();
						spi2 = (Integer)sphalfare.getValue();
						spi3 = (Integer)spelderyfare.getValue();
						spi4 = (Integer)spdisabledfare.getValue();
						spi5 = (Integer)spstudent.getValue();
						stationfrom = comboinit.getSelectedItem().toString();
						stationtogo = combodes.getSelectedItem().toString();
						String hr = arrivehour.getSelectedItem().toString();
						String mn = arriveminute.getSelectedItem().toString();
						String cond1 = (JBpreferaisle.isSelected())?"aisle":"";
						cond1 = (JBpreferwindow.isSelected())?"window":"";
						String timeR = (JRroundtrip.isSelected())?arrivehour.getSelectedItem().toString()+':'+arrivehour.getSelectedItem().toString():"";
						String dateR = (JRroundtrip.isSelected())?jTextFieldDate2.getText():"";
						addNewRes = new AddReservation(textFieldValue,jTextFieldDate2.getText(),hr+':'+mn,stationtogo,
								stationfrom,"","",cond1,spi5,spi1,spi2,spi3,spi4);
						
						try {
							creRes = addNewRes.getInstance();
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
					}
					if (!clickedcounter) {
						int index = list.getSelectedIndex();
						selected[1] = index;
						System.out.println("GUI line1012");
						System.out.println(selected[1]);
						String resno = creRes.saveAndOutput(selected);
						JOptionPane.showMessageDialog(null, "來回票訂票成功！,您的訂位代號是 "+resno+" , 請牢記。");
						panelMain.setVisible(true);
						panelReturn.setVisible(false);
					}
				}
				else {
					System.out.println("gui line 1021 , selected[0] = "+selected[0]);
					selected[1] = -1;
					String resno = creRes.saveAndOutput(selected);
					JOptionPane.showMessageDialog(null, "單程票訂票成功！,您的訂位代號是 "+resno+" , 請牢記。");
					panelMain.setVisible(true);
					panelReturn.setVisible(false);
				}
			}
		});
		SubmittReturnButton.setIcon(new ImageIcon(relativelyPath+"/src/gui/confirm.png"));
		SubmittReturnButton.setBounds(342, 443, 192, 43);
		panelReturn.add(SubmittReturnButton);
		
		JButton modify = new JButton("減少人數");
		modify.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelRefund.setVisible(false);
				panelModify.setVisible(true);
			}
		});
		modify.setBounds(65, 187, 114, 62);
		panelRefund.add(modify);
		
		JButton delete = new JButton("取消行程");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelRefund.setVisible(false);
				panelCancel.setVisible(true);
			}
		});
		delete.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		delete.setBounds(210, 187, 114, 62);
		panelRefund.add(delete);
		
		JButton menu2 = new JButton("");
		menu2.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.setVisible(true);
				panelRefund.setVisible(false);
			}
		});
		menu2.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		menu2.setBounds(683, 406, 178, 53);
		panelRefund.add(menu2);
		
		JPanel panelCheckTimetable = new JPanel();
		frame.getContentPane().add(panelCheckTimetable, "name_310663936739500");
		panelCheckTimetable.setLayout(null);
		
		JPanel panelCheckConcession = new JPanel();
		frame.getContentPane().add(panelCheckConcession, "name_327216762015800");
		panelCheckConcession.setLayout(null);
		
		JPanel panelCheckTransaction = new JPanel();
		frame.getContentPane().add(panelCheckTransaction, "name_329017595856500");
		panelCheckTransaction.setLayout(null);
		
		JPanel panelCheckTransactionNoRN = new JPanel();
		frame.getContentPane().add(panelCheckTransactionNoRN, "name_329186132052100");
		panelCheckTransactionNoRN.setLayout(null);
		
		JPanel panelCheckResult = new JPanel();
		panelCheckResult.setLayout(null);
		frame.getContentPane().add(panelCheckResult, "name_331133422060600");
		
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(105, 93, 662, 342);
		
		panelCheckResult.add(scrollPane);
		
		
		JList listTimetableResult = new JList();
		listTimetableResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTimetableResult.setSelectedIndex(0);
		listTimetableResult.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		listTimetableResult.setBackground(SystemColor.info);
		listTimetableResult.setBounds(105, 93, 662, 342);
		scrollPane.setViewportView(listTimetableResult);
		
		
		
		JLabel label = new JLabel("請選擇服務：");
		label.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label.setBounds(65, 136, 130, 25);
		panelRefund.add(label);
		panelRefund.setVisible(false);
		
		JButton timetable = new JButton("查詢時刻表");
		timetable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(false);
				panelCheckTimetable.setVisible(true);
			}
		});
		timetable.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		timetable.setBounds(99, 180, 114, 62);
		panelCheck.add(timetable);
		
		JButton concession = new JButton("查詢優惠車次");
		concession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(false);
				panelCheckConcession.setVisible(true);
			}
		});
		concession.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		concession.setBounds(244, 180, 114, 62);
		panelCheck.add(concession);
		
		JButton transaction = new JButton("查詢訂位紀錄");
		transaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(false);
				panelCheckTransaction.setVisible(true);
			}
		});
		transaction.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		transaction.setBounds(389, 180, 114, 62);
		panelCheck.add(transaction);
		
		JButton transactionNoRN = new JButton("查詢訂位代號");
		transactionNoRN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(false);
				panelCheckTransactionNoRN.setVisible(true);
			}
		});
		transactionNoRN.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		transactionNoRN.setBounds(534, 180, 114, 62);
		panelCheck.add(transactionNoRN);
		
		JButton menu3 = new JButton("");
		menu3.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
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
		
			
		JLabel EnterDate = new JLabel("請輸入欲查詢時刻表的日期：");
		EnterDate.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		EnterDate.setBounds(99, 129, 253, 26);
		panelCheckTimetable.add(EnterDate);
		
		JButton returnToCheck = new JButton("");
		returnToCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(true);
				panelCheckTimetable.setVisible(false);
			}
		});
		returnToCheck.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		returnToCheck.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		returnToCheck.setBounds(650, 400, 161, 47);
		panelCheckTimetable.add(returnToCheck);
		
		
		JComboBox dd_enterdate = new JComboBox();
		dd_enterdate.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dd_enterdate.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		dd_enterdate.setBounds(300, 203, 52, 21);
		panelCheckTimetable.add(dd_enterdate);
		
		JComboBox yyyy_enterdate = new JComboBox();
		yyyy_enterdate.setModel(new DefaultComboBoxModel(new String[] {"2019"}));
		yyyy_enterdate.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		yyyy_enterdate.setBounds(99, 203, 68, 21);
		panelCheckTimetable.add(yyyy_enterdate);
		
		JComboBox mm_enterdate = new JComboBox();
		mm_enterdate.setModel(new DefaultComboBoxModel(new String[] {"01", "02"}));
		mm_enterdate.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		mm_enterdate.setBounds(207, 203, 52, 21);
		panelCheckTimetable.add(mm_enterdate);
		
		JButton checkSchedule = new JButton("");
		checkSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String date = yyyy_enterdate.getSelectedItem().toString()+"/"+mm_enterdate.getSelectedItem().toString()+"/"+dd_enterdate.getSelectedItem().toString();
				addNewChe = new AddCheck("","","","","","",date,"","","","");
				try {
					creChe = addNewChe.getInstance();
					listTimetableResult.setModel(new AbstractListModel() {
						String[] values = creChe.checkNormalTable();
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
				panelCheckTimetable.setVisible(false);
				panelCheckResult.setVisible(true);
			}
		});
		
		
		checkSchedule.setIcon(new ImageIcon(relativelyPath+"/src/gui/confirm.png"));
		checkSchedule.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		checkSchedule.setBounds(442, 400, 161, 47);
		panelCheckTimetable.add(checkSchedule);
		
		JLabel year = new JLabel("年");
		year.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		year.setBounds(177, 201, 29, 25);
		panelCheckTimetable.add(year);
		
		
		
		JLabel month = new JLabel("月");
		month.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		month.setBounds(269, 201, 29, 25);
		panelCheckTimetable.add(month);
		
		JLabel date = new JLabel("日");
		date.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		date.setBounds(366, 201, 29, 25);
		panelCheckTimetable.add(date);
		
		JLabel labelTimetableResult = new JLabel("查詢結果 :");
		labelTimetableResult.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		labelTimetableResult.setBounds(390, 43, 100, 39);
		panelCheckResult.add(labelTimetableResult);
		
		JButton returnToCheck_timetable = new JButton("");
		returnToCheck_timetable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(true);
				panelCheckResult.setVisible(false);
			}
		});
		returnToCheck_timetable.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		returnToCheck_timetable.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		returnToCheck_timetable.setBounds(695, 445, 161, 47);
		panelCheckResult.add(returnToCheck_timetable);
		
		
		
		
		JLabel label_12 = new JLabel("請輸入欲查詢時刻表的日期：");
		label_12.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_12.setBounds(99, 118, 253, 26);
		panelCheckConcession.add(label_12);
		
		JButton returnToCheck_train = new JButton("");
		returnToCheck_train.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(true);
				panelCheckConcession.setVisible(false);
			}
		});
		returnToCheck_train.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		returnToCheck_train.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		returnToCheck_train.setBounds(650, 389, 161, 47);
		panelCheckConcession.add(returnToCheck_train);
		
		JComboBox comboinit_train = new JComboBox();
		comboinit_train.setToolTipText("起點");
		comboinit_train.setModel(new DefaultComboBoxModel(new String[] {"南港", "台北", "板橋", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南", "左營"}));
		comboinit_train.setSelectedIndex(0);
		comboinit_train.setBounds(490, 190, 75, 21);
		panelCheckConcession.add(comboinit_train);
		
		JComboBox combodes_train = new JComboBox();
		combodes_train.setToolTipText("終點");
		combodes_train.setModel(new DefaultComboBoxModel(new String[] {"南港", "台北", "板橋", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南", "左營"}));
		combodes_train.setBounds(590, 190, 75, 21);
		panelCheckConcession.add(combodes_train);
		
		JLabel year_train = new JLabel("年");
		year_train.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		year_train.setBounds(177, 190, 29, 25);
		panelCheckConcession.add(year_train);
		
		JComboBox yyyy_train = new JComboBox();
		yyyy_train.setModel(new DefaultComboBoxModel(new String[] {"2019"}));
		yyyy_train.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		yyyy_train.setBounds(99, 192, 68, 21);
		panelCheckConcession.add(yyyy_train);
		
		JComboBox mm_train = new JComboBox();
		mm_train.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		mm_train.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		mm_train.setBounds(207, 192, 52, 21);
		panelCheckConcession.add(mm_train);
		
		JLabel month_train = new JLabel("月");
		month_train.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		month_train.setBounds(269, 190, 29, 25);
		panelCheckConcession.add(month_train);
		
		JLabel date_train = new JLabel("日");
		date_train.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		date_train.setBounds(366, 190, 29, 25);
		panelCheckConcession.add(date_train);
		
		JComboBox dd_train = new JComboBox();
		dd_train.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"}));
		dd_train.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		dd_train.setBounds(300, 192, 52, 21);
		panelCheckConcession.add(dd_train);
		
		JComboBox hour_train = new JComboBox();
		hour_train.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		hour_train.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		hour_train.setBounds(207, 308, 52, 21);
		panelCheckConcession.add(hour_train);
		
		JLabel label_10 = new JLabel("時");
		label_10.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label_10.setBounds(269, 306, 29, 25);
		panelCheckConcession.add(label_10);
		
		JComboBox minute_train = new JComboBox();
		minute_train.setModel(new DefaultComboBoxModel(new String[] {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}));
		minute_train.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		minute_train.setBounds(300, 308, 52, 21);
		panelCheckConcession.add(minute_train);
		
		JLabel label_11 = new JLabel("分");
		label_11.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label_11.setBounds(366, 306, 29, 25);
		panelCheckConcession.add(label_11);
		
		JRadioButton bydeparttime = new JRadioButton("出發時間");
		bydeparttime.setBounds(191, 250, 93, 23);
		bydeparttime.setSelected(true);
		panelCheckConcession.add(bydeparttime);
		
		
		JRadioButton byarrivedtime = new JRadioButton("抵達時間");
		byarrivedtime.setBounds(288, 250, 107, 23);
		byarrivedtime.setSelected(false);
		panelCheckConcession.add(byarrivedtime);
		
		bydeparttime.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {			
				if(bydeparttime.isSelected()) {
					byarrivedtime.setSelected(false);
				}
				else {
					bydeparttime.setSelected(true);
				}
			}
		});

		byarrivedtime.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {			
				if(bydeparttime.isSelected()) {
					bydeparttime.setSelected(false);
				}
				else {
					byarrivedtime.setSelected(true);
				}
			}
		});
		
		JButton checkconcession = new JButton("");
		checkconcession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				String date = yyyy_train.getSelectedItem().toString()+"/"+mm_train.getSelectedItem().toString()+"/"+dd_train.getSelectedItem().toString();
				String time = hour_train.getSelectedItem().toString()+"/"+minute_train.getSelectedItem().toString();
				String condition = "depart";
				if (bydeparttime.isSelected()) condition = "depart";
				if (byarrivedtime.isSelected()) condition = "arrive";
				String depart = comboinit_train.getSelectedItem().toString();
				String destination = combodes_train.getSelectedItem().toString();
				
				addNewChe = new AddCheck("","","","","","",date,time,condition,depart,destination);
				try {
					creChe = addNewChe.getInstance();
					
					listTimetableResult.setModel(new AbstractListModel() {
						String[] values = creChe.checkConcessionTable();
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
				panelCheckConcession.setVisible(false);
				panelCheckResult.setVisible(true);
			}
		});
		checkconcession.setIcon(new ImageIcon(relativelyPath+"/src/gui/confirm.png"));
		checkconcession.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		checkconcession.setBounds(442, 389, 161, 47);
		panelCheckConcession.add(checkconcession);
		
		
		
		
		
		JLabel book_inst_train = new JLabel("請選擇起訖車站");
		book_inst_train.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		book_inst_train.setBounds(490, 121, 143, 26);
		panelCheckConcession.add(book_inst_train);
		
		JLabel label_23 = new JLabel("根據 : ");
		label_23.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_23.setBounds(99, 247, 52, 26);
		panelCheckConcession.add(label_23);
		
		JLabel lblNewLabel_3 = new JLabel("查詢優惠車次");
		lblNewLabel_3.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(368, 58, 137, 26);
		panelCheckConcession.add(lblNewLabel_3);
	
		JLabel lableID_transation = new JLabel("請輸入您的身分證字號");
		lableID_transation.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		lableID_transation.setBounds(108, 172, 205, 39);
		panelCheckTransaction.add(lableID_transation);
		
		keyID_transation = new JTextField();
		keyID_transation.setBounds(321, 176, 196, 39);
		panelCheckTransaction.add(keyID_transation);
		keyID_transation.setColumns(10);
		
		reserveID_transation = new JTextField();
		reserveID_transation.setColumns(10);
		reserveID_transation.setBounds(321, 247, 196, 39);
		panelCheckTransaction.add(reserveID_transation);
		
		JLabel lablereserveID_transation = new JLabel("請輸入訂位代號");
		lablereserveID_transation.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		lablereserveID_transation.setBounds(108, 243, 205, 39);
		panelCheckTransaction.add(lablereserveID_transation);
		
		JButton comboinit_transation = new JButton("");
		comboinit_transation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String UID = keyID_transation.getText();
				String reservationNo = reserveID_transation.getText();
				
				addNewChe = new AddCheck(UID, reservationNo,"","","","","","","","",""); 
				boolean find = false;
				try {
					creChe = addNewChe.getInstance();
					String[] recordValue = creChe.checkWithRn();
					if (recordValue[0]!=null) 
						find = true;
					listTimetableResult.setModel(new AbstractListModel() {
						String[] values = recordValue;
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
				if(find) {
					panelCheckTransaction.setVisible(false);
					panelCheckResult.setVisible(true);		
				}
				else {
					JOptionPane.showMessageDialog(null, "查無訂位紀錄!");
					panelCheckTransaction.setVisible(false);
					panelMain.setVisible(true);
				}
			}
		});
		comboinit_transation.setIcon(new ImageIcon(relativelyPath+"/src/gui/confirm.png"));
		comboinit_transation.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		comboinit_transation.setBounds(478, 423, 161, 47);
		panelCheckTransaction.add(comboinit_transation);
		
		JButton combodes_transation = new JButton("");
		combodes_transation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(true);
				panelCheckTransaction.setVisible(false);
			}
		});
		combodes_transation.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		combodes_transation.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		combodes_transation.setBounds(686, 423, 161, 47);
		panelCheckTransaction.add(combodes_transation);
		
		JLabel lblNewLabel_2 = new JLabel("查詢訂位紀錄");
		lblNewLabel_2.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(339, 64, 177, 39);
		panelCheckTransaction.add(lblNewLabel_2);
		
		JLabel label_24 = new JLabel("請輸入您的身分證字號");
		label_24.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_24.setBounds(97, 179, 205, 39);
		panelCheckTransactionNoRN.add(label_24);
		
		keyID_transactionNoRN = new JTextField();
		keyID_transactionNoRN.setColumns(10);
		keyID_transactionNoRN.setBounds(310, 183, 196, 39);
		panelCheckTransactionNoRN.add(keyID_transactionNoRN);
		
		JLabel labelkeyID_transactionNoRN = new JLabel("請選擇起訖車站");
		labelkeyID_transactionNoRN.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		labelkeyID_transactionNoRN.setBounds(97, 245, 205, 39);
		panelCheckTransactionNoRN.add(labelkeyID_transactionNoRN);
		
		JComboBox start_transactionNoRN = new JComboBox();
		start_transactionNoRN.setToolTipText("起點");
		start_transactionNoRN.setModel(new DefaultComboBoxModel(new String[] {"南港", "台北", "板橋", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南", "左營"}));
		start_transactionNoRN.setSelectedIndex(0);
		start_transactionNoRN.setBounds(310, 258, 75, 21);
		panelCheckTransactionNoRN.add(start_transactionNoRN);
		
		JComboBox end_transactionNoRN = new JComboBox();
		end_transactionNoRN.setModel(new DefaultComboBoxModel(new String[] {"南港", "台北", "板橋", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南", "左營"}));
		end_transactionNoRN.setToolTipText("終點");
		end_transactionNoRN.setBounds(410, 258, 75, 21);
		panelCheckTransactionNoRN.add(end_transactionNoRN);
		
		JLabel trainID_transactionNoRN = new JLabel("請輸入訂票之車次號碼");
		trainID_transactionNoRN.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		trainID_transactionNoRN.setBounds(97, 313, 205, 39);
		panelCheckTransactionNoRN.add(trainID_transactionNoRN);
		
		textFieldtrainID_transactionNoRN = new JTextField();
		textFieldtrainID_transactionNoRN.setColumns(10);
		textFieldtrainID_transactionNoRN.setBounds(310, 317, 196, 39);
		panelCheckTransactionNoRN.add(textFieldtrainID_transactionNoRN);
		
		JLabel label_25 = new JLabel("請輸入訂票之車程日期：");
		label_25.setFont(new Font("微軟正黑體", Font.PLAIN, 19));
		label_25.setBounds(97, 131, 215, 26);
		panelCheckTransactionNoRN.add(label_25);
		
		JComboBox noRN_yyyy = new JComboBox();
		noRN_yyyy.setModel(new DefaultComboBoxModel(new String[] {"2019"}));
		noRN_yyyy.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		noRN_yyyy.setBounds(310, 133, 68, 21);
		panelCheckTransactionNoRN.add(noRN_yyyy);
		
		JLabel label_26 = new JLabel("年");
		label_26.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		label_26.setBounds(388, 131, 29, 25);
		panelCheckTransactionNoRN.add(label_26);
		
		JComboBox noRN_mm = new JComboBox();
		noRN_mm.setModel(new DefaultComboBoxModel(new String[] {"01", "02"}));
		noRN_mm.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		noRN_mm.setBounds(418, 133, 52, 21);
		panelCheckTransactionNoRN.add(noRN_mm);
		
		JLabel label_27 = new JLabel("月");
		label_27.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		label_27.setBounds(480, 131, 29, 25);
		panelCheckTransactionNoRN.add(label_27);
		
		JComboBox noRN_dd = new JComboBox();
		noRN_dd.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		noRN_dd.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		noRN_dd.setBounds(511, 133, 52, 21);
		panelCheckTransactionNoRN.add(noRN_dd);
		
		JLabel label_28 = new JLabel("日");
		label_28.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		label_28.setBounds(577, 131, 29, 25);
		panelCheckTransactionNoRN.add(label_28);
		
		JButton comboinit_transactionNoRN = new JButton("");
		comboinit_transactionNoRN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String UID = keyID_transactionNoRN.getText();
				String destination = textFieldtrainID_transactionNoRN.getText();
				String depart = start_transactionNoRN.getSelectedItem().toString();
				String date = end_transactionNoRN.getSelectedItem().toString();;
				String trainNo = textFieldtrainID_transactionNoRN.getText();

				addNewChe= new AddCheck(UID, "",destination,depart, 
						date,trainNo,"","","","","");
				try {
					creChe = addNewChe.getInstance();
					listTimetableResult.setModel(new AbstractListModel() {
						String[] values = creChe.checkWithoutRn();
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
				panelCheckTransactionNoRN.setVisible(false);
				panelCheckResult.setVisible(true);
			}
		});
		comboinit_transactionNoRN.setIcon(new ImageIcon(relativelyPath+"/src/gui/confirm.png"));
		comboinit_transactionNoRN.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		comboinit_transactionNoRN.setBounds(476, 436, 161, 47);
		panelCheckTransactionNoRN.add(comboinit_transactionNoRN);
		
		JButton combodes_transactionNoRN = new JButton("");
		combodes_transactionNoRN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCheck.setVisible(true);
				panelCheckTransactionNoRN.setVisible(false);
			}
		});
		combodes_transactionNoRN.setIcon(new ImageIcon(relativelyPath+"/src/gui/violet.jpg"));
		combodes_transactionNoRN.setFont(new Font("微軟正黑體", Font.PLAIN, 13));
		combodes_transactionNoRN.setBounds(684, 436, 161, 47);
		panelCheckTransactionNoRN.add(combodes_transactionNoRN);
		
		JLabel lblNewLabel_4 = new JLabel("查詢訂位代號");
		lblNewLabel_4.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(361, 58, 165, 33);
		panelCheckTransactionNoRN.add(lblNewLabel_4);
		
		
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
	private boolean uid_resno_error_detection(String UID, String resno) {
		boolean UIDsuccess=true;
		boolean rnsuccess=true;
		if(UID.isEmpty()) {
			JOptionPane.showMessageDialog(null, "身分證字號為空");
			UIDsuccess=false;
		}
		else {
//			/UID error detection
			try {
				Integer.parseInt(UID.substring(1));
			} catch(Exception e) {
				UIDsuccess = false;
			}
			if(UID.length()!=10)
				UIDsuccess = false;
			if((int)UID.charAt(0)<65 || (int)UID.charAt(0)>90)
				UIDsuccess =  false;
			if(!UIDsuccess) {
				JOptionPane.showMessageDialog(null, "身分證字號有誤");
			}
		}
		if(resno.isEmpty()) {
			JOptionPane.showMessageDialog(null, "訂位代號為空");
			rnsuccess=false;
		}
		else {
//			/reservationnumber error detection
			if(resno.length() != 8)
				rnsuccess = false;
			try {
			Integer.parseInt(resno);
			} catch(Exception e) {
			rnsuccess = false;
			}
			if(!rnsuccess) {
				JOptionPane.showMessageDialog(null, "訂位代號有誤");
			}
		}
		if (UIDsuccess && rnsuccess) return true;
		return false;
	}
	private void Table() {
		UtilDateModel model = new UtilDateModel();
		model.setDate(11,01,2019);
//		/ Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
//		/ Don't know about the formatter, but there it is...
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	}
}
