package killdrluckygame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import killdrluckygame.ControllerGuiInterface;
import killdrluckygame.Item;
import killdrluckygame.Player;
import killdrluckygame.ReadOnlyWorldModel;
import killdrluckygame.Space;

public class WorldViewImpl extends JFrame implements WorldViewInterface {

  private List<Space> spaceList;
  private int rows;
  private int columns;
  private ReadOnlyWorldModel model;
  private ControllerGuiInterface listener;
  private JLabel targetCharacterInfoLabel;
  private JLabel petInfoLabel;
  private JLabel currentPlayerLabel;
  private JLabel pickedItemLabel;
  private JPanel infoDisplayPanel;
  private JPanel mainPanel;
  private JPanel gridPanel;
  private JButton addHumanPlayer;
  private JButton addComputerPlayer;
  private JButton viewSpaceInfoButton;
  private JTextField nameField;
  private JTextField maxCapacityField;
  private JTextField roomNameField;
  private BufferedImage humanPlayerImage;
  private KeyListener keyListener;
  private JScrollPane worldScrollablePanel;

  private BufferedImage targetCharacterImage;

  private MenuItemFactory menuItemFactory;
  private boolean humanPlayerAdded;


  public WorldViewImpl(ReadOnlyWorldModel model) {
    this.model = model;
    initialize();
  }

  private void initialize() {

    this.humanPlayerAdded = false;
    this.rows = model.getRows();
    this.columns = model.getColumns();
    this.spaceList = model.getSpaces();
    this.pickedItemLabel = new JLabel();
    this.mainPanel = new JPanel(new BorderLayout());
    this.infoDisplayPanel = new JPanel();
    this.infoDisplayPanel.setLayout(new BoxLayout(infoDisplayPanel, BoxLayout.Y_AXIS));
    this.targetCharacterInfoLabel = new JLabel();
    this.petInfoLabel = new JLabel();
    this.addHumanPlayer = new JButton("Enter human player info");
    this.addComputerPlayer = new JButton("Enter computer player info");
    instructionsLabel();
    this.currentPlayerLabel = new JLabel();
    this.gridPanel = new GridPanel();
    this.keyListener = new ListenKey();
    this.worldScrollablePanel = new JScrollPane(mainPanel);
    gridPanel.addKeyListener(this.keyListener);
    this.menuItemFactory = new GameMenuItemFactory(this);

    initializeMenu();
    loadPlayerImage();
    loadTargetCharacterImage();
    initializeGui();
    updateDisplay();
    initializeKeyListeners();
  }

  private void instructionsLabel() {
    StringBuilder instructionLabel1 = new StringBuilder("Click on a human player to view their description.");
    StringBuilder instructionLabel2 = new StringBuilder("Click on a room to move there.");
    StringBuilder instructionLabel3 = new StringBuilder("Press 'P' to pick an item.");
    StringBuilder instructionLabel4 = new StringBuilder("Press 'L' to look around.");
    StringBuilder instructionLabel5 = new StringBuilder("Press 'A' to attempt on the target character.");

    JLabel label1 = new JLabel(instructionLabel1.toString());
    JLabel label2 = new JLabel(instructionLabel2.toString());
    JLabel label3 = new JLabel(instructionLabel3.toString());
    JLabel label4 = new JLabel(instructionLabel4.toString());
    JLabel label5 = new JLabel(instructionLabel5.toString());

    this.infoDisplayPanel.add(label1);
    this.infoDisplayPanel.add(label2);
    this.infoDisplayPanel.add(label3);
    this.infoDisplayPanel.add(label4);
    this.infoDisplayPanel.add(label5);
  }


