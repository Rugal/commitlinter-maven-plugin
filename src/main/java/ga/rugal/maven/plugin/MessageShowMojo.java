package ga.rugal.maven.plugin;

import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.jgit.errors.RevisionSyntaxException;

/**
 * Show commit linter configuration and basic git information.
 *
 * @author Rugal
 */
@Mojo(name = "show")
public class MessageShowMojo extends AbstractMojo {

  /**
   * Also configurable through Maven or System property: ${commitlinter.skip}.
   */
  @Parameter
  private boolean skip = false;

  @Parameter
  private String gitFolder = Constant.GIT_FOLDER;

  @Parameter
  private String head = Constant.HEAD;

  /**
   * Also configurable through Maven or System property: ${commitlinter.failOnError}.
   */
  @Parameter
  private boolean failOnError = false;

  @Parameter
  private String testCommitMessage;

  @Parameter
  private String matchPattern = "(.*)";

  @Parameter
  private CaptureGroup[] captureGroups = new CaptureGroup[0];

  /**
   * Read some property from system property.
   */
  private void readSystemProperty() {
    final String skipProperty = System.getProperty("commitlinter.skip");
    this.skip = (null != skipProperty)
                ? Boolean.parseBoolean(skipProperty)
                : this.skip;

    final String failOnErrorProperty = System.getProperty("commitlinter.failOnError");
    this.failOnError = (null != failOnErrorProperty)
                       ? Boolean.parseBoolean(failOnErrorProperty)
                       : this.failOnError;
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    this.readSystemProperty();
    final Log log = this.getLog();
    final GitWalker walker = new GitWalker(this.getLog(),
                                           this.gitFolder,
                                           this.head,
                                           this.matchPattern,
                                           this.testCommitMessage);
    try {
      final String string = String.format(Constant.LOG_FORMAT,
                                          "skip", this.skip,
                                          "failOnError", this.failOnError,
                                          "gitFolder", this.gitFolder,
                                          "head", this.head,
                                          "testCommitMessage", this.testCommitMessage,
                                          "matchPattern", this.matchPattern,
                                          "CommitMessage", walker.getRecentCommitMessage());
      log.info("Basic Configuration");
      log.info(string);
      log.info("Capture Group Definition:");
      for (CaptureGroup group : this.captureGroups) {
        log.info(String.format(Constant.CAPTURE_LOG_FORMAT,
                               "CaseFormat", group.getCaseFormat(),
                               "Tense", group.getTense(),
                               "MinimunLength", group.getMin(),
                               "MaximumLength", group.getMax()));
      }
    } catch (IOException | RevisionSyntaxException | NullPointerException ex) {
      throw new MojoFailureException("Unable to lint commit message due to git repository error");
    }
  }
}
