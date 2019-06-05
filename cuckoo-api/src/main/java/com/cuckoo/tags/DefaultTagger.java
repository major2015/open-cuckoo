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

import java.util.Collections;
import java.util.List;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import com.cuckoo.context.NoopScope;
import com.cuckoo.context.Scope;
import com.cuckoo.context.propagation.BinaryFormat;
import com.cuckoo.context.propagation.HttpTextFormat;
import com.cuckoo.internal.Utils;
import com.cuckoo.tags.unsafe.ContextUtils;

/**
 * No-op implementations of {@link Tagger}.
 *
 * @since 0.0.1
 */
@ThreadSafe
public final class DefaultTagger implements Tagger {
    private static final DefaultTagger INSTANCE = new DefaultTagger();
    private static final BinaryFormat<TagMap> BINARY_FORMAT = new NoopBinaryFormat();
    private static final HttpTextFormat<TagMap> HTTP_TEXT_FORMAT = new NoopHttpTextFormat();

    /**
     * Returns a {@code Tagger} singleton that is the default implementation for {@link Tagger}.
     *
     * @return a {@code Tagger} singleton that is the default implementation for {@link Tagger}.
     */
    public static DefaultTagger getInstance() {
        return INSTANCE;
    }

    @Override
    public TagMap getCurrentTagMap() {
        return ContextUtils.getValue();
    }

    @Override
    public TagMap.Builder tagMapBuilder() {
        return new NoopTagMapBuilder();
    }

    @Override
    public Scope withTagMap(TagMap tags) {
        return ContextUtils.withTagMap(tags);
    }

    @Override
    public BinaryFormat<TagMap> getBinaryFormat() {
        return BINARY_FORMAT;
    }

    @Override
    public HttpTextFormat<TagMap> getHttpTextFormat() {
        return HTTP_TEXT_FORMAT;
    }

    @Immutable
    private static final class NoopTagMapBuilder implements TagMap.Builder {

        @Override
        public TagMap.Builder setParent(TagMap parent) {
            Utils.checkNotNull(parent, "parent");
            return this;
        }

        @Override
        public TagMap.Builder setNoParent() {
            return this;
        }

        @Override
        public TagMap.Builder put(TagKey key, TagValue value, TagMetadata tagMetadata) {
            Utils.checkNotNull(key, "key");
            Utils.checkNotNull(value, "value");
            Utils.checkNotNull(tagMetadata, "tagMetadata");
            return this;
        }

        @Override
        public TagMap.Builder remove(TagKey key) {
            Utils.checkNotNull(key, "key");
            return this;
        }

        @Override
        public TagMap build() {
            return EmptyTagMap.INSTANCE;
        }

        @Override
        public Scope buildScoped() {
            return NoopScope.INSTANCE;
        }
    }

    @Immutable
    private static final class NoopBinaryFormat implements BinaryFormat<TagMap> {

        static final byte[] EMPTY_BYTE_ARRAY = {};

        @Override
        public byte[] toByteArray(TagMap tags) {
            Utils.checkNotNull(tags, "tags");
            return EMPTY_BYTE_ARRAY;
        }

        @Override
        public TagMap fromByteArray(byte[] bytes) {
            Utils.checkNotNull(bytes, "bytes");
            return EmptyTagMap.INSTANCE;
        }
    }

    @Immutable
    private static final class NoopHttpTextFormat implements HttpTextFormat<TagMap> {

        @Override
        public List<String> fields() {
            return Collections.emptyList();
        }

        @Override
        public <C> void inject(TagMap tagContext, C carrier, Setter<C> setter) {
            Utils.checkNotNull(tagContext, "tagContext");
            Utils.checkNotNull(carrier, "carrier");
            Utils.checkNotNull(setter, "setter");
        }

        @Override
        public <C> TagMap extract(C carrier, Getter<C> getter) {
            Utils.checkNotNull(carrier, "carrier");
            Utils.checkNotNull(getter, "getter");
            return EmptyTagMap.INSTANCE;
        }
    }
}
