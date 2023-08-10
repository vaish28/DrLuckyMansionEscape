package killdrluckygame;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;
import killdrluckygame.commands.AddComputerPlayerCommand;
import killdrluckygame.commands.AddHumanPlayerCommand;
import killdrluckygame.commands.AttackPlayerCommand;
import killdrluckygame.commands.DisplayPlayerInformationCommand;
import killdrluckygame.commands.DisplaySpaceInformationCommand;
import killdrluckygame.commands.GameOperationCommand;
import killdrluckygame.commands.GraphicalRepresentationWorldCommand;
import killdrluckygame.commands.LookAroundCommand;
import killdrluckygame.commands.MovePlayerCommand;
import killdrluckygame.commands.PetMoveCommand;
import killdrluckygame.commands.PickItemCommand;



/**
 * The GameController class simulates the game controller for the Kill Dr. Lucky game.
 * It provides a method for playing the game and interacting with the players and the game world.
 */
public class GameController implements GameControllerInterface {

  private final Readable in;
  private final Appendable out;
  private boolean gameStart;
  private final Map<String, Function<Scanner, GameOperationCommand>> supportedOperations;
  private String spaceName;
  private String itemName;
  private String username;
  private String playerName;
  private int maxItems;
  private boolean playAfterInput;
  private CustomRandomInterface random;

  /**
   * Initializes the GameController with the specified input stream and output appendable.
   *
   * @param in  The input stream to read input from.
   * @param out The appendable to write output to.
   */

  public GameController(Readable in, Appendable out, CustomRandomInterface random) {
    this.in = in;
    this.out = out;
    this.supportedOperations = new HashMap<>();
    this.spaceName = "";
    this.username = "";
    this.maxItems = 0;
    this.playerName = "";
    this.playAfterInput = false;
    this.gameStart = false;
    this.random = random;
  }

  private void addSupportedOperations(World game, int maxTurns, Appendable out) {


    this.supportedOperations.put("worldview", (Scanner sc) -> {
      GameOperationCommand command = new GraphicalRepresentationWorldCommand(game, out);
      return command;
    });


    this.supportedOperations.put("human", (Scanner sc) -> {
      validateHumanPlayerInformation(game, sc);
      GameOperationCommand command = new AddHumanPlayerCommand(game, username, maxItems,
              spaceName, out);
      playAfterInput = true;
      return command;

    });

    this.supportedOperations.put("computer", (Scanner sc) -> {

      int maxCapacity = generateRandomMaxCapacity();
      int spaceRandomIndex = generateRandomFirstSpace(game);
      GameOperationCommand command = new AddComputerPlayerCommand(game, out, maxCapacity,
              spaceRandomIndex);
      return command;
    });

    this.supportedOperations.put("spaceinfo", (Scanner sc) -> {
      String spaceName = validateSpaceName(game, sc);
      GameOperationCommand command = new DisplaySpaceInformationCommand(game, spaceName, out);
      return command;
    });


    this.supportedOperations.put("playerinfo", (Scanner sc) -> {

      String playerName = validatePlayerName(game, sc);
      GameOperationCommand command = new DisplayPlayerInformationCommand(game, playerName, out);
      return command;
    });


    this.supportedOperations.put("move", (Scanner sc) -> {
      GameOperationCommand command = null;

      if (playAfterInput) {

        if (validateMoveInformation(game, out, sc)) {
          command = new MovePlayerCommand(game, spaceName, out);
        } else {
          throw new IllegalArgumentException("Cannot move to this space:! Enter valid space name");
        }
      } else {
        throw new IllegalStateException("Player not added yet!");
      }
      return command;
    });

    this.supportedOperations.put("pickitem", (Scanner sc) -> {

      GameOperationCommand command = null;

      if (playAfterInput) {
        if (checkSpaceHasItems(game)) {
          if (validateItemInformation(game, out, sc)) {
            command = new PickItemCommand(game, itemName, out, true);
          } else {
            throw new IllegalArgumentException("Cannot pick this item! Enter valid item name");
          }
        } else {
          command = new PickItemCommand(game, itemName, out, false);
        }

      } else {
        throw new IllegalStateException("Player not added yet!");
      }
      return command;
    });

    this.supportedOperations.put("lookaround", (Scanner sc) -> {
      GameOperationCommand command = null;
      if (playAfterInput) {
        if (game.getCurrentPlayer().isHumanControlled()) {
          command = new LookAroundCommand(game, out);
        } else {
          throw new IllegalArgumentException("Cannot look around from current space");
        }
      } else {
        throw new IllegalStateException("Player not added yet");
      }
      return command;
    });

    this.supportedOperations.put("movepet", (Scanner sc) -> {
      GameOperationCommand command = null;
      if (playAfterInput) {
        String spaceName = validPetMoveSpaceName(game, sc);
        if (spaceName != null) {
          command = new PetMoveCommand(game, out, spaceName);
        } else {
          throw new IllegalArgumentException("Cannot move the pet");
        }
      } else {
        throw new IllegalStateException("Player not added successfully");
      }
      return command;
    });
    this.supportedOperations.put("attack", (Scanner sc) -> {
      GameOperationCommand command = null;
      if (playAfterInput) {
        if (game.getCurrentPlayer().isHumanControlled()) {
          if (game.getCurrentPlayer().getItems().size() != 0) {
            String itemToAttack = attackByItem(game, sc);
            if (itemToAttack != null) {
              command = new AttackPlayerCommand(game, itemToAttack, out);
            } else {
              throw new IllegalArgumentException("Illegal Argument Exception");
            }
          } else {
            command = new AttackPlayerCommand(game, "", out);
          }
        }

      } else {
        throw new IllegalStateException("Player not added ");
      }
      return command;
    });

  }


