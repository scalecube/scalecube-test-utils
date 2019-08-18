package io.scalecube.test.fixtures;

import org.opentest4j.TestAbortedException;

@WithFixture(FasterServiceFixture.class)
public class FastFixture implements Fixture {

  public FastFixture(EchoService echoService, PalindromeService palindromeService) {}

  @Override
  public void setUp() throws TestAbortedException {}

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    return null;
  }

  @Override
  public void tearDown() {}
}
