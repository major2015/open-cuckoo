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
 * An interface that represents a span. It has an associated {@link SpanContext}.
 *
 * <p>Spans are created by the {@link Builder#startSapn()} method.
 *
 * <p>{@code Span} <b>must</b> be ended by calling {@link #end()}.
 *
 * @since 0.0.1
 */
public interface Span {
    /**
     * Type of span. Can be used to specify additional relationships between spans in addition to a
     * parent/child relationship.
     *
     * @since 0.0.1
     */
    enum Kind {
        /**
         * Default value. Indicates that the span is used internally.
         *
         * @since 0.0.1
         */
        INTERNAL,

        /**
         * Indicates that the span covers server-side handing of an RPC or other remote request.
         *
         * @since 0.0.1
         */
        SERVER,

        /**
         * Indicates that the span covers the client-side wrapper around an RPC or other remote
         * request.
         *
         * @since 0.0.1
         */
        CLIENT,

        /**
         * Indicates that the span descrobes producer sending a message to a broker. Unlike client
         * and server, there is no direct critical path latency relationship between producer and
         * consumer spans.
         *
         * @since 0.0.1
         */
        PRODUCER,

        /**
         * Indicates that the span describes consumer receiving a message from a broker. Unlike
         * client and server, there is no direct critical path latency relationship between
         * producer and consumer spans.
         * @since 0.0.1
         */
        CONSUMER
    }

    /**
     * Sets an attribute to the {@code Span}. If the {@code Span} previously contained a mapping
     * for the key, the old value is replaced by the specified value.
     *
     * @param key the key for this attribute.
     * @param value the value for this attribute.
     */
    void setAttribute(String key, String value);

    /**
     * Sets an attribute to the {@code Span}. If the {@code Span} previously contained a mapping
     * for the key, the old value is replaced by the specified value.
     *
     * @param key the key for this attribute.
     * @param value the value for this attribute.
     */
    void setAttribute(String key, long value);

    /**
     * Sets an attribute to the {@code Span}. If the {@code Span} previously contained a mapping
     * for the key, the old value is replaced by the specified value.
     *
     * @param key the key for this attribute.
     * @param value the value for this attribute.
     */
    void setAttribute(String key, double value);

    /**
     * Sets an attribute to the {@code Span}. If the {@code Span} previously contained a mapping
     * for the key, the old value is replaced by the specified value.
     *
     * @param key the key for this attribute.
     * @param value the value for this attribute.
     */
    void setattribute(String key, boolean value);

    /**
     * Sets an attribute to the {@code Span}. If the {@code Span} previously contained a mapping
     * for the key, the old value is replaced by the specified value.
     *
     * @param key the key for this attribute.
     * @param value the value for this attribute.
     */
    void setAttribute(String key, AttributeValue value);

    /**
     * Adds an event to the {@code Span}.
     *
     * @param name the name of the event.
     */
    void addEvent(String name);

    /**
     * Adds an event to the {@code Span}.
     *
     * @param name the name of the event.
     * @param attributes the attributes that will be added; there are associated with this event,
     *                   not the {@code Span} as for {@code setAttribute()}.
     */
    void addEvent(String name, Map<String, AttributeValue> attributes);

    /**
     * Adds an event to the {@code Span}.
     *
     * @param event the event to add.
     */
    void addEvent(Event event);

    /**
     * Adds a {@link Link} to the {@code Span}.
     *
     * @param spanContext the context of the linked {@code Span}.
     * @throws NullPointerException if {@code spanContext} if {@code null}.
     * @see #addLink(Link)
     */
    void addLink(SpanContext spanContext);

    /**
     * Adds a {@link Link} to the {@code Span}.
     *
     * @param spanContext the context of the linked {@code Span}.
     * @param attributes the attributes of the {@code Link}.
     * @throws NullPointerException if {@code spanContext} is {@code null}.
     * @throws NullPointerException if {@code attributes} is {@code null}.
     * @see #addLink(Link)
     */
    void addLink(SpanContext spanContext, Map<String, AttributeValue> attributes);

    /**
     * Adds a {@link Link} to the {@code Span}.
     *
     * <p>Used (for example) in batching operations, where a single batch handler processes multiple
     * requests from different traces or the same trace.
     *
     * @param link the link to add.
     */
    void addLink(Link link);

    void setStatus()

    void end();

    interface Builder {
        Span startSapn();
    }
}
