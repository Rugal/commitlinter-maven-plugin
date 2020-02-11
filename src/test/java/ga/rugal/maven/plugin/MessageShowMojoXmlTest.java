package ga.rugal.maven.plugin;

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
public class MessageShowMojoXmlTest extends BaseTest {

  @Parameters
  public static String[] data() {
    return new String[]{
      "lowercase"
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
    this.getMojo("show", this.caseFormat, SUCCESS).execute();
  }
}
