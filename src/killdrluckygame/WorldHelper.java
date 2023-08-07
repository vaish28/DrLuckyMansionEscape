package killdrluckygame;

import java.io.FileReader;
import java.io.IOException;

public class WorldHelper implements WorldSpecificationHelper {

  private String filePath;
  @Override
  public void getFilePath(String fileName) {
    this.filePath = fileName;
  }

  @Override
  public World createWorldSpecification() {
    try {
      return new DrLuckyWorld.Input().readInput(new FileReader(filePath));
    } catch (IOException ex) {
      ex.getMessage();
    }
    return null;
  }
}
