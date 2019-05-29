package io.scalecube.test.fixtures.repeat;

import io.scalecube.test.fixtures.Fixture;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

public class RepeatedFixtureInvocationContext implements TestTemplateInvocationContext, RepeatInfo {

  private final Fixture fixture;
  private final int currentRepetition;
  private final int totalRepetitions;
  private final RepeatedFixtureTestDisplayNameFormatter formatter;

  /**
   * Create a new RepeatedFixtureInvocationContext.
   * @param fixture the current fixture to run on
   * @param currentRepetition the current index of the repetition
   * @param totalRepetitions the total repetitions to do.
   * @param formatter display name formatter for a {@link Repeat @Repeat}.
   */
  public RepeatedFixtureInvocationContext(
      Fixture fixture,
      int currentRepetition,
      int totalRepetitions,
      RepeatedFixtureTestDisplayNameFormatter formatter) {
    this.fixture = fixture;
    this.currentRepetition = currentRepetition;
    this.totalRepetitions = totalRepetitions;
    this.formatter = formatter;
  }

  public Fixture getFixture() {
    return this.fixture;
  }

  @Override
  public String getDisplayName(int invocationIndex) {
    return this.formatter.format(this);
  }

  @Override
  public int getCurrentRepetition() {
    return this.currentRepetition;
  }

  @Override
  public int getTotalRepetitions() {
    return this.totalRepetitions;
  }
}
