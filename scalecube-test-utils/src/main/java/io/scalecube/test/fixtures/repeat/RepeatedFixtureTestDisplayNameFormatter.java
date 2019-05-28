package io.scalecube.test.fixtures.repeat;

import static io.scalecube.test.fixtures.repeat.Repeat.CURRENT_REPETITION_PLACEHOLDER;
import static io.scalecube.test.fixtures.repeat.Repeat.DISPLAY_NAME_PLACEHOLDER;
import static io.scalecube.test.fixtures.repeat.Repeat.FIXTURE_NAME_PLACEHOLDER;
import static io.scalecube.test.fixtures.repeat.Repeat.TOTAL_REPETITIONS_PLACEHOLDER;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

/**
 * Display name formatter for a {@link RepeatedTest @RepeatedTest}.
 *
 * @since 5.0
 */
public class RepeatedFixtureTestDisplayNameFormatter {

  private final String pattern;
  private final String displayName;
  private final String fixtureName;

  /**
   * Create a formatter for fixture and repeat info.
   *
   * @param pattern the pattern in {@link Repeat#name()}
   * @param displayName the {@link DisplayName}
   * @param fixtureName the name of the fixture running in this iteration.
   */
  public RepeatedFixtureTestDisplayNameFormatter(
      String pattern, String displayName, String fixtureName) {
    this.pattern = pattern;
    this.displayName = displayName;
    this.fixtureName = fixtureName;
  }

  String format(int currentRepetition, int totalRepetitions) {
    return this.pattern //
        .replace(FIXTURE_NAME_PLACEHOLDER, this.fixtureName)
        .replace(DISPLAY_NAME_PLACEHOLDER, this.displayName) //
        .replace(CURRENT_REPETITION_PLACEHOLDER, String.valueOf(currentRepetition)) //
        .replace(TOTAL_REPETITIONS_PLACEHOLDER, String.valueOf(totalRepetitions));
  }
}
