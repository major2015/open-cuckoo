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

package com.cuckoo.tags;

import javax.annotation.concurrent.Immutable;

import com.cuckoo.internal.StringUtils;
import com.cuckoo.internal.Utils;
import com.google.auto.value.AutoValue;

@Immutable
@AutoValue
public abstract class TagValue {

    /**
     * The maximum length for a tag value. The value is {@value #MAX_LENGTH}.
     *
     * @since 0.0.1
     */
    public static final int MAX_LENGTH = 255;

    TagValue() {}

    /**
     * Constructs a {@code TagValue} from given string. the string must meet the following
     * requirements:
     *
     * <ol>
     *     <li>It cannot be longer than {@link #MAX_LENGTH}</li>
     *     <li>It can only contain printable ASCII characters.</li>
     * </ol>
     *
     * @param value the tag value.
     * @return a {@code TagValue} from the given string.
     * @throws IllegalArgumentException if the {@code String} is not valid.
     * @since 0.0.1
     */
    public static TagValue create(String value) {
        Utils.checkArgument(isValid(value), "Invalid TagValue: %s", value);
        return new AutoValue_TagValue(value);
    }

    /**
     * Returns the tag value as a {@code String}.
     *
     * @return the tag value as a {@code String}.
     * @since 0.0.1
     */
    public abstract String asString();

    /**
     * Determines whether the given {@code String} is a valid tag value.
     *
     * @param value the tag value to be validated.
     * @return whether the value is valid.
     */
    private static boolean isValid(String value) {
        return value.length() <= MAX_LENGTH && StringUtils.isPrintableString(value);
    }
}
