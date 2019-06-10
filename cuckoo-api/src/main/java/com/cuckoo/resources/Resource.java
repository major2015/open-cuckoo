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

package com.cuckoo.resources;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.cuckoo.internal.StringUtils;
import com.cuckoo.internal.Utils;
import com.google.auto.value.AutoValue;

/**
 * {@link Resource} represents a resource, which capture identifying information about the entities
 * for which signals (stats or traces) are reported.
 */
@Immutable
@AutoValue
public abstract class Resource {
    private static final int MAX_LENGTH = 255;
    private static final String ERROR_MESSAGE_INVALID_CHARS =
        " should be a ASCII string with a length greater than 0 and not exceed "
        + MAX_LENGTH
        + " characters.";
    private static final String ERROR_MESSAGE_INVALID_VALUE =
        " should be a ASCII string with a length not exceed " + MAX_LENGTH + " characters.";
    private static final Resource EMPTY = new AutoValue_Resource(Collections.emptyMap());

    Resource() {}

    public static Resource getEmpty() {
        return EMPTY;
    }

    /**
     * Returns a map of labels that describe the resource.
     *
     * @return a map of labels.
     */
    public abstract Map<String, String> getLabels();

    /**
     * Returns a {@link Resource}.
     *
     * @param labels a map of labels that describe the resource.
     * @return a {@code Resource}.
     * @throws NullPointerException if {@code labels} is null.
     * @throws IllegalArgumentException if label key or label value is not a valid printable ASCII
     *     string or exceed {@link #MAX_LENGTH} characters.
     */
    public static Resource create(Map<String, String> labels) {
        checkLabels(labels);
        return new AutoValue_Resource(labels);
    }

    /**
     * Returns a new, merged {@link Resource} by merging the current {@code Resource} with the
     * {@code other} {@code Resource}. In case of a collision, current {@code Resource} takes
     * precedence.
     *
     * @param other the {@code Resource} that will be merged with {@code this}.
     * @return the newly merged {@code Resource}.
     */
    public Resource merge(@Nullable Resource other) {
        if (other == null) {
            return this;
        }

        Map<String, String> mergedLabelMap = new LinkedHashMap<>(other.getLabels());
        // Labels from resource overwrite labels from otherResource.
        for (Map.Entry<String, String> entry : this.getLabels().entrySet()) {
            mergedLabelMap.put(entry.getKey(), entry.getValue());
        }
        return new AutoValue_Resource(mergedLabelMap);
    }

    private static void checkLabels(Map<String, String> labels) {
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            Utils.checkArgument(
                isValidAndNotEmpty(entry.getKey()), "Label key" + ERROR_MESSAGE_INVALID_CHARS);
            Utils.checkArgument(
                isValid(entry.getValue()), "Label value" + ERROR_MESSAGE_INVALID_VALUE);
        }
    }

    /**
     * Determines whether the given {@code String} is a valid printable ASCII string with a length not
     * exceed {@link #MAX_LENGTH} characters.
     *
     * @param name the name to be validated.
     * @return whether the name is valid.
     */
    private static boolean isValid(String name) {
        return name.length() <= MAX_LENGTH && StringUtils.isPrintableString(name);
    }

    /**
     * Determines whether the given {@code String} is a valid printable ASCII string with a length
     * greater than 0 and not exceed {@link #MAX_LENGTH} characters.
     *
     * @param name the name to be validated.
     * @return whether the name is valid.
     */
    private static boolean isValidAndNotEmpty(String name) {
        return !name.isEmpty() && isValid(name);
    }

}
