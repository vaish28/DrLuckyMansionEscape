package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import killdrluckygame.ActionType;
import killdrluckygame.ControllerGuiImpl;
import killdrluckygame.ControllerGuiInterface;
import killdrluckygame.CustomRandom;
import killdrluckygame.CustomRandomInterface;
import killdrluckygame.DrLuckyItem;
import killdrluckygame.DrLuckySpace;
import killdrluckygame.DrLuckyWorld;
import killdrluckygame.GameCharacter;
import killdrluckygame.HumanControlledPlayer;
import killdrluckygame.Item;
import killdrluckygame.Player;
import killdrluckygame.ReadOnlyWorldModel;
import killdrluckygame.Space;
import killdrluckygame.World;
import killdrluckygame.WorldPosition;
import killdrluckygame.view.WorldViewInterface;
import org.junit.Before;
import org.junit.Test;


/**
 * A test class for the ControllerGuiImpl class, testing its interaction with the game model and
 * view.
 */
public class ControllerViewTest {

  private StringBuilder modelLog;

  private StringBuilder viewLog;

  private Appendable out;

  private Item itemOne;
  private Item itemTwo;
  private Item itemThree;
  private Space spaceOne;
  private Space spaceTwo;
  private Space spaceThree;
  private Space spaceFour;
  private Item itemFour;

  /**
   * Sets up the initial state and common objects for testing.
   */
  @Before
  public void setUp() {
    out = new StringBuffer();
    modelLog = new StringBuilder();
    viewLog = new StringBuilder();

    itemOne = new DrLuckyItem("Sharp Knife", 3);
    itemTwo = new DrLuckyItem("Blade", 5);


    spaceOne = new DrLuckySpace("Garden", new WorldPosition(1, 2),
            new WorldPosition(4, 4));

    spaceTwo = new DrLuckySpace("Jungle", new WorldPosition(2, 2),
            new WorldPosition(5, 4));

    spaceThree = new DrLuckySpace("Distant land", new WorldPosition(3, 2),
            new WorldPosition(6, 4));

  }

