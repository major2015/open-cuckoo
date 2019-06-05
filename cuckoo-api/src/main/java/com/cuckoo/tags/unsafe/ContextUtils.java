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

package com.cuckoo.tags.unsafe;

import com.cuckoo.context.Scope;
import com.cuckoo.tags.EmptyTagMap;
import com.cuckoo.tags.TagMap;
import io.grpc.Context;

/**
 * Utility methods for accessing the {@link TagMap} contained in the {@link io.grpc.Context}.
 *
 * <p>Most code should interact with the current context  via the public APIs in {@link TagMap}
 * and avoid accessing this class directly.
 *
 * @since 0.0.1
 */
public final class ContextUtils {
    private static final Context.Key<TagMap> TAG_MAP_KEY =
        Context.keyWithDefault("opencuckoo-tag-map-key", EmptyTagMap.INSTANCE);

    private ContextUtils() {}

    /**
     * Creates a new {@code Context} with the given value set.
     *
     * @param tagMap the value to be set.
     * @return a new context with the given value set.
     */
    public static Context withValue(TagMap tagMap) {
        return Context.current().withValue(TAG_MAP_KEY, tagMap);
    }

    /**
     * Creates a new {@code Context} with the given value set.
     *
     * @param tagMap the value to be set.
     * @param context the parent {@code Context}.
     * @return a new Context with the given value set.
     */
    public static Context withValue(TagMap tagMap, Context context) {
        return context.withValue(TAG_MAP_KEY, tagMap);
    }

    /**
     * Returns the value from the current {@code Context}.
     *
     * @return the value form the current {@code Context}
     */
    public static TagMap getValue() {
        return TAG_MAP_KEY.get();
    }

    /**
     * Returns the value from the specified {@code Context}.
     *
     * @param context the specified {@code Context}.
     * @return the value from the specified {@code Context}.
     */
    public static TagMap getValue(Context context) {
        return TAG_MAP_KEY.get(context);
    }

    /**
     * Returns a new {@link Scope} encapsulating the provided {@code TagMap} added to the current
     * {@code Context}.
     *
     * @param tagMap the {@code TagMap} to be added to the current {@code Context}.
     * @return the {@link Scope} for the updated {@code Context}.
     */
    public static Scope withTagMap(TagMap tagMap) {
        return TagMapInScope.create(tagMap);
    }
}
