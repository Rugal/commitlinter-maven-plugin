package ga.rugal.maven.plugin;

import ga.rugal.maven.plugin.rule.Kase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Define rules to be enforced on a capture group.
 *
 * @author Rugal Bernstein
 */
@Getter
@NoArgsConstructor
@Setter
public class CaptureGroup {

  private Kase caseFormat = Kase.NONE;

  private int max = Integer.MAX_VALUE;

  private int min = 0;
}
