package ga.rugal.maven.plugin;

import java.io.File;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runners.Parameterized.Parameter;

/**
 *
 * @author Rugal Bernstein
 */
@Ignore
public class BaseTest {

  public static final String SUCCESS = "success";

  public static final String FAIL = "fail";

  public static final String TEMPLATE = "src/test/resources/unittest/%s/%s.xml";

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
  protected MessageValidatorMojo getMojo(String file) throws Exception {
    File pom = new File(file);
    Assert.assertNotNull(pom);
    Assert.assertTrue(pom.exists());

    MessageValidatorMojo mojo = (MessageValidatorMojo) this.rule.lookupMojo("validate", pom);
    Assert.assertNotNull(mojo);
    return mojo;
  }
}
