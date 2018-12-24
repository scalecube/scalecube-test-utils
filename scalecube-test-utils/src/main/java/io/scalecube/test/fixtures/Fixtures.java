package io.scalecube.test.fixtures;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionConfigurationException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;

public class Fixtures
    implements AfterAllCallback,
        TestTemplateInvocationContextProvider,
        BeforeEachCallback,
        BeforeAllCallback,
        ParameterResolver {

  private static Namespace namespace = Namespace.create(Fixtures.class);

  private static final Function<? super Store, ? extends Fixture> getFixtureFromStore =
      store -> store.get("Fixture", Fixture.class);

  private static Optional<Store> getStore(ExtensionContext context) {
    return Optional.of(namespace).map(context::getStore);
  }

  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    getStore(context).map(getFixtureFromStore).ifPresent(Fixture::tearDown);
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    Optional<? extends Fixture> fixture = getStore(context).map(getFixtureFromStore);

    fixture.ifPresent(Fixture::setUp);
    //    if (!fixture.isPresent()) {
    //      if (supportsTestTemplate(context)) {
    //        provideTestTemplateInvocationContexts(context)
    //            .findFirst()
    //            .map(FixtureInvocationContext.class::cast)
    //            .map(FixtureInvocationContext::getFixture)
    //            .ifPresent(Fixture::setUp);
    //      }
    //    }
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    //    Optional<? extends Fixture> fixture = getStore(context).map(getFixtureFromStore);
    //    context
    //        .getTestInstance()
    //        .filter(FixtureAwareTest.class::isInstance)
    //        .map(FixtureAwareTest.class::cast)
    //        .ifPresent(test -> fixture.ifPresent(test::fixture));
  }

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    List<WithFixture> fixtures =
        AnnotationUtils.findRepeatableAnnotations(
            context.getRequiredTestClass(), WithFixture.class);
    return !fixtures.isEmpty();
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
      ExtensionContext context) {
    return AnnotationUtils.findRepeatableAnnotations(
            context.getRequiredTestClass(), WithFixture.class)
        .stream()
        .map(WithFixture::value)
        .flatMap(
            fixtureClass -> {
              try {
                Fixture fixture = FixtureFactory.getFixture(fixtureClass);
                fixture.setUp();
                getStore(context).ifPresent(store -> store.put("Fixture", fixture));
                return Stream.of(fixture);
              } catch (FixtureCreationException ignoredException) {
                new ExtensionConfigurationException("unable to setup fixture", ignoredException)
                    .printStackTrace();
                return Stream.empty();
              }
            })
        .map(FixtureInvocationContext::new);
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return true;
    // parameterContext.getParameter().getType().isAssignableFrom(Fixture.class);
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    Optional<? extends Fixture> fixture = getStore(extensionContext).map(getFixtureFromStore);
    Class<?> paramType = parameterContext.getParameter().getType();
    if (paramType.isAssignableFrom(Fixture.class)) {
      return fixture.orElse(null);
    }
    return fixture.map(f -> f.call(paramType)).orElse(null);
  }
}
