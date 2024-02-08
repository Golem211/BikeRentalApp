package implementation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdminHomePage extends JFrame {

	private DatabaseConnector databaseConnector = new DatabaseConnector();
	private JTextField textField = new JTextField("Text Field");
	
    public AdminHomePage() {
        super("Admin Home Page");

        
        //JButton insertBikeButton = new JButton("Insert Bike");
        //JButton insertReceiptButton = new JButton("Insert Receipt");
        //JButton insertClientButton = new JButton("Insert Client");
        JButton noBikesAtACenter = new JButton("Have Fun: Press Me");
        JButton topCostumerButton = new JButton("Top costumer");
        JButton insertNewCenter = new JButton("insert New Center");
        JButton mediaSalariuCentru = new JButton("medie Salariu Centru");
        JButton cashInPerioad = new JButton("Cash in interval");
        JButton button6 = new JButton("Button 6");
        JButton button7 = new JButton("Button 7");
        JButton button8 = new JButton("Button 8");

        // Create panel for buttons at the top
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(noBikesAtACenter);
        buttonPanel.add(topCostumerButton);
        buttonPanel.add(insertNewCenter);
        buttonPanel.add(mediaSalariuCentru);
        buttonPanel.add(cashInPerioad);
        /*buttonPanel.add(button6);
        buttonPanel.add(button7);
        buttonPanel.add(button8);
        */
        
        // Add action listeners to the buttons
        //insertBikeButton.addActionListener(e -> handleInsertBikeButtonClick());
        //insertReceiptButton.addActionListener(e -> handleInsertReceiptButtonClick());
        //insertClientButton.addActionListener(e -> handleInsertClientButtonClick());
        
        noBikesAtACenter.addActionListener(e -> handleBikesAtACenterButtonClick());
        topCostumerButton.addActionListener(e -> topCostumerButtonClick());
        insertNewCenter.addActionListener(e -> insertNewCenterButtonClick());
        mediaSalariuCentru.addActionListener(e -> averagePayByLocationButtonClick());
        cashInPerioad.addActionListener(e -> cashInIntervalButtonClick());
        // Create a panel to hold the buttons
        
        //buttonPanel.add(insertBikeButton);
        //buttonPanel.add(insertReceiptButton);
        //buttonPanel.add(insertClientButton);

        // Add the panel to the frame
        getContentPane().add(buttonPanel);

        // Set the size of the frame
        setSize(400, 200);
        

        // Add components to the frame using BorderLayout
        add(buttonPanel, BorderLayout.NORTH);
        add(textField, BorderLayout.SOUTH);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Object cashInIntervalButtonClick() {
		// TODO Auto-generated method stub
    	new CashInDialog();
		return null;
	}

	private Object averagePayByLocationButtonClick() {
		new AveragePayDialog();
		return null;
	}

	private Object insertNewCenterButtonClick() {
		// TODO Auto-generated method stub
    	new NewCenterDialog();
    	// aici fac un dialog unde pot scrie Numele centrului Nume si prenume manager
    	
		return null;
	}

	private Object topCostumerButtonClick() {
		// TODO Auto-generated method stub
    	textField.setText("");
    	String return1 = databaseConnector.getTopCostumer();
    	textField.setText(return1);
		return null;
	}

	private Object handleBikesAtACenterButtonClick() {
		// TODO Auto-generated method stub
    	textField.setText("");
    	String return1 = databaseConnector.getBikesAtACenter();
    	textField.setText(return1);
		return null;
	}

	private void handleInsertBikeButtonClick() {
        // Handle Insert Bike button click
        System.out.println("Insert Bike button clicked");
        // Add your custom logic here
    }

    private void handleInsertReceiptButtonClick() {
        // Handle Insert Receipt button click
        System.out.println("Insert Receipt button clicked");
        // Add your custom logic here
    }

    private void handleInsertClientButtonClick() {
        // Handle Insert Client button click
        System.out.println("Insert Client button clicked");
        // Add your custom logic here
    }

    public static void main(String[] args) {
        // Create an instance of AdminHomePage
        AdminHomePage adminHomePage = new AdminHomePage();

        // Set the frame visible
        adminHomePage.setVisible(true);
    }
}
