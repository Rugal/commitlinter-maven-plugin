package ga.rugal.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Value;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * A walker object to go through Git repository.
 *
 * @author Rugal Bernstein
 */
@Value
public class GitWalker {

  private Log log;

  private String gitFolder;

  private String head;

  private String matchPattern;

  private String testCommitMessage;

  /**
   * Get latest commit message from git repository.
   *
   * @return the most recent commit message
   *
   * @throws IOException             when unable to read from file system
   * @throws RevisionSyntaxException when revision error
   */
  public String getRecentCommitMessage() throws IOException, RevisionSyntaxException {
    try {
      // Open an existing repository
      final FileRepositoryBuilder builder = new FileRepositoryBuilder();
      builder.setGitDir(new File(this.gitFolder));
      final Repository repository = builder.build();
      final RevWalk walk = new RevWalk(repository);
      final RevCommit commit = walk.parseCommit(repository.resolve(this.head));
      final String commitMessage = commit.getShortMessage();
      walk.dispose();
      this.log.debug(String.format("Find commit message from git [%s]", commitMessage));
      return commitMessage;
    } catch (IOException | NullPointerException | RevisionSyntaxException e) {
      this.log.error("Unable to open .git directory");
      throw e;
    }
  }

  /**
   * Extract content from special characters.
   *
   * @param capturedString entire commit message
   *
   * @return the pattern matched string
   *
   * @throws MojoFailureException When Mojo failed
   */
  public String extractContent(final String capturedString) throws MojoFailureException {
    final Pattern pattern = Pattern.compile("[\\w\\d\\s-_]+");
    final Matcher matcher = pattern.matcher(capturedString);

    if (!matcher.find()) {
      throw new MojoFailureException(String.format("No content found [%s]", capturedString));
    }
    return matcher.group().trim();
  }

  /**
   * Get most recent commit message and match it with our pattern.
   *
   * @return The pattern matcher.
   *
   * @throws RevisionSyntaxException when revision error
   * @throws IOException             Unable to read git repository
   */
  public Matcher patternMatcher() throws RevisionSyntaxException, IOException {
    String commitMessage = this.getRecentCommitMessage();
    if (null != this.testCommitMessage) {
      commitMessage = this.testCommitMessage;
      this.log.debug(String.format("Use test commit message [%s]", this.testCommitMessage));
    }
    final Pattern pattern = Pattern.compile(this.matchPattern);
    return pattern.matcher(commitMessage);
  }
}
