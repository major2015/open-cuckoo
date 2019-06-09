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

package com.cuckoo.trace;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.concurrent.Immutable;

import com.google.auto.value.AutoValue;

/**
 * A link to a {@link Span}.
 *
 * <p>Used (for example) in batching operations, where a single batch handler processes multiple
 * requests from different traces. Link can be also used to reference spans from the same trace.
 *
 * @since 0.0.1
 */
@Immutable
@AutoValue
public abstract class Link {
    private static final Map<String, AttributeValue> EMPTY_ATTRIBUTES = Collections.emptyMap();

    /**
     * Returns a new {@code Link}.
     *
     * @param context the context of the linked {@code Span}.
     * @return a new {@code Link}.
     */
    public static Link create(SpanContext context) {
        return new AutoValue_Link(context, EMPTY_ATTRIBUTES);
    }

    /**
     * Returns a new {@code Link}.
     *
     * @param context the context of the linked {@code Span}.
     * @param attributes the attributes of the {@code Link}.
     * @return a new {@code Link}
     */
    public static Link create(SpanContext context, Map<String, AttributeValue> attributes) {
        return new AutoValue_Link(context, Collections.unmodifiableMap(new HashMap<>(attributes)));
    }

    /**
     * Returns the {@code TranceId}.
     *
     * @return the {@code TranceId}.
     */
    public abstract SpanContext getContext();

    /**
     * Returns the set of attributes.
     *
     * @return the set of attributes.
     */
    public abstract Map<String, AttributeValue> getAttributes();

    Link() {}
}
