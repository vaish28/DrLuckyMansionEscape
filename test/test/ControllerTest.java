package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import killdrluckygame.*;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a class  for testing the controller in isolation. This tests the commands of the
 * controller. This will test move, pickitem, lookaround, add human and computer player.
 * Also display information about space and player.
 */
public class ControllerTest {

  private Readable in;
  private StringBuilder modelLog;
  private Appendable out;

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
   * This sets up the objects required for the testing enviornment.
   */
  @Before
  public void setUp() {
    out = new StringBuffer();
    modelLog = new StringBuilder();
    itemOne = new DrLuckyItem("Sharp Knife", 3);
    itemTwo = new DrLuckyItem("Blade", 5);


    spaceOne = new DrLuckySpace("Garden", new WorldPosition(1, 2),
            new WorldPosition(4, 4));

    spaceTwo = new DrLuckySpace("Jungle", new WorldPosition(2, 2),
            new WorldPosition(5, 4));

    spaceThree = new DrLuckySpace("Distant land", new WorldPosition(3, 2),
            new WorldPosition(6, 4));
  }

  /**
   * Tests the default or wrong choice of command condition.
   */
  @Test
  public void testDefault() {
    StringReader in = new StringReader("defaultcheck\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {

      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {
        return new DrLuckySpace("Armory", new WorldPosition(23, 25),
                new WorldPosition(12, 13));
      }

    };
    // Act
    systemUnderTest.gamePlay(model, 5);

    // Assert
    assertTrue(out.toString().contains("Enter correct choice from the options to play!"));
  }

