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

package com.cuckoo.context;

import java.io.IOException;

/**
 * A {@link Scope} that does nothing when it is created or closed.
 */
public final class NoopScope implements Scope {

    private NoopScope() {}

    /**
     * A singleton instance of {@code NoopScope}
     *
     * @since 0.0.1
     */
    public static final Scope INSTANCE = new NoopScope();

    @Override
    public void close() {}
}
