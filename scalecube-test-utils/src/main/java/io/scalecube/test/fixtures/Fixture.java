package io.scalecube.test.fixtures;

import io.scalecube.services.Microservices;
import io.scalecube.services.discovery.api.ServiceDiscoveryConfig.Builder;
import java.util.function.Consumer;

public interface Fixture {
  Microservices setUp();

  static Consumer<Builder> joinServicesTo(Microservices anotherCluster) {
    return options -> options.seeds(anotherCluster.discovery().address());
  }

  void tearDown();
}
