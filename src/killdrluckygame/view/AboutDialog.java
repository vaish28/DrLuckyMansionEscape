package killdrluckygame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * The AboutDialog class represents a dialog that displays information about the Dr. Lucky Game.
 * It provides details about the game, its features, and how to play.
 */
public class AboutDialog extends JDialog implements AboutDialogInterface {

  /**
   * Constructs an AboutDialog object.
   *
   * @param parentFrame The parent JFrame to which the dialog is attached.
   */
  public AboutDialog(JFrame parentFrame) {
    super(parentFrame, "About Dr. Lucky Game", true);

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
    contentPanel.setBackground(new Color(51, 102, 153));
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
