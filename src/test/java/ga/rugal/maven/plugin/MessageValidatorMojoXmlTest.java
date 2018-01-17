package ga.rugal.maven.plugin;

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
      "snakecase", "none", "max", "min", "skip", "unmatch", "nocontent", "head", "gitfolder"
    };
  }

  @Before
  public void setUp() {
    System.clearProperty("commitlinter.skip");
    System.clearProperty("commitlinter.failOnError");
  }

  /**
   * These tests should success.
   *
   * @throws Exception
   */
  @Test
  public void caseSuccess() throws Exception {
    System.out.println(String.format("%s on %s", SUCCESS, this.caseFormat));
    MessageValidatorMojo mojo = this.getMojo(String.format(TEMPLATE, this.caseFormat, SUCCESS));
    mojo.execute();
  }

  /**
   * These tests should fail.
   *
   * @throws Exception
   */
  @Test(expected = MojoFailureException.class)
  public void caseFail() throws Exception {
    System.out.println(String.format("%s on %s", FAIL, this.caseFormat));
    MessageValidatorMojo mojo = this.getMojo(String.format(TEMPLATE, this.caseFormat, FAIL));
    mojo.execute();
  }
}
