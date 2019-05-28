package io.scalecube.test.fixtures.repeat;

import static org.apiguardian.api.API.Status.STABLE;

import io.scalecube.test.fixtures.Fixtures;
import org.apiguardian.api.API;

/**
 * {@code RepeatInfo} is used to inject information about the current repetition of a repeated test
 * into {@code @Repeat}, {@code @BeforeEach}, and {@code @AfterEach} methods.
 *
 * <p>If a method parameter is of type {@code RepeatInfo}, {@link Fixtures} will supply an instance
 * of {@code RepeatInfo} corresponding to the current repeated test as the value for the parameter.
 *
 * <p><strong>WARNING</strong>: {@code RepeatInfo} cannot be injected into a {@code @BeforeEach} or
 * {@code @AfterEach} method if the corresponding test method is not marked as {@code @Repeat}. Any
 * attempt to do so will result in a {@link
 * org.junit.jupiter.api.extension.ParameterResolutionException ParameterResolutionException}.
 *
 * @since 5.0
 * @see Repeat
 */
@API(status = STABLE, since = "5.0")
public interface RepeatInfo {

  /**
   * Get the current repetition of the corresponding {@link Repeat @Repeat} method.
   *
   * @return the current repetition index.
   */
  int getCurrentRepetition();

  /**
   * Get the total number of repetitions of the corresponding {@link Repeat @Repeat} method.
   *
   * @return the total number of repetitions.
   * @see Repeat#value
   */
  int getTotalRepetitions();
}
