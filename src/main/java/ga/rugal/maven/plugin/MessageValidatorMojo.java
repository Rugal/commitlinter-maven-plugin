package ga.rugal.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * Validate Git commit message.
 *
 * @author Rugal
 */
@Mojo(name = "validate")
public class MessageValidatorMojo extends AbstractMojo {

  private static final String GIT_FOLDER = ".git";

  private static final String HEAD = "HEAD";

  @Parameter
  private boolean skip = false;

  @Parameter
  private String gitFolder = GIT_FOLDER;

  @Parameter
  private String head = HEAD;

  @Parameter
  private boolean failOnError = false;

  @Parameter
  private String testCommitMessage;

  @Parameter
  private String matchPattern = "(.*)";

  @Parameter
  private CaptureGroup[] captureGroups = new CaptureGroup[0];

  /**
   * Get latest commit message from git repository.
   *
   * @return
   * @throws IOException
   * @throws RevisionSyntaxException
   */
  private String getRecentCommitMessage() throws IOException, RevisionSyntaxException {
    try {
      // Open an existing repository
      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      builder.setGitDir(new File(this.gitFolder));
      Repository repository = builder.build();
      RevWalk walk = new RevWalk(repository);
      RevCommit commit = walk.parseCommit(repository.resolve(this.head));
      String commitMessage = commit.getShortMessage();
      walk.dispose();
      this.getLog().debug(String.format("Find commit message from git [%s]", commitMessage));
      return commitMessage;
    } catch (IOException | NullPointerException | RevisionSyntaxException e) {
      this.getLog().error("Unable to open .git directory");
      throw e;
    }
  }

  /**
   * Extract content from special characters.
   *
   * @param capturedString
   *
   * @return
   *
   * @throws MojoFailureException
   */
  private String extractContent(String capturedString) throws MojoFailureException {
    Pattern pattern = Pattern.compile("[\\w\\d\\s-_]+");
    Matcher matcher = pattern.matcher(capturedString);

    if (!matcher.find()) {
      throw new MojoFailureException(String.format("No content found [%s]", capturedString));
    }
    return matcher.group().trim();
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (this.skip) {
      this.getLog().info("Skip Commitlinter");
      return;
    }
    try {
      String commitMessage = this.getRecentCommitMessage();
      if (null != this.testCommitMessage) {
        commitMessage = this.testCommitMessage;
        this.getLog().debug(String.format("Use test commit message [%s]", this.testCommitMessage));
      }
      Pattern pattern = Pattern.compile(this.matchPattern);
      Matcher matcher = pattern.matcher(commitMessage);

      if (!matcher.find()) {
        throw new MojoFailureException(String.format("No pattern matched by Regex: [%s]",
                                                     this.matchPattern));
      }

      int result = 0;
      for (int i = 1; i <= Math.min(this.captureGroups.length, matcher.groupCount()); i++) {
        String extractedContent = this.extractContent(matcher.group(i));
        this.getLog().debug(extractedContent);
        RuleChecker checker = new RuleChecker(this.captureGroups[i - 1], this.getLog());
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
