package ga.rugal.maven.plugin.model;

import ga.rugal.maven.plugin.rule.CaseRule;
import ga.rugal.maven.plugin.rule.LengthRule;
import ga.rugal.maven.plugin.rule.TenseRule;

import lombok.AllArgsConstructor;
import org.apache.maven.plugin.logging.Log;

/**
 * To check rules against a matched string.
 *
 * @author Rugal Bernstein
 */
@AllArgsConstructor
public class RuleChecker {

  private final CaptureGroup captureGroup;

  private final Log log;

  /**
   * Check a matched string with given rules.
   *
   * @param match The input string
   *
   * @return How many failed checks in this string
   */
  public int check(final String match) {
    int failed = 0;
    this.log.info(String.format("Linting: [%s]", match));
    if (!CaseRule.validate(match, this.captureGroup.getCaseFormat())) {
      this.log.error(String.format("    Case format should be %s",
                                   this.captureGroup.getCaseFormat().name()));
      failed++;
    }
    if (!TenseRule.validate(match, this.captureGroup.getTense())) {
      this.log.error(String.format("    Tense of initial word should be %s",
                                   this.captureGroup.getTense().name()));
      failed++;
    }
    if (!LengthRule.fitMax(match, this.captureGroup.getMax())) {
      this.log.error(String.format("    Length should be no more than %d",
                                   this.captureGroup.getMax()));
      failed++;
    }
    if (!LengthRule.fitMin(match, this.captureGroup.getMin())) {
      this.log.error(String.format("    Length should be no less than %d",
                                   this.captureGroup.getMin()));
      failed++;
    }
    return failed;
  }
}
