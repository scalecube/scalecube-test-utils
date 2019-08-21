package io.scalecube.test.fixtures;

public interface InvocationSpyFixture {

  boolean constructorWasCalled();

  boolean constructorWithPropertiesWasCalled();

  boolean constructorWithInterfaceWasCalled();
}
