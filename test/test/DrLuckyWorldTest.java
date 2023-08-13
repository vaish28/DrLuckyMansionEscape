package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import killdrluckygame.Character;
import killdrluckygame.CustomRandom;
import killdrluckygame.DrLuckyItem;
import killdrluckygame.DrLuckySpace;
import killdrluckygame.DrLuckyWorld;
import killdrluckygame.GameCharacter;
import killdrluckygame.HumanControlledPlayer;
import killdrluckygame.Item;
import killdrluckygame.Player;
import killdrluckygame.Space;
import killdrluckygame.World;
import killdrluckygame.WorldPosition;
import org.junit.Before;
import org.junit.Test;


/**
 * This tests the Dr Lucky World in the world template.
 */
public class DrLuckyWorldTest {

  private World world;
  private Item itemOne;
  private Item itemTwo;
  private Item itemThree;
  private Space spaceOne;
  private Space spaceTwo;
  private Space spaceThree;
  private Space spaceFour;
  private Item itemFour;


  /**
   * Creates a world object and tests if it is a valid object.
   *
   * @param totalRows       the number of rows.
   * @param totalColumns    the number of columns.
   * @param worldName       the name of the world.
   * @param targetCharacter the details about the target character.
   * @param spaceList       the spaceList of spaces.
   * @return a world object.
   */
  protected World worldValidChecker(int totalRows, int totalColumns, String worldName,
                                    Character targetCharacter,
                                    List<Space> spaceList) {
    return new DrLuckyWorld(totalRows, totalColumns, worldName, targetCharacter,
            spaceList, new CustomRandom(0));
  }

