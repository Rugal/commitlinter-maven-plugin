package ga.rugal.maven.plugin.rule;

import java.util.Locale;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import edu.stanford.nlp.simple.Document;

/**
 * Check tense related rules.
 *
 * @author Rugal Bernstein
 */
public class TenseRule {

  private static final Set<String> PAST_TENSE = ImmutableSet.of("VBN", "VBD");

  private static final Set<String> THIRD_PARTY_TENSE = ImmutableSet.of("VBZ");

  private TenseRule() {
  }

  private static boolean matchTense(final Set<String> pattern, final String word) {
    final Document doc = new Document(word);
    return pattern.contains(doc.sentences().get(0).nerTag(0).toUpperCase(Locale.US));
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
  public static boolean isThirdPartyTense(final String word) {
    return matchTense(THIRD_PARTY_TENSE, word);
  }
}
