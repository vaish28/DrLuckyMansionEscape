package killdrluckygame.view;

import javax.swing.*;

public class AboutDialog extends JDialog {

  public AboutDialog(JFrame parentFrame) {
    super(parentFrame, "About Dr. Lucky Game", true);

    // Set the layout and other properties of the dialog

    // Create and add components (labels, buttons, etc.) for the welcome message and credits

    // Example:
    JLabel welcomeLabel = new JLabel("Welcome to Dr. Lucky Game!");
    JLabel creditsLabel = new JLabel("Developed by: Vaishnavi Sunil Madhekar");

    JPanel contentPanel = new JPanel();
    contentPanel.add(welcomeLabel);
    contentPanel.add(creditsLabel);

    add(contentPanel);

    // Set the size, location, and visibility of the dialog
    setSize(300, 200);
    setLocationRelativeTo(parentFrame);
    setVisible(true);
  }
}
