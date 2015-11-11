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
package org.zaizi.sensefy.api.dto.faceting;

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
