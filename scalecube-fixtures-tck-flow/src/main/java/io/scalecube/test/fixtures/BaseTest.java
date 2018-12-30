package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.TestTemplate;

/** Extend this test with your annotations. you may add some more tests. */
public class BaseTest {

  /**
   * basic test.
   *
   * @param echoService an implementation of the {@link EchoService}
   * @param palindromeService an implementation of the {@link PalindromeService}
   */
  @TestTemplate
  public void test(EchoService echoService, PalindromeService palindromeService) {
    System.out.println("------ test 2 services -----");
    assertTrue(palindromeService.palindrome(echoService.echo("MADAM")));
    assertFalse(palindromeService.palindrome(echoService.echo("TEST")));
    assertTrue(palindromeService.palindrome(echoService.echo("level")));
  }

  /**
   * basic test.
   *
   * @param echoService an implementation of the {@link EchoService}
   */
  @TestTemplate
  public void test2(EchoService echoService) {
    System.out.println("------ test 1 service -----");
    assertEquals("TEST", echoService.echo("TEST"));
  }

  /**
   * basic test.
   *
   * @param echoService an implementation of the {@link EchoService}
   */
  @TestTemplate
  public void testAgain(EchoService echoService) {
    System.out.println("------ test 1 service -----");
    assertEquals("test", echoService.echo("test"));
  }
}
