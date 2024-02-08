package implementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewCenterDialog extends JDialog {

    private JTextField numeCentruField;
    private JTextField numeManagerField;
    private JTextField prenumeManagerField;
    private JComboBox<String> nonManageriComboBox;
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public NewCenterDialog() {
        super((JFrame)null, "New Center Dialog", true);

        // Initialize components
        numeCentruField = new JTextField(20);
        numeCentruField.setText("The name of new center: ");
        numeManagerField = new JTextField(20);
        prenumeManagerField = new JTextField(20);
        String[] nonManageri =  databaseConnector.getNonManageri();
        
        nonManageriComboBox = new JComboBox<>(nonManageri);
        // Create labels
        

        // Create OK and Cancel buttons
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("CANCEL");
        

        // Add action listener to OK button
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform actions when OK is clicked
                String numeCentru = numeCentruField.getText();
                String inputString = (String) nonManageriComboBox.getSelectedItem();
                
                String[] words = inputString.split("\\s+");
                
                String numeManager = words[0];
                String prenumeManager= words[1];
                	
                // Add logic to save the data or perform other actions
                // For now, just print the values
                System.out.println("Nume Centru: " + numeCentru);
                System.out.println("Nume Manager: " + numeManager);
                System.out.println("Prenume Manager: " + prenumeManager);
                databaseConnector.insertNewCenter(numeCentru, numeManager, prenumeManager);
                databaseConnector.updateWorkLocation(numeCentru, numeManager, prenumeManager);
                // Close the dialog
                dispose();
            }
        });

        // Add action listener to Cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the dialog without saving data
                dispose();
            }
        });

        // Create panel for components
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(nonManageriComboBox);
        panel.add(numeCentruField);
   
        panel.add(okButton);
        panel.add(cancelButton);
        

        // Set layout for the dialog
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Set dialog properties
        setSize(400, 500);
        
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    
}
