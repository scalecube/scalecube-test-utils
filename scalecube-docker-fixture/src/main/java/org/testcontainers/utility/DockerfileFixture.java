package org.testcontainers.utility;

import io.scalecube.test.fixtures.EchoService;
import io.scalecube.test.fixtures.Fixture;
import io.scalecube.test.fixtures.PalindromeService;
import java.io.IOException;
import org.opentest4j.TestAbortedException;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

public class DockerfileFixture implements Fixture {

  private EchoService echoService;
  private PalindromeService palindromeService;
  private GenericContainer<?> genericContainer;

  @Override
  public void setUp() {

    try {
      ImageFromDockerfile imageFromDockerfile =
          new ImageFromDockerfile()
              .withDockerfileFromBuilder(
                  builder -> builder.from("ubuntu").entryPoint("sleep infinity").build());
      genericContainer = new GenericContainer<>(imageFromDockerfile);
      genericContainer.start();
    } catch (Exception cause) {
      throw new TestAbortedException("unable to start docker", cause);
    }

    echoService =
        s -> {
          try {
            return genericContainer.execInContainer("echo", s).getStdout().trim();
          } catch (UnsupportedOperationException
              | IOException
              | InterruptedException ignoredException) {
            return ignoredException.getMessage();
          }
        };

    palindromeService =
        s -> {
          try {
            StringBuilder cmd =
                new StringBuilder("if [ \"`echo ")
                    .append(s)
                    .append(" | rev`\" = \"")
                    .append(s)
                    .append("\" ];")
                    .append(" then echo true;")
                    .append(" else echo false;")
                    .append("fi");

            ExecResult execResult =
                genericContainer.execInContainer("/bin/bash", "-c", cmd.toString());
            return Boolean.valueOf(execResult.getStdout().trim());
          } catch (UnsupportedOperationException
              | IOException
              | InterruptedException ignoredException) {
            ignoredException.printStackTrace();
            return false;
          }
        };
  }

  @Override
  public <T> T proxyFor(Class<? extends T> clasz) {
    if (EchoService.class.isAssignableFrom(clasz)) {
      return clasz.cast(echoService);
    } else if (PalindromeService.class.isAssignableFrom(clasz)) {
      return clasz.cast(palindromeService);
    }
    return null;
  }

  @Override
  public void tearDown() {
    genericContainer.close();
  }
}
