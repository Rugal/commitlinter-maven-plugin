package ga.rugal.maven.plugin;

/**
 * Class that contains constant value.
 *
 * @author Rugal Bernstein
 */
public interface Constant {

  // default property value
  String GIT_FOLDER_DEFAULT = ".git";

  String HEAD_DEFAULT = "HEAD";

  boolean FAIL_ON_ERROR_DEFAULT = false;

  boolean SKIP_DEFAULT = false;

  String MATCH_PATTERN_DEFAULT = "(.*)";

  // log format
  String LOG_FORMAT = "%n%-20s:[%b]"
                      + "%n%-20s:[%b]"
                      + "%n%-20s:[%s]"
                      + "%n%-20s:[%s]"
                      + "%n%-20s:[%s]"
                      + "%n%-20s:[%s]"
                      + "%n%-20s:[%s]%n";

  String CAPTURE_LOG_FORMAT = "%n%-15s:[%s]"
                              + "%n%-15s:[%s]"
                              + "%n%-15s:[%d]"
                              + "%n%-15s:[%d]%n";

  // system property name
  String FAIL_ON_ERROR = "commitlinter.failOnError";

  String GIT_FOLDER = "commitlinter.gitFolder";

  String HEAD = "commitlinter.head";

  String MATCH_PATTERN = "commitlinter.matchPattern";

  String SKIP = "commitlinter.skip";

  String TEST_COMMIT_MESSAGE = "commitlinter.testCommitMessage";
}
