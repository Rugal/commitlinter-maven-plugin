package ga.rugal.maven.plugin;

/**
 * Class that contains constant value.
 *
 * @author Rugal Bernstein
 */
public interface Constant {

  String GIT_FOLDER = ".git";

  String HEAD = "HEAD";

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
}
