package test;


import killdrluckygame.ComputerControlledPlayer;
import killdrluckygame.HumanControlledPlayer;
import org.junit.Test;

/**
 * The PlayerTest class is used to test the functionality of the Player classes.
 * It contains test cases for HumanControlledPlayer and ComputerControlledPlayer.
 */
public class PlayerTest {

  /**
   * Test case to verify that an IllegalArgumentException is thrown when creating a
   * HumanControlledPlayer with an empty name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void humanPlayerNameTest() {
    new HumanControlledPlayer("", 5);
  }


  /**
   * Test case to verify that an IllegalArgumentException is thrown when creating a
   * ComputerControlledPlayer with an empty name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void computerPlayerNameTest() {
    new ComputerControlledPlayer("",
            5);
  }


  /**
   * Test case to verify that an IllegalArgumentException is thrown when creating a
   * HumanControlledPlayer with a negative maxItem value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void humanPlayerMaxItemTest() {
    new HumanControlledPlayer("Vaishnavi",
            -5);
  }


  /**
   * Test case to verify that an IllegalArgumentException is thrown when creating a
   * ComputerControlledPlayer with a negative maxItem value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void computerPlayerMaxItemTest() {
    new ComputerControlledPlayer("Vaishanvi",
            -5);
  }

}
