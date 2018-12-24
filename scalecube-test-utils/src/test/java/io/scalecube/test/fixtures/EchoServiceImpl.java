package io.scalecube.test.fixtures;

import java.util.concurrent.TimeUnit;

public class EchoServiceImpl implements EchoService {

  private final int milliseconds;

  public EchoServiceImpl(int seconds) {
    this.milliseconds = seconds;
  }

  @Override
  public String echo(String s) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (InterruptedException ignoredException) {
      return ignoredException.getMessage();
    }
    return new String(s);
  }

  @Override
  public String toString() {
    return "Echo Service replying after " + milliseconds + "ms";
  }
}
