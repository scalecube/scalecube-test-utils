package io.scalecube.test.fixtures;

import java.util.function.Function;
import org.junit.jupiter.api.extension.ExtensionContext;

interface FixturesExtension {

  Function<? super Class<? extends Fixture>, ? extends Fixture> setUp(
      WithFixture withFixture, ExtensionContext context);
}