  /**
   * Tests the command of add human player.
   */
  @Test
  public void testAddHumanPlayer() {

    StringReader in = new StringReader("human\nNeha\n6\nArmory\nq");
    GameController systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {
      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {
        return new DrLuckySpace("Armory", new WorldPosition(23, 25),
                new WorldPosition(12, 13));
      }

      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Armory", new WorldPosition(23, 25),
                new WorldPosition(12, 13))));
      }
    };

    // Act
    systemUnderTest.gamePlay(model, 5);
    assertTrue(modelLog.toString().contains("Adding human playerNeha6Armory"));
    // Assert
    assertTrue(out.toString().contains("Player has been added successfully"));
  }

  /**
   * Tests the command of add computer player.
   */
  @Test
  public void testAddComputerPlayer() {
    StringReader in = new StringReader("human\nNeha\n6\nArmory\ncomputer\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {

      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {
        return new DrLuckySpace("Armory", new WorldPosition(23, 25),
                new WorldPosition(12, 13));
      }
      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Armory", new WorldPosition(23, 25),
                new WorldPosition(12, 13))));
      }

    };

    // Act
    systemUnderTest.gamePlay(model, 5);
    assertTrue(modelLog.toString().contains("Adding a computer player"));
    // Assert
    assertTrue(out.toString().contains("Now adding a computer controlled player!\nPlayer "
            + "has been added successfully"));
  }

  /**
   * Tests the command of moving human player.
   */
  @Test
  public void testMoveHumanCommand() {
    StringReader in = new StringReader("human\nNeha\n6\nArmory\nmove\nDrawing Room\nplayerinfo\n"
            + "Neha\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {

      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public boolean isContainsNeighbor(String spaceName, List<String> neighNames) {
        if (("Drawing Room").equals(spaceName)) {
          return true;
        }
        return false;
      }

      @Override
      public Player getPlayerByPlayerName(String playerName) {
        return this.getCurrentPlayer();
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }

      @Override
      public List<String> getNeighborsStrings() {
        modelLog.append("getting neighbors");
        return new ArrayList<>(Arrays.asList("Drawing Room", "Billiard Room"));

      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {
        return new DrLuckySpace("Armory", new WorldPosition(23, 25),
                new WorldPosition(12, 13));
      }

      @Override
      public boolean move(String spaceName) {
        if (("Drawing Room").equals(spaceName)) {
          modelLog.append("Moving player successfully");
          return true;
        }
        return true;

      }

      @Override
      public String playerDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Human Player Information: (Player Name = Aishwarya Items in hand = [])\n");
        sb.append("\nPlayer space is: ")
                .append(new DrLuckySpace("Armory", new WorldPosition(23, 25),
                        new WorldPosition(12, 13)));
        return sb.toString();
      }

      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Armory", new WorldPosition(23, 25),
                new WorldPosition(12, 13))));
      }
    };

    // Act
    systemUnderTest.gamePlay(model, 5);

    assertTrue(modelLog.toString().contains("getting neighborsMoving player successfully"));
    // Assert
    assertTrue(out.toString().contains("Player moved successfully"));
  }


  /**
   * Tests the command of displaying the space information.
   */
  @Test
  public void testDisplaySpaceInfo() {
    StringReader in = new StringReader("human\nNeha\n6\nArmory\nspaceinfo\nArmory\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {

      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }

      @Override
      public List<String> getNeighborsStrings() {
        return new ArrayList<>(Arrays.asList("Drawing Room", "Billiard Room"));
      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {
        return new DrLuckySpace("Armory", new WorldPosition(23, 25),
                new WorldPosition(12, 13));
      }

      @Override
      public String getSpaceInfoWithPlayer(String spaceName) {
        modelLog.append("Space Information (Space Name = Armory");
        return modelLog.toString();
      }

      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Armory",
                new WorldPosition(23, 25),
                new WorldPosition(12, 13))));
      }
    };
    // Act
    systemUnderTest.gamePlay(model, 5);

    assertTrue(modelLog.toString().contains("Space Information (Space Name = Armory"));
    // Assert
    assertTrue(out.toString().contains("Space Information (Space Name = Armory"));
  }


  /**
   * Tests the command of displaying the graphical representation.
   */
  @Test
  public void testGraphicalRepresentation() {
    StringReader in = new StringReader("worldview\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {

      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }


    };
    // Act
    systemUnderTest.gamePlay(model, 5);

    assertTrue(modelLog.toString().contains("Creating the graphical image"));

    assertTrue(out.toString().contains("Displaying the world now!!\nFollow the "
            + "graphical representation of the world of space information"));
  }

  /**
   * Tests the command of picking an item in space.
   */
  @Test
  public void testPickItem() {
    StringReader in = new StringReader("human\nAishwarya\n10\nDrawing Room"
            + "\npickitem\nRevolver\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {

      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {
        return new DrLuckySpace("Drawing Room", new WorldPosition(23, 24),
                new WorldPosition(25, 26));
      }

      @Override
      public Player getCurrentPlayer() {
        Player player = new HumanControlledPlayer("Aishwarya", 10);
        Space space = new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26));
        space.addItemToSpace(new DrLuckyItem("Revolver", 3));
        addMappingOfSpaceAndPlayer(space, player);
        return player;
      }


      @Override
      public Space getCurrentPlayerSpace(Player player) {
        Space space = new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26));
        space.addItemToSpace(new DrLuckyItem("Revolver", 3));
        return space;
      }

      @Override
      public boolean checkSpaceContainsItemsToPick(Space space) {
        return true;
      }

      @Override
      public boolean pickItem(String itemName) {
        modelLog.append("Picking up an item!");
        return true;
      }

      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26))));
      }

    };
    // Act
    systemUnderTest.gamePlay(model, 5);
    assertTrue(modelLog.toString().contains("Picking up an item!"));
    // Assert
    assertTrue(out.toString().contains("Item picked successfully"));
  }

  /**
   * Tests the command of looking around in different spaces.
   */
  @Test
  public void testLookAround() {
    StringReader in = new StringReader("human\nAishwarya\n10\nDrawing Room\nlookaround\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {

      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {
        return new DrLuckySpace("Drawing Room", new WorldPosition(23, 24),
                new WorldPosition(25, 26));
      }

      @Override
      public Player getCurrentPlayer() {
        Player player = new HumanControlledPlayer("Aishwarya", 10);
        Space space = new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26));
        space.addItemToSpace(new DrLuckyItem("Revolver", 3));
        addMappingOfSpaceAndPlayer(space, player);
        return player;
      }

      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26))));
      }

    };

    // Act
    systemUnderTest.gamePlay(model, 5);

    assertTrue(modelLog.toString().contains("Looking around for neighbors"));
    // Assert
    assertTrue(out.toString().contains("Looking around neighbours"));

  }


  /**
   * Tests the command of displaying the player information.
   */
  @Test
  public void testDisplayPlayerInfo() {
    StringReader in = new StringReader("human\nAishwarya\n10\nDrawing Room"
            + "\nplayerinfo\nAishwarya\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {

      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {
        return new DrLuckySpace("Drawing Room", new WorldPosition(23, 24),
                new WorldPosition(25, 26));
      }


      @Override
      public Player getCurrentPlayer() {
        Player player = new HumanControlledPlayer("Aishwarya", 10);
        Space space = new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26));
        space.addItemToSpace(new DrLuckyItem("Revolver", 3));
        addMappingOfSpaceAndPlayer(space, player);
        return player;
      }

      @Override
      public Player getPlayerByPlayerName(String playerName) {
        return new HumanControlledPlayer("Aishwarya", 10);
      }

      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26))));
      }

    };

    // Act
    systemUnderTest.gamePlay(model, 5);
    assertTrue(modelLog.toString().contains("Getting player description from username!"));
    // Assert
    assertTrue(out.toString().contains("Which player information do you want to want to "
            + "view? Enter the username of the player "));

  }


  @Test
  public void testTargetDetailsMockModel() {
    StringReader in = new StringReader("q");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {
      @Override
      public String playerDescription() {
        modelLog.append("getting current player description");
        return modelLog.toString();
      }

      @Override
      public String targetCharacterDetails() {
        modelLog.append("Getting target character details");
        modelLog.append("Name of the target character: Doctor Lucky   "
                + "Target character is in room: ");
        return modelLog.toString();
      }

      @Override
      public boolean isToPromptForInput() {
        return true;
      }
    };
    systemUnderTest.gamePlay(model, 5);

    // Assert

    assertTrue(modelLog.toString().contains("Getting target character details"));

    assertTrue(modelLog.toString().contains("Name of the target character: Doctor Lucky   "
            + "Target character is in room: "));

  }


  @Test
  public void testTargetDetailsModel() {
    StringReader in = new StringReader("q");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());

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
    World model = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));


    systemUnderTest.gamePlay(model, 5);


    assertTrue(out.toString().contains("Name of the target character: Lucky  Target character is "
            + "in room: Armory  \n"
            + "Health 50\n"));

  }


  @Test
  public void testPlayerDetailsMockModel() {
    StringReader in = new StringReader("human\nV\n3\nBilliard Room\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {
      @Override
      public String playerDescription() {
        modelLog.append("getting current player description");
        return modelLog.toString();
      }

      @Override
      public String targetCharacterDetails() {
        modelLog.append("Getting target character details");
        modelLog.append("Name of the target character: Doctor Lucky   "
                + "Target character is in room: ");
        return modelLog.toString();
      }

      @Override
      public String displayCurrentPlayerInfo() {
        modelLog.append("Getting details of the current player");
        modelLog.append("Name of the current player: v Space of this player is ");
        return modelLog.toString();
      }

      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {

        return new DrLuckySpace("Billiard Room", new WorldPosition(23, 22),
                new WorldPosition(15, 16));
      }

      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26))));
      }

    };
    systemUnderTest.gamePlay(model, 5);

    // Assert

    assertTrue(out.toString().contains("Current Player Details are: "));

    assertTrue(modelLog.toString().contains("Getting details of the current player"));
    assertTrue(modelLog.toString().contains("Name of the current player: v Space of this player "
            + "is "));

  }


  @Test
  public void testPlayerDetailsModel() {
    StringReader in = new StringReader("human\nV\n\n3\nBilliard Room\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());

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
    World model = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50,
                    "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(0));
    systemUnderTest.gamePlay(model, 5);


    assertTrue(out.toString().contains("Current Player Details are: \n"
            + "Current player name: V  Current space name: Billiard Room"));

  }



  @Test
  public void testMoveAttack() {
    StringReader in = new StringReader("human\nAishwarya\n10\nDrawing Room\nattack\nKnife\nq");
    GameControllerInterface systemUnderTest = new GameController(in, out, new CustomRandom());
    World model = new MockWorldModel(modelLog, 0, true) {
      @Override
      public boolean isToPromptForInput() {
        return true;
      }

      @Override
      public List<Player> getPlayers() {
        return new ArrayList<>(Arrays.asList(new
                HumanControlledPlayer("aaai", 10)));
      }

      @Override
      public Space getSpaceFromSpaceName(String spaceName) {
        return new DrLuckySpace("Drawing Room", new WorldPosition(23, 24),
                new WorldPosition(25, 26));
      }


      @Override
      public Player getCurrentPlayer() {
        Player player = new HumanControlledPlayer("Aishwarya", 10);
        Space space = new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26));
        space.addItemToSpace(new DrLuckyItem("Revolver", 3));
        addMappingOfSpaceAndPlayer(space, player);
        return player;
      }

      @Override
      public Player getPlayerByPlayerName(String playerName) {
        return new HumanControlledPlayer("Aishwarya", 10);
      }

      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Drawing Room",
                new WorldPosition(23, 24),
                new WorldPosition(25, 26))));
      }


    };
    systemUnderTest.gamePlay(model, 5);
    assertTrue(modelLog.toString().contains("Attacking target"));
    assertTrue(out.toString().contains("No item to pick up hence poking!"
            + "Attack successful!"));
  }

  /**
   * Testing the behaviour of computer for poking.
   */
  @Test
  public void testComputerPlayerAttackPoke() {
    // Set the predictable numbers
    int[] predictableNumbers = {0};
    // Create the CustomRandom object with predictable numbers
    CustomRandomInterface random = new CustomRandom(predictableNumbers[0]);


    GameControllerInterface systemUnderTest = new GameController(in, out, random);


    // Create test DrLuckySpaces
    DrLuckySpace spaceOne = new DrLuckySpace("Billiard Room",
            new WorldPosition(16, 21),
            new WorldPosition(21, 28));


    // Create a test DrLuckyWorld
    DrLuckyWorld world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne)),
            random);

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);


    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace(world);
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();

    Space space = world.getCurrentSpaceTargetIsIn();

    // Assert
    assertEquals(1, player.getMaxItemsCarry());
    assertEquals(spaceOne, world.getCurrentPlayerSpace(world.getCurrentPlayer()));
    assertTrue(spaceOne.toString().equals(world.getCurrentSpaceTargetIsIn().toString()));

    systemUnderTest.simulateAction(world.getCurrentPlayer(), world);
    StringBuilder expected = new StringBuilder();
    expected.append("Name of the target character: Lucky  Target character is in room: "
                    + "Billiard Room  ")
            .append("\nHealth 49\n");

    assertEquals(expected.toString(), world.targetCharacterDetails());

  }

  @Test
  public void testComputerPlayerAttackItem() {
    // Set the predictable numbers
    int[] predictableNumbers = {0};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);

    GameControllerInterface systemUnderTest = new GameController(in, out, random);


    // Create test DrLuckySpaces
    DrLuckySpace spaceOne = new DrLuckySpace("Billiard Room",
            new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    spaceOne.addItemToSpace(itemOne);


    // Create a test DrLuckyWorld
    DrLuckyWorld world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne)),
            random);

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);

    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace(world);
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();
    player.pickItem(itemOne);


    // Assert

    assertEquals(1, player.getMaxItemsCarry());
    assertEquals(spaceOne, world.getCurrentPlayerSpace(world.getCurrentPlayer()));
    assertTrue(spaceOne.toString().equals(world.getCurrentSpaceTargetIsIn().toString()));

    systemUnderTest.simulateAction(world.getCurrentPlayer(), world);

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Name of the target character: Lucky  ");
    stringBuilder.append("Target character is in room: Billiard Room  ").append("\n");
    stringBuilder.append("Health 47\n");

    assertEquals(stringBuilder.toString(), world.targetCharacterDetails());


  }


  @Test
  public void testSimulateActionAttack() {

    // Set the predictable numbers
    int[] predictableNumbers = {0};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);
    DrLuckySpace spaceOne = new DrLuckySpace("Billiard Room",
            new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    GameControllerInterface systemUnderTest = new GameController(in, out, random);
    // Create a test DrLuckyWorld and pass the CustomRandomStub object to the constructor
    DrLuckyWorld world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne)),
            new CustomRandom(0));

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);

    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace(world);
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();

    // Perform simulateAction
    systemUnderTest.simulateAction(player, world);

    // Assert the expected action based on the predictable numbers
    assertEquals(ActionType.ATTACK, world.getPrevActionOfComputer());

  }

  @Test
  public void testSimulateActionMove() {

    int[] predictableNumbers = {1};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);

    GameControllerInterface systemUnderTest = new GameController(in, out, random);
    // Create test DrLuckySpaces
    DrLuckySpace spaceOne = new DrLuckySpace("Billiard Room",
            new WorldPosition(22, 19),
            new WorldPosition(23, 26));
    DrLuckySpace spaceTwo = new DrLuckySpace("Library",
            new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    // Create a test DrLuckyWorld and pass the CustomRandomStub object to the constructor
    DrLuckyWorld world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo)),
            new CustomRandom(1));

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);
    world.addSpaceToSpaceList(spaceTwo);

    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace(world);
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);


    // Get the current player
    Player player = world.getCurrentPlayer();

    // Perform simulateAction
    systemUnderTest.simulateAction(player, world);

    // Assert the expected action based on the predictable numbers
    assertEquals(ActionType.MOVE, world.getPrevActionOfComputer());

  }


  @Test
  public void testSimulateActionLookAround() {

    int[] predictableNumbers = {3};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);

    GameControllerInterface systemUnderTest = new GameController(in, out, random);
    // Create test DrLuckySpaces
    DrLuckySpace spaceOne = new DrLuckySpace("Billiard Room",
            new WorldPosition(22, 19),
            new WorldPosition(23, 26));
    DrLuckySpace spaceTwo = new DrLuckySpace("Library",
            new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    DrLuckySpace spaceThree = new DrLuckySpace("Dining Hall",
            new WorldPosition(15, 22),
            new WorldPosition(17, 27));


    DrLuckySpace spaceFour = new DrLuckySpace("Foyer",
            new WorldPosition(14, 20),
            new WorldPosition(13, 25));

    // Create a test DrLuckyWorld and pass the CustomRandomStub object to the constructor
    DrLuckyWorld world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree, spaceFour)),
            new CustomRandom(3));

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);
    world.addSpaceToSpaceList(spaceTwo);
    world.addSpaceToSpaceList(spaceThree);
    world.addSpaceToSpaceList(spaceFour);


    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace(world);
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);


    // Get the current player
    Player player = world.getCurrentPlayer();


    // Perform simulateAction again
    systemUnderTest.simulateAction(player, world);

    // Assert the expected action based on the predictable numbers
    assertEquals(ActionType.LOOK_AROUND, world.getPrevActionOfComputer());
  }

  @Test
  public void testSimulateActionPick() {

    int[] predictableNumbers = {2};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);

    GameControllerInterface systemUnderTest = new GameController(in, out, random);
    // Create test DrLuckySpaces
    DrLuckySpace spaceOne = new DrLuckySpace("Billiard Room",
            new WorldPosition(22, 19),
            new WorldPosition(23, 26));
    DrLuckySpace spaceTwo = new DrLuckySpace("Library",
            new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    DrLuckySpace spaceThree = new DrLuckySpace("Dining Hall",
            new WorldPosition(15, 22),
            new WorldPosition(17, 27));

    // Create a test DrLuckyWorld and pass the CustomRandomStub object to the constructor
    DrLuckyWorld world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree)),
            new CustomRandom(2));

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);
    world.addSpaceToSpaceList(spaceTwo);
    world.addSpaceToSpaceList(spaceThree);


    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace(world);
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();


    // Perform simulateAction again
    systemUnderTest.simulateAction(player, world);

    // Assert the expected action based on the predictable numbers
    assertEquals(ActionType.PICKUP_ITEM, world.getPrevActionOfComputer());

  }

  /**
   * Tests the details of the computer player.
   */
  @Test
  public void testComputerPlayer() {

    int[] predictableNumbers = {0};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);
    GameControllerInterface systemUnderTest = new GameController(in, out, random);
    spaceOne = new DrLuckySpace("Billiard Room", new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne)),
            new CustomRandom(0));
    world.addSpaceToSpaceList(spaceOne);


    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace(world);
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    Player player = world.getCurrentPlayer();


    assertEquals(1, player.getMaxItemsCarry());
    assertTrue(spaceOne.equals(world.getCurrentPlayerSpace(player)));
  }

  /**
   * Testing the behaviour of computer player with predictable numbers.
   */
  @Test
  public void testComputerPlayerWithPredictableNumbers() {


    int[] predictableNumbers = {2};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);
    GameControllerInterface systemUnderTest = new GameController(in, out, random);

    // Create test DrLuckySpaces
    DrLuckySpace spaceOne = new DrLuckySpace("Billiard Room",
            new WorldPosition(16, 21),
            new WorldPosition(21, 28));
    DrLuckySpace spaceTwo = new DrLuckySpace("Library",
            new WorldPosition(10, 15),
            new WorldPosition(30, 35));
    DrLuckySpace spaceThree = new DrLuckySpace("Kitchen",
            new WorldPosition(5, 8),
            new WorldPosition(15, 20));

    // Create a test DrLuckyWorld
    DrLuckyWorld world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree)),
            random);

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);
    world.addSpaceToSpaceList(spaceTwo);
    world.addSpaceToSpaceList(spaceThree);


    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace(world);
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();

    // Assert
    assertEquals(3, player.getMaxItemsCarry());
    assertTrue(spaceThree.equals(world.getCurrentPlayerSpace(player)));

  }


}
