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
    void setAttribute(String key, boolean value);

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

    /**
     * Sets the {@link Status} to the {@code Span}.
     *
     * <p>If used, this will override the default {@code Span} status. Default is
     * {@link Status#OK}.
     *
     * <p>Only the value of last call will be recorded, and implementations are free to ignore
     * previous calls.
     *
     * @param status the {@link Status} to set.
     */
    void setStatus(Status status);

    /**
     * Marks the end of {@code Span} execution.
     *
     * <p>Only the timing of the first end call for a given {@code Span} will be recorded, and
     * implementations are free to ignore all further calls.
     */
    void end();

    /**
     * Updates the {@code Span} name.
     *
     * <p>If used, this will override the name provided via {@code Span.Builder}.
     *
     * <p>Upon this update, any sampling behavior based on {@code Span} name will depend on the
     * implementation.
     *
     */
    void updateName(String name);

    /**
     * Returns the {@code SpanContext} associated with this {@code Span}.
     *
     * @return the {@code SpanContext} associated with this {@code Span}.
     */
    SpanContext getContext();

    /**
     * Returns {@code true} if this {@code Span} records events (e.g., {@link #addEvent(String)}.
     *
     * @return {@code true} if this {@code Span} records events.
     */
    boolean isRecordingEvents();

    interface Builder {
        /**
         * Sets the parent {@code Span} to use. If not set, the value of
         * {@code Tracer.GetCurrentSpan()} at {@link #startSapn()} time will be used as parent.
         *
         * <p>This <b>must</b> be used to create a {@code Span} when manual Context propagation is
         * used OR when creating a root {@code Span} with a parent with an invalid
         * {@link SpanContext}.
         *
         * <p>Observe this is the preferred method when the parent is a {@code Span} created within
         * the process. Using its {@code SpanContext} as parent remains as a valid, albeit
         * inefficient, operation.
         *
         * <p>If called multiple times, only the last specified value will be used. Observe that
         * the state defined by a previous call to {@link #setNoParent()} will be discarded.
         *
         * @param parent the {@code Span} used to parent.
         * @return this.
         * @throws NullPointerException if {@code parent} is {@code null}.
         * @see #setNoParent()
         */
        Builder setParent(Span parent);

        /**
         * Sets the parent {@link SpanContext} to use. If not set, the value of
         * {@code Tracer.getCurrentSpan()} at {@link #startSapn()} time will be used as parent.
         *
         * <p>Similar to {@link #setParent(Span parent)} but this <b>must</b> be used to create a
         * {@code Span} when the parent is in a different process. This is only intended for use by
         * RPC systems or similar.
         *
         * <p>If no {@link SpanContext} is available, users must call {@link #setNoParent()} in
         * order to create a root {@code Span} for a new trace.
         *
         * <p>If called multiple times, only last specified value will be used. Observe that the
         * state defined by a previous call to {@link #setNoParent()} will be discarded.
         *
         * @param remoteParent the {@code SpanContext} used as parent.
         * @return this.
         * @throws NullPointerException if {@code remoteParent} is {@code null}.
         * @see #setParent(Span)
         * @see #setNoParent()
         */
        Builder setParent(SpanContext remoteParent);

        /**
         * sets the option to become a root {@code Span} for a new trace. If not set, the value of
         * {@code Tracer.getCurrentSpan()} at {@link #startSapn()} time will be used as parent.
         *
         * <p>Observe that any previously set parent will be discarded.
         *
         * @return this.
         */
        Builder setNoParent();

        Builder setSampler();

        Span startSapn();
    }
}
