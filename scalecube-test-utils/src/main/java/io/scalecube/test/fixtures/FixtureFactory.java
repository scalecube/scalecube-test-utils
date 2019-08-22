package io.scalecube.test.fixtures;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

/**
 * An abstract provider for {@link Fixture}. This utility class does not implement {@link Supplier}
 * or {@link Function}.
 */
public class FixtureFactory {

  /**
   * Create a fixture object.
   *
   * @param fixtures fixtures extention
   * @param context the extention context that created this factory
   * @param withFixture annnotation
   * @return a fixture for the test.
   * @throws FixtureCreationException whenever creation is not possible.
   */
  public static Fixture getFixture(
      FixturesExtension fixtures, ExtensionContext context, WithFixture withFixture)
      throws FixtureCreationException {
    Class<? extends Fixture> baseFixtureClass = withFixture.value();
    try {
      List<WithFixture> fixturesToGet =
          AnnotationUtils.findRepeatableAnnotations(baseFixtureClass, WithFixture.class);

      if (fixturesToGet.isEmpty()) {
        Optional<Constructor<? extends Fixture>> constructorWithProperties =
            getConstructorWithProperties(baseFixtureClass);
        if (constructorWithProperties.isPresent()) {
          return constructorWithProperties.get().newInstance(fromEntries(withFixture.properties()));
        }
        return baseFixtureClass.newInstance();
      }

      Constructor<?>[] constructors = baseFixtureClass.getDeclaredConstructors();
      Constructor<?> constructor;
      if (constructors.length == 1) {
        constructor = constructors[0];
      } else {
        throw new FixtureCreationException(
            new ReflectiveOperationException(
                "Fixture factory requires only one constructor in "
                    + baseFixtureClass.getSimpleName()));
      }
      Parameter[] parameters = constructor.getParameters();
      Object[] arguments = new Object[parameters.length];
      ArrayList<Fixture> fixturesList = new ArrayList<>();
      for (WithFixture fixtureToGet : fixturesToGet) {
        Fixture f = fixtures.setUp(fixtureToGet, context).apply(fixtureToGet.value());
        fixturesList.add(f);
        for (int i = 0; i < parameters.length; i++) {
          if (arguments[i] == null) {
            Parameter parameter = parameters[i];
            if (parameter.getType().isAssignableFrom(Properties.class)) {
              arguments[i] = fromEntries(fixtureToGet.properties());
            } else {
              arguments[i] = f.proxyFor(parameter.getType());
            }
          }
        }
      }
      Fixture base = (Fixture.class.cast(constructor.newInstance(arguments)));
      return new CompositeFixture(base, fixturesList);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException cause) {
      throw new FixtureCreationException(cause);
    }
  }

  private static Optional<Constructor<? extends Fixture>> getConstructorWithProperties(
      Class<? extends Fixture> baseFixtureClass) {
    try {
      return Optional.ofNullable(baseFixtureClass.getDeclaredConstructor(Properties.class));
    } catch (NoSuchMethodException | SecurityException ignoredException) {
      return Optional.empty();
    }
  }

  private static Properties fromEntries(String[] entries) {
    Properties p = new Properties(System.getProperties());
    for (String entry : entries) {
      String[] split = entry.split("=");
      if (split.length == 2) {
        String key = split[0].trim();
        String value = split[1].trim();
        if (key.isEmpty()) {
          continue;
        }
        p.put(key, value);
      }
    }
    return p;
  }
}
