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

import static io.scalecube.test.fixtures.repeat.Repeat.CURRENT_REPETITION_PLACEHOLDER;
import static io.scalecube.test.fixtures.repeat.Repeat.DISPLAY_NAME_PLACEHOLDER;
import static io.scalecube.test.fixtures.repeat.Repeat.FIXTURE_NAME_PLACEHOLDER;
import static io.scalecube.test.fixtures.repeat.Repeat.TOTAL_REPETITIONS_PLACEHOLDER;

import org.junit.jupiter.api.RepeatedTest;

/**
 * Display name formatter for a {@link RepeatedTest @RepeatedTest}.
 *
 * @since 5.0
 */
public class RepeatedTestDisplayNameFormatter {

  private final String pattern;
  private final String displayName;
  private final String fixtureName;

  public RepeatedTestDisplayNameFormatter(String pattern, String displayName, String fixtureName) {
    this.pattern = pattern;
    this.displayName = displayName;
    this.fixtureName = fixtureName;
  }

  String format(int currentRepetition, int totalRepetitions) {
    return this.pattern //
        .replace(FIXTURE_NAME_PLACEHOLDER, this.fixtureName)
        .replace(DISPLAY_NAME_PLACEHOLDER, this.displayName) //
        .replace(CURRENT_REPETITION_PLACEHOLDER, String.valueOf(currentRepetition)) //
        .replace(TOTAL_REPETITIONS_PLACEHOLDER, String.valueOf(totalRepetitions));
  }
}
