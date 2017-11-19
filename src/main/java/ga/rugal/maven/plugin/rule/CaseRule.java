package ga.rugal.maven.plugin.rule;

import java.util.Locale;

/**
 * Check case format of text.
 *
 * @author Rugal Bernstein
 */
public class CaseRule {

  /**
   * Check text with given format.
   *
   * @param text
   * @param pattern
   *
   * @return
   */
  public static boolean validate(String text, Kase pattern) {
    boolean result;
    switch (pattern) {
      case LOWERCASE:
        result = isLowerCase(text);
        break;
      case UPPERCASE:
        result = isUpperCase(text);
        break;
      case UPPERCAMELCASE:
        result = isUpperCamelCase(text);
        break;
      case LOWERCAMELCASE:
        result = isLowerCamelCase(text);
        break;
      case KEBABCASE:
        result = isKebabCase(text);
        break;
      case SNAKECASE:
        result = isSnakeCase(text);
        break;
      case SENTENCECASE:
        result = isSentenceCase(text);
        break;
      case NONE:
      default:
        result = true;
    }
    return result;
  }

  /**
   * Check text is camel case.
   *
   * @param text
   *
   * @return
   */
  private static boolean isCamelCase(String text) {
    return !text.contains("_")
           && !text.contains("-")
           && !text.contains(" ");
  }

  /**
   * Check specifically for upper camel case.<BR>
   * ThisIsUpperCamelCase
   *
   * @param text
   *
   * @return
   */
  public static boolean isUpperCamelCase(String text) {
    return isCamelCase(text) && Character.isUpperCase(text.charAt(0));
  }

  /**
   * Check specifically for lower camel case.<BR>
   * thisIsLowerCamelCase
   *
   * @param text
   *
   * @return
   */
  public static boolean isLowerCamelCase(String text) {
    return isCamelCase(text) && Character.isLowerCase(text.charAt(0));
  }

  /**
   * Check specifically for upper case.<BR>
   * THIS IS UPPER CASE / THISISALSOUPPERCASE
   *
   * @param text
   *
   * @return
   */
  public static boolean isUpperCase(String text) {
    return text.toUpperCase(Locale.ENGLISH).equals(text);
  }

  /**
   * Check specifically for lower case.<BR>
   * this is lower case / thisisalsolowercase
   *
   * @param text
   *
   * @return
   */
  public static boolean isLowerCase(String text) {
    return text.toLowerCase(Locale.ENGLISH).equals(text);
  }

  /**
   * Check specifically for kebab case.<BR>
   * this-is-kebab-case-which-comes-from-lisp
   *
   * @param text
   *
   * @return
   */
  public static boolean isKebabCase(String text) {
    return text.toLowerCase(Locale.ENGLISH).replaceAll("_", "-").replaceAll(" ", "-").equals(text);
  }

  /**
   * Check specifically for sentence case.<BR>
   * This is sentence case / It's first character of first word is capitalized
   *
   * @param text
   *
   * @return
   */
  public static boolean isSentenceCase(String text) {
    return Character.isUpperCase(text.charAt(0)) && isLowerCase(text.substring(1));
  }

  /**
   * Check specifically for snake case.<BR>
   * this_is_snake_case / which_is_used_a_lot_in_database_table_design
   *
   * @param text
   *
   * @return
   */
  public static boolean isSnakeCase(String text) {
    return text.toLowerCase(Locale.ENGLISH).replace(" ", "_").replace("-", "_").equals(text);
  }
}