  /**
   * This setups the objects required to test the DrLuckyWorld class.
   */
  @Before
  public void setUp() {

    itemOne = new DrLuckyItem("Sharp Knife", 3);
    itemTwo = new DrLuckyItem("Blade", 5);


    spaceOne = new DrLuckySpace("Garden", new WorldPosition(1, 2),
            new WorldPosition(4, 4));

    spaceTwo = new DrLuckySpace("Jungle", new WorldPosition(2, 2),
            new WorldPosition(5, 4));

    spaceThree = new DrLuckySpace("Distant land", new WorldPosition(3, 2),
            new WorldPosition(6, 4));

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree)),
            new CustomRandom(1));
  }

  /**
   * Tests the scenario where an empty list of spaces is passed to the DrLuckyWorld constructor
   * and expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWorldDescriptionEmptyArrayList() {
    worldValidChecker(12, 12, "LuckyMansion",
            new GameCharacter(50, "Lucky", false),
            null);
  }

  /**
   * Tests the scenario where a negative value is passed for the total number of rows in the world
   * and expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWorldDescriptionNegativeRows() {
    worldValidChecker(-12, 12, "LuckyMansion",
            new GameCharacter(50, "Lucky", false),
            null);
  }

  /**
   * Tests the scenario where a negative value is passed for the total number of columns in the
   * world and expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWorldDescriptionNegativeColumns() {
    worldValidChecker(12, -8, "LuckyMansion",
            new GameCharacter(50, "Lucky", false),
            null);
  }

  /**
   * Tests the scenario where a null value is passed for the world name and expects an
   * IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWorldDescriptionNullWorldName() {
    worldValidChecker(12, 8, "", new GameCharacter(50,
                    "Lucky", false),
            null);
  }

  /**
   * Tests the scenario where a null value is passed for the target character and expects an
   * IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWorldDescriptionNullTargetCharacter() {
    worldValidChecker(12, 8, "Lucky", null,
            null);
  }

  /**
   * Tests if the initial current space for the target character is correctly set to the first
   * space in the space list.
   */
  @Test
  public void testStartIndexZero() {
    assertTrue(world.getCurrentSpaceTargetIsIn().equals(spaceOne));
  }

  /**
   * Tests if the target character moves to the next space in the space list when the
   * moveTargetCharacter method is called.
   */

  @Test
  public void testTargetCharacterMovesFromRoomZeroToRoomOne() {
    world.moveTargetCharacter();
    assertTrue(world.getCurrentSpaceTargetIsIn().equals(spaceTwo));
  }

  /**
   * Tests if the target character moves correctly through multiple spaces in the space list when
   * the moveTargetCharacter method is called multiple times.
   */
  @Test
  public void testTargetCharacterMultipleMoves() {
    world.moveTargetCharacter();
    world.moveTargetCharacter();

    assertTrue(world.getCurrentSpaceTargetIsIn().equals(spaceThree));
  }

  /**
   * Tests if the target character moves correctly from the last space in the space list to the
   * first space when the moveTargetCharacter method is called multiple times.
   */
  @Test
  public void testTargetCharacterBackToZeroMove() {
    world.moveTargetCharacter();
    world.moveTargetCharacter();
    world.moveTargetCharacter();

    assertTrue(world.getCurrentSpaceTargetIsIn().equals(spaceOne));
  }

  /**
   * Tests if the target character moves correctly from the last space in the space list to the
   * second space when the moveTargetCharacter method is called multiple times.
   */

  @Test
  public void testTargetCharacterBackToOneMove() {
    world.moveTargetCharacter();
    world.moveTargetCharacter();
    world.moveTargetCharacter();
    world.moveTargetCharacter(); //return to 0 now since cyclic
    world.moveTargetCharacter(); //return to 2 now since cyclic

    assertTrue(world.getCurrentSpaceTargetIsIn().equals(spaceThree));
  }


  /**
   * Tests if the getName method returns the correct world name.
   */
  @Test
  public void testGetWorldName() {
    assertTrue(world.getName().equals("Dr Lucky Mansion"));
  }

  /**
   * Tests if the getRows method returns the correct total number of rows in the world.
   */
  @Test
  public void testGetWorldRows() {
    assertEquals(world.getRows(), 12);
  }

  /**
   * Tests if the getColumns method returns the correct total number of columns in the world.
   */
  @Test
  public void testGetWorldColumns() {
    assertEquals(world.getColumns(), 8);
  }

  /**
   * Tests if the getTotalSpaces method returns the correct total number of spaces in the world.
   */
  @Test
  public void testGetTotalSpaces() {

    assertEquals(world.getTotalSpaces(), 3);
  }


  /**
   * Tests if the targetCharacterDetails method returns the correct string representation of the
   * target character's details.
   */
  @Test
  public void testGetTargetDetails() {
    String expected = String.format("Name of the target character: Lucky  "
            + "Target character is in room: Garden  \n"
            + "Health 50\n");

    assertEquals(expected, world.targetCharacterDetails());
  }

  /**
   * Tests if the moveTargetCharacter method correctly moves the target character to the next
   * space in the space list.
   */
  @Test
  public void testTargetCharacterMove() {
    world.moveTargetCharacter();
    assertTrue(world.getCurrentSpaceTargetIsIn().equals(spaceTwo));
  }

  /**
   * Tests if the getCurrentSpaceTargetIsIn method returns the correct current space for the
   * target character.
   */
  @Test
  public void testGetCurrentSpaceTargetIsIn() {
    assertTrue(world.getCurrentSpaceTargetIsIn().equals(spaceOne));
  }

  /**
   * Tests if the addSpaceToSpaceList method correctly adds a space to the space list.
   */

  @Test
  public void testAddSpaceTwo() {
    world.addSpaceToSpaceList(spaceOne);
    assertTrue(world.getSpaces().contains(spaceOne));
  }

  /**
   * Tests if the itemInRoom method returns the correct list of items in a specific room.
   */
  @Test
  public void itemInRoomTest() {
    spaceOne.addItemToSpace(itemOne);
    List<Item> tempItems = world.getItemsInRoom("Garden");
    List<Item> list2 = new ArrayList<>();
    list2.add(itemOne);
    assertTrue(list2.equals(tempItems));
  }

  /**
   * Tests if a human player is correctly placed in the specified room when added to the world.
   */

  @Test
  public void testMoveFirst() {
    world.addHumanPlayer("vaish", 6, "Garden");
    Player temp = world.getPlayerByPlayerName("vaish");
    // asserting the starting position.
    assertTrue(world.getCurrentPlayerSpace(temp).toString().equals(spaceOne.toString()));
  }

  /**
   * Tests if a human player can move to a different space in the world.
   */

  @Test
  public void testMovePlayer() {
    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);

    world.addSpaceToSpaceList(spaceOne);
    world.addSpaceToSpaceList(spaceTwo);

    // asserting the starting position.
    world.addHumanPlayer("Player1", 15, "Garden");
    assertTrue(world.getCurrentPlayerSpace(world.getCurrentPlayer()).toString()
            .equals(spaceOne.toString()));

    // now move to the next space.
    // move to next space
    world.move(spaceTwo.getSpaceName());
    // Check if the item is added to the player's inventory
    assertTrue(world.getCurrentPlayerSpace(world.getCurrentPlayer()).toString()
            .equals(spaceTwo.toString()));
  }

  /**
   * Tests if a human player can successfully pick up an item from a space.
   */
  @Test
  public void testPickItemHumanPlayer() {
    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);

    world.addSpaceToSpaceList(spaceOne);
    // Move the player to spaceOne
    // Create a player
    world.addHumanPlayer("Player1", 15, "Garden");

    //picking up an item that does not exist
    assertFalse(world.pickItem(itemTwo.getItemName()));
    // Pick an item from spaceOne
    world.pickItem(itemOne.getItemName());

    // Check if the item is removed from spaceOne
    assertFalse(spaceOne.getItems().contains(itemOne));

    // Check if the item is added to the player's inventory
    assertTrue(world.getCurrentPlayer().getItems().contains(itemOne));
  }

  /**
   * Tests if a human player cannot pick up an item after a certain limit.
   */
  @Test
  public void testMaxCapacityPickItem() {
    spaceOne.addItemToSpace(itemOne);
    spaceOne.addItemToSpace(itemTwo);

    world.addSpaceToSpaceList(spaceOne);
    // Move the player to spaceOne
    // Create a player
    world.addHumanPlayer("Player1", 1, "Garden");

    // Pick an item from spaceOne
    world.pickItem(itemOne.getItemName());

    // Check if the item is removed from spaceOne
    assertFalse(spaceOne.getItems().contains(itemOne));

    // Check if the item is added to the player's inventory
    assertTrue(world.getCurrentPlayer().getItems().contains(itemOne));
    assertEquals(false, world.pickItem(itemTwo.getItemName()));
  }

  /**
   * Tests looking around within the world.
   */
  @Test
  public void testLookAround() {

    spaceOne = new DrLuckySpace("Billiard Room", new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    spaceTwo = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceThree = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceFour = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));

    itemOne = new DrLuckyItem("Billiard Cue", 2);
    itemTwo = new DrLuckyItem("Letter Opener", 2);
    itemThree = new DrLuckyItem("Revolver", 3);

    spaceOne.addItemToSpace(itemOne);
    spaceThree.addItemToSpace(itemTwo);
    spaceFour.addItemToSpace(itemThree);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));

    world.addSpaceToSpaceList(spaceFour);
    // Move the player to spaceOne
    // Create a player
    world.addHumanPlayer("Player1", 15, "Armory");

    // Pick an item from spaceOne
    String lookAround = world.lookAround();
    //TODO assert for lookAround

  }


  /**
   * Tests player turns within the world.
   */
  @Test
  public void testTurnExchange() {

    spaceFour = new DrLuckySpace("Billiard Room", new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    spaceTwo = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceThree = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));

    itemOne = new DrLuckyItem("Billiard Cue", 2);
    itemTwo = new DrLuckyItem("Letter Opener", 2);
    itemThree = new DrLuckyItem("Revolver", 3);
    itemFour = new DrLuckyItem("Fork", 1);

    spaceOne.addItemToSpace(itemThree);
    spaceTwo.addItemToSpace(itemFour);
    spaceThree.addItemToSpace(itemTwo);
    spaceFour.addItemToSpace(itemOne);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));
    // Move the player to spaceOne
    // Create a player
    world.addHumanPlayer("Player1", 15, "Armory");
    world.addHumanPlayer("Player2", 5, "Armory");
    world.addComputerPlayer(3,0);

    // Pick an item from spaceOne
    world.move(spaceTwo.getSpaceName());

    // Check if the item is added to the player has moved
    assertTrue(world.getCurrentPlayerSpace(world.getCurrentPlayer()).toString()
            .equals(spaceTwo.toString()));
    world.nextTurn();

    // next Turn of player 2
    String lookAround = world.lookAround();
    System.out.println(lookAround);
    //TODO lookaround string
  }


  /**
   * Tests the computer player details.
   */
  @Test
  public void testComputer() {
    CustomRandom customRandom = new CustomRandom(0);
    spaceOne = new DrLuckySpace("Billiard Room", new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne)),
            new CustomRandom(0));
    world.addComputerPlayer(3,0);
    assertTrue(spaceOne.equals(world.getCurrentPlayerSpace(world.getCurrentPlayer())));
  }

  /**
   * Tests moving into no neighbor space.
   */
  @Test
  public void testMoveIntoNoNeighbor() {
    // pass a string name and list of neighbors
    assertFalse(world.isContainsNeighbor("Arbitrary Room", new ArrayList<>(Arrays.asList(
            "BilliardRoom",
            "Drawing Room"))));
  }


  /**
   * Tests the game ending condition.
   */
  @Test
  public void testGameEnded() {

    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));

    itemOne = new DrLuckyItem("Billiard Cue", 2);
    itemTwo = new DrLuckyItem("Letter Opener", 2);
    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo)),
            new CustomRandom(0));


    world.addHumanPlayer("Player1", 1, "Drawing Room");
    world.move(spaceTwo.getSpaceName());
    world.pickItem(spaceTwo.getItems().get(0).getItemName());
    assertEquals(world.getNumberOfTurns(), 2);
  }

  /**
   * Tests the print space information.
   */
  @Test
  public void testPrintSpaceInfo() {

    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));
    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));


    itemOne = new DrLuckyItem("Billiard Cue", 2);
    itemTwo = new DrLuckyItem("Letter Opener", 2);
    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo)),
            new CustomRandom(0));

    world.addHumanPlayer("Player1", 1, "Armory");


    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Space Info: ");
    stringBuilder.append("Space Information (Space Name = Armory, WorldPosition UpperLeft Row "
            + "= 22, WorldPosition UpperLeft Column = 19, WorldPosition LowerRight Row = 23, "
            + "WorldPosition LowerRight Column = 26, Items = [Item Information "
            + "(Item Name = Billiard Cue, Damage Value = 2)])").append("\n");
    stringBuilder.append("Players in this room:").append("\n\n");
    stringBuilder.append("Player Info: Human Player Information: "
            + "(Player Name = Player1 Items in hand = [])").append("\n\n");
    stringBuilder.append("The target character is in this room: Character "
            + "Information (Character Name = Lucky, Character Health = 50, "
            + "Character is Target = true)").append("\n");


    String output = stringBuilder.toString();

    assertEquals(output, world.getSpaceInfoWithPlayer("Armory"));

  }

  /**
   * Tests the player description.
   */
  @Test
  public void testPlayerDescription() {
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));
    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));


    itemOne = new DrLuckyItem("Billiard Cue", 2);
    itemTwo = new DrLuckyItem("Letter Opener", 2);
    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo)),
            new CustomRandom(0));

    world.addHumanPlayer("Player1", 1, "Armory");

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Human Player Information: (Player Name = Player1 Items in hand = [])");
    stringBuilder.append("\n\n");
    stringBuilder.append("Player space is: Space Information (Space Name = Armory, ");
    stringBuilder.append("WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 19, ");
    stringBuilder.append("WorldPosition LowerRight Row = 23, "
            + "WorldPosition LowerRight Column = 26, ");
    stringBuilder.append("Items = [Item Information (Item Name = Billiard Cue, "
            + "Damage Value = 2)])\n");


    String output = stringBuilder.toString();
    assertEquals(output, world.playerDescription());


    stringBuilder = new StringBuilder();

    stringBuilder.append("Human Player Information: (Player Name = Player1 Items in hand = [");
    stringBuilder.append("Item Information (Item Name = Billiard Cue, Damage Value = 2)])\n\n");

    stringBuilder.append("Player space is: Space Information (Space Name = Armory, ");
    stringBuilder.append("WorldPosition UpperLeft Row = 22, WorldPosition "
            + "UpperLeft Column = 19, ");
    stringBuilder.append("WorldPosition LowerRight Row = 23, "
            + "WorldPosition LowerRight Column = 26, ");
    stringBuilder.append("Items = No Items)\n");

    output = stringBuilder.toString();
    world.pickItem(itemOne.getItemName());

    assertEquals(output, world.playerDescription());
  }

  /**
   * Test to check the attack of the human player by using an item.
   */

  @Test
  public void testAttackHumanItem() {
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));
    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));


    itemOne = new DrLuckyItem("Billiard Cue", 2);
    itemTwo = new DrLuckyItem("Letter Opener", 2);
    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo)),
            new CustomRandom(0));

    world.addHumanPlayer("Player1", 1, "Drawing Room");

    boolean truePick = world.pickItem("Letter Opener");
    assertEquals(truePick, true);
    world.moveTargetCharacter();
    world.nextTurn();


    world.attackHuman("Letter Opener");
    world.moveTargetCharacter();
    world.nextTurn();
    StringBuilder expected = new StringBuilder("Name of the target character: Lucky  Target "
            + "character is in room: Armory  \n"
            + "Health 48\n");

    assertEquals(expected.toString(), world.targetCharacterDetails());

  }


  /**
   * Test to check the attack of human player by poking.
   */
  @Test
  public void testAttackHumanPoke() {
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));
    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));


    itemOne = new DrLuckyItem("Billiard Cue", 2);
    itemTwo = new DrLuckyItem("Letter Opener", 2);
    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo)),
            new CustomRandom(0));
    world.addHumanPlayer("Player1", 1, "Drawing Room");
    world.lookAround();
    world.moveTargetCharacter();
    world.nextTurn();


    world.attackHuman("");
    world.moveTargetCharacter();
    world.nextTurn();

    StringBuilder sb = new StringBuilder();

    sb.append("Name of the target character: Lucky  ");
    sb.append("Target character is in room: Armory  ").append("\n");
    sb.append("Health 49\n");

    assertEquals(sb.toString(), world.targetCharacterDetails());
  }


  @Test
  public void testAttackHumanNoPlayersInNeigbhorInSpaces() {
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));
    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));
    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(5, 2),
            new WorldPosition(2, 1));
    Space spaceFive = new DrLuckySpace("Trojan Room", new WorldPosition(3, 13),
            new WorldPosition(5, 1));


    itemOne = new DrLuckyItem("Billiard Cue", 2);
    itemTwo = new DrLuckyItem("Letter Opener", 2);
    itemThree = new DrLuckyItem("Soft Knife", 2);
    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);
    spaceThree.addItemToSpace(itemThree);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceFive, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));
    world.addHumanPlayer("Player1", 1, "Drawing Room");
    world.addHumanPlayer("Player2", 1, "Foyer");


    List<Space> ne = world.getNeighbors(spaceTwo);
    assertTrue(ne.contains(spaceThree));
    assertFalse(ne.contains(spaceFive));
    assertFalse(ne.contains(spaceFour));


    world.pickItem("Letter Opener");
    world.moveTargetCharacter();
    world.nextTurn();

    world.lookAround();
    world.moveTargetCharacter();
    world.nextTurn();


    world.attackHuman("Letter Opener");
    world.moveTargetCharacter();
    world.nextTurn();

    StringBuilder sb = new StringBuilder();

    sb.append("Name of the target character: Lucky  ");
    sb.append("Target character is in room: Dining Hall  ").append("\n");
    sb.append("Health 48\n");

    assertEquals(sb.toString(), world.targetCharacterDetails());
  }



  @Test
  public void testAttackTargetCharacterSpaceDifferent() {

    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));
    spaceTwo = new DrLuckySpace("Dining Hall", new WorldPosition(23, 13),
            new WorldPosition(27, 18));
    spaceThree = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));


    itemOne = new DrLuckyItem("Billiard Cue", 2);
    itemTwo = new DrLuckyItem("Letter Opener", 2);
    spaceOne.addItemToSpace(itemOne);
    spaceThree.addItemToSpace(itemTwo);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree)),
            new CustomRandom(0));


    world.addHumanPlayer("Player1", 1, "Drawing Room");
    world.pickItem("Letter Opener");
    assertTrue(world.getCurrentPlayer().getPlayerDescription().contains("Letter Opener"));
    world.moveTargetCharacter();
    world.nextTurn();

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Name of the target character: Lucky ");
    stringBuilder.append(" Target character is in room: Dining Hall  ").append("\n");
    stringBuilder.append("Health 50\n");

    String result = stringBuilder.toString();


    assertEquals(result, (world.targetCharacterDetails()));
    assertFalse(world.attackHuman(""));
    world.moveTargetCharacter();
    world.nextTurn();

    stringBuilder = new StringBuilder();

    stringBuilder.append("Name of the target character: Lucky ");
    stringBuilder.append(" Target character is in room: Drawing Room  ").append("\n");
    stringBuilder.append("Health 50\n");

    result = stringBuilder.toString();

    assertEquals(result, world.targetCharacterDetails());

  }




  @Test
  public void testIsPlayerSeenAttack() {

    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));


    Space spaceFive = new DrLuckySpace("Trophy Room",
            new WorldPosition(10, 21),
            new WorldPosition(15, 18));

    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(26, 13),
            new WorldPosition(27, 18));


    itemOne = new DrLuckyItem("Letter Opener", 2);
    itemTwo = new DrLuckyItem("Paint Brush", 3);
    itemThree = new DrLuckyItem("Knife", 5);
    itemFour = new DrLuckyItem("Trophy head", 4);

    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);
    spaceThree.addItemToSpace(itemThree);
    spaceFour.addItemToSpace(itemFour);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceFive, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));
    // TODO add three players

    Player playerOne = new HumanControlledPlayer("v", 3);
    Player playerTwo = new HumanControlledPlayer("a", 2);
    Player playerThree = new HumanControlledPlayer("n", 4);

    world.addHumanPlayer("v", 3, "Drawing Room");
    world.addHumanPlayer("a", 2, "Dining Hall");
    world.addHumanPlayer("b", 5, "Foyer");

    world.pickItem("Letter Opener");
    world.attackHuman("Letter Opener");

    assertEquals("Name of the target character: Lucky  Target character is in room: "
            + "Armory  \nHealth 50\n", world.targetCharacterDetails());
  }






  @Test
  public void lookAroundPlayersInCurrentSpace() {
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));


    Space spaceFive = new DrLuckySpace("Trophy Room",
            new WorldPosition(10, 21),
            new WorldPosition(15, 18));

    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(26, 13),
            new WorldPosition(27, 18));


    itemOne = new DrLuckyItem("Letter Opener", 2);
    itemTwo = new DrLuckyItem("Paint Brush", 3);
    itemThree = new DrLuckyItem("Knife", 5);
    itemFour = new DrLuckyItem("Trophy head", 4);

    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);
    spaceThree.addItemToSpace(itemThree);
    spaceFour.addItemToSpace(itemFour);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceFive, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));

    Player playerOne = new HumanControlledPlayer("v", 3);
    Player playerTwo = new HumanControlledPlayer("a", 2);

    world.addHumanPlayer("v", 3, "Drawing Room");
    world.addHumanPlayer("a", 2, "Drawing Room");

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The name of the space you are currently in: Drawing Room").append("\n");
    stringBuilder.append("Name of the other players in your room are:");
    stringBuilder.append("v").append("\n");
    stringBuilder.append("a").append("\n");
    stringBuilder.append("The items that are currently laying in the room: ").append("\n");
    stringBuilder.append("Item Information (Item Name = Paint Brush, Damage Value = 3)")
            .append("\n\n");

    stringBuilder.append("Space Info: Space Information (Space Name = Armory, ")
            .append("WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 19, ")
            .append("WorldPosition LowerRight Row = 23, WorldPosition LowerRight Column = 26, ")
            .append("Items = [Item Information (Item Name = Letter Opener, Damage Value = 2)])")
            .append("\n");
    stringBuilder.append("Players: No players in this room").append("\n");
    stringBuilder.append("The target character is in this room: Character Information ")
            .append("(Character Name = Lucky, Character Health = 50, Character is Target = true)")
            .append("\n");

    stringBuilder.append("\nSpace Info: Space Information (Space Name = Dining Hall, "
            + "WorldPosition UpperLeft Row = 12, WorldPosition UpperLeft Column = 11, "
            + "WorldPosition LowerRight Row = 21, WorldPosition LowerRight Column = 20, "
            + "Items = [Item Information (Item Name = Knife, Damage Value = 5)])").append("\n");
    stringBuilder.append("Players: No players in this room").append("\n\n");

    stringBuilder.append("Space Info: Space Information (Space Name = "
                    + "Foyer, WorldPosition UpperLeft Row = 26, WorldPosition UpperLeft Column = 13, "
                    + "WorldPosition LowerRight Row = 27, WorldPosition LowerRight Column = 18, "
                    + "Items = [Item Information (Item Name = Trophy head, Damage Value = 4)])")
            .append("\n");
    stringBuilder.append("Players: No players in this room\n");


    String result = stringBuilder.toString();


    assertEquals(result, world.lookAround());

  }


  @Test
  public void lookAroundNoPlayersInCurrentSpace() {
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));


    Space spaceFive = new DrLuckySpace("Trophy Room",
            new WorldPosition(10, 21),
            new WorldPosition(15, 18));

    spaceTwo = new DrLuckySpace("Drawing Room",
            new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(26, 13),
            new WorldPosition(27, 18));


    itemOne = new DrLuckyItem("Letter Opener", 2);
    itemTwo = new DrLuckyItem("Paint Brush", 3);
    itemThree = new DrLuckyItem("Knife", 5);
    itemFour = new DrLuckyItem("Trophy head", 4);

    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);
    spaceThree.addItemToSpace(itemThree);
    spaceFour.addItemToSpace(itemFour);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceFive, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));

    Player playerOne = new HumanControlledPlayer("v", 3);
    Player playerTwo = new HumanControlledPlayer("a", 2);

    world.addHumanPlayer("v", 3, "Drawing Room");
    world.addHumanPlayer("a", 2, "Dining Hall");


    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The name of the space you are currently in: Drawing Room").append("\n");
    stringBuilder.append("There are no players in the room other than you.").append("\n");
    stringBuilder.append("The items that are currently laying in the room: ").append("\n");
    stringBuilder.append("Item Information (Item Name = Paint Brush, Damage Value = 3)")
            .append("\n");

    stringBuilder.append("\nSpace Info: Space Information (Space Name = Armory, ")
            .append("WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 19, ")
            .append("WorldPosition LowerRight Row = 23, WorldPosition LowerRight Column = 26, ")
            .append("Items = [Item Information (Item Name = Letter Opener, Damage Value = 2)])")
            .append("\n");
    stringBuilder.append("Players: No players in this room").append("\n");
    stringBuilder.append("The target character is in this room: Character Information ")
            .append("(Character Name = Lucky, Character Health = 50, Character is Target = true)")
            .append("\n");
    stringBuilder.append("Space Info: Space Information (Space Name = Dining Hall, "
            + "WorldPosition UpperLeft Row = 12, WorldPosition UpperLeft Column = 11, "
            + "WorldPosition LowerRight Row = 21, WorldPosition LowerRight Column = 20, "
            + "Items = [Item Information (Item Name = Knife, Damage Value = 5)])").append("\n");
    stringBuilder.append("Players in this room:").append("\n\n");
    stringBuilder.append("Player Info: Human Player Information: (Player Name = a "
            + "Items in hand = [])").append("\n\n\n");

    stringBuilder.append("Space Info: Space Information (Space Name = Foyer, "
                    + "WorldPosition UpperLeft Row = 26, WorldPosition UpperLeft Column = 13,"
                    + " WorldPosition LowerRight Row = 27, WorldPosition LowerRight Column = 18, "
                    + "Items = [Item Information (Item Name = Trophy head, Damage Value = 4)])")
            .append("\n");
    stringBuilder.append("Players: No players in this room\n");

    String result = stringBuilder.toString();

    assertEquals(result, world.lookAround());

  }

  @Test
  public void lookAroundNeighboringPlayers() {
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));


    Space spaceFive = new DrLuckySpace("Trophy Room",
            new WorldPosition(10, 21),
            new WorldPosition(15, 18));

    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(26, 13),
            new WorldPosition(27, 18));


    itemOne = new DrLuckyItem("Letter Opener", 2);
    itemTwo = new DrLuckyItem("Paint Brush", 3);
    itemThree = new DrLuckyItem("Knife", 5);
    itemFour = new DrLuckyItem("Trophy head", 4);

    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);
    spaceThree.addItemToSpace(itemThree);
    spaceFour.addItemToSpace(itemFour);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceFive, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));

    Player playerOne = new HumanControlledPlayer("v", 3);
    Player playerTwo = new HumanControlledPlayer("a", 2);

    world.addHumanPlayer("v", 3, "Drawing Room");
    world.addHumanPlayer("a", 2, "Dining Hall");
    world.addHumanPlayer("b", 2, "Foyer");


    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The name of the space you are currently in: Drawing Room").append("\n");
    stringBuilder.append("There are no players in the room other than you.").append("\n");
    stringBuilder.append("The items that are currently laying in the room: ").append("\n");
    stringBuilder.append("Item Information (Item Name = Paint Brush, Damage Value = 3)")
            .append("\n");

    stringBuilder.append("\nSpace Info: Space Information (Space Name = Armory, ")
            .append("WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 19, ")
            .append("WorldPosition LowerRight Row = 23, WorldPosition LowerRight Column = 26, ")
            .append("Items = [Item Information (Item Name = Letter Opener, Damage Value = 2)])")
            .append("\n");
    stringBuilder.append("Players: No players in this room").append("\n");
    stringBuilder.append("The target character is in this room: Character Information ")
            .append("(Character Name = Lucky, Character Health = 50, Character is Target = true)")
            .append("\n");

    stringBuilder.append("Space Info: Space Information (Space Name = Dining Hall, "
            + "WorldPosition UpperLeft Row = 12, WorldPosition UpperLeft Column = 11, "
            + "WorldPosition LowerRight Row = 21, WorldPosition LowerRight Column = 20, "
            + "Items = [Item Information (Item Name = Knife, Damage Value = 5)])").append("\n");
    stringBuilder.append("Players in this room:").append("\n\n");
    stringBuilder.append("Player Info: Human Player Information: (Player Name = a "
            + "Items in hand = [])").append("\n\n");

    stringBuilder.append("Space Info: Space Information (Space Name = Foyer, "
                    + "WorldPosition UpperLeft Row = 26, WorldPosition UpperLeft Column = 13,"
                    + " WorldPosition LowerRight Row = 27, WorldPosition LowerRight Column = 18, "
                    + "Items = [Item Information (Item Name = Trophy head, Damage Value = 4)])")
            .append("\n");

    stringBuilder.append("Players in this room:").append("\n\n");
    stringBuilder.append("Player Info: Human Player Information: ");
    stringBuilder.append("(Player Name = b Items in hand = [])\n\n");

    String result = stringBuilder.toString();

    assertEquals(result, world.lookAround());


  }


  @Test
  public void lookAroundNoPlayersInNeighbors() {
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));


    Space spaceFive = new DrLuckySpace("Trophy Room",
            new WorldPosition(10, 21),
            new WorldPosition(15, 18));

    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(30, 35),
            new WorldPosition(28, 35));


    itemOne = new DrLuckyItem("Letter Opener", 2);
    itemTwo = new DrLuckyItem("Paint Brush", 3);
    itemThree = new DrLuckyItem("Knife", 5);
    itemFour = new DrLuckyItem("Trophy head", 4);

    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);
    spaceThree.addItemToSpace(itemThree);
    spaceFour.addItemToSpace(itemFour);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceFive, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));

    Player playerOne = new HumanControlledPlayer("v", 3);
    Player playerTwo = new HumanControlledPlayer("a", 2);

    world.addHumanPlayer("v", 3, "Drawing Room");
    world.addHumanPlayer("a", 3, "Foyer");

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The name of the space you are currently in: Drawing Room").append("\n");
    stringBuilder.append("There are no players in the room other than you.").append("\n");
    stringBuilder.append("The items that are currently laying in the room: ").append("\n");
    stringBuilder.append("Item Information (Item Name = Paint Brush, Damage Value = 3)")
            .append("\n");

    stringBuilder.append("\nSpace Info: Space Information (Space Name = Armory, ")
            .append("WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 19, ")
            .append("WorldPosition LowerRight Row = 23, WorldPosition LowerRight Column = 26, ")
            .append("Items = [Item Information (Item Name = Letter Opener, Damage Value = 2)])")
            .append("\n");

    stringBuilder.append("Players: No players in this room").append("\n");
    stringBuilder.append("The target character is in this room: Character Information ")
            .append("(Character Name = Lucky, Character Health = 50, Character is Target = true)")
            .append("\n");

    stringBuilder.append("\nSpace Info: Space Information (Space Name = Dining Hall, "
            + "WorldPosition UpperLeft Row = 12, WorldPosition UpperLeft Column = 11, "
            + "WorldPosition LowerRight Row = 21, WorldPosition LowerRight Column = 20, "
            + "Items = [Item Information (Item Name = Knife, Damage Value = 5)])").append("\n");
    stringBuilder.append("Players: No players in this room\n");

    String result = stringBuilder.toString();
    assertEquals(result, world.lookAround());


  }

  @Test
  public void lookAroundItemsInCurrentSpace() {

    //neighboring spaces has items, one space has no items
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));

    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(26, 13),
            new WorldPosition(27, 18));


    itemOne = new DrLuckyItem("Letter Opener", 2);
    itemTwo = new DrLuckyItem("Paint Brush", 3);
    itemThree = new DrLuckyItem("Knife", 5);

    spaceOne.addItemToSpace(itemOne);
    spaceTwo.addItemToSpace(itemTwo);
    spaceThree.addItemToSpace(itemThree);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));


    world.addHumanPlayer("v", 3, "Drawing Room");
    world.addHumanPlayer("a", 2, "Dining Hall");
    world.addHumanPlayer("b", 2, "Foyer");

    StringBuilder sb = new StringBuilder();
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The name of the space you are currently in: Drawing Room").append("\n");
    stringBuilder.append("There are no players in the room other than you.").append("\n");
    stringBuilder.append("The items that are currently laying in the room: ").append("\n");
    stringBuilder.append("Item Information (Item Name = Paint Brush, Damage Value = 3)")
            .append("\n");


    stringBuilder.append("\nSpace Info: Space Information (Space Name = Armory, ")
            .append("WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 19, ")
            .append("WorldPosition LowerRight Row = 23, WorldPosition LowerRight Column = 26, ")
            .append("Items = [Item Information (Item Name = Letter Opener, Damage Value = 2)])")
            .append("\n");
    stringBuilder.append("Players: No players in this room").append("\n");
    stringBuilder.append("The target character is in this room: Character Information ")
            .append("(Character Name = Lucky, Character Health = 50, Character is Target = true)")
            .append("\n");



    stringBuilder.append("Space Info: Space Information (Space Name = Dining Hall, "
            + "WorldPosition UpperLeft Row = 12, WorldPosition UpperLeft Column = 11, "
            + "WorldPosition LowerRight Row = 21, WorldPosition LowerRight Column = 20, "
            + "Items = [Item Information (Item Name = Knife, Damage Value = 5)])").append("\n");
    stringBuilder.append("Players in this room:").append("\n\n");
    stringBuilder.append("Player Info: Human Player Information: (Player Name = a "
            + "Items in hand = [])").append("\n\n");

    stringBuilder.append("Space Info: Space Information (Space Name = Foyer, "
            + "WorldPosition UpperLeft Row = 26, WorldPosition UpperLeft Column = 13, "
            + "WorldPosition LowerRight Row = 27, WorldPosition LowerRight Column = 18, "
            + "Items = No Items)").append("\n");
    stringBuilder.append("Players in this room:").append("\n\n");
    stringBuilder.append("Player Info: Human Player Information: (Player Name = b "
            + "Items in hand = [])\n\n");

    String result = stringBuilder.toString();
    assertEquals(result, world.lookAround());


  }

  @Test
  public void lookAroundNoItemsInCurrentSpace() {
    //neighboring spaces has items, one space has no items
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));

    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(26, 13),
            new WorldPosition(27, 18));


    itemOne = new DrLuckyItem("Letter Opener", 2);
    itemThree = new DrLuckyItem("Knife", 5);

    spaceOne.addItemToSpace(itemOne);
    spaceThree.addItemToSpace(itemThree);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));


    world.addHumanPlayer("v", 3, "Drawing Room");
    world.addHumanPlayer("a", 2, "Dining Hall");
    world.addHumanPlayer("b", 2, "Foyer");

    StringBuilder sb = new StringBuilder();
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The name of the space you are currently in: Drawing Room").append("\n");
    stringBuilder.append("There are no players in the room other than you.").append("\n");
    stringBuilder.append("No items in your current room").append("\n");


    stringBuilder.append("\nSpace Info: Space Information (Space Name = Armory, ")
            .append("WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 19, ")
            .append("WorldPosition LowerRight Row = 23, WorldPosition LowerRight Column = 26, ")
            .append("Items = [Item Information (Item Name = Letter Opener, Damage Value = 2)])")
            .append("\n");
    stringBuilder.append("Players: No players in this room").append("\n");
    stringBuilder.append("The target character is in this room: Character Information ")
            .append("(Character Name = Lucky, Character Health = 50, Character is Target = true)")
            .append("\n");

    stringBuilder.append("Space Info: Space Information (Space Name = Dining Hall, "
            + "WorldPosition UpperLeft Row = 12, WorldPosition UpperLeft Column = 11, "
            + "WorldPosition LowerRight Row = 21, WorldPosition LowerRight Column = 20, "
            + "Items = [Item Information (Item Name = Knife, Damage Value = 5)])").append("\n");
    stringBuilder.append("Players in this room:").append("\n\n");
    stringBuilder.append("Player Info: Human Player Information: (Player Name = a "
            + "Items in hand = [])").append("\n\n");

    stringBuilder.append("Space Info: Space Information (Space Name = Foyer, "
            + "WorldPosition UpperLeft Row = 26, WorldPosition UpperLeft Column = 13, "
            + "WorldPosition LowerRight Row = 27, WorldPosition LowerRight Column = 18, "
            + "Items = No Items)").append("\n");
    stringBuilder.append("Players in this room:").append("\n\n");
    stringBuilder.append("Player Info: Human Player Information: (Player Name = b "
            + "Items in hand = [])\n\n");

    String result = stringBuilder.toString();
    assertEquals(result, world.lookAround());
  }

  @Test
  public void lookAroundTargetCharacter() {
    //neighboring spaces has items, one space has no items
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));

    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(26, 13),
            new WorldPosition(27, 18));


    itemOne = new DrLuckyItem("Letter Opener", 2);
    itemThree = new DrLuckyItem("Knife", 5);

    spaceOne.addItemToSpace(itemOne);
    spaceThree.addItemToSpace(itemThree);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));


    world.addHumanPlayer("v", 3, "Drawing Room");


    world.move("Dining Hall");
    world.moveTargetCharacter();
    world.nextTurn();

    world.pickItem("Knife");
    world.moveTargetCharacter();
    world.nextTurn();

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The name of the space you are currently in: Dining Hall").append("\n");
    stringBuilder.append("There are no players in the room other than you.").append("\n");
    stringBuilder.append("No items in your current room").append("\n");
    stringBuilder.append("The target character is in this room: Character "
            + "Information (Character Name = Lucky, Character Health = 50, "
            + "Character is Target = true)").append("\n\n");

    stringBuilder.append("Space Info: Space Information (Space Name = Armory, ")
            .append("WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 19, ")
            .append("WorldPosition LowerRight Row = 23, WorldPosition LowerRight Column = 26, ")
            .append("Items = [Item Information (Item Name = Letter Opener, Damage Value = 2)])")
            .append("\n");
    stringBuilder.append("Players: No players in this room").append("\n");


    stringBuilder.append("\nSpace Info: Space Information (Space Name = Drawing Room, "
            + "WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 13, "
            + "WorldPosition LowerRight Row = 25, WorldPosition LowerRight Column = 18, "
            + "Items = No Items)").append("\n");
    stringBuilder.append("Players: No players in this room\n");

    String result = stringBuilder.toString();
    assertEquals(result.toString(), world.lookAround());
  }


  @Test
  public void lookAroundTargetCharacterNeighbor() {
    //neighboring spaces has items, one space has no items
    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
            new WorldPosition(23, 26));

    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
            new WorldPosition(25, 18));

    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
            new WorldPosition(21, 20));

    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(26, 13),
            new WorldPosition(27, 18));


    itemOne = new DrLuckyItem("Letter Opener", 2);
    itemThree = new DrLuckyItem("Knife", 5);

    spaceOne.addItemToSpace(itemOne);
    spaceThree.addItemToSpace(itemThree);

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));


    world.addHumanPlayer("v", 3, "Drawing Room");


    world.move("Dining Hall");
    world.moveTargetCharacter();
    world.nextTurn();


    StringBuilder stringBuilder = new StringBuilder();




    stringBuilder.append("The name of the space you are currently in: Dining Hall").append("\n");
    stringBuilder.append("There are no players in the room other than you.").append("\n");
    stringBuilder.append("The items that are currently laying in the room: ").append("\n");
    stringBuilder.append("Item Information (Item Name = Knife, Damage Value = 5)").append("\n\n");

    stringBuilder.append("Space Info: Space Information (Space Name = Armory, ")
            .append("WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 19, ")
            .append("WorldPosition LowerRight Row = 23, WorldPosition LowerRight Column = 26, ")
            .append("Items = [Item Information (Item Name = Letter Opener, Damage Value = 2)])")
            .append("\n");
    stringBuilder.append("Players: No players in this room").append("\n");

    stringBuilder.append("\nSpace Info: Space Information (Space Name = Drawing Room, "
            + "WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 13, "
            + "WorldPosition LowerRight Row = 25, WorldPosition LowerRight Column = 18, "
            + "Items = No Items)").append("\n");
    stringBuilder.append("Players: No players in this room").append("\n");

    stringBuilder.append("The target character is in this room: Character Information ("
            + "Character Name = Lucky, Character Health = 50, Character is Target = true)\n");

    String result = stringBuilder.toString();

    assertEquals(result.toString(), world.lookAround());
  }



