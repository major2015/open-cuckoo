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

import com.cuckoo.context.Scope;
import com.cuckoo.context.propagation.BinaryFormat;
import com.cuckoo.context.propagation.HttpTextFormat;

/**
 * Object for creating new {@link TagMap}s and {@code TagMap}s based on the current context.
 *
 * <p>This class returns {@link TagMap.Builder builders} that can be sued to create the
 * implementation-dependent {@link TagMap}s.</p>
 *
 * <p>Implementations may have different constraints and are free to convert tag contexts
 * to their own subtypes. This means callers cannot assume the {@link #getCurrentTagMap()}
 * current context} is the same instance as the one {@link } </p>
 */
public interface Tagger {

    /**
     * Returns the current {@code TagMap}.
     *
     * @return the current {@code TagMap}.
     * @since 0.0.1
     */
    TagMap getCurrentTagMap();

    /**
     * Returns a new {@code Builder}.
     *
     * @return a new {@code Builder}.
     * @since 0.0.1
     */
    TagMap.Builder tagMapBuilder();

    /**
     * Enters the scope of code where the given {@code TagMap} is in the current context (replacing
     * the previous {@code TagMap}) and returns an object that represents that scope. The scope is
     * exited when the returned object is closed.
     *
     * @param tags the {@code TagMap} to be set to the current context.
     * @return an object that defines a scope where the given {@code TagMap} is set to the current
     *      context.
     * @since 0.0.1
     */
    Scope withTagMap(TagMap tags);

    /**
     * Returns the {@link BinaryFormat} for this implementation.
     *
     * <p>
     *  Example of usage on the client:
     *
     *  <pre>
     *      {@code
     *          private static final Tagger tagger = OpenCuckoo.getTagger();
     *          private static final BinaryFormat binaryFormat =
     *              OpenCuckoo.getTagger().getBinaryFormat();
     *
     *          Request createRequest() {
     *              Request req = new Request();
     *              byte[] tagsBuffer = binaryFormat.toByteArray(tagger.getCurrentTagMap();
     *              request.addMetadata("tags", tagsBuffer);
     *              return request;
     *          }
     *      }
     *  </pre>
     * </p>
     *
     * <p>
     *     Example of usage on the server:
     *
     *     <pre>{@code
     *          private static final Tagger tagger = OpenCuckoo.getTagger();
     *          private static final BinaryFormat binaryFormat =
     *              OpenCuckoo.getTagger().getBinaryFormat();
     *
     *          void onRequestReceived(Request request) {
     *              byte[] tagsBuffer = request.getMetadata("tags");
     *              TagMap tagMap = TextFormat.fromByteArray(tagsBuffer);
     *              try (Scope s = tagger.withTagMap(tagMap)) {
     *                  // Handle request an send response back.
     *              }
     *          }
     *     }
     *     </pre>
     * </p>
     *
     * @return the {@code BinaryFormat} for this implementation.
     * @since 0.0.1
     */
    BinaryFormat<TagMap> getBinaryFormat();

    /**
     * Returns the {@link HttpTextFormat} for this implementation.
     *
     * <p>
     *     Usually this will be the W3C Correlation Context as the HTTP text format.
     *     For more details, see
     *     <a href="https://github.com/w3c/correlation-context">correlation-context</a>
     * </p>
     *
     * <p>
     *     Example of usage on the client:
     *     <pre>{@code
     *          private static final Tagger tagger = OpenCuckoo.getTagger();
     *          private static final HttpTextFormat textFormat = OpenCuckoo.getTagger()
     *              .getHttpTextFormat();
     *
     *          private static final HttpTextFormat.Setter setter =
     *              new HttpTextFormat.Setter<HttpURLConnection>() {
     *                  public void put(HttpURRLConnection carrier, String key, String value) {
     *                      carrier.setRequestProperty(field, value);
     *                  }
     *              };
     *          void makeHttpRequest() {
     *              HttpURLConnection = (HttpURLConnection) new URL("http://myserver").openConnection();
     *              textFormat.inject(tagger.getCurrentTagMap(), Connection, httpURLConnectionSetter);
     *              // Send the request, wait for response and maybe set the status if not ok.
     *          }
     *     }
     *     </pre>
     * </p>
     *
     * <p>
     *     Example of usage on the server:
     *
     *     <pre>
     *         private static final Tagger tagger = OpenCuckoo.getTagger();
     *         private static final HttpTextFormat textFormat = OpenCuckoo.getTagger()
     *              .getHttpTextFormat();
     *         private static final HttpTextFormat.Getter<HttpRequest> getter = ...;
     *
     *         void onRequestReceived(HttpRequest request) {
     *             TagMap tagMap = textFormat.extract(request, getter);
     *             try (Scope s = tagger.withTagMap(tagMap)) {
     *                  // Handle request and send response back.
     *             }
     *         }
     *     </pre>
     * </p>
     *
     * @return the {@code HttpTextFormat} for this implementation
     * @since 0.0.1
     */
    HttpTextFormat<TagMap> getHttpTextFormat();
}
