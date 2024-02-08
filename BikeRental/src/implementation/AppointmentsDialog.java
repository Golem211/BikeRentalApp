package implementation;

import javax.swing.*;

public class AppointmentsDialog extends JDialog {
    private JTextArea appointmentsTextArea;

    public AppointmentsDialog() {
        
        initComponents();
    }

    private void initComponents() {
        appointmentsTextArea = new JTextArea(20, 40);
        appointmentsTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(appointmentsTextArea);

        getContentPane().add(scrollPane);
        pack();
    }

    public void setAppointmentsText(String appointmentsText) {
        
    	appointmentsTextArea.setText(appointmentsText);
    }
}
