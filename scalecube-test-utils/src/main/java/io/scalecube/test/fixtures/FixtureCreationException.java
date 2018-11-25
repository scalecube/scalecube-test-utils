package io.scalecube.test.fixtures;

public class FixtureCreationException extends Exception {

  public FixtureCreationException(ReflectiveOperationException cause) {
    super(cause);
  }
}
