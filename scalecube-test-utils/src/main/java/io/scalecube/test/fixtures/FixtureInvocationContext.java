package io.scalecube.test.fixtures;

import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

public class FixtureInvocationContext implements TestTemplateInvocationContext {

  private final Fixture fixture;

  protected FixtureInvocationContext(Fixture fixture) {
    this.fixture = fixture;
  }

  public Fixture getFixture() {
    return this.fixture;
  }
}
