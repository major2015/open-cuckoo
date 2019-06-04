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

import java.util.Iterator;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.cuckoo.context.Scope;

/**
 * A map from {@link TagKey} to {@link TagValue} and {@link TagMetadata} that can be used to label
 * anything that is associated with a specific operation.
 *
 * <P>For example, {@code TagMap}s can be used to label stats, log message, or debugging
 * information.</P>
 *
 * @since 0.0.1
 */
@Immutable
public interface TagMap {

    /**
     * Returns an iterator over the tags in this {@code TagMap}.
     *
     * @return an iterator over the tags in this {@code TgaMao}.
     * @since 0.0.1
     */
    Iterator<Tag> getIterator();

    /**
     * Returns the {@code TagValue} associated with the given {@code TagKey}.
     *
     * @param tagKey tag key to return the value for.
     * @return the {@code TagValue} associated with the given {@code TagKey}, or {@code null} if no
     *      {@code Tag} with the given {@code tagKey} is in this {@code TagMap}
     */
    @Nullable
    TagValue getTagValue(TagKey tagKey);

    /**
     * Builder for the {@link TagMap} class.
     *
     * @since 0.0.1
     */
    interface Builder {
        /**
         * Sets the parent {@code TagMap} to use. If not set, the value of
         * {@code Tagger.getCurrentTagMap()}
         *
         * @param parent
         * @return
         */
        Builder setParent(TagMap parent);

        Builder setNoParent();

        Builder put(TagKey key, TagValue value, TagMetadata tagMetadata);

        Builder remove(TagKey key);

        TagMap build();

        Scope buildScoped();
    }
}
