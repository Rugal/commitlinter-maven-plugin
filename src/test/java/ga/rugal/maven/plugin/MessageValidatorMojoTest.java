package ga.rugal.maven.plugin;

import java.io.File;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Rugal
 */
@RunWith(Parameterized.class)
public class MessageValidatorMojoTest {

  private static final String SUCCESS = "success";

  private static final String FAIL = "fail";

  private static final String TEMPLATE = "src/test/resources/unittest/%s/%s.xml";

  @Parameters
  public static String[] data() {
    return new String[]{
      "lowercase", "uppercase", "uppercamelcase", "lowercamelcase", "sentencecase", "kebabcase",
      "snakecase", "none", "max", "min", "skip", "unmatch", "nocontent", "head", "gitfolder"
    };
  }

  @Parameter
  public String caseFormat;

  @Rule
  public MojoRule rule = new MojoRule();

  /**
   * Get Mojo object from each pom file.
   *
   * @param file
   * @return
   * @throws Exception
   */
  private MessageValidatorMojo getMojo(String file) throws Exception {
    File pom = new File(file);
    Assert.assertNotNull(pom);
    Assert.assertTrue(pom.exists());

    MessageValidatorMojo mojo = (MessageValidatorMojo) this.rule.lookupMojo("validate", pom);
    Assert.assertNotNull(mojo);
    return mojo;
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
