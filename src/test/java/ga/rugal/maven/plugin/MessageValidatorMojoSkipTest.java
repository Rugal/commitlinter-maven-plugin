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
public class MessageValidatorMojoSkipTest extends BaseTest {

  @Parameters
  public static String[] data() {
    return new String[]{
      "skipFromSystemProperty"
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
    System.setProperty("commitlinter.skip", "true");
    System.out.println(String.format("%s on %s", SUCCESS, this.caseFormat));
    this.getMojo("validate", this.caseFormat, SUCCESS).execute();
  }

  /**
   * These tests should fail.
   *
   * @throws Exception
   */
  @SneakyThrows
  @Test(expected = MojoFailureException.class)
  public void caseFail() {
    System.setProperty("commitlinter.skip", "false");
    System.out.println(String.format("%s on %s", FAIL, this.caseFormat));
    this.getMojo("validate", this.caseFormat, FAIL).execute();
  }
}
