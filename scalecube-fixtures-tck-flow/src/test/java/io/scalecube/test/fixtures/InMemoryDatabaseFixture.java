package io.scalecube.test.fixtures;

import org.opentest4j.TestAbortedException;

public class InMemoryDatabaseFixture implements Fixture {

  private DatasourceService data;

  @Override
  public void setUp() throws TestAbortedException {
    data = new InMemoryDatabaseService();
  }

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    if (clasz.isAssignableFrom(DatasourceService.class)) {
      return clasz.cast(data);
    }
    return null;
  }

  @Override
  public void tearDown() {
    data.keys().forEach(data::delete);
  }
}
