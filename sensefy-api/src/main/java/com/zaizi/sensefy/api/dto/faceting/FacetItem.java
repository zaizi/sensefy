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
package com.zaizi.sensefy.api.dto.faceting;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a single Facet Item with the value, the counting and the filter query related to the value.
 * @author Alessandro Benedetti
 *         17/10/2014
 *         search-api
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = { "value", "occurrence", "filter" } )
public class FacetItem
{
    private String value;
    private Long occurrence;
    private String filter;

    public FacetItem()
    {
    }

    public FacetItem( String value, Long occurrence, String filter )
    {
        this.value = value;
        this.occurrence = occurrence;
        this.filter = filter;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public Long getOccurrence()
    {
        return occurrence;
    }

    public void setOccurrence( Long occurrence )
    {
        this.occurrence = occurrence;
    }

    public String getFilter()
    {
        return filter;
    }

    public void setFilter( String filter )
    {
        this.filter = filter;
    }

    @Override
    public String toString()
    {
        return "FacetItem{" +
            "value='" + value + '\'' +
            ", occurrence=" + occurrence +
            ", filter='" + filter + '\'' +
            '}';
    }
}
