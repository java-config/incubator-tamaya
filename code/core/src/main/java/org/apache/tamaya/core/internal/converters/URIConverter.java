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
package org.apache.tamaya.core.internal.converters;

import org.apache.tamaya.spi.ConversionContext;
import org.apache.tamaya.spi.PropertyConverter;
import org.osgi.service.component.annotations.Component;

import java.net.URI;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Converter, converting from String to URI, using new URI(createValue).
 */
@Component(service = PropertyConverter.class)
public class URIConverter implements PropertyConverter<URI> {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    @Override
    public URI convert(String value) {
        if(value==null || value.isEmpty()){
            return null;
        }
        ConversionContext.doOptional(ctx ->
                ctx.addSupportedFormats(getClass(), "<uri> -> new URI(uri)"));
        String trimmed = Objects.requireNonNull(value).trim();
        try {
            return new URI(trimmed);
        } catch (Exception e) {
            LOG.log(Level.FINE, "Unparseable URI: " + trimmed, e);
        }
        return null;
    }

    @Override
    public boolean equals(Object o){
        return Objects.nonNull(o) && getClass().equals(o.getClass());
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }
}
