package killdrluckygame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * The {@code DiagramWorldClass} class represents a graphical representation of the world.
 * It creates a 2D grid with rooms and displays it using Java's graphics capabilities.
 */
public class DiagramWorldClass extends JPanel {
  private List<Space> spaceList = new ArrayList<>(); // creating a list to store spaces
  private final int rows;
  private final int columns;

  /**
   * Initialises the number of rows and columns in the world.
   *
   * @param rows    value indicating the rows
   * @param columns value indicating the columns
   */
  public DiagramWorldClass(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
  }

  /**
   * This paints the image on the 2d grid.
   *
   * @param graphicWorld the <code>Graphics</code> context in which to paint
   */
  @Override
  public void paint(Graphics graphicWorld) {
    // overriding the paint method to render graphical representation

    Image imgWorld = create2dGrid();
    graphicWorld.drawImage(imgWorld, 30, 30, this);
  }

  private Image create2dGrid() {

    int worldWidth = rows * 10;
    int worldHeight = columns * 10;
    BufferedImage bufferedImage = new BufferedImage(worldWidth, worldHeight,
            BufferedImage.TYPE_INT_RGB);
    Graphics graphicWorld = bufferedImage.getGraphics();

    // Set the font for the labels
    graphicWorld.setFont(new Font("Arial", Font.PLAIN, 10));

    graphicWorld.setColor(Color.white);
    graphicWorld.fillRect(0, 0, worldWidth, worldHeight);

    int numRows = rows;
    int numCols = columns;
    int cellWidth = worldWidth / numCols;
    int cellHeight = worldHeight / numRows;

    graphicWorld.setColor(Color.BLACK);

    for (int col = 0; col <= numCols; col++) {
      int x = col * cellWidth;
      graphicWorld.drawLine(x, 0, x, worldHeight);
    }

    // Draw horizontal grid lines
    for (int row = 0; row <= numRows; row++) {
      int y = row * cellHeight;
      graphicWorld.drawLine(0, y, worldWidth, y);
    }

    for (Space room : spaceList) {


      int roomRow = room.getUpperLeftPosition().getRow(); /// cellHeight;
      int roomCol = room.getUpperLeftPosition().getColumn(); /// cellWidth;


      int roomX = roomCol * cellWidth;
      int roomY = roomRow * cellHeight;

      int roomWidth =
              (room.getLowerRightPosition().getColumn() - room.getUpperLeftPosition().getColumn()
                      + 1) * cellWidth;
      int roomHeight =
              (room.getLowerRightPosition().getRow() - room.getUpperLeftPosition().getRow() + 1)
                      * cellHeight;

      // Generate a random color for each room
      Color roomColor = new Color((int) (Math.random() * 0x1000000));
      graphicWorld.setColor(roomColor);


      graphicWorld.fillRect(roomX, roomY, roomWidth, roomHeight);

      graphicWorld.setColor(Color.BLACK);
      FontMetrics fm = graphicWorld.getFontMetrics();

      String roomName = room.getSpaceName();
      int textWidth = fm.stringWidth(roomName);
      int textHeight = fm.getHeight();

      // Calculate the position to center the text within the rectangle
      int textX = roomX + (roomWidth - textWidth) / 2;
      int textY = roomY + (roomHeight - textHeight) / 2 + fm.getAscent();


      graphicWorld.drawString(room.getSpaceName(), textX, textY);


    }

    String filePath = "grid_image.png"; // Provide your desired file path
    saveAsPng(bufferedImage, filePath);
    graphicWorld.dispose();


    return bufferedImage;
  }

  private void saveAsPng(BufferedImage bufferedImage, String filePath) {
    File outputFile = new File(filePath);
    try {
      ImageIO.write(bufferedImage, "png", outputFile);

    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }


  /**
   * Creates the JFrame for rendering of the graphical image. Displays the graphical representation
   * of the world.
   *
   * @param spaceList list indicating the spaces.
   */
  public void display(List<Space> spaceList) {
    this.spaceList = spaceList;
    JFrame frame = new JFrame();
    frame.getContentPane().add(this);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }


}
