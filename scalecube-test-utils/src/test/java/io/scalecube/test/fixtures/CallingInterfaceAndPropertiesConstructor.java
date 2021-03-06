package io.scalecube.test.fixtures;

import java.util.Properties;
import org.opentest4j.TestAbortedException;

@WithFixture(CallingInterfaceConstructor.class)
public class CallingInterfaceAndPropertiesConstructor implements Fixture {

  private final InvocationSpyFixture spy =
      new InvocationSpyFixture() {

        @Override
        public boolean constructorWithPropertiesWasCalled() {
          return constructorWithPropertiesWasCalled;
        }

        @Override
        public boolean constructorWithInterfaceWasCalled() {
          return constructorWithInterfaceWasCalled;
        }

        @Override
        public boolean constructorWasCalled() {
          return constructorWasCalled;
        }
      };
  private boolean constructorWasCalled = false;
  private boolean constructorWithPropertiesWasCalled = false;
  private boolean constructorWithInterfaceWasCalled = false;
  private final Properties properties;

  public CallingInterfaceAndPropertiesConstructor(InterfaceToSomeService service, Properties p) {
    constructorWithInterfaceWasCalled = service != null;
    this.properties = p;
    constructorWithPropertiesWasCalled = p != null;
  }

  @Override
  public void setUp() throws TestAbortedException {
    // do nothing
  }

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    if (clasz.isAssignableFrom(InvocationSpyFixture.class)) {
      return clasz.cast(this.spy);
    }
    if (clasz.isAssignableFrom(Class.class)) {
      return clasz.cast(this.getClass());
    }
    if (clasz.isAssignableFrom(Properties.class)) {
      return clasz.cast(properties);
    }
    return null;
  }

  @Override
  public void tearDown() {
    // do nothing
  }
}
