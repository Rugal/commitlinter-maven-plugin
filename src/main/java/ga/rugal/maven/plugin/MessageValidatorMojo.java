package ga.rugal.maven.plugin;

import java.io.IOException;
import java.util.regex.Matcher;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.jgit.errors.RevisionSyntaxException;

/**
 * Validate Git commit message.
 *
 * @author Rugal
 */
@Mojo(name = "validate", defaultPhase = LifecyclePhase.VALIDATE)
public class MessageValidatorMojo extends AbstractMojo {

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
    if (this.skip) {
      this.getLog().info("Skip Commitlinter");
      return;
    }
    try {
      final GitWalker walker = new GitWalker(this.getLog(),
                                             this.gitFolder,
                                             this.head,
                                             this.matchPattern,
                                             this.testCommitMessage);
      final Matcher matcher = walker.patternMatcher();
      if (!matcher.find()) {
        throw new MojoFailureException(String.format("No pattern matched by Regex: [%s]",
                                                     this.matchPattern));
      }

      int result = 0;
      for (int i = 1; i <= Math.min(this.captureGroups.length, matcher.groupCount()); i++) {
        final String extractedContent = walker.extractContent(matcher.group(i));
        this.getLog().debug(extractedContent);
        final RuleChecker checker = new RuleChecker(this.captureGroups[i - 1], this.getLog());
        result += checker.check(extractedContent);
      }
      if (0 == result) {
        return;
      }
      if (this.failOnError) {
        throw new MojoFailureException("Commit Lint failed, please check rules");
      }
    } catch (IOException | RevisionSyntaxException | NullPointerException ex) {
      throw new MojoFailureException("Unable to lint commit message due to git repository error");
    }
  }
}
