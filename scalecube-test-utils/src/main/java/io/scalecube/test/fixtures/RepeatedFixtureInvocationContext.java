package io.scalecube.test.fixtures;

import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

public class RepeatedFixtureInvocationContext implements TestTemplateInvocationContext {

  private final Fixture fixture;
  private final int currentRepetition;
  private final int totalRepetitions;

  protected RepeatedFixtureInvocationContext(
      Fixture fixture, int currentRepetition, int totalRepetitions) {
    this.fixture = fixture;
    this.currentRepetition = currentRepetition;
    this.totalRepetitions = totalRepetitions;
  }

  public Fixture getFixture() {
    return this.fixture;
  }

  @Override
  public String getDisplayName(int invocationIndex) {
    return this.fixture.name() + "[  " + currentRepetition + " / " + totalRepetitions + " ]";
  }
}