  private String attackByItem(World game, Scanner scanner) {
    Player player = game.getCurrentPlayer();
    String itemName = "";
    boolean validInput = false;

    if (player.isHumanControlled()) {
      List<Item> itemList = player.getItems();
      if (itemList.size() != 0) {
        for (Item item : itemList) {
          try {
            out.append(item.getItemName()).append("-");
            out.append(String.valueOf(item.getDamageValue())).append("\n");
          } catch (IOException ex) {
            display(ex.getMessage());
          }
        }

        while (!validInput) {
          try {
            out.append("Enter the item name you want to attack with\n");
            itemName = scanner.nextLine();
            if (itemName != null) {
              validInput = true;
            }

          } catch (IOException ex) {
            display(ex.getMessage());
          }
        }
      }

    }

    return itemName;
  }


  private void validateHumanPlayerInformation(World game, Scanner scan) {

    try {
      out.append("Add the details of the player ");
      out.append("Enter the username and maximum capacity of carrying the items")
              .append("\n");

      username = scan.nextLine();
      out.append(username).append("\n");

      boolean validInput = false;
      maxItems = 0;
      while (!validInput) {
        try {
          out.append("Add your max capacity to carry items for this player ").append("\n");

          maxItems = Integer.parseInt(String.valueOf(scan.nextInt()));
          scan.nextLine();
          validInput = true;
        } catch (InputMismatchException ex) {
          out.append("Invalid input. Please enter an integer value.").append("\n");

          scan.nextLine();
        }
      }

      out.append(String.valueOf(maxItems)).append("\n");

      validInput = false;
      spaceName = "";
      while (!validInput) {
        out.append("Which room do you want to move in? Refer to the image:").append("\n");
        for (Space space : game.getSpaces()) {
          if (!space.equals(game.getPetSpace())) {
            out.append(space.getSpaceName());
            out.append("\n");
          }
        }
        spaceName = scan.nextLine();
        out.append(spaceName).append("\n");

        if (game.getSpaceFromSpaceName(spaceName) != null) {
          validInput = true;
        } else {
          out.append("Invalid space name. Please enter a correct space name.").append("\n");
        }
      }
      playAfterInput = true;
    } catch (IOException ex) {
      ex.getMessage();
    }

  }

  private String validateSpaceName(World game, Scanner scan) {
    String spaceName;
    boolean validInput;
    validInput = false;
    spaceName = "";
    try {
      while (!validInput) {
        out.append("Which room do you want to want to view information about? Refer to "
                + "the image:\n");

        spaceName = scan.nextLine();

        validInput = isValidInput(game, spaceName, validInput);
      }
    } catch (IOException ex) {
      ex.getMessage();
    }

    return spaceName;
  }

  private String validPetMoveSpaceName(World game, Scanner scan) {
    String spaceName;
    boolean validInput;
    validInput = false;
    spaceName = "";
    try {

      if (game.getCurrentPlayer().isHumanControlled()) {
        while (!validInput) {
          out.append("Which room do you want to want to move the pet to?");

          spaceName = scan.nextLine();

          validInput = isValidInput(game, spaceName, validInput);
        }
      }
    } catch (IOException ex) {
      ex.getMessage();
    }

    return spaceName;
  }

