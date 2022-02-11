package ga.rugal.maven.plugin;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Rugal Bernstein
 */
public class HookMojoTest extends BaseTest {

  /**
   * These tests should success.
   *
   * @throws Exception
   */
  @SneakyThrows
  @Test
  public void caseSuccess() {
    final var mojo = this.getMojo("hook", "head", SUCCESS);
    Assert.assertNotNull(mojo);
    mojo.execute();
  }
}
