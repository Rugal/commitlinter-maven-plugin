package ga.rugal.maven.plugin;

import java.io.IOException;
import java.util.regex.Matcher;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.eclipse.jgit.errors.RevisionSyntaxException;

/**
 * Validate Git commit message.
 *
 * @author Rugal
 */
@Mojo(name = "validate", defaultPhase = LifecyclePhase.VALIDATE)
public class ValidateMojo extends AbstractCommitlinterMojo {

  @Override
  public void execute(final Configuration c) throws MojoExecutionException, MojoFailureException {
    if (c.isSkip()) {
      this.getLog().info("Skip Commitlinter");
      return;
    }
    try {
      final GitWalker walker = new GitWalker(this.getLog(),
                                             c.getGitFolder(),
                                             c.getHead(),
                                             c.getMatchPattern(),
                                             c.getTestCommitMessage());
      final Matcher matcher = walker.patternMatcher();
      if (!matcher.find()) {
        throw new MojoFailureException(String.format("No pattern matched by Regex: [%s]",
                                                     c.getMatchPattern()));
      }

      int result = 0;
      final CaptureGroup[] cg = c.getCaptureGroups();
      for (int i = 1; i <= Math.min(cg.length, matcher.groupCount()); i++) {
        final String extractedContent = walker.extractContent(matcher.group(i));
        this.getLog().debug(extractedContent);
        final RuleChecker checker = new RuleChecker(cg[i - 1], this.getLog());
        result += checker.check(extractedContent);
      }
      if (0 == result) {
        return;
      }
      if (c.isFailOnError()) {
        throw new MojoFailureException("Commit Lint failed, please check rules");
      }
    } catch (IOException | RevisionSyntaxException | NullPointerException ex) {
      ex.printStackTrace();
      throw new MojoFailureException("Unable to lint commit message due to git repository error");
    }
  }
}
