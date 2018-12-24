package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(EchoServiceFixture.class)
@WithFixture(FasterEchoServiceFixture.class)
//@TestInstance(Lifecycle.PER_CLASS)
class BaseTest {

  @BeforeAll
  private static void setup() {
    System.out.println("-------------------");
  }

  @TestTemplate
  public void test1(Fixture fixture) {
    EchoService echoService = fixture.call(EchoService.class);
    System.out.println("------ test 1 -----");
    System.out.println(echoService);
    assertEquals("A", echoService.echo("A"));
  }

  @TestTemplate
  public void test2(Fixture fixture) {
    EchoService echoService = fixture.call(EchoService.class);

    System.out.println("------ test 2 -----");
    System.out.println(echoService);
    assertEquals("B", echoService.echo("B"));
  }
  
  @TestTemplate
  public void test3(EchoService echoService) {
    System.out.println("------ test 3 -----");
    System.out.println(echoService);
    assertEquals("B", echoService.echo("B"));
  }

}
