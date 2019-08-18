package io.scalecube.test.fixtures;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(value = FastFixture.class, lifecycle = Lifecycle.PER_CLASS)
class CompositeFixtureTest extends BaseTest {}
