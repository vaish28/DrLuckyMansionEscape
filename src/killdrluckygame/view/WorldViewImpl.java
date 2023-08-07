package killdrluckygame.view;

import killdrluckygame.ControllerGuiInterface;
import killdrluckygame.Player;
import killdrluckygame.ReadOnlyWorldModel;
import killdrluckygame.Space;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class WorldViewImpl extends JFrame implements WorldViewInterface {

  private List<Space> spaceList;
  private final int rows;
  private final int columns;
  private ReadOnlyWorldModel model;
  private ControllerGuiInterface listener;
  private JLabel targetCharacterInfoLabel;
  private JLabel petInfoLabel;
  private JLabel currentPlayerLabel;
  private JPanel infoDisplayPanel;
  private JPanel mainPanel;
  private JPanel gridPanel;
  private JButton addHumanPlayer;
  private JButton addComputerPlayer;
  private JTextField nameField;
  private JTextField maxCapacityField;
  private JTextField roomNameField;

  public WorldViewImpl(ReadOnlyWorldModel model) {
    this.model = model;
    this.rows = model.getRows();
    this.columns = model.getColumns();
    this.spaceList = model.getSpaces();
    mainPanel = new JPanel(new BorderLayout());
    infoDisplayPanel = new JPanel(new FlowLayout());
    targetCharacterInfoLabel = new JLabel();
    petInfoLabel = new JLabel();
    addHumanPlayer = new JButton("Enter human player info");
    addComputerPlayer = new JButton("Enter computer player info");
    currentPlayerLabel = new JLabel();
    gridPanel = new GridPanel();
    initializeGui();
    updateDisplay();
  }

  private void initializeGui() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Dr Lucky World Game");
    JPanel infoGridPanel = new JPanel(new GridLayout(2, 2));

    targetCharacterInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    infoDisplayPanel.add(targetCharacterInfoLabel);

    petInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    infoDisplayPanel.add(petInfoLabel);

    currentPlayerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    infoDisplayPanel.add(currentPlayerLabel);

    infoGridPanel.add(infoDisplayPanel, BorderLayout.NORTH);
    infoGridPanel.add(gridPanel, BorderLayout.CENTER);

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.insets = new Insets(50, 0, 0, 0);

    mainPanel.add(infoGridPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    addHumanPlayer.addActionListener(e -> showInputDialog());
    addComputerPlayer.addActionListener(e -> showAddComputerPlayer());
    buttonPanel.add(addHumanPlayer);
    buttonPanel.add(addComputerPlayer);
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.insets = new Insets(20, 0, 0, 0);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Set the preferred size for the main panel
    mainPanel.setPreferredSize(new Dimension(800, 800));

    // Add the main panel to the JFrame
    add(mainPanel);
    pack(); // Pack the JFrame
    setLocationRelativeTo(null); // Center the frame on the screen
    setSize(600, 600);
    setVisible(true);
  }

  private void showAddComputerPlayer() {
    listener.processComputerClick();
    updateDisplay();
  }

  private void updateDisplay() {
    displayTargetCharacterInfo();
    displayTargetCharacterPetInfo();
    displayCurrentPlayerInfo();
    gridPanel.repaint();
  }

  private void displayTargetCharacterInfo() {
    if (model != null) {
      targetCharacterInfoLabel.setText(model.targetCharacterDetails());
    }
  }

  private void displayCurrentPlayerInfo() {
    if (model != null) {
      currentPlayerLabel.setText(model.displayCurrentPlayerInfo());
    }
  }

  private void displayTargetCharacterPetInfo() {
    if (model != null) {
      petInfoLabel.setText(model.getCurrentPetInfo());
    }
  }

  private void showInputDialog() {
    nameField = new JTextField(10);
    maxCapacityField = new JTextField(10);
    roomNameField = new JTextField(20);

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.add(new JLabel("Name:"));
    inputPanel.add(nameField);
    inputPanel.add(new JLabel("Maximum Capacity :"));
    inputPanel.add(maxCapacityField);
    inputPanel.add(new JLabel("Room you want to enter into:"));
    inputPanel.add(roomNameField);

    int result = JOptionPane.showConfirmDialog(
            this, // Use 'this' as the parent frame
            inputPanel,
            "Enter Your Information",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    if (result == JOptionPane.OK_OPTION) {
      String name = nameField.getText();
      int maxCapacity = Integer.parseInt(maxCapacityField.getText());
      String nameOfRoom = roomNameField.getText();
      listener.processHumanUserInfoClick(name, maxCapacity, nameOfRoom);
      updateDisplay();
    }
  }

  @Override
  public void addListener(ControllerGuiInterface listener) {
    this.listener = listener;
  }

  private class GridPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      // Draw the grid here
      int worldWidth = getWidth();
      int worldHeight = getHeight();
      int numRows = rows;
      int numCols = columns;
      int cellWidth = worldWidth / numCols;
      int cellHeight = worldHeight / numRows;

      g.setColor(Color.white);
      g.fillRect(0, 0, worldWidth, worldHeight);

      g.setColor(Color.BLACK);

      for (int col = 0; col <= numCols; col++) {
        int x = col * cellWidth;
        g.drawLine(x, 0, x, worldHeight);
      }

      // Draw horizontal grid lines
      for (int row = 0; row <= numRows; row++) {
        int y = row * cellHeight;
        g.drawLine(0, y, worldWidth, y);
      }

      for (Space room : spaceList) {
        int roomRow = room.getUpperLeftPosition().getRow();
        int roomCol = room.getUpperLeftPosition().getColumn();

        int roomX = roomCol * cellWidth;
        int roomY = roomRow * cellHeight;

        int roomWidth = (room.getLowerRightPosition().getColumn() - room.getUpperLeftPosition().getColumn() + 1) * cellWidth;
        int roomHeight = (room.getLowerRightPosition().getRow() - room.getUpperLeftPosition().getRow() + 1) * cellHeight;

        // Generate a random color for each room
        Color roomColor = new Color((int) (Math.random() * 0x1000000));
        g.setColor(roomColor);

        g.fillRect(roomX, roomY, roomWidth, roomHeight);

        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();

        String roomName = room.getSpaceName();
        int textWidth = fm.stringWidth(roomName);
        int textHeight = fm.getHeight();

        // Calculate the position to center the text within the rectangle
        int textX = roomX + (roomWidth - textWidth) / 2;
        int textY = roomY + (roomHeight - textHeight) / 2 + fm.getAscent();

        g.drawString(room.getSpaceName(), textX, textY);

        Map<Space, List<Player>> map = model.getMappingOfSpaceAndPlayer();
        List<Player> players = map.get(room);

        if (players != null) {
          for (Player player : players) {
            int playerXCenter = roomX + (roomWidth / 2);
            int playerYCenter = roomY + (roomHeight / 2);
            int playerRadius = Math.min(cellWidth, cellHeight) / 4;

            g.setColor(Color.BLUE); // You can use any color you want for the players
            g.fillOval(playerXCenter - playerRadius, playerYCenter - playerRadius, 2 * playerRadius, 2 * playerRadius);
          }
        }
      }
    }

    @Override
    public Dimension getPreferredSize() {
      // Set the preferred size of the grid panel to be the size of the parent container
      return getParent().getSize();
    }
  }
}






