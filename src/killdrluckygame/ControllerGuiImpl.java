package killdrluckygame;

import killdrluckygame.view.WorldViewInterface;

import java.util.List;
import java.util.Map;

public class ControllerGuiImpl implements ControllerGuiInterface {
  private World worldModel;
  private WorldViewInterface worldView;
  private final int maxTurns;
  private CustomRandomInterface random;

  public ControllerGuiImpl(CustomRandomInterface random,
                           World worldModel, WorldViewInterface worldView, int maxTurns) {
    this.worldModel = worldModel;
    this.worldView = worldView;
    this.random = random;
    this.maxTurns = maxTurns;
    initializeListeners();
    worldView.addListener(this);
  }


  @Override
  public void playGame() {
    //click listener
    // keyboard listener
    // makevisible
  }
  private void initializeListeners() {
    // Add listeners to the GUI components (e.g., buttons, mouse events, keyboard events)
    // When the user interacts with the GUI, the corresponding methods in this controller
    // will be called to handle the actions.
    // For example:
    // - Add a button click listener to handle actions like "Look Around," "Pick Up Item," etc.
    // - Add a mouse click listener to handle moving the player in the world.
    // - Add keyboard event listeners to handle key presses for game actions.

    // Example:
    // worldView.getMoveButton().addActionListener(e -> moveButtonClicked());
    // worldView.getPickUpButton().addActionListener(e -> pickUpButtonClicked());
    // worldView.getWorldPanel().addMouseListener(new MouseAdapter() {
    //     public void mouseClicked(MouseEvent e) {
    //         handleMouseClick(e.getX(), e.getY());
    //     }
    // });
    // worldView.addKeyListener(new KeyAdapter() {
    //     public void keyPressed(KeyEvent e) {
    //         handleKeyPress(e.getKeyCode());
    //     }
    // });
  }

  // Implement methods to handle different user actions based on the GUI interactions
  // For example:
  private void moveButtonClicked() {
    // Logic to handle moving the player in the world
    // Call methods from the game model to update the player's position
    // Update the GUI to reflect the changes in the world

  }

  private void pickUpButtonClicked() {
    // Logic to handle picking up an item in the world
    // Call methods from the game model to pick up the item
    // Update the GUI to reflect the changes in the world
  }

  private void handleMouseClick(int x, int y) {
    // Logic to handle mouse click events
    // Determine the action based on the clicked position in the world
    // Call methods from the game model to update the game state
    // Update the GUI to reflect the changes in the world
  }

  private void handleKeyPress(int keyCode) {
    // Logic to handle key press events
    // Determine the action based on the pressed key
    // Call methods from the game model to update the game state
    // Update the GUI to reflect the changes in the world
  }

  public void processHumanUserInfoClick(String name, int maxCapacity, String roomName) {


    worldModel.addHumanPlayer(name, maxCapacity, roomName);

    Map<Space, List<Player>> mapping = worldModel.getMappingOfSpaceAndPlayer();

    if (mapping != null) {
      // Iterate through the mapping and display the details
      for (Map.Entry<Space, List<Player>> entry : mapping.entrySet()) {
        Space space = entry.getKey();
        List<Player> players = entry.getValue();

        // Iterate through the list of players for the current space
        for (Player currentplayer : players) {
          System.out.println("Space: " + space.getSpaceName() + " | Player: " +
                  currentplayer.getName());
        }
      }
    }
  }


  public void processComputerClick() {
    // Do something with the user input, e.g., create a new player with the provided data
    // and add it to the model.
    // Example:
    int maxCapacity = generateRandomMaxCapacity();
    int spaceRandomIndex = generateRandomFirstSpace();
    worldModel.addComputerPlayer(maxCapacity,spaceRandomIndex);


    Map<Space, List<Player>> mapping = worldModel.getMappingOfSpaceAndPlayer();

    if (mapping != null) {
      // Iterate through the mapping and display the details
      for (Map.Entry<Space, List<Player>> entry : mapping.entrySet()) {
        Space space = entry.getKey();
        List<Player> players = entry.getValue();

        // Iterate through the list of players for the current space
        for (Player currentplayer : players) {
          System.out.println("Space: " + space.getSpaceName() + " | Player: " +
                  currentplayer.getName());
        }
      }
    }
  }


  @Override
  public int generateRandomMaxCapacity() {
    return random.nextInt(1, 4);
  }

  @Override
  public int generateRandomFirstSpace() {
    return random.nextInt(worldModel.getSpaces().size());
  }

}

