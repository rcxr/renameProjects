package renameProjects;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

class Renamer extends SimpleFileVisitor<Path> {
  private final PathMatcher matcher;
  private final Transformer transformer;

  Renamer() throws TransformerConfigurationException, TransformerFactoryConfigurationError {
    matcher = FileSystems.getDefault().getPathMatcher("glob:.project");
    transformer = TransformerFactory.newInstance()
        .newTransformer(new StreamSource(Main.class.getResourceAsStream("/transformation.xslt")));
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    if (matcher.matches(file.getFileName()) && Files.isWritable(file)) {
      StringWriter stringWriter = new StringWriter();
      InputStream fileStream = Files.newInputStream(file);
      try {
        System.out.println("Renaming " + file.getParent().getParent().getFileName());
        transformer.setParameter("student", file.getParent().getParent().getFileName());
        transformer.transform(new StreamSource(fileStream), new StreamResult(stringWriter));
        System.out.println("\t SUCCESS!");
      } catch (TransformerException e) {
        System.out.println("\t ERROR!");
        e.printStackTrace();
      }
      fileStream.close();

      Writer fileWriter = Files.newBufferedWriter(file);
      fileWriter.write(stringWriter.toString());
      fileWriter.close();
    }

    return super.visitFile(file, attrs);
  }
}
