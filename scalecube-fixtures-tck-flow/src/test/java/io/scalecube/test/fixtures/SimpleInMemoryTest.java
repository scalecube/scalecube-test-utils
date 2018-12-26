package io.scalecube.test.fixtures;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(FasterServiceFixture.class)
@WithFixture(SlowServiceFixture.class)
public class SimpleInMemoryTest extends BaseTest {}