  private void loadTargetCharacterImage() {
    try {
      targetCharacterImage = ImageIO.read(new File("res/target.jpg"));
      System.out.println("Target character image loaded successfully!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initializeMenu() {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("Game");
    JMenuItem newGame = menuItemFactory.
            createMenuItem("New Game (New World)", "NewGame");
    JMenuItem newGameSameWorld = menuItemFactory.
            createMenuItem("New Game (Same World)", "NewGameSameWorld");
    JMenuItem quit = menuItemFactory.createMenuItem("Quit", "Quit");

    fileMenu.add(newGame);
    fileMenu.add(newGameSameWorld);
    fileMenu.addSeparator();
    fileMenu.add(quit);

    menuBar.add(fileMenu);

    setJMenuBar(menuBar);
  }

  private void initializeKeyListeners() {
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
          showPickItemDialog();
          updateDisplay();
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
          showLookAroundMessage();
          updateDisplay();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
          attemptOnTargetCharacter();
          updateDisplay();
        } else {
          showInCorrectKeyDialog();
          updateDisplay();
        }
      }
    });
    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  private void showInCorrectKeyDialog() {
    String message = "Incorrect Key selected!";
    String messageTitle = "Incorrect Key!";
    displayErrorDialog(messageTitle, message);
  }

  private void showLookAroundMessage() {
    String lookAround = listener.processInput("lookaround", new String[]{});

    JTextArea textArea = new JTextArea(lookAround);
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    textArea.setCaretPosition(0); // Scroll to the beginning of the text

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 300)); // Adjust the size as needed

    JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Look Around",
            JOptionPane.INFORMATION_MESSAGE
    );

    updateDisplay();
    computerTurn();
  }

  private void computerTurn() {
    String resultComputer = listener.computerPlayerTurn();
    if (resultComputer != null && (!resultComputer.equals(""))) {
      displayMessageDialog("Computer Turn", resultComputer);
    }


  }

  private void attemptOnTargetCharacter() {

    String resultAttack = "";
    String message = "Are you sure you want to attempt on the target character's life?";
    String messageTitle = "Attempt on Target Character";
    int result = JOptionPane.showConfirmDialog(
            this,
            message,
            messageTitle,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);


    if (result == JOptionPane.YES_OPTION) {

      Player currentPlayer = model.getCurrentPlayer();
      List<Item> items = currentPlayer.getItems();
      // Create an option pane with radio buttons for the items
      JRadioButton[] radioButtons = new JRadioButton[items.size() + 1];
      ButtonGroup group = new ButtonGroup();
      JPanel panel = new JPanel(new GridLayout(0, 1));
      radioButtons[0] = new JRadioButton("Poke in the eye!");
      group.add(radioButtons[0]);
      panel.add(radioButtons[0]);
      int radioButtonIndex = 1;
      for (int i = 0; i < items.size(); i++) {
        radioButtons[radioButtonIndex] = new JRadioButton(items.get(i).getItemName());
        group.add(radioButtons[radioButtonIndex]);
        panel.add(radioButtons[radioButtonIndex]);
        radioButtonIndex++;
      }

      int response = JOptionPane.showConfirmDialog(this, panel,
              "Choose an item to attack with",
              JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

      if (response == JOptionPane.OK_OPTION) {
        // Determine the selected item and execute the attack
        for (int i = 0; i < radioButtons.length; i++) {
          if (radioButtons[i].isSelected()) {
            if (radioButtons[i].isSelected() && i == 0) {
              resultAttack = listener.processInput("attack", new String[]{"poke"});

            } else {
              String item = items.get(i - 1).getItemName();
              resultAttack = listener.processInput("attack", new String[]{item});
              break;
            }

          }
        }
      }
      messageTitle = "Attacking Now!";
      displayMessageDialog(messageTitle,resultAttack);
    }
    else {
      messageTitle = "Attack cancelled";
      message = "You cancelled the attack";
      displayMessageDialog(messageTitle,message);
    }



    updateDisplay();
    computerTurn();
    updateDisplay();
  }


  private void showPickItemDialog() {


    List<Item> playerItemList = model.getCurrentPlayerSpace(model.getCurrentPlayer()).getItems();

    if (playerItemList.isEmpty()) {

      String messageTitle = "No Items Available";
      String message = "There are no items to pick.";
      displayMessageDialog(messageTitle, message);
      listener.advanceTargetCharacter();
      updateDisplay();
      listener.computerPlayerTurn();
      return;

    }

    String[] itemNames = new String[playerItemList.size()];
    for (int i = 0; i < playerItemList.size(); i++) {
      itemNames[i] = playerItemList.get(i).getItemName();
    }


    String selectedItem = JOptionPane.showInputDialog(
            this,
            "Select an item to pick:",
            "Pick Item",
            JOptionPane.PLAIN_MESSAGE,
            null,
            itemNames,
            itemNames[0]).toString();

    String resultPick = "";
    if (selectedItem != null) {
      resultPick = listener.processInput("pickitem", new String[]{selectedItem});
      if(!("").equals(resultPick)) {
        String messageTitle = "Picked item now!";
        displayMessageDialog(messageTitle, resultPick);
      }

    }



    updateDisplay();
    computerTurn();
  }


  private void loadPlayerImage() {
    try {
      humanPlayerImage = ImageIO.read(new File("res/images.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initializeGui() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Dr Lucky World Game");


    JPanel infoGridPanel = new JPanel(new BorderLayout(2, 2));

    targetCharacterInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    infoDisplayPanel.add(targetCharacterInfoLabel);


    currentPlayerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    infoDisplayPanel.add(currentPlayerLabel);
    currentPlayerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    infoDisplayPanel.add(pickedItemLabel);
    infoGridPanel.add(infoDisplayPanel, BorderLayout.PAGE_START);
    infoGridPanel.add(gridPanel, BorderLayout.CENTER);

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.insets = new Insets(50, 0, 0, 0);

    mainPanel.add(infoGridPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    addHumanPlayer.addActionListener(e -> showHumanPlayerInputDialog());
    addComputerPlayer.addActionListener(e -> showAddComputerPlayer());
    viewSpaceInfoButton = new JButton("View Space Information");
    viewSpaceInfoButton.addActionListener(e -> showViewSpaceInfoDialog());

    buttonPanel.add(addHumanPlayer);
    buttonPanel.add(addComputerPlayer);
    buttonPanel.add(viewSpaceInfoButton);
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.insets = new Insets(20, 0, 0, 0);
    mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

    // Set the preferred size for the main panel
    mainPanel.setPreferredSize(new Dimension(800, 800));


    add(worldScrollablePanel);
    // Add the main panel to the JFrame

    pack(); // Pack the JFrame
    setLocationRelativeTo(null); // Center the frame on the screen
    setSize(600, 600);

  }



  private void showViewSpaceInfoDialog() {
    // Create an input dialog to get the room name
    String selectedSpace = JOptionPane.showInputDialog(
            this,
            "Enter the room name to view information:",
            "View Space Information",
            JOptionPane.PLAIN_MESSAGE);

    if (selectedSpace != null && !selectedSpace.trim().isEmpty()) {
      String spaceInfo = listener.processInput("spaceinfo", new String[]{selectedSpace});
      String messageTitle = "Space Information - " + selectedSpace;
      displayMessageDialog(messageTitle, spaceInfo);
    } else {
      String message = "Please enter a valid room name.";
      String messageTitle = "Invalid Input";
      displayErrorDialog(messageTitle, message);
    }
  }

  private void showAboutDialog() {
    AboutDialog aboutDialog = new AboutDialog(this);
    aboutDialog.dispose(); // Close the about screen after it's shown
  }

  @Override
  public void setVisibleMain() {
    setVisible(true);
  }

  @Override
  public void setVisibleAboutDialog() {
    showAboutDialog();
  }


  //  @Override
//  public void displayMessageDialogWithJPane(String title, String message, ) {
//
//  }
  @Override
  public void displayMessageDialog(String title, String message) {
    JOptionPane.showMessageDialog(this,
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void displayErrorDialog(String title, String message) {
    JOptionPane.showMessageDialog(
            this,
            message,
            title,
            JOptionPane.ERROR_MESSAGE
    );
  }

  private void showAddComputerPlayer() {

    if (humanPlayerAdded) {
      listener.processInput("computer", new String[]{});
      updateDisplay();
    } else {
      String message = "Please add a human player before adding a computer player.";
      String messageTitle = "Invalid Action";
      displayErrorDialog(messageTitle, message);
    }
    updateDisplay();
  }

  private void updateDisplay() {
    displayTargetCharacterInfo();
    displayCurrentPlayerInfo();
    gridPanel.repaint();
//    instructionsLabel(); // Add this line
    gridPanel.removeKeyListener(this.keyListener);
    this.keyListener = new ListenKey();
    gridPanel.addKeyListener(this.keyListener);
  }

  private void displayTargetCharacterInfo() {
    if (model != null) {
      targetCharacterInfoLabel.setText(model.targetCharacterDetails());
    }
  }

  private void displayCurrentPlayerInfo() {
    StringBuilder sb = new StringBuilder();
    if (model != null) {
      sb.append(model.displayCurrentPlayerInfo());
      sb.append("\n");

      Player currentPlayer = model.getCurrentPlayer();
      if (currentPlayer != null && currentPlayer.isHumanControlled()) {
        List<String> neighNames = model.getNeighborsStrings();

        if (!neighNames.isEmpty()) {
          sb.append("  ");
          sb.append("Neighbors you can move into:\n");
          for (String neighbor : neighNames) {
            sb.append("    ").append(neighbor).append("\n");
          }
        } else {
          sb.append("No neighbors available to move into.\n");
        }
      }
    }

    currentPlayerLabel.setText(sb.toString());
  }


  private void showHumanPlayerInputDialog() {
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
      String nameOfRoom = roomNameField.getText();

      // Check if the selected room is not the same as the target character's starting room
      if (!nameOfRoom.equals(model.getCurrentSpaceTargetIsIn().getSpaceName())) {
        //  listener.processHumanUserInfoClick(name, maxCapacity, nameOfRoom);
        listener.processInput("human", new String[]{name, maxCapacityField.getText(), nameOfRoom});
        humanPlayerAdded = true;
        updateDisplay();
      } else {
        String message = "You cannot enter the room where the target character starts.";
        String messageTitle = "Invalid Room";
        displayErrorDialog(messageTitle, message);
      }
    }
  }

  @Override
  public void addListener(ControllerGuiInterface listener) {
    this.listener = listener;
  }

  @Override
  public void startNewGameWithNewWorld() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this); // Show the file chooser dialog

    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();

      // Create a JPanel to hold the input components
      JPanel inputPanel = new JPanel();
      inputPanel.setLayout(new GridLayout(2, 2));

      JTextField maxTurnsField = new JTextField(10);
      inputPanel.add(new JLabel("Maximum Number of Turns:"));
      inputPanel.add(maxTurnsField);

      int inputResult = JOptionPane.showConfirmDialog(
              this,
              inputPanel,
              "Enter New Game Information",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.PLAIN_MESSAGE
      );

      if (inputResult == JOptionPane.OK_OPTION) {
        String maxTurnsInput = maxTurnsField.getText();
        if (maxTurnsInput.isEmpty() || !maxTurnsInput.matches("\\d+")) {
          JOptionPane.showMessageDialog(
                  this,
                  "Please enter a valid maximum number of turns.",
                  "Invalid Input",
                  JOptionPane.ERROR_MESSAGE
          );
          return;
        }

        int maxTurns = Integer.parseInt(maxTurnsInput);

        try {
          // Load the new world from the selected file and start the game with maxTurns
          listener.loadNewGame(selectedFile.getAbsolutePath(), maxTurns);
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(this, "Error loading the new world: " + ex.getMessage(),
                  "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    } else {
      String message = "No file selected!";
      String messageTitle = "Error";
      displayErrorDialog(messageTitle, message);
    }
  }


  @Override
  public void setWorld(ReadOnlyWorldModel world) {
    if (world != null) {
      this.model = world;
      moveToGame(); // Move to the game view
    } else {
      String message = "Failed to load the new world!";
      String messageTitle = "Error";
      displayErrorDialog(messageTitle, message);

    }
  }

  @Override
  public void moveToGame() {
//    if (this != null) {
//      remove(worldPanel); // Remove the existing WorldPanel if any
//    }
//    worldPanel = new WorldPanel(world); // Create the new WorldPanel
//    add(worldPanel, BorderLayout.CENTER); // Add it to the center of the layout
//    revalidate(); // Inform the layout manager that the structure has changed
//    refresh(); // Repaint the view
    initialize();

  }


  @Override
  public void startNewGameWithSameWorld() {
    remove(worldScrollablePanel);
    initialize();
    revalidate(); // Inform the layout manager that the structure has changed
    refresh(); // Repaint the view
    listener.resetGame();

  }

  @Override
  public void refresh(String... args) {

    worldScrollablePanel.repaint();
  }

  @Override
  public void gameEnd() {
    if (model.getTargetHealth() == 0) {
      StringBuilder sb = new StringBuilder();
      sb.append("Congratulations! You have successfully defeated the target character.");
      sb.append("\nYou win!");

      String message = sb.toString();
      String messageTitle = "Game Over - Defeat!";
      displayMessageDialog(messageTitle, message);

    } else {
      StringBuilder sb = new StringBuilder();
      sb.append("Oh no! The turns are exhausted and the target character is still alive.");
      sb.append("\nYou lose!");

      String message = sb.toString();
      String messageTitle = "Game Over - Defeat!";
      displayErrorDialog(messageTitle, message);
      System.exit(0);
    }
  }


  private class GridPanel extends JPanel {

    // Draw the grid here
    int cellWidth;
    int cellHeight;


    public GridPanel() {
      addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleMouseClick(e);
        }
      });
    }

    private void handleMouseClick(MouseEvent e) {
      int mouseX = e.getX();
      int mouseY = e.getY();

      // Determine the clicked room based on mouse coordinates
      int roomCol = mouseX / cellWidth;
      int roomRow = mouseY / cellHeight;
      Space clickedRoom = getClickedRoom(roomRow, roomCol);

      if (clickedRoom != null) {

//        if (model.getCurrentPlayer().isHumanControlled()) { // Check if the current player is human-controlled
          if (listener.checkIfPlayerDescription(clickedRoom)) {

            String description = listener.processInput("playerinfo",
                    new String[]{model.getCurrentPlayer().getName()}); // Replace with your description logic

            String messageTitle = "Player Description";
            displayMessageDialog(messageTitle,description);

          } else if (listener.isValidMove(model.getCurrentPlayer(), clickedRoom)) {
            String resultMove = listener.processInput("move",
                    new String[]{clickedRoom.getSpaceName()});
            String messageTitle = "Moving player";
            displayMessageDialog(messageTitle,resultMove);
            computerTurn();
            updateDisplay();
          } else {
            String resultMove = "Invalid move! The space is not your neighbor";
            String messageTitle = "Invalid move!";
            displayMessageDialog(messageTitle,resultMove);
          }
//        }
      }
    }

    private Space getClickedRoom(int row, int col) {
      for (Space room : spaceList) {
        int roomRow = room.getUpperLeftPosition().getRow();
        int roomCol = room.getUpperLeftPosition().getColumn();
        int roomWidth = (room.getLowerRightPosition().getColumn() - roomCol + 1) * cellWidth;
        int roomHeight = (room.getLowerRightPosition().getRow() - roomRow + 1) * cellHeight;

        if (col >= roomCol && col < roomCol + roomWidth / cellWidth
                && row >= roomRow && row < roomRow + roomHeight / cellHeight) {
          return room;
        }
      }
      return null;
    }


    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      int worldWidth = getWidth();
      int worldHeight = getHeight();
      int numRows = rows;
      int numCols = columns;
      cellWidth = worldWidth / numCols;
      cellHeight = worldHeight / numRows;
      g.setColor(Color.white);
      g.fillRect(0, 0, worldWidth, worldHeight);


      for (Space room : spaceList) {
        int roomRow = room.getUpperLeftPosition().getRow();
        int roomCol = room.getUpperLeftPosition().getColumn();

        int roomX = roomCol * cellWidth;
        int roomY = roomRow * cellHeight;

        int roomWidth = (room.getLowerRightPosition().getColumn()
                - room.getUpperLeftPosition().getColumn() + 1) * cellWidth;
        int roomHeight = (room.getLowerRightPosition().getRow()
                - room.getUpperLeftPosition().getRow() + 1) * cellHeight;

        g.setColor(Color.BLACK);
        g.drawRect(roomX, roomY, roomWidth, roomHeight);
        g.setColor(new Color(144, 238, 175));
        g.fillRect(roomX + 1, roomY + 1, roomWidth - 1, roomHeight - 1);


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
            if (player.isHumanControlled()) {
              int playerXCenter = roomX + (roomWidth / 2);
              int playerYCenter = roomY + (roomHeight / 2);
              int playerSize = (Math.min(cellWidth, cellHeight) / 2) * 2;

              g.setColor(Color.GREEN); // Use green color for human player
//              g.fillRect(playerXCenter - playerSize / 2, playerYCenter - playerSize / 2, playerSize, playerSize);
              int[] xPoints = {playerXCenter, playerXCenter - playerSize, playerXCenter + playerSize};
              int[] yPoints = {playerYCenter - playerSize, playerYCenter + playerSize, playerYCenter + playerSize};

              g.fillPolygon(xPoints, yPoints, 3); // Draw the triangle
            } else {
              // For computer players, draw the blue circle (as before)
              int playerXCenter = roomX + (roomWidth / 2);
              int playerYCenter = roomY + (roomHeight / 2);
              int playerRadius = (Math.min(cellWidth, cellHeight) / 4) * 5;

              g.setColor(Color.BLUE);
              g.fillOval(playerXCenter - playerRadius,
                      playerYCenter - playerRadius,
                      playerRadius, playerRadius);
            }
          }
        }


        if (room.equals(model.getCurrentSpaceTargetIsIn())) {
          int targetCharacterXCenter = roomX + (roomWidth / 2);
          int targetCharacterYCenter = roomY + (roomHeight / 2);
          int targetCharacterRadius = (Math.min(cellWidth, cellHeight) / 4) * 5;

          // Draw the target character image instead of an oval
          if (targetCharacterImage != null) {
            int imgWidth = targetCharacterRadius * 2;
            int imgHeight = targetCharacterRadius * 2;
            int imgX = targetCharacterXCenter - imgWidth / 2;
            int imgY = targetCharacterYCenter - imgHeight / 2;
            g.drawImage(targetCharacterImage, imgX, imgY, imgWidth, imgHeight, this);
          }
        }
      }
      addKeyListener(new ListenKey());

    }


    @Override
    public Dimension getPreferredSize() {
      // Set the preferred size of the grid panel to be the size of the parent container
      return getParent().getSize();
    }
  }

  private class ListenKey implements KeyListener {

    public ListenKey() {

      // Set the focusable state of the frame to true so that it can receive keyboard events
      setFocusable(true);
      // Request focus to the frame to start receiving keyboard events
      requestFocus();
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_P) {

        showPickItemDialog();
        updateDisplay();

      } else if (e.getKeyCode() == KeyEvent.VK_L) {
        showLookAroundMessage();
        updateDisplay();
      } else if (e.getKeyCode() == KeyEvent.VK_A) {
        attemptOnTargetCharacter();
        updateDisplay();
      }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

  }
}






