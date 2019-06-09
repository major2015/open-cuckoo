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

import java.util.Map;

/**
 * Sampler is used to make decisions on {@link Span} sampling.
 */
public interface Sampler {

    /**
     * Returns the description of this {@code Sampler}. This may be displayed on debug pages or in
     * the logs.
     *
     * <p>Example: "ProbabilitySampler{0.000100}"
     *
     * @return the description of this {@code Sampler}.
     */
    String getDescription();

    /**
     * Sampling decision returned by {@link }
     */
    interface Decision {

        /**
         * Returns sampling decision whether span should be sampled or not.
         *
         * @return sampling decision.
         */
        boolean isSampled();

        /**
         * Returns tags which will be attached to the span.
         *
         * @return attributes added to span. These attributes should be added to the span ony for
         * root span or when sampling decision {@link #isSampled()} changes from false to true.
         */
        Map<String, AttributeValue> attributes();
    }
}
