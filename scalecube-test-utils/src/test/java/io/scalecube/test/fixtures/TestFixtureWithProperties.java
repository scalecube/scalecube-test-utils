package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Properties;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(CallingDefaultConstructor.class)
@WithFixture(CallingPropertiesConstructor.class)
@WithFixture(value = WithSomeProperties.class, properties = "property=value")
@WithFixture(value = WithSomeProperties.class, properties = "property=valu11e")
public class TestFixtureWithProperties {

  @TestTemplate
  public void basicTest(InvocationSpyFixture spy, Properties p, Class<?> testType) {
    if (testType.isAssignableFrom(CallingDefaultConstructor.class)) {
      assertTrue(spy.constructorWasCalled());
      assertFalse(spy.constructorWithInterfaceWasCalled());
      assertFalse(spy.constructorWithPropertiesWasCalled());
    } else if (testType.isAssignableFrom(CallingPropertiesConstructor.class)) {
      assertFalse(spy.constructorWasCalled());
      assertFalse(spy.constructorWithInterfaceWasCalled());
      assertTrue(spy.constructorWithPropertiesWasCalled());
    } else if (testType.isAssignableFrom(WithSomeProperties.class)) {
      assertFalse(spy.constructorWasCalled());
      assertFalse(spy.constructorWithInterfaceWasCalled());
      assertTrue(spy.constructorWithPropertiesWasCalled());
      assertEquals("value", p.getProperty("property"));
    } else {
      fail();
    }
  }
}
