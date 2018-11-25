package io.scalecube.test.fixtures;

import java.util.function.Function;
import java.util.function.Supplier;

/** 
 * An abstract provider for {@link Fixture}.
 * This utility class does not implement {@link Supplier} or {@link Function}.
 */
public class FixtureFactory {

  /**
   * Create a fixture object.
   *
   * @param fixtureClass the class of the fixture object to be constructed.
   * @return a fixture for the test.
   * @throws FixtureCreationException whenever creation is not possible.
   */
  public static Fixture getFixture(Class<? extends Fixture> fixtureClass)
      throws FixtureCreationException {
    try {
      return fixtureClass.newInstance();
    } catch (InstantiationException | IllegalAccessException cause) {
      throw new FixtureCreationException(cause);
    }
  }
}
