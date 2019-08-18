package io.scalecube.test.fixtures;

import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An abstract provider for {@link Fixture}. This utility class does not implement {@link Supplier}
 * or {@link Function}.
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
      Fixture fixture = fixtureClass.newInstance();
      if (fixtureClass.isAnnotationPresent(WithFixtures.class)) {
        for (Field f : fixtureClass.getFields()) {
          Class<?> type = f.getType();
          if (type.isAssignableFrom(Fixture.class)) {
            Fixture newFixtureForField = getFixture(type.asSubclass(Fixture.class));
            f.set(fixture, newFixtureForField);
          }
        }
      }
      return fixture;
    } catch (InstantiationException | IllegalAccessException cause) {
      throw new FixtureCreationException(cause);
    }
  }
}
