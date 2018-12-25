package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(SlowServiceFixture.class)
@WithFixture(FasterServiceFixture.class)
class BaseTest {

  @BeforeAll
  private static void setup() {
    System.out.println("-------------------");
  }

  @TestTemplate()
  public void test1(Fixture fixture) {
    EchoService echoService = fixture.proxyFor(EchoService.class);
    System.out.println("------ test 1 -----");
    System.out.println(echoService);
    assertEquals("A", echoService.echo("A"));
  }

  @TestTemplate
  public void test2(Fixture fixture) {
    EchoService echoService = fixture.proxyFor(EchoService.class);
    System.out.println("------ test 2 -----");
    assertEquals("B", echoService.echo("B"));
  }

  @TestTemplate
  public void test3(EchoService echoService, PalindromeService palindromeService) {
    System.out.println("------ test 3 -----");
    assertTrue(palindromeService.palindrome(echoService.echo("CABAC")));
    assertFalse(palindromeService.palindrome(echoService.echo("TEST")));
  }
}
