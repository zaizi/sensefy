package com.zaizi.sensefy.api.utils;

import org.apache.http.HttpResponse;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: Alessandro Benedetti
 * Date: 08/07/2014
 */
public class HttpResponseUtils
{


    /**
     * Return the body message from the HttpResponse
     * @param httpResponse
     * @return
     * @throws java.io.IOException
     * @throws org.json.JSONException
     */
    public static String getMessage(HttpResponse httpResponse) throws IOException, JSONException
    {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null;)
        {
            builder.append( line.trim() );
        }
        return builder.toString();

    }
}
