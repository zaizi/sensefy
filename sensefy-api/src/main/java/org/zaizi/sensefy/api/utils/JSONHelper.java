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
package org.zaizi.sensefy.api.utils;

import org.apache.solr.client.solrj.response.UpdateResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JSON Helper class
 * 
 * @author aayala
 * 
 */
public class JSONHelper
{
    @SuppressWarnings("rawtypes")
    public static Object toJSON(Object object) throws JSONException
    {
        if (object instanceof Map)
        {
            JSONObject json = new JSONObject();
            Map map = (Map) object;
            for (Object key : map.keySet())
            {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        }
        else if (object instanceof Iterable)
        {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable) object))
            {
                json.put(value);
            }
            return json;
        }
        else
        {
            return object;
        }
    }

    public static boolean isEmptyObject(JSONObject object)
    {
        return object.names() == null;
    }

    public static Map<String, Object> getMap(JSONObject object, String key) throws JSONException
    {
        return toMap(object.getJSONObject(key));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, Object> toMap(JSONObject object) throws JSONException
    {
        Map<String, Object> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext())
        {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List toList(JSONArray array) throws JSONException
    {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++)
        {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }

    private static Object fromJson(Object json) throws JSONException
    {
        if (json == JSONObject.NULL)
        {
            return null;
        }
        else if (json instanceof JSONObject)
        {
            return toMap((JSONObject) json);
        }
        else if (json instanceof JSONArray)
        {
            return toList((JSONArray) json);
        }
        else
        {
            return json;
        }
    }

    /**
     * Build a Jackson JsonNode from a JSON String
     * 
     * @param jsonString
     * @return
     * @throws java.io.IOException
     */
    public static JsonNode parseStringToJsonNode(String jsonString) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory jsonFactory = mapper.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
        JsonParser jsonParser = jsonFactory.createJsonParser(jsonString);
        JsonNode jsonNode = mapper.readTree(jsonParser);
        return jsonNode;
    }

    /**
     * Builds a JSON as ok AbstractSensefyResponse, taking the query time from the update response
     * 
     * @param process
     * @return
     * @throws JSONException
     */
    public static JSONObject buildOkUpdateJson(UpdateResponse process) throws JSONException
    {
        JSONObject obj = new JSONObject();
        obj.put("status", 200);
        obj.put("Qtime", process.getQTime());
        return obj;
    }
}
