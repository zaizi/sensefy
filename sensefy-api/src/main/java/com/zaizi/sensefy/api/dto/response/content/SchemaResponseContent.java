/*******************************************************************************
 * Sensefy
 *
 * Copyright (c) Zaizi Limited, All rights reserved.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 *******************************************************************************/
package com.zaizi.sensefy.api.dto.response.content;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.solr.common.util.SimpleOrderedMap;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zaizi.sensefy.api.utils.serializer.SolrSerializer;

/**
 * @Author: Alessandro Benedetti
 * Date: 17/07/2014
 * @since 1.4
 */
@XmlRootElement
@XmlType(propOrder = { "content", "schemaFields" })
public class SchemaResponseContent
{
    @JsonSerialize(using = SolrSerializer.class)
    private ArrayList<SimpleOrderedMap<String>> schemaFields;

    private JsonNode content;

    public ArrayList<SimpleOrderedMap<String>> getSchemaFields()
    {
        return schemaFields;
    }

    public void setSchemaFields( ArrayList<SimpleOrderedMap<String>> schemaFields )
    {
        this.schemaFields = schemaFields;
    }

    public JsonNode getContent()
    {
        return content;
    }

    public void setContent( JsonNode content )
    {
        this.content = content;
    }
}
