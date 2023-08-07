package killdrluckygame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * This class is an implementation of the World interface and represents the world in the game.
 * It provides functionality to manage players, spaces, and items within the game world.
 * The DrLuckyWorld class encapsulates the game board, player turns, and various game operations.
 * It also includes methods to add players, move players, pick up items, and perform other
 * game-related actions.
 * This game has two types of players that are human controlled player and computer controlled
 * player.
 */
public class DrLuckyWorld implements World {
  private final int totalRows;
  private final int totalColumns;
  private final String worldName;
  private int currentSpaceIndex;
  private int currentTurn;
  private final Character targetCharacter;
  private List<Space> spaceList;
  private ComputerControlledPlayer cmPlayer;
  private ActionType prevAction;
  private List<Player> playerList;
  private Map<Space, List<Player>> mappingSpaceToPlayers;
  private final CustomRandomInterface random;
  private int numberOfTurns;
  private Space petSpace;
  private List<Item> evidenceList;
  private final TargetCharacterPetInterface pet;
  private int computerPlayerCount;


  /**
   * This initialises the rows, columns, name of thw world, target character, list of the spaces,
   * and list of the items.
   *
   * @param totalRows       value indicating the total rows.
   * @param totalColumns    value indicating the total columns.
   * @param worldName       value indicating name of the world.
   * @param targetCharacter character indicating the information about target.
   * @param spaceList       list of the spaces in whole world.
   * @param random          random object for generating random integers
   * @throws IllegalArgumentException Illegal argument exception for the invalid values
   */
  public DrLuckyWorld(int totalRows, int totalColumns, String worldName,
                      Character targetCharacter,
                      List<Space> spaceList, CustomRandomInterface random,
                      TargetCharacterPetInterface pet)
          throws IllegalArgumentException {

    if (totalRows <= 0 || totalColumns < 0
            || "".equals(worldName) || targetCharacter == null
            || spaceList == null) {
      throw new IllegalArgumentException(
              "Negative values or invalid values ! Conditions must satisfy !");
    } // checking for all illegal values

    this.totalRows = totalRows;
    this.totalColumns = totalColumns;
    this.targetCharacter = targetCharacter;
    this.worldName = worldName;
    this.spaceList = spaceList;
    this.pet = pet;

    this.currentTurn = 0;
    this.currentSpaceIndex = 0;
    this.petSpace = spaceList.get(currentSpaceIndex); //set starting position of pet

    this.cmPlayer = null;
    this.prevAction = null;
    this.mappingSpaceToPlayers = new HashMap<>();
    this.playerList = new ArrayList<>();
    this.evidenceList = new ArrayList<>();
    this.random = random;
    this.numberOfTurns = 0;
    this.computerPlayerCount = 0;
  }


  // These are getter methods for world details specifically.

  @Override
  public String getName() {
    return this.worldName;
  }


  @Override
  public int getRows() {
    return this.totalRows;
  }

  @Override
  public int getColumns() {
    return this.totalColumns;
  }


  @Override
  public int getTotalSpaces() {
    return spaceList.size();
  }


  @Override
  public List<Space> getSpaces() {
    return spaceList;
  }

  // These methods are for getting the details and adding the spaces to the world.

  @Override
  public void addSpaceToSpaceList(Space space) {
    spaceList.add(space);
  }


  @Override
  public void addMappingOfSpaceAndPlayer(Space space, Player player) {
    List<Player> players = mappingSpaceToPlayers.get(space);
    // Check if the ArrayList is null
    if (players == null) {
      // If it's null, initialize a new ArrayList
      players = new ArrayList<>();
    }

    // Append a new player to the ArrayList
    players.add(player);

    // Put the modified ArrayList back into the map using the same key
    mappingSpaceToPlayers.put(space, players);

  }


  @Override
  public Space getSpaceFromSpaceName(String spaceName) {
    for (Space space : spaceList) {
      if (space.getSpaceName().equals(spaceName)) {
        return space;
      }
    }
    return null;
  }


