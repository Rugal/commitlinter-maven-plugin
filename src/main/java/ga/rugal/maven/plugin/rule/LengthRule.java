package ga.rugal.maven.plugin.rule;

/**
 * Check length related rules.
 *
 * @author Rugal Bernstein
 */
public class LengthRule {

  /**
   * Check text length is no more than max.
   *
   * @param text
   * @param max
   * @return
   */
  public static boolean fitMax(String text, int max) {
    return text.length() <= max;
  }

  /**
   * Check text length is no less than min.
   *
   * @param text
   * @param min
   * @return
   */
  public static boolean fitMin(String text, int min) {
    return text.length() >= min;
  }
}
