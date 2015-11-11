/**
 * (C) Copyright 2015 Zaizi Limited (http://www.zaizi.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at 
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 **/
package org.zaizi.sensefy.api.dto.response.content;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.solr.common.util.SimpleOrderedMap;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.zaizi.sensefy.api.utils.serializer.SolrSerializer;

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
