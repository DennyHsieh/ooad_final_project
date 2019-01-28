package net.sourceforge.jdatepicker.demo;  
import java.awt.BorderLayout;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.text.SimpleDateFormat;  
  
import javax.swing.ImageIcon;  
import javax.swing.JButton;  
import javax.swing.JPanel;  
import javax.swing.JTextField;  
import javax.swing.SwingUtilities;  
import javax.swing.WindowConstants;  
  
import net.sourceforge.jdatepicker.impl.JDatePickerDialog;  
  
public class JDatePickerDemo extends javax.swing.JFrame {  
    private static final long serialVersionUID = 1L;  
    private JPanel jPanelMain;  
    private JTextField jTextFieldDate;  
    private JButton jButtonDate;  
      
    //  Customized  
    private JDatePickerDialog datePicker;  
  
    /** 
    * Auto-generated main method to display this JFrame 
    */  
    public static void main(String[] args) {  
        SwingUtilities.invokeLater(new Runnable() {  
            public void run() {  
                JDatePickerDemo inst = new JDatePickerDemo();  
                inst.setLocationRelativeTo(null);  
                inst.setVisible(true);  
            }  
        });  
    }  
      
    public JDatePickerDemo() {  
        super();  
        initGUI();  
        // Setup JDatePicker here.  
        datePicker = new JDatePickerDialog(this, true, "Demo"); // arg1=Interactive frame; arg2=mode, arg3=title  
        datePicker.registerTF(jTextFieldDate);  
        datePicker.applyDateFormat(new SimpleDateFormat("yyyy-MM-dd"));  
    }  
      
    private void initGUI() {  
        try {  
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  
            {  
                jPanelMain = new JPanel();  
                getContentPane().add(jPanelMain, BorderLayout.CENTER);  
                jPanelMain.setBackground(new java.awt.Color(0,255,128));  
                jPanelMain.setLayout(null);  
                jPanelMain.setSize(300, 80);  
                {  
                    jTextFieldDate = new JTextField();  
                    jPanelMain.add(jTextFieldDate);  
                    jTextFieldDate.setText("Date");  
                    jTextFieldDate.setBounds(12, 12, 314, 32);  
                }  
                {  
                    jButtonDate = new JButton();  
                    jPanelMain.add(jButtonDate);  
                    jButtonDate.setBounds(332, 12, 41, 32);  
                    jButtonDate.setIcon(newImageIcon(getClass().getClassLoader().getResource("net/sourceforge/jdatepicker/demo/images/date.png")));  
                    jButtonDate.addActionListener(new ActionListener() {  
                        public void actionPerformed(ActionEvent evt) {  
                            jButtonDateActionPerformed(evt);  
                        }  
                    });  
                }  
            }  
            pack();  
            this.setSize(400, 100);  
        } catch (Exception e) {  
            //add your error handling code here  
            e.printStackTrace();  
        }  
    }  
      
    private void jButtonDateActionPerformed(ActionEvent evt) {  
        datePicker.setVisible(true);  
    }  
  
}  