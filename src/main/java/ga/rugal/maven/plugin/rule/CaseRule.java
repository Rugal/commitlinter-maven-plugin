package ga.rugal.maven.plugin.rule;

import java.util.Locale;

/**
 * Check case format of text.
 *
 * @author Rugal Bernstein
 */
public class CaseRule {

  private CaseRule() {
  }

  /**
   * Check text with given format.
   *
   * @param text    The input string
   * @param pattern The case format to accept
   *
   * @return If the input string matches the given case format
   */
  public static boolean validate(final String text, final Kase pattern) {
    final boolean result;
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
   * @param text The input string
   *
   * @return If the input string matches the given case format
   */
  private static boolean isCamelCase(final String text) {
    return !text.contains("_")
           && !text.contains("-")
           && !text.contains(" ");
  }

  /**
   * Check specifically for upper camel case.<BR>
   * ThisIsUpperCamelCase
   *
   * @param text The input string
   *
   * @return If the input string matches the given case format
   */
  public static boolean isUpperCamelCase(final String text) {
    return isCamelCase(text) && Character.isUpperCase(text.charAt(0));
  }

  /**
   * Check specifically for lower camel case.<BR>
   * thisIsLowerCamelCase
   *
   * @param text The input string
   *
   * @return If the input string matches the given case format
   */
  public static boolean isLowerCamelCase(final String text) {
    return isCamelCase(text) && Character.isLowerCase(text.charAt(0));
  }

  /**
   * Check specifically for upper case.<BR>
   * THIS IS UPPER CASE / THISISALSOUPPERCASE
   *
   * @param text The input string
   *
   * @return If the input string matches the given case format
   */
  public static boolean isUpperCase(final String text) {
    return text.toUpperCase(Locale.ENGLISH).equals(text);
  }

  /**
   * Check specifically for lower case.<BR>
   * this is lower case / thisisalsolowercase
   *
   * @param text The input string
   *
   * @return If the input string matches the given case format
   */
  public static boolean isLowerCase(final String text) {
    return text.toLowerCase(Locale.ENGLISH).equals(text);
  }

  /**
   * Check specifically for kebab case.<BR>
   * this-is-kebab-case-which-comes-from-lisp
   *
   * @param text The input string
   *
   * @return If the input string matches the given case format
   */
  public static boolean isKebabCase(final String text) {
    return text.toLowerCase(Locale.ENGLISH).replaceAll("_", "-").replaceAll(" ", "-").equals(text);
  }

  /**
   * Check specifically for sentence case.<BR>
   * This is sentence case / It's first character of first word is capitalized
   *
   * @param text The input string
   *
   * @return If the input string matches the given case format
   */
  public static boolean isSentenceCase(final String text) {
    return Character.isUpperCase(text.charAt(0)) && isLowerCase(text.substring(1));
  }

  /**
   * Check specifically for snake case.<BR>
   * this_is_snake_case / which_is_used_a_lot_in_database_table_design
   *
   * @param text The input string
   *
   * @return If the input string matches the given case format
   */
  public static boolean isSnakeCase(final String text) {
    return text.toLowerCase(Locale.ENGLISH).replace(" ", "_").replace("-", "_").equals(text);
  }
}
