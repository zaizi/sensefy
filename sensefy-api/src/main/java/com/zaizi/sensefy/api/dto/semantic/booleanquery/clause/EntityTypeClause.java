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
package com.zaizi.sensefy.api.dto.semantic.booleanquery.clause;

/**
 * This class has the responsability to model an EntityType boolean clause
 * @author Alessandro Benedetti
 *         27/10/2014
 *         search-api
 * @since 1.4
 */
public class EntityTypeClause implements BooleanClause
{
    public static final String SMLT_ENTITY_TYPES = "smlt_entity_types";


    /* Entity Type Index */
    public static final String ENTITY_TYPE_ID_FIELD = "type";


    private String id;
    private String attribute;
    private String value;

    public EntityTypeClause( String id, String attribute, String value )
    {
        this.id = id;
        this.attribute = attribute;
        this.value = value;
    }

    public String getClause(){
        String query;
        String baseQuery="";

            if ( attribute == null )
            {
                baseQuery = ENTITY_TYPE_ID_FIELD + ":\"" + id+ "\"";
            }
            else
            {
                baseQuery = ENTITY_TYPE_ID_FIELD + ":\"" + id + "\" AND " + attribute + ":\""
                    + value + "\"";
            }
        return "("+baseQuery+")";
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public void setAttribute( String attribute )
    {
        this.attribute = attribute;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }
}
