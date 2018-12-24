import io.scalecube.services.Microservices;
import io.scalecube.test.fixtures.Fixture;

public abstract class ScalecubeMicroservicesFixture implements Fixture {

  protected final Microservices microservices;

  protected ScalecubeMicroservicesFixture(Microservices microservices) {
    this.microservices = microservices;
  }

  @Override
  public <T> T call(Class<? extends T> service) {
    return microservices.call().create().api(service);
  }

  @Override
  public void tearDown() {
    microservices.shutdown().block();
  }
}