  private boolean isValidInput(World game, String spaceName, boolean validInput)
          throws IOException {
    if (game.getSpaceFromSpaceName(spaceName) != null) {
      validInput = true;
      out.append(spaceName).append("\n");
    } else {
      out.append("Invalid space name. Please enter a correct space name.").append("\n");
    }
    return validInput;
  }

  private String validatePlayerName(World game, Scanner sc) {
    boolean validInput;
    validInput = false;
    playerName = "";
    try {
      while (!validInput) {
        out.append("Which player information do you want to want to view? Enter the username of "
                + "the player \n");

        playerName = sc.nextLine();
        if (game.getPlayerByPlayerName(playerName) != null) {
          out.append(playerName).append("\n");
          validInput = true;
        } else {
          out.append("Invalid player name. Please enter a correct space name.").append("\n");
        }
      }
    } catch (IOException ex) {
      ex.getMessage();
    }

    return playerName;
  }

  private boolean validateMoveInformation(World game, Appendable out, Scanner scan) {
    try {
      if (game.getCurrentPlayer().isHumanControlled()) {
        out.append("It is the turn of " + game.getCurrentPlayer().getPlayerDescription())
                .append("\n");

        List<String> neighNames = game.getNeighborsStrings();
        for (String neighbor : neighNames) {
          out.append(neighbor).append("\n");
        }
        out.append("\n");
        out.append("Enter the space name you want to move in about").append("\n");

        spaceName = scan.nextLine();
        out.append(spaceName).append("\n");
        if (game.isContainsNeighbor(spaceName, neighNames)) {
          return true;
        }

      }

    } catch (IOException ex) {
      ex.getMessage();
    }

    return false;
  }


  private boolean validateItemInformation(World game, Appendable out, Scanner scan) {
    try {
      if (game.getCurrentPlayer().isHumanControlled()) {
        out.append("It is the turn of " + game.playerDescription())
                .append("\n");

        out.append(game.getCurrentPlayerSpace(game.getCurrentPlayer()).toString()).append("\n");

        out.append("Enter the item you want to pick from the current space ")
                .append("\n");
        out.append("Pick an item ").append("\n");
        String itemNameCheck = scan.nextLine();
        for (Item item : game.getCurrentPlayerSpace(game.getCurrentPlayer()).getItems()) {
          if (itemNameCheck.equals(item.getItemName())) {
            itemName = itemNameCheck;
            return true;
          }
        }

      }
    } catch (IOException ex) {
      ex.getMessage();
    }
    return false;

  }

  private boolean checkSpaceHasItems(World game) {
    boolean hasItems = game.checkSpaceContainsItemsToPick(game
            .getCurrentPlayerSpace(game.getCurrentPlayer()));
    if (hasItems) {
      return true;
    }
    return false;
  }

  @Override
  public void gamePlay(World game, int maxTurns) {

    Objects.requireNonNull(game);
    Scanner scan = new Scanner(this.in);

    addSupportedOperations(game, maxTurns, out);
    display("The number of maximum turns allocated are: " + maxTurns);
    display("----- The World Game -----");
    String choice = "";
    do {
      if (game.isToPromptForInput()) {
        StringBuilder welcomeString = new StringBuilder();

        if (playAfterInput) {
          display("Current Player Details are: ");
          display(game.displayCurrentPlayerInfo());
        }

        //TODO ask if all players starting point

        display("Target Character Details: ");
        display((game.targetCharacterDetails()));
        display("\n");

        display("Target Character Pet Details: ");
        display(game.getCurrentPetInfo());
        display("\n");

        welcomeString.append("Enter the choice for the playing the game: ").append("\n")
                .append("worldview: for viewing the graphical representation, ").append("\n")
                .append("human: for adding a human controlled player")
                .append("\n")
                .append("computer: for adding a computer controlled player")
                .append("\n")
                .append("spaceinfo: Displaying the space information")
                .append("\n")
                .append("playerinfo: displaying the player information")
                .append("\n")
                .append("move: for moving the current player")
                .append("\n")
                .append("pickitem: for picking an item in the space,")
                .append("\n")
                .append("lookaround: for looking in spaces surrounding each other")
                .append("\n")
                .append("movepet: for moving the pet around the world")
                .append("\n")
                .append("attack: for attack on the target character");

        display(welcomeString.toString());
        choice = scan.nextLine();
        display(choice);
        try {
          Function<Scanner, GameOperationCommand> commandToExecute =
                  this.supportedOperations.getOrDefault(choice, null);
          if (commandToExecute == null) {
            display("Enter correct choice from the options to play!");
            continue;
          }
          commandToExecute.apply(scan).execute();
        } catch (IllegalStateException ex) {
          // To indicate wrong state of game play - when a command is executed before adding details
          // of a player.
          display("Add details of computer or human player first ");
        } catch (IllegalArgumentException ex) {
          // To indicate wrong input from the user
          display(ex.getMessage());
        }
      } else {

        StringBuilder sb = new StringBuilder();
        sb.append(game.displayCurrentPlayerInfo()).append(" \n ");
        String result = sb.toString();
        display(result);
        display("\nBefore state of computer player \n");
        display(game.playerDescription());
        String resultSimulate = simulateAction(game.getCurrentPlayer(), game);
        display(resultSimulate);
        display("\n");
        display("\nNext state of computer player \n");
        display(game.playerDescription());
        game.nextTurn();
      }
    } while (!("q").equals(choice) && (!game.hasGameEnded(maxTurns)));

    if (game.getTargetHealth() == 0) {
      display("Players have won the game !");
    } else {
      display("The target character wins the game");
      display("\n");
      display("All the players have lost the game!");
    }

  }

