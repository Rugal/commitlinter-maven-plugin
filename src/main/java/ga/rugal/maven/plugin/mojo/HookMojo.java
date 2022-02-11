package ga.rugal.maven.plugin.mojo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import ga.rugal.maven.plugin.Configuration;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Install git hook as commit-msg.
 *
 * @author Rugal
 */
@Mojo(name = "hook")
public class HookMojo extends AbstractCommitlinterMojo {

  @Override
  public void execute(final Configuration c) throws MojoExecutionException, MojoFailureException {
    final Log log = this.getLog();
    try {
      final var r = this.getClass().getClassLoader().getResource("commit-msg");
      final var source = Paths.get(r.toURI());
      final var target = String.format("%s/hooks/commit-msg", c.getGitFolder());
      log.info(String.format("Write hook file to [%s]", target));
      FileUtils.copyFile(source.toFile(), new File(target));
    } catch (final URISyntaxException ex) {
      log.error("Invalid source file path", ex);
      throw new MojoExecutionException("Invalid source file path");
    } catch (final IOException ex) {
      log.error("Unable to write file", ex);
      throw new MojoExecutionException("Unable to write file");
    }
  }
}
