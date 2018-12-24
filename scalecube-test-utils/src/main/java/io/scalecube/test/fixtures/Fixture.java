package io.scalecube.test.fixtures;

public interface Fixture {

  void setUp();

  <T> T call(Class<? extends T> clasz);

  void tearDown();
}
