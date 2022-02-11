package ga.rugal.maven.plugin.model;

import ga.rugal.maven.plugin.rule.Kase;
import ga.rugal.maven.plugin.rule.Tense;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Define rules to be enforced on a capture group.
 *
 * @author Rugal Bernstein
 */
@Getter
@NoArgsConstructor
public class CaptureGroup {

  private Kase caseFormat = Kase.NONE;

  private int max = Integer.MAX_VALUE;

  private int min = 0;

  private Tense tense = Tense.NONE;
}
