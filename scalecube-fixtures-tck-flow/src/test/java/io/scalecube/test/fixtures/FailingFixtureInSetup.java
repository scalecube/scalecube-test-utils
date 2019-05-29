package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.opentest4j.TestAbortedException;

public class FailingFixtureInSetup implements Fixture {

  private boolean setupStarted = false;

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
    assertTrue(setupStarted);
  }
}
