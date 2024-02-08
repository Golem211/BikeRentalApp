package implementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class CashInDialog extends JDialog {

    private JFormattedTextField yearTextField1;
    private JFormattedTextField yearTextField2;
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    // Default constructor without parameters
    public CashInDialog() {
        super((Frame) null, "Cash In Dialog", true);

        try {
            MaskFormatter yearFormatter = new MaskFormatter("####");

            yearTextField1 = new JFormattedTextField(yearFormatter);
            yearTextField2 = new JFormattedTextField(yearFormatter);

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Year 1: "));
            panel.add(yearTextField1);
            panel.add(new JLabel("Year 2: "));
            panel.add(yearTextField2);

            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    submitButtonClicked();
                }
            });

            panel.add(submitButton);

            add(panel);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void submitButtonClicked() {
        String year1 = yearTextField1.getText();
        String year2 = yearTextField2.getText();


        System.out.println("Year 1: " + year1);
        System.out.println("Year 2: " + year2);
        double cash = databaseConnector.getCashInFrom(year1, year2);
        String message = " Income between "+ year1 +' '+ year2 +" is: " + cash + "lei";
        JOptionPane.showMessageDialog(null, message, "Average Salary", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CashInDialog cashInDialog = new CashInDialog();
           
        });
    }
}
