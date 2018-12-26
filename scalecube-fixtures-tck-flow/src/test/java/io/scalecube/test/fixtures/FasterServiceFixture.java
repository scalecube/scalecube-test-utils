package io.scalecube.test.fixtures;

public class FasterServiceFixture implements Fixture {

  private EchoService echoService;
  private PalindromeService palindromeService;

  @Override
  public void setUp() {
    echoService = s -> s;
    palindromeService =
        s -> {
          int length = s.length();
          int i, begin, end, middle;
          begin = 0;
          end = length - 1;
          middle = (begin + end) / 2;

          for (i = begin; i <= middle; i++) {
            if (s.charAt(begin) == s.charAt(end)) {
              begin++;
              end--;
            } else {
              break;
            }
          }
          return (i == middle + 1);
        };
    System.out.println("faster service.init");
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
    System.out.println("faster service kill");
  }
}
