package killdrluckygame.view;

import killdrluckygame.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
  private JTextField nameField;
  private JTextField maxCapacityField;
  private JTextField roomNameField;
  private BufferedImage humanPlayerImage;
  private KeyListener keyListener;
  private JScrollPane worldScrollablePanel;
  private boolean ignoreMouseEvents = false;

  private BufferedImage targetCharacterImage;
  private int cellWidth; // Add cellWidth as a class variable
  private int cellHeight;
  private MenuItemFactory menuItemFactory = new GameMenuItemFactory(this);

  public WorldViewImpl(ReadOnlyWorldModel model) {
    this.model = model;
    initialize();
  }

  private void initialize() {
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
    this.currentPlayerLabel = new JLabel();
    this.gridPanel = new GridPanel();
    this.keyListener = new ListenKey();
    this.worldScrollablePanel = new JScrollPane(mainPanel);
    gridPanel.addKeyListener(this.keyListener);

    initializeMenu();
    loadPlayerImage();
    loadTargetCharacterImage();
    initializeGui();
    updateDisplay();
    initializeKeyListeners();
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
    JMenuItem newGame = menuItemFactory.createMenuItem("New Game (New World)", "NewGame");
    JMenuItem newGameSameWorld = menuItemFactory.createMenuItem("New Game (Same World)", "NewGameSameWorld");
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
        }
      }
    });
    this.setFocusable(true);
    this.requestFocusInWindow();
  }


  private void showLookAroundMessage() {
    String lookAround = listener.lookAround();
    JOptionPane.showMessageDialog(
            WorldViewImpl.this,
            lookAround,
            "Look Around",
            JOptionPane.INFORMATION_MESSAGE);
    updateDisplay();
  }

  private void attemptOnTargetCharacter() {

    String resultAttack="";
    int result = JOptionPane.showConfirmDialog(
            WorldViewImpl.this,
            "Are you sure you want to attempt on the target character's life?",
            "Attempt on Target Character",
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

      int response = JOptionPane.showConfirmDialog(this, panel, "Choose an item to attack with",
              JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

      if (response == JOptionPane.OK_OPTION) {
        // Determine the selected item and execute the attack
        for (int i = 0; i < radioButtons.length; i++) {
          if (radioButtons[i].isSelected()) {
            if(radioButtons[i].isSelected() && i==0) {
              resultAttack = listener.attemptOnTargetCharacter("");
            }
            else {
              String item = items.get(i).getItemName();
              resultAttack = listener.attemptOnTargetCharacter(item);
              break;
            }

          }
        }
      }
    }
    JOptionPane.showMessageDialog(
            WorldViewImpl.this,
            resultAttack,
            "Attacking now!",
            JOptionPane.INFORMATION_MESSAGE);
    updateDisplay();
  }

  private void showPickItemDialog() {


    List<Item> playerItemList = model.getCurrentPlayerSpace(model.getCurrentPlayer()).getItems();


    String[] itemNames = new String[playerItemList.size()];
    for (int i = 0; i < playerItemList.size(); i++) {
      itemNames[i] = playerItemList.get(i).getItemName();
    }


    String selectedItem = (String) JOptionPane.showInputDialog(
            WorldViewImpl.this,
            "Select an item to pick:",
            "Pick Item",
            JOptionPane.PLAIN_MESSAGE,
            null,
            itemNames,
            itemNames[0]);

    if (selectedItem != null) {
      listener.pickItem(selectedItem); // Call the pickItem function in the controller with the selected item
    }
    updateDisplay();
  }



  private void loadPlayerImage() {
    try {
      humanPlayerImage = ImageIO.read(new File("res/images.jpg"));
      System.out.println("Player image loaded successfully!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initializeGui() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Dr Lucky World Game");

    showAboutDialog();

    JPanel infoGridPanel = new JPanel(new BorderLayout(2, 2));

    targetCharacterInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    infoDisplayPanel.add(targetCharacterInfoLabel);

    petInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    infoDisplayPanel.add(petInfoLabel);

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
    addHumanPlayer.addActionListener(e -> showInputDialog());
    addComputerPlayer.addActionListener(e -> showAddComputerPlayer());
    buttonPanel.add(addHumanPlayer);
    buttonPanel.add(addComputerPlayer);
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
    setVisible(true);
  }

  private void showAboutDialog() {
    AboutDialog aboutDialog = new AboutDialog(this);
    aboutDialog.dispose(); // Close the about screen after it's shown
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

  @Override
  public void startNewGameWithNewWorld() {
    String worldFileName = JOptionPane.showInputDialog(
            this, // Parent component
            "Enter the name of the new world file:", // Message
            "New Game with New World", // Dialog title
            JOptionPane.PLAIN_MESSAGE // Message type
    );

    if (worldFileName != null && !worldFileName.trim().isEmpty()) {
      try {
        // Load the new world from the specified file
        listener.loadNewGame(worldFileName);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, "Error loading the new world: " + ex.getMessage(),
              "Error", JOptionPane.ERROR_MESSAGE);
    }
    } else {
      JOptionPane.showMessageDialog(this, "Please input a valid file name.",
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void setWorld(ReadOnlyWorldModel world) {
    if (world != null) {
      this.model = world;
      moveToGame(); // Move to the game view
    } else {
      JOptionPane.showMessageDialog(this, "Failed to load the new world.",
              "Error", JOptionPane.ERROR_MESSAGE);
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




  private class GridPanel extends JPanel {

    public GridPanel() {
     // addKeyListener(new ListenKey());
      addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          int mouseX = e.getX();
          int mouseY = e.getY();

          // Determine the clicked room based on mouse coordinates
          int roomCol = mouseX / cellWidth;
          int roomRow = mouseY / cellHeight;
          Space clickedRoom = getClickedRoom(roomRow, roomCol);

          if (clickedRoom != null ) {
            String result = listener.movePlayerToRoom(model.getCurrentPlayer(), clickedRoom);
            System.out.println(result);
            updateDisplay();
          }
        }
      });
    }

    //&& model.isValidMove(model.getCurrentPlayer(), clickedRoom)

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

        int roomWidth = (room.getLowerRightPosition().getColumn()
                - room.getUpperLeftPosition().getColumn() + 1) * cellWidth;
        int roomHeight = (room.getLowerRightPosition().getRow()
                - room.getUpperLeftPosition().getRow() + 1) * cellHeight;

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
            if (player.isHumanControlled()) {
              int playerXCenter = roomX + (roomWidth / 2);
              int playerYCenter = roomY + (roomHeight / 2);
              int playerSize = (Math.min(cellWidth, cellHeight) / 2) * 5;

              g.setColor(Color.GREEN); // Use green color for human player
              g.fillRect(playerXCenter - playerSize / 2, playerYCenter - playerSize / 2, playerSize, playerSize);
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

    public ListenKey(){

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