  @Override
  public List<Item> getItemsInRoom(String roomName) {
    List<Item> itemsInRoom = new ArrayList<>();
    for (Space space : spaceList) {
      if (space.getSpaceName().equals(roomName)) {
        for (Item item : space.getItems()) {
          itemsInRoom.add(item);
        }
      }
    }
    return itemsInRoom;
  }


  // These methods are for getting the neighbors for a particular space.
  @Override
  public List<Space> getNeighbors(Space space) {
    return calculateNeighbors(space);
  }

  @Override
  public List<String> printNeighbors(List<Space> neigh) {

    List<String> neighNames = new ArrayList<>();
    for (Space space : neigh) {
      //TODO pet space invisible
      if (!getPetSpace().equals(space)) {
        neighNames.add(space.getSpaceName());
      }
    }
    return neighNames;
  }

  @Override
  public List<String> getNeighborsStrings() {
    List<Space> neigh =
            this.getNeighbors(this.getCurrentPlayerSpace(this.getCurrentPlayer()));
    List<String> neighNames = printNeighbors(neigh);
    return neighNames;
  }

  private List<Space> calculateNeighbors(Space space) {

    List<Space> neighbors = new ArrayList<>();

    // Retrieve the coordinates of the current room

    int currentRoomUpperLeftRow = space.getUpperLeftPosition().getRow();
    int currentRoomUpperLeftCol = space.getUpperLeftPosition().getColumn();
    int currentRoomLowerRightRow = space.getLowerRightPosition().getRow();
    int currentRoomLowerRightCol = space.getLowerRightPosition().getColumn();

    // Iterate through the other rooms
    for (Space otherSpace : spaceList) {

      if (!otherSpace.equals(space)) {
        // Retrieve the coordinates of the other room

        int otherRoomUpperLeftRow = otherSpace.getUpperLeftPosition().getRow();
        int otherRoomUpperLeftCol = otherSpace.getUpperLeftPosition().getColumn();
        int otherRoomLowerRightRow = otherSpace.getLowerRightPosition().getRow();
        int otherRoomLowerRightCol = otherSpace.getLowerRightPosition().getColumn();

        // Check if the rooms share a wall in either the row or column dimension
        if ((currentRoomUpperLeftRow == otherRoomLowerRightRow + 1 && currentRoomUpperLeftCol
                <= otherRoomLowerRightCol && currentRoomLowerRightCol >= otherRoomUpperLeftCol)
                || (currentRoomLowerRightRow == otherRoomUpperLeftRow - 1 && currentRoomUpperLeftCol
                <= otherRoomLowerRightCol && currentRoomLowerRightCol >= otherRoomUpperLeftCol)
                || (currentRoomUpperLeftCol == otherRoomLowerRightCol + 1 && currentRoomUpperLeftRow
                <= otherRoomLowerRightRow && currentRoomLowerRightRow >= otherRoomUpperLeftRow)
                || (currentRoomLowerRightCol == otherRoomUpperLeftCol - 1 && currentRoomUpperLeftRow
                <= otherRoomLowerRightRow && currentRoomLowerRightRow >= otherRoomUpperLeftRow)) {

          // Add the neighboring room to the list
          neighbors.add(otherSpace);
        }
      }
    }
    return neighbors;
  }

  // These methods are for getting the details about the target character.

  @Override
  public String targetCharacterDetails() {
    StringBuilder sb = new StringBuilder();
    sb.append("Name of the target character: ").append(targetCharacter.getCharacterName()).append(
                    "  Target character is in room: ")
            .append(getCurrentSpaceTargetIsIn().getSpaceName()).append("  ")
            .append("\nHealth ").append(targetCharacter.getHealth()).append("\n");
    return sb.toString();
  }

  @Override
  public int getTargetHealth() {
    return targetCharacter.getHealth();
  }


  // Update the current space index as the target character moves to the next space.
  @Override
  public void moveTargetCharacter() {
    currentSpaceIndex++;
    if (currentSpaceIndex > getTotalSpaces() - 1) {
      currentSpaceIndex = 0;
    }
  }

