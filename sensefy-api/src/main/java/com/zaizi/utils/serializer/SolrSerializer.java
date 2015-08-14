package com.zaizi.utils.serializer;

import org.apache.solr.common.util.SimpleOrderedMap;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author: Alessandro Benedetti
 * Date: 09/07/2014
 */
public class SolrSerializer extends JsonSerializer<ArrayList<SimpleOrderedMap<String>>>
{
    @Override
    public void serialize(ArrayList<SimpleOrderedMap<String>> arrayMap, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonProcessingException
    {
        jgen.writeStartArray();
        for(SimpleOrderedMap<String> map:arrayMap){
        jgen.writeStartObject();
        for(int i=0;i<map.size();i++){

            String key=map.getName( i );
            Object val = map.getVal( i );
            String value= val.toString();
            jgen.writeStringField(key, value);
        }
        jgen.writeEndObject();  }
        jgen.writeEndArray();
    }
}
