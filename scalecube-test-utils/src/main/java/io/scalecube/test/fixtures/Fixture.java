package io.scalecube.test.fixtures;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opentest4j.TestAbortedException;

/**
 * A Fixture is an abstraction of a test environment. Some implementations would create the
 * environment itself, some may create some mocks, and others can just create client to access a
 * real running environment. The test should not care how the environment looks like.
 *
 * <p>Black-Box testing should:
 *
 * <ol>
 *   <li>The test class should be annotated with {@link ExtendWith} and {@link Fixtures}. class as
 *       value.
 *   <li>The test class should be annotated with {@link WithFixture} annotation(s) and some
 *       implementation classes as value.
 *   <li>each test should be annotated with {@link TestTemplate} annotataion.
 *   <li>each test should have some parameters (probably with interfaces type) which will be
 *       accessed during the test.
 * </ol>
 *
 * <p>e.g:
 *
 * <pre>{@code
 * @ExtendWith(Fixtures.class)
 * @WithFixture(SlowServiceFixture.class)
 * @WithFixture(FasterServiceFixture.class)
 * public class BaseTest {
 *
 * @TestTemplate()
 * public void test1(Fixture fixture) {
 * EchoService echoService = fixture.proxyFor(EchoService.class);
 * System.out.println("------ test 1 -----");
 * System.out.println(echoService);
 * assertEquals("A", echoService.echo("A"));
 * }
 *
 * @TestTemplate
 * public void test2(Fixture fixture) {
 * EchoService echoService = fixture.proxyFor(EchoService.class);
 * System.out.println("------ test 2 -----");
 * assertEquals("B", echoService.echo("B"));
 * }
 *
 * @TestTemplate
 * public void test3(EchoService echoService, PalindromeService palindromeService) {
 * System.out.println("------ test 3 -----");
 * assertTrue(palindromeService.palindrome(echoService.echo("CABAC")));
 * assertFalse(palindromeService.palindrome(echoService.echo("TEST")));
 * }
 * }
 * }</pre>
 */
public interface Fixture {

  /**
   * Set up the environment access. May throw a {@link TestAbortedException} if something went
   * wrong.
   */
  void setUp() throws TestAbortedException;

  /**
   * Create an access object to a component in the environment. An implementor should return null if
   * the requested object is invalid or not relevant to this type of an environment. An implementor
   * must not throw any runtime exception in this method, although an invocation of any method on
   * the returned object is subjected to failures of any type. The returned object can be either
   * mock, local or remote (using or not using {@link Proxy}).
   *
   * @param clasz the type of the object in the environment. mostly an interface.
   * @return an access object, nu
   */
  <T> T proxyFor(Class<? extends T> clasz);

  /**
   * Tear down the environment, close all resources needed in this fixture.
   *
   * <p>An implementor should not throw exceptions during teardown process.
   */
  void tearDown();

  default String name() {
    return getClass().getSimpleName();
  }
}
