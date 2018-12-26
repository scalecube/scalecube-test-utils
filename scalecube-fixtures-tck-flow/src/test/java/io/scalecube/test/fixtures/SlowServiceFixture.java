package io.scalecube.test.fixtures;

public class SlowServiceFixture implements Fixture {

  EchoService echoService;
  PalindromeService palindromeService;

  @Override
  public void setUp() {
    echoService = new EchoServiceImpl(100);
    this.palindromeService = s -> new StringBuilder(s).reverse().toString().equals(s);
    System.out.println("service.init");
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
