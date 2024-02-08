package implementation;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterPage implements ActionListener {

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
	    private JButton btnInsert;
	    private DatabaseConnector databaseConnector;
      //  private JLabel spacer = new JLabel(" ");
	    public JFrame createRegisterPage() {
	        frame = new JFrame("Register Form");
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
	        btnInsert = new JButton("REGISTER");
	        btnInsert.setBounds(10, 10, 100, 40);

	        btnInsert.addActionListener(this); 

	        // Add components to the frame
	        panel1.add(new JLabel("Nume: "));
	        panel1.add(textField1);
	        
	        panel2.add(new JLabel("Prenume: "));
	        panel2.add(textField2);
	        
	        panel3.add(new JLabel("Email: "));
	        panel3.add(textField3);
	        
	        panel4.add(new JLabel("Parola: "));
	        panel4.add(textField4);
	        
	        panel5.add(btnInsert);
	        
	        /*panel1 = new JPanel();
	        panel2 = new JPanel();
	        panel3 = new JPanel();*/
	        
	        //frame.setVisible(true);
	        frame.add(panel1);
	        frame.add(panel2);
	        frame.add(panel3);
	        frame.add(panel4);
	        frame.add(panel5);
	        //frame.add(btnInsert);
	       // frame.add(spacer);
	        databaseConnector = new DatabaseConnector();
	        return frame;
	    }

	    

	    private void insertData() throws SQLException {
	        // Get user input from text fields
			String nume = textField1.getText();
			String prenume = textField2.getText();
			String email = textField3.getText();
			String parola = textField4.getText();

			// Create SQL insert statement
			String sqlInsert = "INSERT INTO Utilizator (nume, prenume, email, parola) VALUES ('" +
			        nume + "', '" + prenume + "', '" + email + "', '" + parola+ "')";

			// Execute the insert statement
			databaseConnector.executeUpdate(sqlInsert);

			JOptionPane.showMessageDialog(frame, "Data inserted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

			// Clear text fields after insertion
			textField1.setText("");
			textField2.setText("");
			textField3.setText("");
			textField4.setText("");
	    }



		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
           try {
        	   insertData();
			} catch (SQLException e1) {
					// TODO Auto-generated catch block
				e1.printStackTrace();
				}
       }
        
}

	    


