package io.scalecube.test.fixtures;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(value = FasterServiceFixture.class, lifecycle = Lifecycle.PER_CLASS)
@WithFixture(value = SlowServiceFixture.class, lifecycle = Lifecycle.PER_METHOD)
public class SimpleInMemoryTest extends BaseTest {}
