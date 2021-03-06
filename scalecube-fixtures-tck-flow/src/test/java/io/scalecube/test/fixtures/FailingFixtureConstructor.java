package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.opentest4j.TestAbortedException;

public class FailingFixtureConstructor implements Fixture {

  private boolean setupStarted = false;

  public FailingFixtureConstructor() {
    throw new IllegalArgumentException();
  }

  @Override
  public void setUp() throws TestAbortedException {
    setupStarted = true;
    throw new TestAbortedException("Fixture Setup Failed");
  }

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void tearDown() {
    System.out.println(" terminating " + this.getClass().getSimpleName());
    assertFalse(setupStarted);
  }
}
