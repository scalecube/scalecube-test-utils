package org.testcontainers.utility;

import io.scalecube.test.fixtures.BaseTest;
import io.scalecube.test.fixtures.Fixtures;
import io.scalecube.test.fixtures.WithFixture;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(DockerfileFixture.class)
public class TestDockerContainer extends BaseTest {}
