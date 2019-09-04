package io.scalecube.test.fixtures;

import java.util.List;
import org.opentest4j.TestAbortedException;

final class CompositeFixture implements Fixture {
  private final Fixture base;
  private final List<Fixture> parameters;

  public CompositeFixture(Fixture base, List<Fixture> parameters) {
    this.parameters = parameters;
    this.base = base;
  }

  @Override
  public void setUp() throws TestAbortedException {
    base.setUp();
  }

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    T t = this.base.proxyFor(clasz);
    if (t != null) {
      return t;
    }
    for (Fixture fixture : parameters) {
      t = fixture.proxyFor(clasz);
      if (t != null) {
        return t;
      }
    }
    return null;
  }

  @Override
  public void tearDown() {
    base.tearDown();
    for (Fixture fixture : parameters) {
      fixture.tearDown();
    }
  }

  @Override
  public String name() {
    return this.base.name();
  }

  public Fixture base() {
    return base;
  }
}
