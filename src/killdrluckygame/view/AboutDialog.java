package killdrluckygame.view;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {

  public AboutDialog(JFrame parentFrame) {
    super(parentFrame, "About Dr. Lucky Game", true);

    // Set the layout and other properties of the dialog

    // Create and add components (labels, buttons, etc.) for the welcome message and credits


    // Example:
    JLabel welcomeLabel = new JLabel("Welcome to Dr. Lucky Game!");
    JLabel creditsLabel = new JLabel("Developed by: Vaishnavi Sunil Madhekar");

    StringBuilder sb = new StringBuilder();
    sb.append("Get ready to be amazed! Your about to enter a land of ");
    sb.append("adventure game!");
    sb.append("\n");

    sb.append("The game allows to add upto ten players in the game!\n");
    sb.append("You can add a human player or computer player\n");

    sb.append("The human player is controlled by you\n");
    sb.append("The computer player is controlled by me -- the computer!\n");


    JTextArea infoTextArea = new JTextArea(sb.toString());
    infoTextArea.setEditable(false);
    infoTextArea.setLineWrap(true); // Enable text wrapping
    infoTextArea.setWrapStyleWord(true); // Wrap at word boundaries
    infoTextArea.setOpaque(false); // Make the background transparent
    infoTextArea.setFont(UIManager.getFont("Label.font"));
    infoTextArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the text horizontally
    infoTextArea.setForeground(Color.WHITE);


    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBackground(new Color(51, 102, 153)); // Set content panel background color
    contentPanel.add(welcomeLabel);
    contentPanel.add(creditsLabel);

    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.setBackground(new Color(51, 102, 153)); // Set info panel background color
    infoPanel.add(infoTextArea, BorderLayout.CENTER); // Center-align the text area
    contentPanel.add(infoPanel);

    add(contentPanel);

    // Set the size, location, and visibility of the dialog
    setSize(400, 250);
    setLocationRelativeTo(parentFrame);
    setVisible(true);
  }
}
