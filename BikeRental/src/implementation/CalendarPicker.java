package implementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.stream.IntStream;

public class CalendarPicker extends JDialog {
    private Integer selectedYear;
    private Integer selectedMonth;
    private Integer selectedDay;
    private Integer selectedHour;
    private Integer selectedInterval;

    public CalendarPicker() {
        initUI();
        setModal(true);
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel yearLabel = new JLabel("Select Year:");
        JLabel monthLabel = new JLabel("Select Month:");
        JLabel dayLabel = new JLabel("Select Day:");
        JLabel hourLabel = new JLabel("Select Hour:");
        JLabel intervalLabel = new JLabel("Select Interval:");

        Integer[] years = getRange(2024, 2030);
        JComboBox<Integer> yearComboBox = new JComboBox<>(years);
        panel.add(yearLabel);
        panel.add(yearComboBox);

        Integer[] months = getRange(1, 12);
        JComboBox<Integer> monthComboBox = new JComboBox<>(months);
        panel.add(monthLabel);
        panel.add(monthComboBox);

        JComboBox<Integer> dayComboBox = new JComboBox<>();
        panel.add(dayLabel);
        panel.add(dayComboBox);

        Integer[] hours = getRange(6, 21);
        JComboBox<Integer> hourComboBox = new JComboBox<>(hours);
        panel.add(hourLabel);
        panel.add(hourComboBox);

        Integer[] intervals = getRange(1, 12);
        JComboBox<Integer> intervalComboBox = new JComboBox<>(intervals);
        panel.add(intervalLabel);
        panel.add(intervalComboBox);

        yearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDayComboBox(dayComboBox, (Integer) yearComboBox.getSelectedItem(), (Integer) monthComboBox.getSelectedItem());
            }
        });

        monthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDayComboBox(dayComboBox, (Integer) yearComboBox.getSelectedItem(), (Integer) monthComboBox.getSelectedItem());
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedYear = (Integer) yearComboBox.getSelectedItem();
                selectedMonth = (Integer) monthComboBox.getSelectedItem();
                selectedDay = (Integer) dayComboBox.getSelectedItem();
                selectedHour = (Integer) hourComboBox.getSelectedItem();
                selectedInterval = (Integer) intervalComboBox.getSelectedItem();

                // Handle the selected values as needed
                System.out.println("Selected Date and Time: " + selectedYear + "-" +
                        String.format("%02d", selectedMonth) + "-" +
                        String.format("%02d", selectedDay) + " " +
                        String.format("%02d", selectedHour) + ":00" +
                        "\nSelected Interval: " + selectedInterval + " hours");
               // dispose();
            }
        });

        panel.add(new JLabel());
        panel.add(submitButton);

        getContentPane().add(panel);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private static Integer[] getRange(int start, int end) {
        return IntStream.rangeClosed(start, end).boxed().toArray(Integer[]::new);
    }

    private static void updateDayComboBox(JComboBox<Integer> dayComboBox, int year, int month) {
        int maxDays = YearMonth.of(year, month).lengthOfMonth();
        Integer[] days = getRange(1, maxDays);

        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>(days);
        dayComboBox.setModel(model);
    }
    public Integer getSelectedYear() {
        return selectedYear;
    }

    public Integer getSelectedMonth() {
        return selectedMonth;
    }

    public Integer getSelectedDay() {
        return selectedDay;
    }

    public Integer getSelectedHour() {
        return selectedHour;
    }

    public Integer getSelectedInterval() {
        return selectedInterval;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JDialog dialog = new CalendarPicker();
            dialog.setVisible(true);
        });
    }
}
