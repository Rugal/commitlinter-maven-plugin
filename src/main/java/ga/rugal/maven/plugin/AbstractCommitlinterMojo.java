package ga.rugal.maven.plugin;

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Abstract class for basic and common logic.
 *
 * @author Rugal
 */
public abstract class AbstractCommitlinterMojo extends AbstractMojo {

  @Parameter
  private boolean skip = Constant.SKIP_DEFAULT;

  @Parameter
  private String gitFolder = Constant.GIT_FOLDER_DEFAULT;

  @Parameter
  private String head = Constant.HEAD_DEFAULT;

  @Parameter
  private boolean failOnError = Constant.FAIL_ON_ERROR_DEFAULT;

  @Parameter
  private String testCommitMessage;

  @Parameter
  private String matchPattern = Constant.MATCH_PATTERN_DEFAULT;

  @Parameter
  private CaptureGroup[] captureGroups = new CaptureGroup[0];

  /**
   * Read some property from system property.
   */
  private void read(final Map<String, String> map, final String key) {
    final String property = System.getProperty(key);
    if (null != property) {
      map.put(key, property);
    }
  }

  /**
   * Group system property into map.
   *
   * @return
   */
  private Map<String, String> readSystemProperty() {
    final Map<String, String> map = new HashMap<>();
    this.read(map, Constant.FAIL_ON_ERROR);
    this.read(map, Constant.GIT_FOLDER);
    this.read(map, Constant.HEAD);
    this.read(map, Constant.MATCH_PATTERN);
    this.read(map, Constant.SKIP);
    this.read(map, Constant.TEST_COMMIT_MESSAGE);
    return map;
  }

  private Configuration getConfiguration() {
    final Map<String, String> map = this.readSystemProperty();
    this.failOnError = Boolean.parseBoolean(map.getOrDefault(Constant.FAIL_ON_ERROR, "false"));
    this.gitFolder = map.getOrDefault(Constant.GIT_FOLDER, this.gitFolder);
    this.head = map.getOrDefault(Constant.HEAD, this.head);
    this.matchPattern = map.getOrDefault(Constant.MATCH_PATTERN, this.matchPattern);
    this.skip = Boolean.parseBoolean(map.getOrDefault(Constant.SKIP, "false"));
    this.testCommitMessage = map.getOrDefault(Constant.TEST_COMMIT_MESSAGE, this.testCommitMessage);

    return Configuration.builder()
            .captureGroups(captureGroups)
            .failOnError(failOnError)
            .gitFolder(gitFolder)
            .head(head)
            .matchPattern(matchPattern)
            .skip(skip)
            .testCommitMessage(testCommitMessage)
            .build();
  }

  /**
   * Execute commitlinter mojo.
   *
   * @param configuration all preset configuration, priority is 1. system property 2. configuration
   *                      3. default value
   *
   * @throws MojoExecutionException
   * @throws MojoFailureException
   */
  public abstract void execute(Configuration configuration) throws MojoExecutionException,
                                                                   MojoFailureException;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    this.execute(this.getConfiguration());
  }
}
