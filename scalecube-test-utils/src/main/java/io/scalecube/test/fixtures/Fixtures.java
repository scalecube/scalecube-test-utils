package io.scalecube.test.fixtures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.Extension;
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
import org.opentest4j.TestAbortedException;

/**
 * The main {@link Extension} of {@link Fixture}s.
 *
 * @see Fixture
 */
public class Fixtures
    implements AfterAllCallback,
        AfterEachCallback,
        TestTemplateInvocationContextProvider,
        ParameterResolver {

  private static final String FIXTURE = "Fixture";
  private static final String FIXTURE_LIFECYCLE = "FixtureLifecycle";
  private static final String FIXTURE_CLASS = "FixtureClass";

  private static Namespace namespace = Namespace.create(Fixtures.class);

  private final Map<Class<? extends Fixture>, Fixture> initializedFixtures = new HashMap<>();

  private static final Function<? super Store, ? extends Fixture> getFixtureFromStore =
      store -> store.get(FIXTURE, Fixture.class);

  private static Optional<Store> getStore(ExtensionContext context) {
    return Optional.of(namespace).map(context::getStore);
  }

  @Override
  public void afterAll(ExtensionContext extensionContext) throws Exception {
    tearDown(extensionContext);
  }

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    getStore(context)
        .ifPresent(
            store -> {
              Lifecycle lifecycle = store.get(FIXTURE_LIFECYCLE, TestInstance.Lifecycle.class);
              if (Lifecycle.PER_METHOD.equals(lifecycle)) {
                tearDown(context);
              }
            });
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
        .flatMap(
            withFixture -> {
              Fixture fixture =
                  initializedFixtures.computeIfAbsent(
                      withFixture.value(), setUp(withFixture.value()));
              Optional<ExtensionContext> storeInContext = Optional.of(context);
              Lifecycle lifecycle = withFixture.lifecycle();
              if (Lifecycle.PER_CLASS.equals(lifecycle)) {
                storeInContext = context.getParent();
              }
              if (fixture != null) {
                storeInContext.ifPresent(
                    ctx -> {
                      getStore(ctx)
                          .ifPresent(store -> store.put(FIXTURE_CLASS, withFixture.value()));
                      getStore(ctx).ifPresent(store -> store.put(FIXTURE_LIFECYCLE, lifecycle));
                      getStore(ctx).ifPresent(store -> store.put(FIXTURE, fixture));
                    });
                return Stream.of(fixture);
              } else {
                return Stream.empty();
              }
            })
        .map(FixtureInvocationContext::new);
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return getStore(extensionContext).map(getFixtureFromStore).isPresent();
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    Optional<? extends Fixture> fixture = getStore(extensionContext).map(getFixtureFromStore);
    Class<?> paramType = parameterContext.getParameter().getType();
    return fixture.map(f -> f.proxyFor(paramType)).orElse(null);
  }

  private static Function<? super Class<? extends Fixture>, ? extends Fixture> setUp(
      Class<? extends Fixture> fixtureClass) {
    return clz -> {
      Fixture f;
      try {
        f = FixtureFactory.getFixture(fixtureClass);
        f.setUp();
        return f;
      } catch (FixtureCreationException fixtureCreationException) {
        throw new TestAbortedException(
            "unable to setup fixture",
            new ExtensionConfigurationException(
                "unable to setup fixture", fixtureCreationException));
      } catch (TestAbortedException abortedException) {
        throw abortedException;
      }
    };
  }

  private void tearDown(ExtensionContext extensionContext) {
    getStore(extensionContext)
        .ifPresent(
            store -> {
              Optional<Fixture> fixture = Optional.ofNullable(store.get(FIXTURE, Fixture.class));
              store.remove(FIXTURE);
              store.remove(FIXTURE_LIFECYCLE);

              fixture.ifPresent(Fixture::tearDown);
              fixture.map(Object::getClass).ifPresent(initializedFixtures::remove);
              fixture.map(Object::getClass).ifPresent(store::remove);
            });
  }
}
