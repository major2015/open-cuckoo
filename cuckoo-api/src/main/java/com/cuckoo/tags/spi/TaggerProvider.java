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

package com.cuckoo.tags.spi;

import com.cuckoo.tags.Tagger;

/**
 * TaggerProvider is service provider for {@link com.cuckoo.tags.Tagger}. Fully qualified class
 * name of the implementation should be registered in
 * {@code META-INF/services/com.cuckoo.tags.spi.TaggerProvider}.
 *
 * A specific implementation can be selected by a system property
 * {@code com.cuckoo.tags.spi.TaggerProvider}
 * with value of fully qualified class name.
 *
 * @see com.cuckoo.OpenCuckoo
 */
public interface TaggerProvider {

    /**
     * Creates a new tagger instance.
     *
     * @return a tagger instance.
     */
    Tagger create();
}
