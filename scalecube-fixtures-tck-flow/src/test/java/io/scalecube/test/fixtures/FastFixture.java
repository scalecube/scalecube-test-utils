package io.scalecube.test.fixtures;

import org.junit.jupiter.api.Assumptions;
import org.opentest4j.TestAbortedException;

@WithFixture(FasterServiceFixture.class)
@WithFixture(InMemoryDatabaseFixture.class)
public class FastFixture implements Fixture {

  private EchoService echoService;
  private PalindromeService palindromeService;
  private DatasourceService datasourceService;

  public FastFixture(
      EchoService echoService,
      PalindromeService palindromeService,
      DatasourceService datasourceService) {
    // you can put here some assignments, but it is not compulsory because all delegations
    // are in CompositeFixture
    this.echoService = echoService;
    this.palindromeService = palindromeService;
    this.datasourceService = datasourceService;
  }

  @Override
  public void setUp() throws TestAbortedException {
    // here you should put the connectivity that you may want to make / verify
    // you should use the services from the parameters as is, no casting.
    // do not assume that they are not null
    Assumptions.assumeFalse(echoService == null);
    Assumptions.assumeFalse(palindromeService == null);
    Assumptions.assumeFalse(datasourceService == null);
  }

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    // here you should put proxy for something that does not exists in the services above.
    // no need to delegate proxies (unless you know that one of the services is null).
    return null;
  }

  @Override
  public void tearDown() {
    // put here the tear down of this class setup. should not tear down the services.
  }
}
