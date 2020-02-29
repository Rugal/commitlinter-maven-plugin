package ga.rugal.maven.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Show commit linter configuration and basic git information.
 *
 * @author Rugal
 */
@Mojo(name = "hook")
public class HookMojo extends AbstractCommitlinterMojo {

  private static final String CONTENT = "#!/bin/bash\n"
                                        + "file=$1\n"
                                        + "message=`cat $file`\n"
                                        + "mvn ga.rugal.maven:commitlinter-maven-plugin:"
                                        + "1.6.0-SNAPSHOT:validate "
                                        + "-DtestCommitMessage=${message}\n";

  @Override
  public void execute(final Configuration c) throws MojoExecutionException, MojoFailureException {
    final Log log = this.getLog();
    final String target = String.format("%s/hooks/commit-msg", c.getGitFolder());
    final File file = new File(target);
    log.info(String.format("Write hook file to [%s]", target));
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(CONTENT);
    } catch (final IOException ex) {
      log.error("Unable to write file", ex);
    }
  }
}
