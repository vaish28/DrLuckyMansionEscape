package killdrluckygame.view;
import javax.swing.JMenuItem;
public interface MenuItemFactory {
  JMenuItem createMenuItem(String label, String actionCommand);
}
