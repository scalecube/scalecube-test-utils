package io.scalecube.test.fixtures;

import java.util.Properties;
@WithFixture(CallingInterfaceConstructor.class)
public class CallingInterfaceAndPropertiesConstructor extends BaseFixture implements Fixture {
  
  public CallingInterfaceAndPropertiesConstructor(InterfaceToSomeService service) {
    super(new Properties(), service);
  }
}
