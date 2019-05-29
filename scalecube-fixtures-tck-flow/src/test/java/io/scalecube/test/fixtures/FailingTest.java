package io.scalecube.test.fixtures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

// @WithFixture(value = FailingFixtureConstructor.class)
@WithFixture(value = FailingFixtureInSetup.class)
@ExtendWith(Fixtures.class)
public class FailingTest {

  @BeforeEach
  public void setUp(TestInfo testInfo) {
    System.out.println(testInfo.getDisplayName() + " started");
  }

  @TestTemplate
  public void failureDetection(TestInfo testInfo) {
    Assertions.fail("test should not run");
  }
}
