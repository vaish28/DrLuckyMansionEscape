package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import killdrluckygame.*;
import killdrluckygame.view.WorldViewInterface;
import org.junit.Before;
import org.junit.Test;
public class ControllerViewTest {

  private StringBuilder modelLog;

  private StringBuilder viewLog;

  private Appendable out;

  @Before
  public void setUp() {
    out = new StringBuffer();
    modelLog = new StringBuilder();
    viewLog = new StringBuilder();

  }

  @Test
  public void testAddComputerPlayer() {
    World world = new MockWorldModel(modelLog,1,true);

    CustomRandomInterface random = new CustomRandom();


    WorldViewInterface view = new MockView(viewLog);
//      @Override
//      public void showAddComputerPlayer() {
//        viewLog.append("Adding a computer player");
//      }
//    };
    ControllerGuiInterface controller = new
            DummyController(random , world,view,4,"",out);

  }
}
