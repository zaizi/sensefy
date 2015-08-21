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
