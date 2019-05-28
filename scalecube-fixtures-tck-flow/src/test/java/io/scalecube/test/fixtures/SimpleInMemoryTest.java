package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.scalecube.test.fixtures.repeat.Repeat;
import io.scalecube.test.fixtures.repeat.RepeatInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(value = FasterServiceFixture.class, lifecycle = Lifecycle.PER_CLASS)
@WithFixture(value = SlowServiceFixture.class, lifecycle = Lifecycle.PER_METHOD)
public class SimpleInMemoryTest extends BaseTest {

  @Repeat(4)
  @TestTemplate
  @DisplayName("Welcome")
  public void test3(EchoService echoService, RepeatInfo info) {
    assertNotNull(echoService);
    System.out.println(
        "+++++++++++ in exeution "
            + info.getCurrentRepetition()
            + " of "
            + info.getTotalRepetitions()
            + " ECHO IS "
            + echoService.toString());
  }

  @Repeat(3)
  @TestTemplate
  @DisplayName("REPEATED 3 TIMES")
  public void testRepated(RepeatInfo info) {
    assertNotNull(info);
    System.out.println(
        "@@@ Exeution "
            + info.getCurrentRepetition()
            + " of "
            + info.getTotalRepetitions()
            + " @@@");
  }

  @TestTemplate
  @DisplayName("NOT REPEATED")
  public void testNotRepated(RepeatInfo info) {
    assertEquals(null, info);
  }
}
