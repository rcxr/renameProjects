package renameProjects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class Main {

  /**
   * Renames all the Eclipse project definitions in a given path to avoid name collisions and to
   * easily import them in bulk into Eclipse.
   * 
   * @param args Expected to contain a path containing Eclipse project definitions.
   * @throws IllegalArgumentException if you fail to pass the required parameter to the program.
   * @throws FileNotFoundException if the path does not exist.
   * @throws IOException if there was an I/O error.
   * @throws TransformerFactoryConfigurationError
   * @throws TransformerConfigurationException
   */
  public static void main(String[] args) throws FileNotFoundException, IOException,
      TransformerConfigurationException, TransformerFactoryConfigurationError {
    if (0 == args.length) {
      throw new IllegalArgumentException(
          "Program expects an argument indicating the path containing the Eclipse projects.");
    }

    Path path = Paths.get(args[0]);
    if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
      throw new FileNotFoundException("The path you provided is not valid.");
    }

    Files.walkFileTree(path, new Renamer());
  }
}
