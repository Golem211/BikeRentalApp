package implementation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChangePersonalDetails extends CheckPassword implements ActionListener{
	
	public ChangePersonalDetails(String oldPassword, String oldEmail) {
		super(oldPassword, oldEmail);
		// TODO Auto-generated constructor stub
		
	}

	private JFrame frame;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JButton btnUpdate;
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    
    
    
    public JFrame createRegisterPage() {
        frame = new JFrame("UPDATE info Form");
        frame.setLayout(null); // 0 rows, 2 columns
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create text fields for user input
        textField1 = new JTextField(15);
        textField2 = new JTextField(15);
        textField3 = new JTextField(15);
        textField4 = new JTextField(15);
        
        panel1 = new JPanel();
        panel1.setBackground(Color.cyan);
        panel1.setBounds(10, 10, 400, 30);
        
        
        panel2 = new JPanel();
        panel2.setBackground(Color.gray);
        panel2.setBounds(10, 50, 400, 30);
        
        panel3 = new JPanel();
        panel3.setBackground(Color.lightGray);
        panel3.setBounds(10, 90, 400, 30);
        
        
        panel4 = new JPanel();
        panel4.setBackground(Color.magenta);
        panel4.setBounds(10, 130, 400, 30);
        
        panel5 = new JPanel();
        panel5.setBackground(Color.blue);
        panel5.setBounds(50, 170, 80, 40);
        // Create insert button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(10, 10, 100, 40);
        
        btnUpdate.addActionListener(this); 

        // Add components to the frame
        panel1.add(new JLabel("New Nume: "));
        panel1.add(textField1);
        
        panel2.add(new JLabel("New Prenume: "));
        panel2.add(textField2);
        
        panel3.add(new JLabel("New Email: "));
        panel3.add(textField3);
        
        panel4.add(new JLabel("New Parola: "));
        panel4.add(textField4);
        
        panel5.add(btnUpdate);
        
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
         
        return frame;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//aici cod sql pt update
		System.out.println(textField1.getText()+' '+ textField2.getText()+' '+textField3.getText()+' '+textField4.getText()+' '+oldEmail+' '+oldPassword);
		databaseConnector.updatePersonalDetails(textField1.getText(), textField2.getText(),textField3.getText(),textField4.getText(),oldEmail,oldPassword);
		
	}


}
