package killdrluckygame;

import killdrluckygame.view.WorldViewImpl;
import killdrluckygame.view.WorldViewInterface;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


/**
 * The DrLuckyWorldDriver class is a driver code that demonstrates the gameplay mechanics of the
 * world game.
 * It allows the user to provide command-line arguments to specify the file path and maximum number
 * of turns.
 * The game is then played using the provided input file and maximum turns.
 */
public class DrLuckyWorldDriver {

  private static String filePath;
  private static int maxTurns;

  private static CustomRandomInterface random;

  /**
   * Constructs a new DrLuckyWorldDriver object with default values for file path and maximum turns.
   */
  public DrLuckyWorldDriver() {
    this.filePath = "";
    this.maxTurns = 0;
    this.random = new CustomRandom();

  }

  /**
   * Drives the gameplay of the world game.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {


    Scanner scan = new Scanner(System.in);

    if (args.length > 0) {
      filePath = args[0];  // Get the first command-line argument as the file path

      // Use the file path as needed
      String print = String.format("File path: " + filePath);
      System.out.println(print);

      if (args.length > 1) {
        try {
          maxTurns = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
          System.out.println("Invalid max turns provided.");
        }
      }
    } else {
      System.out.println("No file path provided.");
    }


    try {

      Readable read = new InputStreamReader(System.in);

      World game = new DrLuckyWorld.Input().readInput(new FileReader(filePath));
      // Create the game view
      WorldViewInterface worldView = new WorldViewImpl(game);
      random = new CustomRandom();
      ControllerGuiInterface play = new DummyController(random, game, worldView,
              maxTurns, filePath, System.out);//new ControllerGuiImpl( random,game, worldView , maxTurns, filePath);
      play.playGame();
    } catch (IOException e) {
      // Handle file reading or parsing errors
      String.format("An error occurred while reading the file: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
