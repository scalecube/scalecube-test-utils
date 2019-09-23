package io.scalecube.test.fixtures;

import java.util.Properties;
import org.opentest4j.TestAbortedException;

public class InjectedFixture implements Fixture {
  private final InterfaceToSomeService service;
  private final InvocationSpyFixture spy;
  private final Properties p;

  public InjectedFixture(InterfaceToSomeService service, Properties p) {
    this.service = service;
    this.p = p;
    spy =
        new InvocationSpyFixture() {

          @Override
          public boolean constructorWithPropertiesWasCalled() {
            return true;
          }

          @Override
          public boolean constructorWithInterfaceWasCalled() {
            return true;
          }

          @Override
          public boolean constructorWasCalled() {
            return true;
          }
        };
  }

  @Override
  public void setUp() throws TestAbortedException {}

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
    if (clasz.isAssignableFrom(Properties.class)) {
      return clasz.cast(p);
    }
    return null;
  }

  @Override
  public void tearDown() {
    // TODO Auto-generated method stub

  }
}
