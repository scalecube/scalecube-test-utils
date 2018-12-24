package io.scalecube.test.fixtures;

public class FasterEchoServiceFixture implements Fixture {

  EchoService service;

  @Override
  public void setUp() {
    service = new EchoServiceImpl(30);
    System.out.println("faster service.init");
  }

  @Override
  public <T> T call(Class<? extends T> aClass) {
    if (aClass.isAssignableFrom(EchoService.class)) {
      return aClass.cast(service);
    } else {
      return null;
    }
  }

  @Override
  public void tearDown() {
    System.out.println("service.kill");
  }
}
