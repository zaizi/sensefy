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
