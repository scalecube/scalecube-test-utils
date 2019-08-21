package io.scalecube.test.fixtures;

import java.util.Properties;

public class CallingPropertiesConstructor extends BaseFixture implements Fixture {
  public CallingPropertiesConstructor() {
    super(new Properties());
  }
}
