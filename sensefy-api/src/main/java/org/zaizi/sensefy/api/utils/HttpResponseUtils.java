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
