package io.scalecube.test.fixtures;

import java.util.Properties;

@WithFixture(CallingInterfaceConstructor.class)
public class CallingPropertiesAndInterfaceConstructor extends BaseFixture implements Fixture {

  public CallingPropertiesAndInterfaceConstructor(InterfaceToSomeService service) {
    super(service, new Properties());
  }
}