  @Override
  public Space getCurrentSpaceTargetIsIn() {
    return spaceList.get(currentSpaceIndex);
  }


  // get current player

  @Override
  public Player getCurrentPlayer() {
    if (playerList.size() != 0) {
      return playerList.get(currentTurn);
    }
    return null;
  }
  // get players list

  @Override
  public List<Player> getPlayers() {
    return this.playerList;
  }

  @Override
  public String getPlayerDescriptionFromUsername(String playerName) {
    Player player = getPlayerByPlayerName(playerName);
    StringBuilder sb = new StringBuilder();
    sb.append(player.getPlayerDescription());
    sb.append("\nPlayer space is: ")
            .append(this.getCurrentPlayerSpace(this.getCurrentPlayer()).toString()).append("\n");
    return sb.toString();
  }

  @Override
  public Player getPlayerByPlayerName(String playerName) {
    for (Player player : playerList) {
      if (playerName.equals(player.getName())) {
        return player;
      }
    }
    return null;
  }

  @Override
  public String playerDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getCurrentPlayer().getPlayerDescription());
    sb.append("\nPlayer space is: ")
            .append(this.getCurrentPlayerSpace(this.getCurrentPlayer()).toString()).append("\n");
    return sb.toString();
  }

  @Override
  public String displayCurrentPlayerInfo() {
    //TODO name , space  only.
    Player player = this.getCurrentPlayer();
    StringBuilder sb = new StringBuilder();
    if(player!=null) {
      sb.append("Current player name: ");
      sb.append(player.getName());
      sb.append("  ");
      sb.append("Current space name: ");
      sb.append(getCurrentPlayerSpace(player).getSpaceName()).append("\n");
    }
    return sb.toString();

  }

  // This gets the space of the current player.
  @Override
  public Space getCurrentPlayerSpace(Player player) {
    for (Map.Entry<Space, List<Player>> entry : mappingSpaceToPlayers.entrySet()) {
      List<Player> players = entry.getValue();
      if (players.contains(player)) {
        return entry.getKey();
      }
    }
    return null; // Player not found in any space
  }

  @Override
  public void nextTurn() {
    if (currentTurn >= playerList.size() - 1) {
      currentTurn = 0;
    } else {
      currentTurn++;
    }

  }

  @Override
  public int getNumberOfTurns() {
    return this.numberOfTurns;
  }

  @Override
  public boolean hasGameEnded(int maxTurns) {

    if (this.numberOfTurns >= maxTurns) {
      return true;
    }
    if (targetCharacter.getHealth() == 0) {
      return true;
    } else if ((targetCharacter.getHealth() != 0)) {
      return false;
    }
    return false;
  }

  @Override
  public String toString() {
    return String.format("World Information: (World Name = %s, Total Rows = %s, Total columns = "
                    + "%s , Spaces = %s )", getName(),
            getRows(), getColumns(), getSpaces());
  }

  @Override
  public String getCurrentPetInfo() {
    return String.format("Name of the pet: " + pet.getPetName() + "\n  Pet space information: \n"
            + petSpace.getSpaceName());
    // only the name of the space.
  }

  @Override
  public Map<Space, List<Player>> getMappingOfSpaceAndPlayer() {
    return mappingSpaceToPlayers;
  }

  @Override
  public void petMove(String movePetToSpace) {
    increaseNumberOfTurns();
    this.petSpace = getSpaceFromSpaceName(movePetToSpace);
  }

  @Override
  public Space getPetSpace() {
    return petSpace;
  }

  @Override
  public String displayPotentialListOfSpaces() {
    StringBuilder sb = new StringBuilder();
    for (Space space : spaceList) {
      if (!space.equals(getPetSpace())) {
        sb.append(space.getSpaceName());
        sb.append("\n");
      }
    }
    return sb.toString();
  }


  // These are the methods for adding a human player to the game.
  @Override
  public void addHumanPlayer(String playerName, int maxItemsCarry,
                             String spaceName) {

    Player humanPlayer = new HumanControlledPlayer(playerName, maxItemsCarry);
    playerList.add(humanPlayer);
    Space space = getSpaceFromSpaceName(spaceName);
    // TODO: add space and player mapping:
    addMappingOfSpaceAndPlayer(space, humanPlayer);

  }


  // This move method moves the human player first and then checks if the next turn is of that
  // computer player. If it is it will call the simulateAction method to simulate the next move
  // of the computer player.

  @Override
  public boolean move(String spaceName) {

    if (playerList.size() != 0) {
      increaseNumberOfTurns();
      Player currentPlayer = playerList.get(currentTurn);
      if (currentPlayer.isHumanControlled()) {
        Space space = getSpaceFromSpaceName(spaceName);
        if (space != null) {
          changeSpacePlayerMapping(currentPlayer, space);
          return true;
        }
        return false;
      }
    }

    return true;
  }

  // This method maps the space with players.
  private void changeSpacePlayerMapping(Player player, Space newSpace) {
    // Get the current space of the player
    Space currentSpace = getCurrentPlayerSpace(player);
    // Check if the new space exists in the mapping
    if (mappingSpaceToPlayers.containsKey(currentSpace)) {
      // Move the player from the current space to the new space
      List<Player> currentSpacePlayers = mappingSpaceToPlayers.get(currentSpace);
      List<Player> newSpacePlayers = mappingSpaceToPlayers.get(newSpace);
      if (newSpacePlayers == null) {
        newSpacePlayers = new ArrayList<>();
      }

      // Remove the player from the current space
      currentSpacePlayers.remove(player);

      // Add the player to the new space
      newSpacePlayers.add(player);

      // If the current space becomes empty, remove it from the mapping
      if (currentSpacePlayers.isEmpty()) {
        mappingSpaceToPlayers.remove(currentSpace);
      }
      mappingSpaceToPlayers.put(newSpace, newSpacePlayers);
    }
  }

  // This pick item method allows the human player to pick an item and then checks if the next
  // turn is of the computer player If it is it will call the simulateAction method to simulate
  // the next move of the computer player.

  //TODO when the space has no items to pick up.
  @Override
  public boolean checkSpaceContainsItemsToPick(Space space) {
    if (space.getItems().size() != 0) {
      return true;
    }
    return false;
  }

  @Override
  public boolean pickItem(String itemName) {
    Player currentPlayer = playerList.get(currentTurn);
    increaseNumberOfTurns();
    if (currentPlayer.isHumanControlled()) {
      Item item = checkValidItemExists(currentPlayer, itemName);
      if (item != null) {
        if (!currentPlayer.isMaxCapacityPickItemExhausted()) {
          currentPlayer.pickItem(item);
          getCurrentPlayerSpace(currentPlayer).removeItemFromSpace(item);
          return true;
        }
      }
      return false;
    }
    return true;
  }


  private Item checkValidItemExists(Player player, String itemName) {
    for (Item item : getCurrentPlayerSpace(player).getItems()) {
      if (item.getItemName().equals(itemName)) {
        return item;
      }
    }
    return null;
  }


  // This lookAround method allows the human player to look and explore the neighboring spaces and
  // then checks if the next turn is of a computer player. If it is it will call the
  // simulateAction method to simulate the next move of the computer player.

  @Override
  public String lookAround() {
    Player currentPlayer = playerList.get(currentTurn);
    increaseNumberOfTurns();
    if (currentPlayer.isHumanControlled()) {
      return lookAroundString(currentPlayer);
    }
    return null;  // if no neighbors
  }

  private String lookAroundString(Player currentPlayer) {
    Space currentPlayerSpace = getCurrentPlayerSpace(currentPlayer);
    List<Space> neList = getNeighbors(currentPlayerSpace);
    String lookAround = printSpaceInfoForLookAround(neList, currentPlayerSpace);
    return lookAround;
  }


  private String printSpaceInfoForLookAround(List<Space> neList, Space currentPlayerSpace) {

    StringBuilder sb = new StringBuilder();

    // current space information
    List<Player> playerList = mappingSpaceToPlayers.get(currentPlayerSpace);
    List<Item> itemList = currentPlayerSpace.getItems();

    sb.append("The name of the space you are currently in: " + currentPlayerSpace.getSpaceName()
            + "\n");


    if ((playerList != null) && (playerList.size() != 0) && (checkIfSamePlayer(getCurrentPlayer(),
            playerList))) {
      sb.append("Name of the other players in your room are:");
      for (Player player : playerList) {
        sb.append(player.getName());
        sb.append("\n");
      }
    } else {
      sb.append("There are no players in the room other than you.\n");
    }

    if (itemList.size() != 0) {
      //TODO print items in the room
      sb.append("The items that are currently laying in the room: \n");
      for (Item item : itemList) {
        sb.append(item.toString());
        sb.append("\n");
      }

    } else {
      sb.append("No items in your current room\n");
    }

    // TODO check target character in the current space
    if ((currentPlayerSpace != null) && (currentPlayerSpace.equals(getCurrentSpaceTargetIsIn()))) {
      sb.append(String.format("The target character is in this room: %s\n", targetCharacter,
              "\n"));
    }


    //TODO check if pet is in the same space:
    if (currentPlayerSpace.equals(getPetSpace())) {
      sb.append("The target character's pet is in this room! \n ");
      sb.append(getCurrentPetInfo());
      sb.append("\n");
    }

    // TODO Neighboring spaces
    if (neList != null) {
      for (Space space : neList) {
        if (!space.equals(getPetSpace())) {
          sb.append(this.printSpaceInfo(space));
        }
      }
    }
    return sb.toString();
  }

  private boolean checkIfSamePlayer(Player currentPlayer, List<Player> playerList) {
    if ((playerList.size() == 1) && (playerList.contains(currentPlayer))) {
      return false;
    }
    return true;
  }


  @Override
  public String getSpaceInfoWithPlayer(String spaceName) {
    return printSpaceInfo(getSpaceFromSpaceName(spaceName));
  }

  // add pet description
  @Override
  public String printSpaceInfo(Space space) {
    StringBuilder sb = new StringBuilder();
    if (mappingSpaceToPlayers.containsKey(space)) {
      List<Player> players = mappingSpaceToPlayers.get(space);
      sb.append(String.format("Space Info: %s\nPlayers in this room:\n\n", space));
      for (Player player : players) {
        sb.append(String.format("Player Info: %s\n", player.getPlayerDescription(), "\n"));
      }
      if ((space != null) && (space.equals(getCurrentSpaceTargetIsIn()))) {
        sb.append(String.format("The target character is in this room: %s\n", targetCharacter,
                "\n"));
      }

      if ((space != null) && (space.equals(getPetSpace()))) {
        sb.append(String.format("The pet of target character is in this space: %s\n",
                getCurrentPetInfo(), "\n"));
      }
    } else {
      sb.append("\n");
      sb.append(String.format("Space Info: %s\nPlayers: No players in this room", space))
              .append("\n");
      if ((space != null) && (space.equals(getCurrentSpaceTargetIsIn()))) {
        sb.append(String.format("The target character is in this room: %s\n", targetCharacter,
                "\n"));
      }
      if ((space != null) && (space.equals(getPetSpace()))) {
        sb.append(String.format("The pet of target character is in this space: %s\n",
                getCurrentPetInfo(), "\n"));
      }
    }


    // TODO add neighbors display
    // here those spaces that are adjacent and do not have a pet will be visible.

    return sb.toString();
  }


  @Override
  public void addComputerPlayer(int maxCapacityComputer, int spaceIndexRandom) {

    // Generate a random string
    String randomComputerName = generateComputerNameString();
    int maxCapacity = maxCapacityComputer;
    cmPlayer = new ComputerControlledPlayer(randomComputerName, maxCapacity);
    playerList.add(cmPlayer);

    Space randomSpace = getSpaces().get(spaceIndexRandom);
    addMappingOfSpaceAndPlayer(randomSpace, cmPlayer);

  }

  private String generateComputerNameString() {
    String nameComputer = "computerplayer";

    StringBuilder sb = new StringBuilder();
    sb.append(nameComputer).append(computerPlayerCount);
    computerPlayerCount++;
    return sb.toString();
  }



  @Override
  public boolean isContainsNeighbor(String spaceName, List<String> neighNames) {
    return neighNames.contains(spaceName);
  }


  @Override
  public void increaseNumberOfTurns() {
    this.numberOfTurns += 1;
  }

  @Override
  public boolean checkIfTargetCharacterInSameSpace(Player player) {
    if (spaceList.get(currentSpaceIndex).equals(getCurrentPlayerSpace(player))) {
      return true;
    }
    return false;
  }

  @Override
  public String evaluateLookAround(Space currentSpace, Player player) {
    List<Space> spacesNe = getNeighbors(currentSpace);
    String lookAround = printSpaceInfoForLookAround(spacesNe, currentSpace);
    return lookAround;

  }


  @Override
  public String evaluatePickItem(List<Item> items, Player player) {

    StringBuilder sb = new StringBuilder();
    if (items.size() == 0) {
      return "";
    }
    if (player.isMaxCapacityPickItemExhausted()) {
      return "";
    }

    Item bestItem = items.get(0);
    for (Item itemPick : items) {
      if (itemPick.getDamageValue() > bestItem.getDamageValue()) {
        bestItem = itemPick;
      }
    }

    player.pickItem(bestItem);
    getCurrentPlayerSpace(player).removeItemFromSpace(bestItem);
    sb.append("\nComputer picked up item - ");
    sb.append(bestItem.toString());
    return sb.toString();

  }

  @Override
  public String evaluateNeighboringScores(Space currentSpace, Player player) {

    // check of target character for neighboring
    // along with best score.
    StringBuilder sb = new StringBuilder();
    int bestScore = 0;
    List<Space> neighboringSpaces = getNeighbors(currentSpace);
    Space bestSpaceToMove = neighboringSpaces.get(0);

    if (neighboringSpaces.size() != 0) {



      for (Space space : neighboringSpaces) {
        if (!space.equals(getPetSpace())) {
          int score = 0;
          for (Item item : space.getItems()) {
            score += item.getDamageValue();
          }
          if (score > bestScore) {
            bestScore = score;
            bestSpaceToMove = space;
          }
        }
      }
    }
    changeSpacePlayerMapping(player, bestSpaceToMove);
    sb.append("\nComputer moved - ");
    sb.append(bestSpaceToMove.toString());
    return sb.toString();
  }


  // These are the methods for the graphical representation of the world.
  @Override
  public void createImage() {
    DiagramWorldClass draw = new DiagramWorldClass(getRows(), getColumns());
    draw.display(spaceList);
  }


  // game mechanics oriented methods

  @Override
  public boolean isToPromptForInput() {
    if (playerList.size() == 0) {
      return true;
    }
    Player currentPlayer = getCurrentPlayer();
    if (currentPlayer.isHumanControlled()) {
      return true;
    }
    return false;
  }


  @Override
  public void performComputerAttack() {
    // get the item with the highest damage value and kill.
    Item maxDamageValueItem = null;
    int maxDameValue = Integer.MIN_VALUE;
    if (this.getCurrentPlayer().isComputerControlled() && (targetCharacter.getHealth() > 0)) {
      Player player = this.getCurrentPlayer();
      List<Item> itemList = this.getCurrentPlayer().getItems();
      if (itemList.size() != 0) {
        for (Item item : itemList) {
          if (maxDameValue < item.getDamageValue()) {
            maxDamageValueItem = item;
            maxDameValue = item.getDamageValue();
          }
        }
        targetCharacter.reduceHealth(maxDameValue);
        this.numberOfTurns++;


        evidenceList.add(maxDamageValueItem);
        this.getCurrentPlayer().removeItem(maxDamageValueItem);

      } else {
        // if no high value item perform poke
        targetCharacter.reduceHealth(1);
      }


    }

  }

  @Override
  public List<Item> getEvidenceList() {
    return evidenceList;
  }

  @Override
  public boolean isPlayerSeen() {
    Player playerCurrent = this.getCurrentPlayer();
    Space spaceCurrent = getCurrentPlayerSpace(playerCurrent);

    if (mappingSpaceToPlayers.containsKey(spaceCurrent)) {
      List<Player> players = mappingSpaceToPlayers.get(spaceCurrent);
      if (players.size() != 1) {
        return true;
      }
    }

    // get neighbors and check if the neighboring spaces have players which is not a target
    // character
    if (checkIfNeighboringSpaceHasPlayers(spaceCurrent)) {
      return true;
    }
    return false;
  }

  private boolean checkIfNeighboringSpaceHasPlayers(Space currentSpace) {

    // get neighbors
    // for each neighbor check if it contains any players
    List<Space> spaceNeighbors = getNeighbors(currentSpace);
    for (Space space : spaceNeighbors) {
      if (mappingSpaceToPlayers.containsKey(space)) {
        List<Player> players = mappingSpaceToPlayers.get(space);
        if ((players.size() != 0) && (!currentSpace.equals(getPetSpace()))) {
          return true;
        }
      }
    }
    return false;
  }


  private Item checkValidItemExistsInPlayerHand(Player currentPlayer, String itemName) {
    List<Item> itemList = currentPlayer.getItems();
    for (Item item : itemList) {
      if (item.getItemName().equals(itemName)) {
        return item;
      }
    }
    return null;
  }


  @Override
  public boolean attackHuman(String itemName) {
    boolean attackSuccessful = false;
    Item item = checkValidItemExistsInPlayerHand(this.getCurrentPlayer(), itemName);
    if ((targetCharacter.getHealth() > 0) && (!isPlayerSeen()) && (item != null)
            && checkIfTargetCharacterInSameSpace(this.getCurrentPlayer())) {
      targetCharacter.reduceHealth(item.getDamageValue());
      increaseNumberOfTurns();
      attackSuccessful = true;
      evidenceList.add(item);
      this.getCurrentPlayer().removeItem(item);

    } else {
      if (targetCharacter.getHealth() > 0 && (!isPlayerSeen())
              && checkIfTargetCharacterInSameSpace(this.getCurrentPlayer())) {
        targetCharacter.reduceHealth(1);
        increaseNumberOfTurns();
        attackSuccessful = true;
      }
    }
    return attackSuccessful;
  }


  @Override
  public ActionType getPrevActionOfComputer() {
    return prevAction;
  }


  @Override
  public void changePrevAction(ActionType newAction) {
    prevAction = newAction;
  }

  /**
   * This class is for reading and parsing the input from the file.
   */
  public static class Input {
    private Scanner scanner;
    private List<Space> spaceList = new ArrayList<>();

    private final StringBuilder dimensionName;
    private final List<Integer> gridDimensions;
    private final StringBuilder targetName;
    private final int[] targetHealth;
    private final StringBuilder petName;

    /**
     * A constructor to build the objects of world class.
     */
    public Input() {

      // 2-d grid variables
      dimensionName = new StringBuilder();
      gridDimensions = new ArrayList<>();

      // target character input variables
      targetHealth = new int[1];
      targetName = new StringBuilder();

      //pet details
      petName = new StringBuilder();
    }

    /**
     * Reads the input from a Readable object and generates the DrLuckyWorld object.
     *
     * @param readable The Readable object containing the input.
     * @return The DrLuckyWorld object generated from the input.
     */
    public DrLuckyWorld readInput(Readable readable) {


      readInformation(dimensionName, gridDimensions, targetHealth, targetName, readable);


      //create the target character
      Character target = new GameCharacter(targetHealth[0], targetName.toString(), true);

      TargetCharacterPetInterface pet = new TargetCharacterPet(petName.toString().trim());


      String worldName = dimensionName.toString().trim();

      //create the luckyworld object
      return new DrLuckyWorld(gridDimensions.get(0), gridDimensions.get(1),
              worldName, target, spaceList, new CustomRandom(), pet);

    }


    private void readInformation(StringBuilder dimensionName,
                                 List<Integer> gridDimensions,
                                 int[] targetHealth, StringBuilder targetName,
                                 Readable readable) {
      // reading world details
      scanner = new Scanner(readable);
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        // Process the line as per your requirements
        String[] values = line.split(" "); // Assuming space-separated values
        for (String value : values) {

          if (isNumeric(value)) {
            // Value is a number
            gridDimensions.add(Integer.parseInt(value));

          } else {
            // Value is a string
            dimensionName.append(value).append(" ");
          }
        }

      }

      // reading the target character details

      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        // Process the line as per your requirements
        String[] values = line.split(" "); // Assuming space-separated values

        for (String value : values) {
          if (isNumeric(value)) {
            // Value is a number
            targetHealth[0] = Integer.parseInt(value);

          } else {
            // Value is a string
            targetName.append(value).append(" ");

          }
        }

      }

      populatePetInformation();

      populateSpaceInformation();

      populateItemInformationForEachSpace();

    }

    private void populatePetInformation() {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        // Process the line as per your requirements
        String[] values = line.split(" "); // Assuming space-separated values
        for (String value : values) {

          // Value is a string
          petName.append(value).append(" ");

        }

      }
    }

    private void populateItemInformationForEachSpace() {
      StringBuilder itemName;
      int countOfItems = count();

      while (countOfItems > 0) {
        List<Integer> inputItemInfo = new ArrayList<>();
        itemName = new StringBuilder();
        if (scanner.hasNextLine()) {
          String line = scanner.nextLine();

          // Process the line as per your requirements
          String[] values = line.split(" "); // Assuming space-separated values

          for (String value : values) {
            if (isNumeric(value)) {
              inputItemInfo.add(Integer.parseInt(value));

            } else {
              itemName.append(value).append(" ");

            }

          }
          Item item = new DrLuckyItem(itemName.toString().trim(),
                  inputItemInfo.get(1));
          // add the item to the arraylist
          spaceList.get(inputItemInfo.get(0)).addItemToSpace(item);
        }

        countOfItems--;

      }
    }

    private void populateSpaceInformation() {
      // reading the spaces information
      int countOfRooms = count();
      StringBuilder roomName;
      while (countOfRooms > 0) {
        List<Integer> inputSpaceDimension = new ArrayList<>();
        roomName = new StringBuilder();
        if (scanner.hasNextLine()) {
          String line = scanner.nextLine();

          // Process the line as per your requirements
          String[] values = line.split(" "); // Assuming space-separated values

          for (String value : values) {
            if (isNumeric(value)) {
              // Value is a number
              inputSpaceDimension.add(Integer.parseInt(value));

            } else {
              // Value is a string
              roomName.append(value).append(" ");

            }


          }
          addSpaceToSpaceList(new DrLuckySpace(roomName.toString().trim(),
                  new WorldPosition(inputSpaceDimension.get(0), inputSpaceDimension.get(1)),
                  new WorldPosition(inputSpaceDimension.get(2), inputSpaceDimension.get(3))));
        }

        countOfRooms--;

      }
    }

    private void addSpaceToSpaceList(Space space) {
      spaceList.add(space);
    }

    private int count() {
      int count = 0;
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        // Process the line as per your requirements
        String[] values = line.split(" "); // Assuming space-separated values

        for (String value : values) {
          count = Integer.parseInt(value);
        }
      }
      return count;
    }

    private static boolean isNumeric(String str) {
      try {
        Integer.parseInt(str);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    }
  }
}
