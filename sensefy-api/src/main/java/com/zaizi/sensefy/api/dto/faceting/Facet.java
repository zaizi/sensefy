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
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents one facet, related to one field and containing a list of Facet items.
 *
 * @author Alessandro Benedetti
 *         17/10/2014
 *         search-api
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = { "label", "facetItems"} )
public class Facet
{
    private String label;

    private List<FacetItem> facetItems;

    public Facet()
    {
        facetItems=new LinkedList<FacetItem>(  );
    }

    public Facet( String label)
    {
        this.label = label;
        facetItems=new LinkedList<FacetItem>(  );
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }

    public List<FacetItem> getFacetItems()
    {
        return facetItems;
    }

    public void setFacetItems( List<FacetItem> facetItems )
    {
        this.facetItems = facetItems;
    }
    
    public void addFacetItems( FacetItem facetItem )
    {
        this.facetItems.add( facetItem ) ;
    }

    @Override
    public String toString()
    {
        return "Facet{" +
            "label='" + label + '\'' +
            "'}'";
    }
}
