package ga.rugal.maven.plugin;

import ga.rugal.maven.plugin.rule.Kase;

import lombok.Data;

/**
 * Define rules to be enforced on a capture group.
 *
 * @author Rugal Bernstein
 */
@Data
public class CaptureGroup {

  private Kase caseFormat = Kase.NONE;

  private int max = Integer.MAX_VALUE;

  private int min = 0;
}
