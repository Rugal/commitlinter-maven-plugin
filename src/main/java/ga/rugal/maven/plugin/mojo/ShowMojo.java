package ga.rugal.maven.plugin.mojo;

import java.io.IOException;

import ga.rugal.maven.plugin.Configuration;
import ga.rugal.maven.plugin.Constant;
import ga.rugal.maven.plugin.model.CaptureGroup;
import ga.rugal.maven.plugin.model.GitWalker;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.eclipse.jgit.errors.RevisionSyntaxException;

/**
 * Show commit linter configuration and basic git information.
 *
 * @author Rugal
 */
@Mojo(name = "show")
public class ShowMojo extends AbstractCommitlinterMojo {

  @Override
  public void execute(final Configuration c) throws MojoExecutionException, MojoFailureException {
    final Log log = this.getLog();
    final GitWalker walker = new GitWalker(this.getLog(),
                                           c.getGitFolder(),
                                           c.getHead(),
                                           c.getMatchPattern(),
                                           c.getTestCommitMessage());
    try {
      final String string = String.format(Constant.LOG_FORMAT,
                                          "skip", c.isSkip(),
                                          "failOnError", c.isFailOnError(),
                                          "gitFolder", c.getGitFolder(),
                                          "head", c.getHead(),
                                          "testCommitMessage", c.getTestCommitMessage(),
                                          "matchPattern", c.getMatchPattern(),
                                          "CommitMessage", walker.getRecentCommitMessage());
      log.info("Basic Configuration");
      log.info(string);
      log.info("Capture Group Definition:");
      for (CaptureGroup group : c.getCaptureGroups()) {
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