//  @Test
//  public void testComputerPickedMaxItemValue() {
//    spaceOne = new DrLuckySpace("Armory", new WorldPosition(22, 19),
//            new WorldPosition(23, 26));
//
//    spaceTwo = new DrLuckySpace("Drawing Room", new WorldPosition(22, 13),
//            new WorldPosition(25, 18));
//
//    spaceThree = new DrLuckySpace("Dining Hall", new WorldPosition(12, 11),
//            new WorldPosition(21, 20));
//
//    spaceFour = new DrLuckySpace("Foyer", new WorldPosition(2, 8),
//            new WorldPosition(9, 10));
//
//
//    itemOne = new DrLuckyItem("Letter Opener", 2);
//    itemTwo = new DrLuckyItem("Fork", 2);
//    itemThree = new DrLuckyItem("Knife", 5);
//
//    spaceOne.addItemToSpace(itemOne);
//    spaceTwo.addItemToSpace(itemThree);
//    spaceTwo.addItemToSpace(itemTwo);
//    spaceFour.addItemToSpace(itemOne);
//
//
//    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
//            new GameCharacter(50,
//                    "Lucky", true),
//            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
//            new CustomRandom(1), new Pet("Dr Fortune Cat"));
//
//
//    world.addHumanPlayer("v", 3, "Foyer");
//    world.addComputerPlayer();
//    world.lookAround();
//    world.moveTargetCharacter();
//    world.nextTurn();
//
//
//    Player cmPlayer = world.getCurrentPlayer();
//    cmPlayer.pickItem(itemThree);
//    cmPlayer.pickItem(itemTwo);
//
//    world.simulateAction(world.getCurrentPlayer());
//    world.moveTargetCharacter();
//    world.nextTurn();
//
//
//    List<Item> itemList = world.getEvidenceList();
//    assertTrue(itemThree.equals(itemList.get(0)));
//
//  }


}

