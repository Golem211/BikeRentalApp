package implementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombinedFrames extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton switchButton;

    public CombinedFrames() {
        super("Combined Frames");

        // Create instances of your existing JFrames
        Container registerPanel = createRegisterPanel().getContentPane(); // Get the content pane
        Container loginPanel = createMainLoginPageFrame().getContentPane(); // Get the content pane

        // Create a CardLayout and JPanel to hold the frames
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add the frames to the card panel
        cardPanel.add(registerPanel, "Register");
        cardPanel.add(loginPanel, "Login");

        // Create a button to switch between frames
        switchButton = new JButton("Switch Frame");
        switchButton.addActionListener(this);

        // Set up the layout
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
        add(switchButton, BorderLayout.SOUTH);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Switch between frames when the button is clicked
        cardLayout.next(cardPanel);
    }

    private JFrame createFrame(String content) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new JLabel(content));
        frame.setSize(300, 200);
        return frame;
    }
    private JFrame createRegisterPanel() {
        RegisterPage registerPage = new RegisterPage();
        registerPage.createRegisterPage(); // Assuming createRegisterPage() creates the frame
        return registerPage.createRegisterPage();
    }
    private JFrame createMainLoginPageFrame() {
        MainLoginPage mainLoginPage = new MainLoginPage();
        return mainLoginPage.createMainLoginPage();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CombinedFrames());
    }
}
