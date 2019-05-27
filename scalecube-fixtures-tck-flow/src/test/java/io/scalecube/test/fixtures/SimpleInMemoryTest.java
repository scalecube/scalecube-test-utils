package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(value = FasterServiceFixture.class, lifecycle = Lifecycle.PER_CLASS)
@WithFixture(value = SlowServiceFixture.class, lifecycle = Lifecycle.PER_METHOD)
public class SimpleInMemoryTest extends BaseTest {

  @RepeatedTest(2)
  @TestTemplate
  @DisplayName("Welcome ")
  public void test3(EchoService echoService) {
    assertNotNull(echoService);
    System.out.println("+++++++++++ ECHO IS " + echoService.toString());
    super.test2(echoService);
  }
}
