package io.scalecube.test.fixtures;

import org.opentest4j.TestAbortedException;

public class CallingInterfaceConstructor implements Fixture {
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
  private static InterfaceToSomeService service =
      new InterfaceToSomeService() {
        @Override
        public void method() {
          // here goes some code
        }
      };

  public CallingInterfaceConstructor() {
    constructorWithInterfaceWasCalled = true;
  }

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    if (clasz.isAssignableFrom(InterfaceToSomeService.class)) {
      return clasz.cast(service);
    }
    if (clasz.isAssignableFrom(InvocationSpyFixture.class)) {
      return clasz.cast(this.spy);
    }
    if (clasz.isAssignableFrom(Class.class)) {
      return clasz.cast(this.getClass());
    }
    return null;
  }

  @Override
  public void setUp() throws TestAbortedException {}

  @Override
  public void tearDown() {}
}
