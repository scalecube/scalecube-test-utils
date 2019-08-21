package io.scalecube.test.fixtures;

import java.util.Properties;
import org.opentest4j.TestAbortedException;

public class BaseFixture implements Fixture {

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
  protected final Properties properties;

  protected BaseFixture() {
    constructorWasCalled = true;
    this.properties = System.getProperties();
  }

  protected BaseFixture(Properties p) {
    this.properties = p;
    constructorWithPropertiesWasCalled = true;
  }

  protected BaseFixture(InterfaceToSomeService withSomeInterface) {
    constructorWithInterfaceWasCalled = true;
    this.properties = System.getProperties();
    withSomeInterface.method();
  }

  protected BaseFixture(Properties p, InterfaceToSomeService withSomeInterface) {
    constructorWithInterfaceWasCalled = true;
    constructorWithPropertiesWasCalled = true;
    this.properties = p;
    withSomeInterface.method();
  }

  protected BaseFixture(InterfaceToSomeService withSomeInterface, Properties p) {
    constructorWithInterfaceWasCalled = true;
    constructorWithPropertiesWasCalled = true;
    this.properties = p;
    withSomeInterface.method();
  }

  @Override
  public void setUp() throws TestAbortedException {
    properties.get("");
  }

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    if (clasz.isAssignableFrom(InvocationSpyFixture.class)) {
      return clasz.cast(this.spy);
    }
    if (clasz.isAssignableFrom(Class.class)) {
      return clasz.cast(this.getClass());
    }
    return null;
  }

  @Override
  public void tearDown() {
    properties.clear();
  }
}
