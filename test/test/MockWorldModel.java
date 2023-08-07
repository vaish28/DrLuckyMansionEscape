package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import killdrluckygame.*;

/**
 * This class is mock model for testing the controller in isolation from the model. This class
 * does not contain the concrete implementations for all the methods. This class is only for the
 * purpose of testing the controller.
 */
public class MockWorldModel implements World {


  private Map<Space, List<Player>> mappingSpaceToPlayers;
  private StringBuilder log;
  private final int uniqueCode;

  private final boolean isHumanControlled;


  /**
   * This constructor is required for initialising the mock model object.
   *
   * @param log               this is used to log the output using String Builder.
   * @param uniqueCode        this is a unique code used to test the controller.
   * @param isHumanControlled this is used to decidde whether it is computer controlled or human
   *                          controlled player.
   */
  public MockWorldModel(StringBuilder log, int uniqueCode, boolean isHumanControlled) {
    this.log = log;
    this.uniqueCode = uniqueCode;
    this.isHumanControlled = isHumanControlled;
    this.mappingSpaceToPlayers = new HashMap<>();
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public int getRows() {
    return 0;
  }

  @Override
  public int getColumns() {
    return 0;
  }

  @Override
  public int getTotalSpaces() {
    return 0;
  }

  @Override
  public List<Space> getSpaces() {
    return null;
  }


  @Override
  public void addSpaceToSpaceList(Space space) {

  }

  @Override
  public Space getCurrentSpaceTargetIsIn() {
    return null;
  }

  @Override
  public List<Item> getItemsInRoom(String roomName) {
    return null;
  }

  @Override
  public void addComputerPlayer(int maxCapacityComputer, int spaceIndexRandom) {
    log.append("Adding a computer player");

  }

  @Override
  public boolean isContainsNeighbor(String spaceName, List<String> neighNames) {
    return false;
  }

  @Override
  public String playerDescription() {
    return null;
  }



  @Override
  public String evaluateLookAround(Space currentSpace, Player player) {
    return null;
  }

  @Override
  public String evaluatePickItem(List<Item> items, Player player) {
    return null;
  }

  @Override
  public String evaluateNeighboringScores(Space currentSpace, Player player) {
    return null;
  }


  @Override
  public void createImage() {
    log.append("Creating the graphical image");
  }

  @Override
  public int getTargetHealth() {
    return 0;
  }

  @Override
  public void moveTargetCharacter() {

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
    log.append("mapping player and space");
  }


  @Override
  public String targetCharacterDetails() {
    return null;
  }

  @Override
  public String displayPotentialListOfSpaces() {
    return null;
  }


  @Override
  public void addHumanPlayer(String playerName, int maxItemsCarry, String spaceName) {
    log.append("Adding human player");
    log.append(playerName);
    log.append(maxItemsCarry);
    log.append(spaceName);
  }

  @Override
  public Space getSpaceFromSpaceName(String spaceName) {
    return null;
  }

  @Override
  public boolean move(String spaceName) {
    return false;
  }

  @Override
  public String lookAround() {
    return log.append("Looking around for neighbors").toString();
  }

  @Override
  public boolean checkSpaceContainsItemsToPick(Space space) {
    return false;
  }

  @Override
  public boolean pickItem(String itemName) {
    log.append("Picking up an item!");
    return false;
  }

  @Override
  public String printSpaceInfo(Space space) {
    return null;
  }

  @Override
  public void nextTurn() {

  }

  @Override
  public List<Space> getNeighbors(Space space) {
    return null;
  }



  @Override
  public Player getCurrentPlayer() {
    Player player = new HumanControlledPlayer("Neha", 6);
    return player;
  }

  @Override
  public List<Player> getPlayers() {
    return null;
  }


  @Override
  public boolean isToPromptForInput() {
    return false;
  }

  @Override
  public String getSpaceInfoWithPlayer(String spaceName) {
    return null;
  }

  @Override
  public int getNumberOfTurns() {
    return 0;
  }

  @Override
  public boolean hasGameEnded(int maxTurns) {
    return false;
  }

  @Override
  public String getPlayerDescriptionFromUsername(String playerName) {
    return log.append("Getting player description from username!").toString();
  }

  @Override
  public Player getPlayerByPlayerName(String playerName) {
    return null;
  }

  @Override
  public List<String> printNeighbors(List<Space> neigh) {
    return null;
  }

  @Override
  public List<String> getNeighborsStrings() {
    return null;
  }

  @Override
  public String getCurrentPetInfo() {
    return log.append("Current player is:").toString();
  }

  @Override
  public Map<Space, List<Player>> getMappingOfSpaceAndPlayer() {
    return null;
  }

  @Override
  public void petMove(String petMoveToSpace) {
    log.append("Moving the pet");
  }

  @Override
  public Space getPetSpace() {
    return null;
  }

  @Override
  public void performComputerAttack() {
  }

  @Override
  public List<Item> getEvidenceList() {
    return null;
  }

  @Override
  public boolean isPlayerSeen() {
    return false;
  }

  @Override
  public boolean attackHuman(String itemName) {
    log.append("Attacking target");
    return true;
  }


  @Override
  public String displayCurrentPlayerInfo() {
    return getCurrentPlayer().getPlayerDescription();
  }

  @Override
  public Space getCurrentPlayerSpace(Player player) {
    return null;
  }

  @Override
  public ActionType getPrevActionOfComputer() {
    return null;
  }

  @Override
  public void increaseNumberOfTurns() {

  }

  @Override
  public boolean checkIfTargetCharacterInSameSpace(Player player) {
    return false;
  }

  @Override
  public void changePrevAction(ActionType newAction) {

  }

}