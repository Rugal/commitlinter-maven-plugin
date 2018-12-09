package ga.rugal.maven.plugin.rule;

import java.util.Locale;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import edu.stanford.nlp.simple.Sentence;

/**
 * Check tense related rules.
 *
 * @author Rugal Bernstein
 */
public class TenseRule {

  private static final Set<String> PAST_TENSE = ImmutableSet.of("VBN", "VBD");

  private static final Set<String> THIRD_PARTY_TENSE = ImmutableSet.of("VBZ");

  private static final Set<String> PRESENT_TENSE = ImmutableSet.of("VB", "VBP");

  private TenseRule() {
  }

  private static boolean matchTense(final Set<String> pattern, final String word) {
    final Sentence sent = new Sentence(word);
    final String toUpperCase = sent.posTag(0).toUpperCase(Locale.US);
    return pattern.contains(toUpperCase);
  }

  /**
   * Check word is past tense.
   *
   * @param word target word or sentence
   *
   * @return true if word is past tense, otherwise return false
   */
  public static boolean isPastTense(final String word) {
    return matchTense(PAST_TENSE, word);
  }

  /**
   * Check word is third party tense.
   *
   * @param word target word or sentence
   *
   * @return true if word is third party tense, otherwise return false
   */
  public static boolean isPresentTense(final String word) {
    return matchTense(PRESENT_TENSE, word);
  }

  /**
   * Check word is third party tense.
   *
   * @param word target word or sentence
   *
   * @return true if word is third party tense, otherwise return false
   */
  public static boolean isThirdPartyTense(final String word) {
    return matchTense(THIRD_PARTY_TENSE, word);
  }

  /**
   * Check text with given tense.
   *
   * @param word  The input string
   * @param tense The tense to accept
   *
   * @return If the input string matches the given case format
   */
  public static boolean validate(final String word, final Tense tense) {
    final boolean result;
    switch (tense) {
      case PRESENT:
        result = isPresentTense(word);
        break;
      case PAST:
        result = isPastTense(word);
        break;
      case THIRD_PARTY:
        result = isThirdPartyTense(word);
        break;
      case NONE:
      default:
        result = true;
    }
    return result;
  }
}
