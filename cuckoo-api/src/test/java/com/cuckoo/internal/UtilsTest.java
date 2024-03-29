/*
 * Copyright 2019, OpenCuckoo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cuckoo.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link Utils}.
 */
@RunWith(JUnit4.class)
public class UtilsTest {
    private static final String TEST_MESSAGE = "test message";
    private static final String TEST_MESSAGE_TEMPLATE = "I ate %s apples.";
    private static final int TEST_MESSAGE_VALUE = 2;
    private static final String FORMATTED_SIMPLE_TEST_MESSAGE = "I ate 2 apples.";
    private static final String FORMATTED_COMPLEX_TEST_MESSAGE = "I ate 2 apples. [2]";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkArgument() {
        Utils.checkArgument(true, TEST_MESSAGE);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(TEST_MESSAGE);
        Utils.checkArgument(false, TEST_MESSAGE);
    }

    @Test
    public void checkArgument_NullErrorMessage() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("null");
        Utils.checkArgument(false, null);
    }

    @Test
    public void checkArgument_WithSimpleFormat() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(FORMATTED_SIMPLE_TEST_MESSAGE);
        Utils.checkArgument(false, TEST_MESSAGE_TEMPLATE, TEST_MESSAGE_VALUE);
    }

    @Test
    public void checkArgument_WithComplexFormat() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(FORMATTED_COMPLEX_TEST_MESSAGE);
        Utils.checkArgument(false, TEST_MESSAGE_TEMPLATE, TEST_MESSAGE_VALUE, TEST_MESSAGE_VALUE);
    }
}