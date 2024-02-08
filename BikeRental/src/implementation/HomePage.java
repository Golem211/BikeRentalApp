package implementation;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HomePage {
    private String name;
    private String surname;
    private String password;
    private String email;
    
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> rentalCenterComboBox;
    //private JTextField dateTextField;
    //private JComboBox<String> hourComboBox;
    
    private JButton chooseDateButton;
    private Integer sYear;
    private Integer sMonth;
    private Integer sDay;
    private Integer sHour;
    private Integer sInterval;
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    
    private JButton makeAppointmentButton;
    private JButton changePersonalDetails;
    private JButton showAppointmentsButton;
    private JButton deleteAppointmentsButton;
    private JButton deleteUserButton;
    // to modify the user nume prenume email parola.
    public HomePage(String name, String surname, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public JFrame createHomePageFrame(String[] Categorii,String[] locatii, double[] preturi) {
        JFrame frame = new JFrame("Home Page");

        

        JLabel welcomeLabel = new JLabel("Hello, " + name + " " + surname + "!\n Make an Appointment:");

        categoryComboBox = new JComboBox<>(Categorii);
        rentalCenterComboBox = new JComboBox<>(locatii);
        

        chooseDateButton = new JButton("Choose Date");
        chooseDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the date picker or calendar
                openCalendarPicker();
            }
        });

        
        String oldPassword = this.getPassword();
        String oldEmail = this.getEmail();
        changePersonalDetails = new JButton("Change Personal Details");
        changePersonalDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the date picker or calendar
            	System.out.println("Old Password is: " + (oldPassword != null ? oldPassword : "N/A"));
            	CheckPassword detailsChanger = new CheckPassword(oldPassword,oldEmail);
            	
            }
        });
        
        
        makeAppointmentButton = new JButton("Make Appointment");
        makeAppointmentButton.addActionListener(new ActionListener() {
        	@Override 
            public void actionPerformed(ActionEvent e) {
                
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                String selectedCenter = (String) rentalCenterComboBox.getSelectedItem();
                
                String selectedDate = Integer.toString(sYear) +'-'+ Integer.toString(sMonth) +'-' + Integer.toString(sDay);
                System.out.println("values are:");
                System.out.println(selectedCategory +' '+ selectedCenter +' '+ email+' '+ password);
                System.out.println(selectedDate +' '+ sHour +' '+sInterval);
                
                databaseConnector.makeAppointment(selectedCenter,selectedCategory,email,password,selectedDate,sHour,sInterval); 
                
                JOptionPane.showMessageDialog(null, "Appointment made:\nCategory: " + selectedCategory +
                        "\nRental Center: " + selectedCenter + "\nDate: " ,
                        "Appointment Made", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        showAppointmentsButton = new JButton("Show Appointments");
        showAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointments();
            }
        });
        
        
        deleteAppointmentsButton = new JButton("Delete Appointments");
        deleteAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAppointments();
            }
        });
        
        
        deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
                frame.dispose();
            }
        });
        // Add components to the frame

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(welcomeLabel);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(new JLabel("Category:"));
        panel.add(categoryComboBox);
        
        
        
        panel.add(new JLabel("Rental Center:"));
        panel.add(rentalCenterComboBox);
        
        panel.add(new JLabel("Date:"));

        panel.add(chooseDateButton);
        panel.add(new JLabel()); // Empty label for spacing
        
        panel.add(makeAppointmentButton);
        panel.add(changePersonalDetails);
        panel.add(showAppointmentsButton);
        panel.add(deleteAppointmentsButton);
        panel.add(deleteUserButton);
        frame.add(panel);

        // Set frame properties
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        return frame;
    }
    protected void deleteUser() {
		// TODO Auto-generated method stub
    	databaseConnector.deleteUser(email, password);
    	JOptionPane.showMessageDialog(null, "User deleted", " ", JOptionPane.INFORMATION_MESSAGE);
    	
		
	}

	private void openCalendarPicker() {
        CalendarPicker calendarPicker = new CalendarPicker();
        calendarPicker.setVisible(true);
        

        
        System.out.println("Selected Year: " + calendarPicker.getSelectedYear());
        System.out.println("Selected Month: " + calendarPicker.getSelectedMonth());
    
        this.sYear = calendarPicker.getSelectedYear(); // sql statement to record the programare
        this.sMonth = calendarPicker.getSelectedMonth();
        this.sDay = calendarPicker.getSelectedDay();
        this.sHour= calendarPicker.getSelectedHour();
        this.sInterval= calendarPicker.getSelectedInterval();
    }
    
    private void showAppointments() {
        
    	List<String> userAppointments = databaseConnector.getUserAppointments(email, password);

        // Create and display the AppointmentsDialog
        AppointmentsDialog appointmentsDialog = new AppointmentsDialog();
        appointmentsDialog.setAppointmentsText(userAppointments.toString());
        appointmentsDialog.setVisible(true);
    }

    private void deleteAppointments() {
        
    	databaseConnector.deleteAppointment(email, password);
    	JOptionPane.showMessageDialog(null, "Appointments deleted", " ", JOptionPane.INFORMATION_MESSAGE);

        // Create and display the AppointmentsDialog
        
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
