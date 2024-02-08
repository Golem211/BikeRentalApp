package implementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckPassword {
    protected String oldPassword;
    protected String oldEmail;

    public CheckPassword(String oldPassword, String oldEmail) {
        this.oldPassword = oldPassword;
        this.oldEmail = oldEmail;
        createUI();
    }

    private void createUI() {
        JFrame frame = new JFrame("Change Personal Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Old Password:");
        JTextField passwordField = new JTextField(20);
        JButton checkButton = new JButton("Check");

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPassword = passwordField.getText();
                if (enteredPassword.equals(oldPassword)) {
                    openChangeDetailsFrame();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect Password");
                }
            }
        });

        panel.add(label);
        panel.add(passwordField);
        panel.add(checkButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void openChangeDetailsFrame() { // aici trebuie sa fac
        // Code to open another frame for changing details
        // You can implement this based on your requirements
        // AICI TREBUIE SA FAC UI PT UPDATE UTILIZATOR
    	ChangePersonalDetails changePersonalDetails = new ChangePersonalDetails(oldPassword, oldEmail);
    	
    	JFrame changeDetails = changePersonalDetails.createRegisterPage();
    	changeDetails.setVisible(true);
        JOptionPane.showMessageDialog(null, "Password matched. Opening change details frame.");
    }

    public static void main(String[] args) {
        // Example usage
        CheckPassword detailsChanger = new CheckPassword("yourOldPassword","youroldemail");
    }
}
