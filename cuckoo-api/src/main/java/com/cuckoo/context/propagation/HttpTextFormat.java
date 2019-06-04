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

package com.cuckoo.context.propagation;

import java.util.List;
import javax.annotation.Nullable;

/**
 * Injects and extracts a value as text into carriers that travel in-bran across process
 * boundaries.
 * Encoding is expected to conform to the HTTP Header Field semantics. Values are often
 * encoded as RPC/HTTP request headers.
 *
 * <p>The carrier of propagated data on both the client (injector) and server (extractor) side is
 * usually an http request. Propagation is usually implemented via library- specific request
 * interceptors, where the client-side injects values and the server-side extracts them.
 *
 * @param <V>
 * @since 0.0.1
 */
public interface HttpTextFormat<V> {
    /**
     * The propagation fields defined. If your carrier is reused, you should delete the fields here
     * before calling {@link #inject(Object, Object, Setter)}.
     *
     * <p>For example, if the carrier is a single-use or immutable request object, you don't need
     * to clear fields as they couldn't have been set before. If it is a mutable, retryable object,
     * successive calls should clear there fields first.</p>
     *
     * @return list of fields that will be used by this formatter.
     * @since 0.0.1
     */
    // the use cases of this are:
    // * allow pre-allocation of fields, especially in system like gRPC Metadata
    // * allow a single-pass over an iterator
    List<String> fields();

    /**
     * Injects the value downstream. For example, as http headers.
     *
     * @param value the value to be injected.
     * @param carrier holds propagation fields. For example, an outgoing message or http request.
     * @param setter invoked for each propagation key to add or remove.
     * @param <C> carrier of propagation fields, such as an http request.
     * @since 0.0.1
     */
    <C> void inject(V value, C carrier, Setter<C> setter);

    /**
     * Extracts the value from upstream. For example, as http headers.
     *
     * <p>If the value could not be parsed, the underlying implementation will decide to return an
     * object representing either an empty value, an invalid value, or a valid value.
     * Implementation must return {@code null}.
     *
     * @param carrier holds propagation fields. For example, an outgoing message or http request.
     * @param getter invoked for each propagation key to get.
     * @param <C> carrier of propagation fields, such as an http request.
     * @return the extracted value, never {@code null}.
     * @since 0.0.1
     */
    <C> V extract(C carrier, Getter<C> getter);

    /**
     * Class that allows a {@code HttpTextFormat} to set propagated fields into a carrier.
     *
     * <p>{@code Setter} is stateless and allows to be saved as a constant to avoid runtime
     * allocations.</p>
     *
     * @param <C> carrier of propagation fields, such as an http request.
     * @since 0.0.1
     */
    interface Setter<C> {
        /**
         * Replaces a propagated field with the given value.
         *
         * <p>For example, a setter for an {@link java.net.HttpURLConnection} would be the method
         * reference {@link java.net.HttpURLConnection#addRequestProperty(String, String)}</p>
         *
         * @param carrier holds propagation fields. For example, an outgoing message or http
         *                request.
         * @param key the key of the field.
         * @param value the value of the field.
         * @since 0.0.1
         */
        void put(C carrier, String key, String value);
    }

    /**
     * Interface that allows a {@code HttpTextFormat} to read propagated fields from a carrier.
     *
     * <p>{@code Getter} is stateless and allows to be saved as a constant to avoid runtime
     * allocations.
     *
     * @param <C> carrier of propagation fields, such as an http request.
     * @since 0.0.1
     */
    interface Getter<C> {

        /**
         * Returns the first value of the given propagation {@code key} or returns {@code null}.
         *
         * @param carrier carrier of propagation fields, such as an http request.
         * @param key the key of the field.
         * @return the first value of the given propagation {@code key} or returns {@code null}.
         * @since 0.0.1
         */
        @Nullable
        String get(C carrier, String key);
    }
}
