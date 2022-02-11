package ga.rugal.maven.plugin;

import lombok.SneakyThrows;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Rugal Bernstein
 */
@RunWith(Parameterized.class)
public class MessageValidatorMojoXmlTest extends BaseTest {

  @Parameters
  public static String[] data() {
    return new String[]{
      "lowercase", "uppercase", "uppercamelcase", "lowercamelcase", "sentencecase", "kebabcase",
      "snakecase", "none", "max", "min", "skip", "unmatch", "nocontent", "head", "gitfolder",
      "present", "past", "thirdparty"
    };
  }

  @Before
  public void setUp() {
    System.clearProperty(Constant.SKIP);
    System.clearProperty(Constant.FAIL_ON_ERROR);
  }

  /**
   * These tests should success.
   *
   * @throws Exception
   */
  @SneakyThrows
  @Test
  public void caseSuccess() {
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
    System.out.println(String.format("%s on %s", FAIL, this.caseFormat));
    this.getMojo("validate", this.caseFormat, FAIL).execute();
  }
}
