package ga.rugal.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Says "Hi" to the user.
 *
 */
@Mojo(name = "sayhi")
public class GreetingMojo extends AbstractMojo {

//  private static final String GIT_FOLDER = ".git";
  //git log --format=%B -n 1 HEAD

  @Override
  public void execute() throws MojoExecutionException {
    ProcessBuilder builder = new ProcessBuilder();
    builder.command("sh", "-c", "git log --format=%B -n 1 HEAD");
    Process process;
    try {
      process = builder.start();
      Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
      while (scanner.hasNext()) {
        getLog().info(scanner.next());
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
