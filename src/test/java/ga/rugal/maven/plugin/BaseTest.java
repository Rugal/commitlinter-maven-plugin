package ga.rugal.maven.plugin;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
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

  private static final String TEMPLATE = "src/test/resources/unittest/%s/%s.xml";

  @Parameter
  public String caseFormat;

  @Rule
  public MojoRule rule = new MojoRule();

  /**
   * Get Mojo object from each pom file.
   *
   * @param mojoName   name of mojo
   * @param caseFormat string case format
   * @param result     expected result
   *
   * @return created mojo
   *
   * @throws Exception unable to find target mojo
   */
  protected AbstractMojo getMojo(final String mojoName,
                                 final String caseFormat,
                                 final String result) throws Exception {
    final File pom = new File(String.format(TEMPLATE, caseFormat, result));
    Assert.assertTrue(pom.exists());
    Assert.assertNotNull(pom);
    Assert.assertTrue(pom.exists());

    final AbstractMojo mojo = (AbstractMojo) this.rule.lookupMojo(mojoName, pom);
    Assert.assertNotNull(mojo);
    return mojo;
  }
}
