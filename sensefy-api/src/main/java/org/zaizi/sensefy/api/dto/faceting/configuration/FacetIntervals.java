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
package org.zaizi.sensefy.api.dto.faceting.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alessandro Benedetti
 *         24/12/2014
 *         search-api
 * @since 1.4
 */
public class FacetIntervals
{
  public static Map<String,String> interval2query;
    public static Map<String,String> query2interval;

    public static final String TODAY=("[NOW/DAY TO NOW/DAY+1DAY]");
    public static final String YESTERDAY=("[NOW/DAY-1DAY TO NOW/DAY]");
    public static final String LAST_WEEK=("[NOW/DAY-7DAYS TO NOW/DAY+1DAY]");
    public static final String LAST_MONTH=("[NOW/DAY-30DAYS TO NOW/DAY+1DAY]");
    public static final String LAST_YEAR=("[NOW/DAY-1YEAR TO NOW/DAY+1DAY]");

    static {
        HashMap<String, String> interval2queryTransient =new HashMap<String, String>( );
        HashMap<String, String> query2intervalTransient =new HashMap<String, String>( );
        interval2queryTransient.put( "today", TODAY );
        query2intervalTransient.put(TODAY,"today");
        interval2queryTransient.put( "yesterday", YESTERDAY );
        query2intervalTransient.put(YESTERDAY,"yesterday");
        interval2queryTransient.put( "last_week", LAST_WEEK );
        query2intervalTransient.put(LAST_WEEK,"last_week");
        interval2queryTransient.put( "last_month", LAST_MONTH );
        query2intervalTransient.put(LAST_MONTH,"last_month");
        interval2queryTransient.put( "last_year", LAST_YEAR );
        query2intervalTransient.put(LAST_YEAR,"last_year");
        interval2query = Collections.unmodifiableMap( interval2queryTransient );
        query2interval = Collections.unmodifiableMap( query2intervalTransient );
    }



}
