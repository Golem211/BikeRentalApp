package implementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AveragePayDialog extends JDialog {

    
    
    
    private JComboBox<String> locatiiComboBox;
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public AveragePayDialog() {
        super((JFrame)null, "New Center Dialog", true);

        // Initialize components
        
        
        
        String[] locatii =  databaseConnector.getLocations();
        
        locatiiComboBox = new JComboBox<>(locatii);
        // Create labels
        

        // Create OK and Cancel buttons
        JButton submitButton = new JButton("submit");
        
        

        // Add action listener to OK button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform actions when OK is clicked
               
                String numeCentru = (String) locatiiComboBox.getSelectedItem();
                
                double average = databaseConnector.getAverageCenter(numeCentru);	
                
                // Add logic to save the data or perform other actions
                // For now, just print the values
                String message = " Average salary at centre: "+numeCentru +" is: " + average;
                JOptionPane.showMessageDialog(null, message, "Average Salary", JOptionPane.INFORMATION_MESSAGE);
                // Close the dialog
                dispose();
            }
        });

        // Add action listener to Cancel button
        
        // Create panel for components
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(locatiiComboBox);
        
   
        panel.add(submitButton);
        
        

        // Set layout for the dialog
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Set dialog properties
        setSize(300, 400);
        
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    
}
