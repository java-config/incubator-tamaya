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
package org.apache.tamaya.core.properties;

import org.apache.tamaya.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provider implementation that combines multiple other config by intersecting
 * the key/values common to all config, conflicting keys are resolved using an
 * {@link org.apache.tamaya.AggregationPolicy}.
 */
class IntersectingPropertySource extends AbstractPropertySource {

	private static final long serialVersionUID = -1492990130201110889L;
	private List<PropertySource> providers;
    private PropertySource aggregatedDelegate;

    public IntersectingPropertySource(MetaInfo metaInfo, AggregationPolicy policy, List<PropertySource> providers) {
        super(MetaInfoBuilder.of(metaInfo).setType("intersection").build());
        this.providers = new ArrayList<>(providers);
        aggregatedDelegate = PropertySourceBuilder.of(getMetaInfo()).withAggregationPolicy(policy)
                .addProviders(this.providers).build();
    }

    @Override
    public Optional<String> get(String key) {
        if (containsKey(key))
            return aggregatedDelegate.get(key);
        return Optional.empty();
    }

    @Override
    public boolean containsKey(String key) {
        for (PropertySource prov : this.providers) {
            if (!prov.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<String, String> toMap() {
        return aggregatedDelegate.toMap().entrySet().stream().filter(en -> containsKey(en.getKey())).collect(
                Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}