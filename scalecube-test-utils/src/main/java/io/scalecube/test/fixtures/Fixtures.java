package io.scalecube.test.fixtures;

import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

public class Fixtures implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback {

  private static final Function<? super Store, ? extends Fixture> getFixtureFromStore =
      store -> store.get("Fixture", Fixture.class);

  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    getStore(context).map(getFixtureFromStore).ifPresent(Fixture::tearDown);
  }

  @Override
  public void beforeAll(ExtensionContext context) throws FixtureCreationException {
    Class<?> requiredTestClass = context.getRequiredTestClass();
    Optional<Class<? extends Fixture>> fixtureClass =
        Optional.ofNullable(requiredTestClass)
            .filter(clz -> clz.isAnnotationPresent(WithFixture.class))
            .map(clz -> clz.getAnnotation(WithFixture.class))
            .map(WithFixture::value);

    if (fixtureClass.isPresent()) {
      Fixture fixture = FixtureFactory.getFixture(fixtureClass.get());
      getStore(context).ifPresent(store -> store.put("Fixture", fixture));
    }
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    context
        .getTestInstance()
        .filter(FixtureAwareTest.class::isInstance)
        .map(FixtureAwareTest.class::cast)
        .ifPresent(test -> test.fixture(getStore(context).map(getFixtureFromStore).get()));
  }

  private static Optional<Store> getStore(ExtensionContext context) {
    return Optional.of(Namespace.GLOBAL).map(context::getStore);
  }
}
