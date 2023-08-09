package killdrluckygame;

import killdrluckygame.view.WorldViewImpl;
import killdrluckygame.view.WorldViewInterface;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ControllerGuiImpl implements ControllerGuiInterface {
  private World worldModel;
  private WorldViewInterface worldView;
  private final int maxTurns;
  private CustomRandomInterface random;
  private String filePath;
  public ControllerGuiImpl(CustomRandomInterface random,
                           World worldModel, WorldViewInterface worldView, int maxTurns, String filePath) {
    this.worldModel = worldModel;
    this.worldView = worldView;
    this.random = random;
    this.maxTurns = maxTurns;
    this.filePath = filePath;
    initializeListeners();
    worldView.addListener(this);
  }


  @Override
  public void playGame() {

  }
  private void initializeListeners() {

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
  public String pickItem(String pickedItem) {
    if(worldModel!=null) {
      if(worldModel.pickItem(pickedItem)) {
        worldModel.moveTargetCharacter();
        worldModel.nextTurn();
        return "Item picked successfully";
      }
      else {
        return "Item not picked successfully";
      }
    }

    return "";
  }

  @Override
  public String lookAround() {
    if(worldModel!=null) {
      if(worldModel.lookAround()!=null) {
        worldModel.moveTargetCharacter();
        worldModel.nextTurn();
        return worldModel.lookAround();
      }
      else {
        return "Look around not successful";
      }
    }
    return "";
  }

  @Override
  public String attemptOnTargetCharacter(String itemName) {
    if(worldModel!=null) {
      if(worldModel.attackHuman(itemName)) {
        worldModel.moveTargetCharacter();
        worldModel.nextTurn();
        return "Attack successful";
      }
      else {
        return "Attack not successful";
      }
    }
    return "";
  }

  @Override
  public void loadNewGame(String worldFileName) {
    try {
      this.worldModel = new DrLuckyWorld.Input().readInput(new FileReader(worldFileName));
    } catch (IOException e) {
      // Handle file reading or parsing errors
      String.format("An error occurred while reading the file: " + e.getMessage());
      e.printStackTrace();
    }
    worldView.setWorld(worldModel);
  }

  public void resetGame() {

    try {
      this.worldModel = new DrLuckyWorld.Input().readInput(new FileReader(filePath));
    } catch (IOException e) {
      String.format(e.getMessage());
    }
    worldView.setWorld(worldModel);
  }

  @Override
  public String movePlayerToRoom(Player currentPlayer, Space clickedRoom) {
    if(worldModel.move(clickedRoom.getSpaceName())){
      worldModel.moveTargetCharacter();
      worldModel.nextTurn();
      return "moved successfully";
    }

    return "move not successful";
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

