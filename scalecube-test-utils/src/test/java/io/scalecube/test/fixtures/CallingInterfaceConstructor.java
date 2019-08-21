package io.scalecube.test.fixtures;

public class CallingInterfaceConstructor extends BaseFixture {
  private static InterfaceToSomeService service =
      new InterfaceToSomeService() {
        @Override
        public void method() {
          // here goes some code
        }
      };

  public CallingInterfaceConstructor() {
    super(service);
  }
}
