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
package org.apache.tamaya.spisupport;

import org.apache.tamaya.Configuration;
import org.apache.tamaya.spi.ConfigurationBuilder;
import org.apache.tamaya.spi.ConfigurationContext;
import org.apache.tamaya.spi.ConfigurationContextBuilder;
import org.apache.tamaya.spi.ConfigurationProviderSpi;
import org.osgi.service.component.annotations.Component;

import java.util.Objects;

/**
 * Implementation of the Configuration API. This class uses the current {@link ConfigurationContext} to evaluate the
 * chain of {@link org.apache.tamaya.spi.PropertySource} and {@link org.apache.tamaya.spi.PropertyFilter}
 * instance to evaluate the current Configuration.
 */
@Component(service = ConfigurationProviderSpi.class)
public class TestConfigurationProvider implements ConfigurationProviderSpi {

    private Configuration config = new DefaultConfigurationBuilder()
            .addDefaultPropertyConverters()
            .addDefaultPropertyFilters()
            .addDefaultPropertySources()
            .build();
    @Override
    public Configuration getConfiguration() {
        return config;
    }

    @Override
    public Configuration createConfiguration(ConfigurationContext context) {
        return new DefaultConfiguration(context);
    }

    @Override
    public ConfigurationBuilder getConfigurationBuilder() {
        return new DefaultConfigurationBuilder();
    }

    @Override
    public ConfigurationContextBuilder getConfigurationContextBuilder() {
        return new DefaultConfigurationContextBuilder();
    }

    @Override
    public void setConfiguration(Configuration config) {
        Objects.requireNonNull(config.getContext());
        this.config = Objects.requireNonNull(config);
    }

    @Override
    public boolean isConfigurationSettable() {
        return true;
    }

    /**
     * @deprecated use {@link Configuration#getContext()} instead.
     */
    @Deprecated
    @Override
    public ConfigurationContext getConfigurationContext() {
        return this.config.getContext();
    }

    /**
     * @deprecated the context should be given upon creation of the {@link Configuration}
     */
    @Deprecated
    @Override
    public void setConfigurationContext(ConfigurationContext context){
        this.config = new DefaultConfigurationBuilder(context).build();
    }

}