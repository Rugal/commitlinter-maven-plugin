package ga.rugal.maven.plugin;

import static ga.rugal.maven.plugin.BaseTest.FAIL;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Rugal Bernstein
 */
@RunWith(Parameterized.class)
public class MessageShowMojoSkipTest extends BaseTest {

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
    this.getMojo("show", this.caseFormat, SUCCESS).execute();
  }

  /**
   * These tests should fail.
   */
  @SneakyThrows
  @Test
  public void caseFail() {
    System.setProperty("commitlinter.skip", "false");
    System.out.println(String.format("%s on %s", FAIL, this.caseFormat));
    this.getMojo("show", this.caseFormat, FAIL).execute();
  }
}
