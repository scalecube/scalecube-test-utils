# Test utilities for Scalecube
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b0ccd1fb212142dba806884f839bdaf5)](https://www.codacy.com/app/ScaleCube/scalecube-test-utils?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=scalecube/scalecube-test-utils&amp;utm_campaign=Badge_Grade)

[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/b0ccd1fb212142dba806884f839bdaf5)](https://www.codacy.com/app/ScaleCube/scalecube-test-utils?utm_source=github.com&utm_medium=referral&utm_content=scalecube/scalecube-test-utils&utm_campaign=Badge_Coverage)

As a test developer I want to have the ability to test some components in multiple types of environment.

Some environments would be with mocks.
Some would mock only data-layers and create all components in this test JVM.
Some environments would be as docker containers plus real machines hybrid. **You name it!!!**

But as the tests writer - I truly don't care. 

So what's the plan?

## Let's split environments and tests

let's say we have two interfaces:

```java
package io.scalecube.test.fixtures;

@FunctionalInterface
public interface EchoService {
  String echo(String s);
}
```
And 
```java
@FunctionalInterface
public interface PalindromeService {
  boolean palindrome(String s);
}
```

Both of them are in the system under test.

### The test

```java

package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(SlowServiceFixture.class)
@WithFixture(FasterServiceFixture.class)
public class BaseTest {

  @TestTemplate
  public void test(EchoService echoService, PalindromeService palindromeService) {
    System.out.println("------ test -----");
    assertTrue(palindromeService.palindrome(echoService.echo("CABAC")));
    assertFalse(palindromeService.palindrome(echoService.echo("TEST")));
  }
}
```

This test is testing EchoService and PalindromeService.
As you can see, these are given to the test code as plain parameters to the test method as interfaces.
So where are the concrete objects?
in the Fixtures: here is an example of a tested system in-memory `SlowServiceFixture` and `FasterServiceFixture`:

### The fixtures

```java
public class SlowServiceFixture implements Fixture {

  EchoService echoService;
  PalindromeService palindromeService;

  @Override
  public void setUp() {
    echoService = s -> new StringBuilder(s).reverse().reverse().reverse().reverse().toString();
    this.palindromeService = s -> new StringBuilder(s).reverse().reverse().reverse().reverse().reverse().toString().equals(s);
    System.out.println("service.init");
  }

  @Override
  public <T> T proxyFor(Class<? extends T> aClass) {
    if (aClass.isAssignableFrom(EchoService.class)) {
      return aClass.cast(echoService);
    }
    if (aClass.isAssignableFrom(PalindromeService.class)) {
      return aClass.cast(palindromeService);
    } else {
      return null;
    }
  }

  @Override
  public void tearDown() {
    System.out.println("echo service kill");
  }
}
``` 
And...
```java
public class FasterServiceFixture implements Fixture {

  EchoService echoService;
  PalindromeService palindromeService;

  @Override
  public void setUp() {
    echoService = s -> new StringBuilder(s).toString();
    this.palindromeService = s -> new StringBuilder(s).reverse().toString().equals(s);
    System.out.println("service.init");
  }

  @Override
  public <T> T proxyFor(Class<? extends T> aClass) {
    if (aClass.isAssignableFrom(EchoService.class)) {
      return aClass.cast(echoService);
    }
    if (aClass.isAssignableFrom(PalindromeService.class)) {
      return aClass.cast(palindromeService);
    } else {
      return null;
    }
  }

  @Override
  public void tearDown() {
    System.out.println("echo service kill");
  }
}
```

Obviously, there are more options, like:

``` java
public class DockerfileFixture implements Fixture {

  private EchoService echoService;
  private PalindromeService palindromeService;
  GenericContainer genericContainer;

  @Override
  public void setUp() {

    ImageFromDockerfile imageFromDockerfile =
        new ImageFromDockerfile()
            .withDockerfileFromBuilder(
                builder -> builder.from("ubuntu").entryPoint("sleep infinity").build());
    genericContainer = new GenericContainer<>(imageFromDockerfile);
    genericContainer.start();
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

            ExecResult execResult = genericContainer.execInContainer("/bin/bash","-c", cmd.toString());
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
```
which will do the same in dockers!
