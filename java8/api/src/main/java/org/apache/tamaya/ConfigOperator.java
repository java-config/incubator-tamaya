/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tamaya;

/**
 * Models a function that maps a given {@link Configuration} to another {@link Configuration}. This can be used
 * to modell additional functionality and applying it to a given {@link Configuration} instance by calling
 * the {@link Configuration#with(ConfigOperator)} method.
 */
@FunctionalInterface
public interface ConfigOperator {

    /**
     * Creates a new {@link Configuration} based on the given Configuration. Operators basically acts similar to
     * decorators, whereas operated instances may have non compatible behaviour, e.g. by applying security constraints
     * or view restrictions.
     *
     * @param config the input configuration, not null.
     * @return the operated configuration, never null.
     */
    Configuration operate(Configuration config);
}
