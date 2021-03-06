package io.scalecube.test.fixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(Fixtures.class)
@WithFixture(value = FastFixture.class, lifecycle = Lifecycle.PER_CLASS)
class CompositeFixtureTest extends BaseTest {

  @Test
  public void verifyDataIntegrity(DatasourceService service) {
    service.put("A", "B");
    assertEquals("B", service.get("A"));
  }

  @Test
  public void verifyDataIntegrity2(DatasourceService data, EchoService echo) {
    data.put("A", "B");
    assertEquals("B", echo.echo(data.get("A")));
  }

  @Test
  public void verifyDataIntegrity3(DatasourceService data, PalindromeService palindromeService) {
    data.put("A", "CAFEEFAC");
    assertTrue(palindromeService.palindrome(data.get("A")));
  }
}
