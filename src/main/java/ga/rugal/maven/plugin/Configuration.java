package ga.rugal.maven.plugin;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;
import lombok.Value;

/**
 * Class for storing linting configuration.
 *
 * @author rugal
 */
@Builder
@SuppressFBWarnings({"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
@Value
public class Configuration {

  /**
   * If commitlinter will skip.<BR>
   * Also configurable through Maven or System property: ${commitlinter.skip}.
   */
  private boolean skip;

  /**
   * Local git folder.<BR>
   * Also configurable through Maven or System property: ${commitlinter.gitFolder}.
   */
  private String gitFolder;

  /**
   * The head ref to check for.<BR>
   * Also configurable through Maven or System property: ${commitlinter.head}.
   */
  private String head;

  /**
   * Fail the build if commitlinter check not pass.<BR>
   * Also configurable through Maven or System property: ${commitlinter.failOnError}.
   */
  private boolean failOnError;

  /**
   * The test commit message only for testing purpose.<BR>
   * Also configurable through Maven or System property: ${commitlinter.testCommitMessage}.
   */
  private String testCommitMessage;

  /**
   * The regex pattern to match.<BR>
   * Also configurable through Maven or System property: ${commitlinter.matchPattern}.
   */
  private String matchPattern;

  /**
   * The capture group configuration for regex to split. Unable to configure through system
   * property.
   */
  private CaptureGroup[] captureGroups;
}
