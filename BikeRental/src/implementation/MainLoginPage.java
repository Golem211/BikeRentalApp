package implementation;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainLoginPage  implements ActionListener{
	private JFrame mainFrame;
	private JTextField textField1;
	private JTextField textField2;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JButton btnLogin;
	private DatabaseConnector databaseConnector;
	
	
	public JFrame createMainLoginPage() {
		mainFrame = new JFrame("Login Page");
		textField1 = new JTextField(20);
		textField2 = new JTextField(20);
		btnLogin = new JButton("Login");
		databaseConnector = new DatabaseConnector();
		
        mainFrame.setLayout(null); // 0 rows, 2 columns
        mainFrame.setSize(500, 300);
        //frame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create text fields for user input
        
        panel1 = new JPanel();
        panel1.setBackground(Color.cyan);
        panel1.setBounds(10, 10, 400, 30);
        
        
        panel2 = new JPanel();
        panel2.setBackground(Color.gray);
        panel2.setBounds(10, 50, 400, 30);
        
        
        panel3 = new JPanel();
        panel3.setBackground(Color.gray);
        panel3.setBounds(10, 90, 400, 30);
        
        btnLogin.setBounds(10, 10, 100, 40);

        btnLogin.addActionListener(this); 

        // Add components to the frame
        panel1.add(new JLabel("email: "));
        panel1.add(textField1);
        
        panel2.add(new JLabel("parola: "));
        panel2.add(textField2);
        
        panel3.add(btnLogin);
        
        //mainFrame.setVisible(true);
        mainFrame.add(panel1);
        mainFrame.add(panel2);
        mainFrame.add(panel3);
        return mainFrame;
        
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		String[] userInfo = databaseConnector.loginVerify(textField1, textField2);
		String[] isAdmin = databaseConnector.adminVerify(textField1, textField2);
		if (userInfo != null  ) {
		    String name = userInfo[0];
		    String surname = userInfo[1];

		    JOptionPane.showMessageDialog(mainFrame, "Hello, " + name + " " + surname + "! Welcome back.", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
		    
		    if (isAdmin != null) {
		        int choice = JOptionPane.showConfirmDialog(
		                mainFrame,
		                "Hello, " + name + " " + surname + "! You are logged in as an admin. Do you want to continue as an admin?",
		                "Admin Confirmation",
		                JOptionPane.YES_NO_OPTION);

		        if (choice == JOptionPane.YES_OPTION) {
		            // Open the AdminHomePage
		            AdminHomePage adminHomePage = new AdminHomePage();
		            adminHomePage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		            adminHomePage.setVisible(true);
		            return; // Skip the regular user part
		        }
		    }		    
		    
		    // Open another page or perform actions based on successful login
		    
		    String[] locatii = databaseConnector.getLocations(); // to put in the constructor
		    List<BikeCategory> bikeCategories = databaseConnector.getBikeCategoryInfo();
		    // get the name of categories in order
		    String[] categoryOptions = bikeCategories.stream()
		            .map(BikeCategory::getDenumire)
		            .toArray(String[]::new);
		    
		    double[] prices = bikeCategories.stream()
		            .mapToDouble(BikeCategory::getPretInchiriatOra)
		            .toArray();
		    
		    
		    HomePage homePage = new HomePage(name, surname, textField2.getText(), textField1.getText());
		    
		    
		    textField1.setText("");
            textField2.setText("");
		    // Create and display the home page frame
		    JFrame homePageFrame = homePage.createHomePageFrame(categoryOptions, locatii, prices);
		    homePageFrame.setVisible(true);
		    //JFrame homeFrame = homePage.createHomePageFrame(); 
		    //homeFrame.setVisible(true);
		    
		} else {
		    JOptionPane.showMessageDialog(mainFrame, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	   
   }


}
