package io.scalecube.test.fixtures.repeat;

/*
 * Copyright 2015-2019 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

import static org.apiguardian.api.API.Status.STABLE;

import org.apiguardian.api.API;

/**
 * {@code RepetitionInfo} is used to inject information about the current repetition of a repeated
 * test into {@code @Repeat}, {@code @BeforeEach}, and {@code @AfterEach} methods.
 *
 * <p>If a method parameter is of type {@code RepetitionInfo}, JUnit will supply an instance of
 * {@code RepetitionInfo} corresponding to the current repeated test as the value for the parameter.
 *
 * <p><strong>WARNING</strong>: {@code RepetitionInfo} cannot be injected into a {@code @BeforeEach}
 * or {@code @AfterEach} method if the corresponding test method is not a {@code @Repeat}. Any
 * attempt to do so will result in a {@link
 * org.junit.jupiter.api.extension.ParameterResolutionException ParameterResolutionException}.
 *
 * @since 5.0
 * @see Repeat
 * @see TestInfo
 */
@API(status = STABLE, since = "5.0")
public interface RepetitionInfo {

  /** Get the current repetition of the corresponding {@link Repeat @Repeat} method. */
  int getCurrentRepetition();

  /**
   * Get the total number of repetitions of the corresponding {@link Repeat @Repeat}
   * method.
   *
   * @see Repeat#value
   */
  int getTotalRepetitions();
}
