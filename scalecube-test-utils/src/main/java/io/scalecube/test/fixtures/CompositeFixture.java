package io.scalecube.test.fixtures;

import java.util.List;
import java.util.stream.Stream;
import org.opentest4j.AssertionFailedError;
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
    for (Fixture inParameters : parameters) {
      inParameters.setUp();
    }
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
    Exception exception = new Exception();
    for (Fixture fixture : parameters) {
      try {
        fixture.tearDown();
      } catch (Exception suppressException) {
        exception.addSuppressed(suppressException);
      }
    }
    try {
      base.tearDown();
    } catch (Exception mainException) {
      if (exception.getSuppressed().length != 0) {
        Stream.of(exception.getSuppressed()).forEach(mainException::addSuppressed);
      }
      throw mainException;
    }
    if (exception.getSuppressed().length != 0) {
      throw new AssertionFailedError("tear down failed", exception);
    }
  }

  @Override
  public String name() {
    return this.base.name();
  }
}