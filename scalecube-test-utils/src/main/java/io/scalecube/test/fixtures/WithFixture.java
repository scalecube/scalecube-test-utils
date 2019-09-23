package io.scalecube.test.fixtures;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(WithFixtures.class)
public @interface WithFixture {
  /**
   * Type of fixture.
   *
   * @return the type of the Fixture this test would like to have initialize with
   */
  Class<? extends Fixture> value();

  /**
   * The lifecycle of fixture.
   *
   * @return {@link Lifecycle#PER_CLASS} (default) for a fixture that should setup {@link BeforeAll}
   *     test executions and tears down {@link AfterAll} tests, or {@link Lifecycle#PER_METHOD} for
   *     a fixture that setup {@link BeforeEach} test execution and tears down {@link AfterEach}
   */
  TestInstance.Lifecycle lifecycle() default Lifecycle.PER_CLASS;

  /**
   * Properties for this fixture. use the format "k=v"
   *
   * @return
   */
  String[] properties() default {};

  /**
   * Dependency for this fixture. Injection for proxies provided by these fixtures is done from the
   * constructor.
   *
   * @return a list of fixtures that this fixture might depend on.
   * @implNote properties from the test will also be injected to the dependent class via the sole
   *     constructor.
   */
  Class<? extends Fixture>[] dependsOn() default {};
}
