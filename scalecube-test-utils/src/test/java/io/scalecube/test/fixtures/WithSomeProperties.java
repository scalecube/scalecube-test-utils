package io.scalecube.test.fixtures;

import java.util.Properties;

public class WithSomeProperties extends BaseFixture implements Fixture {
  public WithSomeProperties(Properties p) {
    super(p);
  }

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    if (clasz.isAssignableFrom(Properties.class)) {
      return clasz.cast(properties);
    }
    return super.proxyFor(clasz);
  }
}
