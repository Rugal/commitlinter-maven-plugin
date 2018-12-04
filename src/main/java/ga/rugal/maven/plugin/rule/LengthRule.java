package ga.rugal.maven.plugin.rule;

/**
 * Check length related rules.
 *
 * @author Rugal Bernstein
 */
public class LengthRule {

  private LengthRule() {
  }

  /**
   * Check text length is no more than max.
   *
   * @param text The input string
   * @param max  The maximum length accepted
   * @return If the input string follows the rule
   */
  public static boolean fitMax(final String text, final int max) {
    return text.length() <= max;
  }

  /**
   * Check text length is no less than min.
   *
   * @param text The input string
   * @param min  The minimum length accepted
   * @return If the input string follows the rule
   */
  public static boolean fitMin(final String text, final int min) {
    return text.length() >= min;
  }
}