  @Test
  public void testStartNewGameWithNewWorld() {
    // Create a subclass of Input and override the readInformation method

    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public World reload(Readable readable) {
        modelLog.append("Model reset");
        return new MockWorldModel(modelLog, 1, true);
      }
    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Adding new world specification !");
      }
    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "", out);
    controller.loadNewGame("res/mansion.txt", 3);

    assertTrue(viewLog.toString().contains("Adding new world specification !"));
    assertTrue(modelLog.toString().contains("Model reset"));
  }


  @Test
  public void testAdvanceTargetCharacter() {
    // Create a subclass of Input and override the readInformation method

    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public World reload(Readable readable) {
        modelLog.append("Model reset");
        return new MockWorldModel(modelLog, 1, true);
      }

      @Override
      public void moveTargetCharacter() {
        modelLog.append("called advance target character");

      }

      @Override
      public void nextTurn() {
        modelLog.append("called next turn!");
      }
    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Adding new world specification !");
      }
    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "", out);
    controller.advanceTargetCharacter();

    assertTrue(modelLog.toString().contains("called advance target charactercalled next turn!"));
  }


  @Test
  public void testResetGame() {
    // Create a subclass of Input and override the readInformation method

    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public World reload(Readable readable) {
        modelLog.append("Model reset");
        return new MockWorldModel(modelLog, 1, true);
      }

      @Override
      public void moveTargetCharacter() {
        modelLog.append("called advance target character");

      }

      @Override
      public void nextTurn() {
        modelLog.append("called next turn!");
      }
    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Resetting the game!");
      }
    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "res/mansion.txt", out);
    controller.resetGame();
    assertTrue(viewLog.toString().contains("Resetting the game!"));
    assertTrue(modelLog.toString().contains("Model reset"));
  }


  @Test
  public void testPlayGame() {
    // Create a subclass of Input and override the readInformation method

    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public World reload(Readable readable) {
        modelLog.append("Model reset");
        return new MockWorldModel(modelLog, 1, true);
      }

      @Override
      public void moveTargetCharacter() {
        modelLog.append("called advance target character");

      }

      @Override
      public void nextTurn() {
        modelLog.append("called next turn!");
      }
    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Resetting the game!");
      }
    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "res/mansion.txt", out);
    controller.playGame();
    assertTrue(viewLog.toString().contains("setting the about dialog panel visible"));
    assertTrue(viewLog.toString().contains("setting the view main panel visible"));


  }

  @Test
  public void testProcessInputForInvalidValueOfMaxCapacity() {
    // Create a subclass of Input and override the readInformation method

    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public World reload(Readable readable) {
        modelLog.append("Model reset");
        return new MockWorldModel(modelLog, 1, true);
      }

      @Override
      public void moveTargetCharacter() {
        modelLog.append("called advance target character");

      }

      @Override
      public void nextTurn() {
        modelLog.append("called next turn!");
      }

      @Override
      public Space getSpaceFromSpaceName(String roomName) {
        return new DrLuckySpace("Billiard Room", new WorldPosition(13, 15),
                new WorldPosition(23, 25));
      }

      @Override
      public void addHumanPlayer(String playerName, int maxItems, String spaceName) {
        throw new IllegalArgumentException("Max capacity cannot be negative!");
      }
    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Resetting the game!");
      }

      @Test
      public void displayErrorDialog(String title, String message) {
        viewLog.append(title);
        viewLog.append(message);
      }


    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "res/mansion.txt", out);
    controller.processInput("human", new String[]{"v", "-3", "Billiard Room"});

    assertTrue(viewLog.toString().contains("ERROR"));
    assertTrue(viewLog.toString().contains("Max capacity cannot be negative!"));
    System.out.println(viewLog.toString());

  }


  @Test
  public void testProcessInputForInvalidValueOfUserName() {
    // Create a subclass of Input and override the readInformation method

    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public World reload(Readable readable) {
        modelLog.append("Model reset");
        return new MockWorldModel(modelLog, 1, true);
      }

      @Override
      public void moveTargetCharacter() {
        modelLog.append("called advance target character");

      }

      @Override
      public void nextTurn() {
        modelLog.append("called next turn!");
      }

      @Override
      public Space getSpaceFromSpaceName(String roomName) {
        return new DrLuckySpace("Billiard Room", new WorldPosition(13, 15),
                new WorldPosition(23, 25));
      }

      @Override
      public void addHumanPlayer(String playerName, int maxItems, String spaceName) {
        throw new IllegalArgumentException("Username cannot be empty!");
      }
    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Resetting the game!");
      }

      @Test
      public void displayErrorDialog(String title, String message) {
        viewLog.append(title);
        viewLog.append(message);
      }


    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "res/mansion.txt", out);
    controller.processInput("human", new String[]{"", "3", "Billiard Room"});

    assertTrue(viewLog.toString().contains("Username cannot be empty!"));

  }


  @Test
  public void testProcessInputForInvalidValueOfSpaceName() {
    // Create a subclass of Input and override the readInformation method

    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public World reload(Readable readable) {
        modelLog.append("Model reset");
        return new MockWorldModel(modelLog, 1, true);
      }

      @Override
      public void moveTargetCharacter() {
        modelLog.append("called advance target character");

      }

      @Override
      public void nextTurn() {
        modelLog.append("called next turn!");
      }

      @Override
      public Space getSpaceFromSpaceName(String roomName) {
        return new DrLuckySpace("Billiard Room", new WorldPosition(13, 15),
                new WorldPosition(23, 25));
      }

      @Override
      public void addHumanPlayer(String playerName, int maxItems, String spaceName) {
        throw new IllegalArgumentException("Room name is invalid!");
      }
    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Resetting the game!");
      }

      @Test
      public void displayErrorDialog(String title, String message) {
        viewLog.append(title);
        viewLog.append(message);
      }


    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "res/mansion.txt", out);
    controller.processInput("human", new String[]{"v", "3", "abc room"});

    assertTrue(viewLog.toString().contains("Room name is invalid!"));

  }


  @Test
  public void testProcessInputForInvalidValueOfSpaceNameForSpaceInfo() {
    // Create a subclass of Input and override the readInformation method

    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public World reload(Readable readable) {
        modelLog.append("Model reset");
        return new MockWorldModel(modelLog, 1, true);
      }

      @Override
      public void moveTargetCharacter() {
        modelLog.append("called advance target character");

      }

      @Override
      public void nextTurn() {
        modelLog.append("called next turn!");
      }

      @Override
      public Space getSpaceFromSpaceName(String roomName) {
        return new DrLuckySpace("Billiard Room", new WorldPosition(13, 15),
                new WorldPosition(23, 25));
      }

      @Override
      public void addHumanPlayer(String playerName, int maxItems, String spaceName) {
        throw new IllegalArgumentException("Room name is invalid!");
      }
    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Resetting the game!");
      }

      @Test
      public void displayErrorDialog(String title, String message) {
        viewLog.append(title);
        viewLog.append(message);
      }


    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "res/mansion.txt", out);
    controller.processInput("human", new String[]{"v", "3", "abc room"});

    assertTrue(viewLog.toString().contains("Room name is invalid!"));

  }


  @Test
  public void testIsValidMove() {

    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public Space getCurrentPlayerSpace(Player currentPlayer) {
        modelLog.append("Model reset");
        return new DrLuckySpace("Drawing Room", new WorldPosition(15, 23),
                new WorldPosition(13, 25));
      }

      @Override
      public List<String> getNeighborsStrings() {
        List<String> neighbors = new ArrayList<>();
        neighbors.add("Billiard Room");
        neighbors.add("Armory");
        modelLog.append("getting neighbors!");
        return neighbors;
      }

      @Override
      public boolean isContainsNeighbor(String spaceName, List<String> list) {
        modelLog.append("Checking valid move!");
        return true;
      }

    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Resetting the game!");
      }

      @Test
      public void displayErrorDialog(String title, String message) {
        viewLog.append(title);
        viewLog.append(message);
      }


    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "res/mansion.txt", out);
    controller.isValidMove(new HumanControlledPlayer("abc", 1), spaceOne);

    assertTrue(modelLog.toString().contains("getting neighbors!Checking valid move!"));
  }

  @Test
  public void testGameEnded() {

  }

  @Test
  public void testIfClickIsPlayerDescription() {

  }

  @Test
  public void testIfComputerPlayerTurn() {

  }

  @Test
  public void processInvalidAttack() {

  }

  @Test
  public void testCommandPattern() {
    World world = new MockWorldModel(modelLog, 1, true) {
      @Override
      public World reload(Readable readable) {
        modelLog.append("Model reset");
        return new MockWorldModel(modelLog, 1, true);
      }

      @Override
      public Space getSpaceFromSpaceName(String name) {
        return new DrLuckySpace("Billiard Room", new WorldPosition(20, 25),
                new WorldPosition(16, 15));
      }


    };

    CustomRandomInterface random = new CustomRandom();

    WorldViewInterface view = new MockView(viewLog) {
      @Override
      public void setWorld(ReadOnlyWorldModel world) {
        viewLog.append("Adding new world specification !");
      }
    };

    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "", out);
    controller.processInput("human", new String[]{"Neha", "6", "Billiard Room"});

    System.out.println(out.toString());
    assertTrue(modelLog.toString().contains("Adding human player"));

  }

  @Test
  public void testCommandPatternComputerPlayer() {

    CustomRandomInterface random = new CustomRandom();

    World world = new MockWorldModel(modelLog, 1, true) {


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
        return new DrLuckySpace("Armory",
                new WorldPosition(23, 25),
                new WorldPosition(12, 13));
      }

      @Override
      public List<Space> getSpaces() {
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Armory",
                new WorldPosition(23, 25),
                new WorldPosition(12, 13))));
      }
    };

    WorldViewInterface view = new MockView(viewLog);
    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "", out);
    controller.processInput("computer", new String[]{});
    assertTrue(modelLog.toString().contains("Adding a computer player"));
    assertTrue(out.toString().contains("Now adding a computer controlled player!\n"));
    assertTrue(out.toString().contains("Player has been added successfully"));
  }


  @Test
  public void testPlayerInformation() {

    CustomRandomInterface random = new CustomRandom();

    World world = new MockWorldModel(modelLog, 1, true) {


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

    WorldViewInterface view = new MockView(viewLog);
    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "", out);
    controller.processInput("playerinfo", new String[]{"Aishwarya"});


    assertTrue(modelLog.toString().contains("Getting player description from username!"));
    assertTrue(out.toString().contains("Getting player description from username!"));

  }

  @Test
  public void testSpaceInformation() {

    CustomRandomInterface random = new CustomRandom();

    World world = new MockWorldModel(modelLog, 1, true) {


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

      @Override
      public String getSpaceInfoWithPlayer(String spaceName) {
        modelLog.append("The drawing room space info!");
        return "Space Information!";
      }
    };

    WorldViewInterface view = new MockView(viewLog);
    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "", out);
    controller.processInput("spaceinfo", new String[]{"Drawing Room"});


    assertTrue(modelLog.toString().contains("The drawing room space info!"));
    assertTrue(out.toString().contains("Drawing Room"));
    assertTrue(out.toString().contains("The Space information Space Information!"));

  }


  @Test
  public void testPickItem() {

    CustomRandomInterface random = new CustomRandom();

    World world = new MockWorldModel(modelLog, 1, true) {


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

      @Override
      public String getSpaceInfoWithPlayer(String spaceName) {
        modelLog.append("The drawing room space info!");
        return "Space Information!";
      }

      public boolean pickItem(String itemName) {
        modelLog.append("item picked successfully!");
        return true;

      }
    };

    WorldViewInterface view = new MockView(viewLog);
    ControllerGuiInterface controller = new ControllerGuiImpl(random, world, view, 4,
            "", out);
    controller.processInput("pickitem", new String[]{"Revolver"});

    assertTrue(modelLog.toString().contains("item picked successfully!"));
    assertTrue(out.toString().contains("Item picked successfully"));

  }


  @Test
  public void testMoveHumanCommand() {

    CustomRandomInterface random = new CustomRandom();
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
        return new ArrayList<>(Arrays.asList(new DrLuckySpace("Armory",
                new WorldPosition(23, 25),
                new WorldPosition(12, 13))));
      }
    };
    WorldViewInterface view = new MockView(viewLog);
    ControllerGuiInterface controller = new ControllerGuiImpl(random, model, view, 4,
            "", out);

    controller.processInput("move", new String[]{"Drawing Room"});
    assertTrue(modelLog.toString().contains("Moving player successfully"));
    // Assert
    assertTrue(out.toString().contains("Player moved successfully"));
  }


  /**
   * Tests the command of looking around in different spaces.
   */
  @Test
  public void testLookAround() {

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

    WorldViewInterface view = new MockView(viewLog);
    ControllerGuiInterface controller = new ControllerGuiImpl(new CustomRandom(), model, view,
            4, "", out);

    controller.processInput("lookaround", new String[]{});

    assertTrue(modelLog.toString().contains("Looking around for neighbors"));
    // Assert
    assertTrue(out.toString().contains("Looking around neighbours"));

  }

  @Test
  public void testSimulateActionPick() {

    int[] predictableNumbers = {2};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);

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


    WorldViewInterface view = new MockView(viewLog);

    ControllerGuiInterface systemUnderTest = new ControllerGuiImpl(random, world, view,
            3, "res/mansion.txt", out);
    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace();
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();


    // Perform simulateAction again
    systemUnderTest.simulateAction(player);

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
    spaceOne = new DrLuckySpace("Billiard Room", new WorldPosition(16, 21),
            new WorldPosition(21, 28));

    World world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne)),
            new CustomRandom(0));
    world.addSpaceToSpaceList(spaceOne);


    WorldViewInterface view = new MockView(viewLog);

    ControllerGuiInterface systemUnderTest = new ControllerGuiImpl(random, world, view,
            3, "res/mansion.txt", out);

    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace();
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
    World world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne, spaceTwo, spaceThree)),
            random);

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);
    world.addSpaceToSpaceList(spaceTwo);
    world.addSpaceToSpaceList(spaceThree);


    WorldViewInterface view = new MockView(viewLog);

    ControllerGuiInterface systemUnderTest = new ControllerGuiImpl(random, world, view,
            3, "res/mansion.txt", out);

    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace();
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();
    // Assert
    assertEquals(3, player.getMaxItemsCarry());
    assertTrue(spaceThree.equals(world.getCurrentPlayerSpace(player)));

  }

  @Test
  public void testSimulateActionLookAround() {

    int[] predictableNumbers = {3};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);


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

    WorldViewInterface view = new MockView(viewLog);

    ControllerGuiInterface systemUnderTest = new ControllerGuiImpl(random, world, view,
            3, "res/mansion.txt", out);

    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace();
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);


    // Get the current player
    Player player = world.getCurrentPlayer();


    // Perform simulateAction again
    systemUnderTest.simulateAction(player);

    // Assert the expected action based on the predictable numbers
    assertEquals(ActionType.LOOK_AROUND, world.getPrevActionOfComputer());
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

    // Create test DrLuckySpaces
    DrLuckySpace spaceOne = new DrLuckySpace("Billiard Room",
            new WorldPosition(16, 21),
            new WorldPosition(21, 28));


    // Create a test DrLuckyWorld
    World world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne)),
            random);

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);

    WorldViewInterface view = new MockView(viewLog);

    ControllerGuiInterface systemUnderTest = new ControllerGuiImpl(random, world, view,
            3, "res/mansion.txt", out);
    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace();
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();

    Space space = world.getCurrentSpaceTargetIsIn();

    // Assert
    assertEquals(1, player.getMaxItemsCarry());
    assertEquals(spaceOne, world.getCurrentPlayerSpace(world.getCurrentPlayer()));
    assertTrue(spaceOne.toString().equals(world.getCurrentSpaceTargetIsIn().toString()));

    systemUnderTest.simulateAction(world.getCurrentPlayer());
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

    WorldViewInterface view = new MockView(viewLog);

    ControllerGuiInterface systemUnderTest = new ControllerGuiImpl(random, world, view,
            3, "res/mansion.txt", out);

    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace();
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();
    player.pickItem(itemOne);


    // Assert

    assertEquals(1, player.getMaxItemsCarry());
    assertEquals(spaceOne, world.getCurrentPlayerSpace(world.getCurrentPlayer()));
    assertTrue(spaceOne.toString().equals(world.getCurrentSpaceTargetIsIn().toString()));

    systemUnderTest.simulateAction(world.getCurrentPlayer());

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

    // Create a test DrLuckyWorld and pass the CustomRandomStub object to the constructor
    DrLuckyWorld world = new DrLuckyWorld(12, 8, "Dr Lucky Mansion",
            new GameCharacter(50, "Lucky", true),
            new ArrayList<>(Arrays.asList(spaceOne)),
            new CustomRandom(0));

    // Add spaces to the world
    world.addSpaceToSpaceList(spaceOne);

    WorldViewInterface view = new MockView(viewLog);

    ControllerGuiInterface systemUnderTest = new ControllerGuiImpl(random, world, view,
            3, "res/mansion.txt", out);
    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace();
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);

    // Get the current player
    Player player = world.getCurrentPlayer();

    // Perform simulateAction
    systemUnderTest.simulateAction(player);

    // Assert the expected action based on the predictable numbers
    assertEquals(ActionType.ATTACK, world.getPrevActionOfComputer());

  }

  @Test
  public void testSimulateActionMove() {

    int[] predictableNumbers = {1};
    // Create the CustomRandom object with predictable numbers
    CustomRandom random = new CustomRandom(predictableNumbers[0]);


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

    WorldViewInterface view = new MockView(viewLog);

    ControllerGuiInterface systemUnderTest = new ControllerGuiImpl(random, world, view,
            3, "res/mansion.txt", out);
    int maxCapacity = systemUnderTest.generateRandomMaxCapacity();
    int spaceIndex = systemUnderTest.generateRandomFirstSpace();
    // Add a computer player
    world.addComputerPlayer(maxCapacity, spaceIndex);


    // Get the current player
    Player player = world.getCurrentPlayer();

    // Perform simulateAction
    systemUnderTest.simulateAction(player);

    // Assert the expected action based on the predictable numbers
    assertEquals(ActionType.MOVE, world.getPrevActionOfComputer());

  }
}
