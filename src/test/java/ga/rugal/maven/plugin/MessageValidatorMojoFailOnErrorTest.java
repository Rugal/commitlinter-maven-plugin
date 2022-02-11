package ga.rugal.maven.plugin;

import lombok.SneakyThrows;
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
  @SneakyThrows
  @Test
  public void caseSuccess() {
    System.setProperty("commitlinter.failOnError", "false");
    System.out.println(String.format("%s on %s", SUCCESS, this.caseFormat));
    this.getMojo("validate", this.caseFormat, SUCCESS).execute();
  }

  /**
   * These tests should fail.
   *
   * @throws Exception
   */
  @Test(expected = MojoFailureException.class)
  @SneakyThrows
  public void caseFail() {
    System.setProperty("commitlinter.failOnError", "true");
    System.out.println(String.format("%s on %s", FAIL, this.caseFormat));
    this.getMojo("validate", this.caseFormat, FAIL).execute();
  }
}
