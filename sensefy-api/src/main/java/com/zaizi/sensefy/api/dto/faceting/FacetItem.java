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
