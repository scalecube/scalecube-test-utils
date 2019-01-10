package io.scalecube.test.fixtures;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface WithFixtures {
  /**
   * fixtures.
   *
   * @return all of the Fixture this test would like to have initialize with
   */
  WithFixture[] value();
}
