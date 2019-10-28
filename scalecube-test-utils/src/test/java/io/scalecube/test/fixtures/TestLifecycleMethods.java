package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(CallingDefaultConstructor.class)
public class TestLifecycleMethods {

  @BeforeEach
  private void init(InvocationSpyFixture spy, Properties p, Class<?> testType) {
    assertNotNull(spy);
  }

  @TestTemplate
  public void basicTest(InvocationSpyFixture spy, Properties p, Class<?> testType) {
    if (testType.isAssignableFrom(CallingDefaultConstructor.class)) {
      assertTrue(spy.constructorWasCalled());
      assertFalse(spy.constructorWithInterfaceWasCalled());
      assertFalse(spy.constructorWithPropertiesWasCalled());
    }
  }

  @AfterEach
  private void finish(InvocationSpyFixture spy, Properties p, Class<?> testType) {
    assertNotNull(spy);
  }
}
