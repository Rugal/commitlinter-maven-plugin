package ga.rugal.maven.plugin;

import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Rugal Bernstein
 */
@RunWith(Parameterized.class)
public class MessageValidatorMojoFailOnErrorTest extends BaseTest {

  @Parameters
  public static String[] data() {
    return new String[]{
      "failOnErrorFromSystemProperty"
    };
  }

  /**
   * These tests should success.
   *
   * @throws Exception
   */
  @Test
  public void caseSuccess() throws Exception {
    System.setProperty("commitlinter.failOnError", "false");
    System.out.println(String.format("%s on %s", SUCCESS, this.caseFormat));
    this.getMojo(this.caseFormat, SUCCESS).execute();
  }

  /**
   * These tests should fail.
   *
   * @throws Exception
   */
  @Test(expected = MojoFailureException.class)
  public void caseFail() throws Exception {
    System.setProperty("commitlinter.failOnError", "true");
    System.out.println(String.format("%s on %s", FAIL, this.caseFormat));
    this.getMojo(this.caseFormat, FAIL).execute();
  }
}
