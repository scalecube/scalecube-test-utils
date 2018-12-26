package io.scalecube.test.fixtures;

import java.util.concurrent.TimeUnit;

public class SlowServiceFixture implements Fixture {

  private EchoService echoService;
  private PalindromeService palindromeService;

  @Override
  public void setUp() {
    echoService = s -> new StringBuilder(sleep(s)).toString();
    palindromeService = s -> new StringBuilder(sleep(s)).reverse().toString().equals(sleep(s));
    System.out.println("service.init");
  }

  private static String sleep(String s) {
    try {
      TimeUnit.MILLISECONDS.sleep(2 * s.length());
    } catch (InterruptedException ignoredException) {
    }
    return s;
  }

  @Override
  public <T> T proxyFor(Class<? extends T> aClass) {
    if (aClass.isAssignableFrom(EchoService.class)) {
      return aClass.cast(echoService);
    }
    if (aClass.isAssignableFrom(PalindromeService.class)) {
      return aClass.cast(palindromeService);
    } else {
      return null;
    }
  }

  @Override
  public void tearDown() {
    System.out.println("echo service kill");
  }
}