  @Override
  public String simulateAction(Player currentPlayer, World game) {

    game.increaseNumberOfTurns();
    double moveProbability = 0.4;
    double pickUpProbability = 0.3;
    double lookAroundProbability = 0.5;
    double randomValue = random.nextDouble();
    StringBuilder sb = new StringBuilder();
    String result = "";

    if (game.checkIfTargetCharacterInSameSpace(currentPlayer)) {

      game.changePrevAction(ActionType.ATTACK);
      sb.append("Performing attack");
      game.performComputerAttack();
      result = sb.toString();

    } else {
      if (randomValue < moveProbability && (game.getPrevActionOfComputer() != ActionType.MOVE)) {

        game.changePrevAction(ActionType.MOVE);
        sb.append("Performing move");
        result = performRandomAction(ActionType.MOVE, currentPlayer, game);
        sb.append(result);
        result = sb.toString();

      } else if (randomValue < moveProbability + pickUpProbability
              && (game.getPrevActionOfComputer() != ActionType.PICKUP_ITEM)) {

        sb.append("Performing pick");
        game.changePrevAction(ActionType.PICKUP_ITEM);
        result = performRandomAction(ActionType.PICKUP_ITEM, currentPlayer, game);
        sb.append(result);
        result = sb.toString();

      } else if (randomValue < (moveProbability + pickUpProbability + lookAroundProbability)
              && (game.getPrevActionOfComputer() != ActionType.LOOK_AROUND)) {

        sb.append("Performing lookaround");
        game.changePrevAction(ActionType.LOOK_AROUND);
        result = performRandomAction(ActionType.LOOK_AROUND, currentPlayer, game);
        sb.append(result);
        result = sb.toString();

      } else {

        game.changePrevAction(ActionType.MOVE_PET);
        int spaceNumber = random.nextInt(0, game.getSpaces().size() - 1);
        game.petMove(game.getSpaces().get(spaceNumber).getSpaceName());
        sb.append("Performing pet move");
        result = sb.toString();

      }
    }

    return result;
  }



  private String performRandomAction(ActionType action, Player player, World game) {
    String result = "";
    switch (action) {
      case MOVE:
        // Evaluate the desirability of moving to a neighboring space
        result = game.evaluateNeighboringScores(game.getCurrentPlayerSpace(player), player);

        break;
      case PICKUP_ITEM:
        // Evaluate the desirability of picking up an item
        result = game.evaluatePickItem(game.getCurrentPlayerSpace(player).getItems(), player);

        break;
      case LOOK_AROUND:
        // Evaluate the desirability of looking around
        result = game.evaluateLookAround(game.getCurrentPlayerSpace(player), player);
        break;
      default:
        break;
    }

    return result;
  }

  @Override
  public int generateRandomMaxCapacity() {
    return random.nextInt(1, 4);
  }

  @Override
  public int generateRandomFirstSpace(World game) {
    return random.nextInt(game.getSpaces().size());
  }


  private void display(String message) {
    try {
      out.append(message).append("\n");
    } catch (IOException ex) {
      ex.getMessage();
    }
  }
}