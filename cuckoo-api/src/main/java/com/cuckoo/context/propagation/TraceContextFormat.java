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

/**
 * Implementation of the TraceContext propagation protocol. See
 * <a href=https://github.com/w3c/distributed-tracing>w3c/distributed-tracing</a>
 */
public class TraceContextFormat implements HttpTextFormat {
    @Override
    public List<String> fields() {
        return null;
    }

    @Override
    public Object extract(Object carrier, Getter getter) {
        return null;
    }

    @Override
    public void inject(Object value, Object carrier, Setter setter) {

    }
}
