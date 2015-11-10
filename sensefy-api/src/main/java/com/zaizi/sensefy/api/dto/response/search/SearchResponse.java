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
package com.zaizi.sensefy.api.dto.response.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zaizi.sensefy.api.dto.clustering.Cluster;
import com.zaizi.sensefy.api.dto.faceting.Facet;
import com.zaizi.sensefy.api.dto.response.AbstractSensefyResponse;
import com.zaizi.sensefy.api.dto.response.ResponseHeader;
import com.zaizi.sensefy.api.dto.response.content.SearchResults;

/**
 * Semantic Search Response
 * It contains :
 *
 * header - the header of the response
 * searchResults - the body of the response containing documents
 * facets - the facets associated to the response
 * semanticFacets - the semantic facets associated to the response, entity type -> list of entities with occurrence
 * error - any error
 *
 * @author Alessandro Benedetti
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = { "header", "searchResults","facets","clusters", "error" } )
public class SearchResponse
    extends AbstractSensefyResponse
{
    private List<Cluster> clusters;
    private List<Facet>  facets;
    private SearchResults searchResults;

    public SearchResponse()
    {
        super.header=new ResponseHeader();
    }

    public SearchResults getSearchResults()
    {
        return searchResults;
    }

    public void setSearchResults( SearchResults searchResults )
    {
        this.searchResults = searchResults;
    }

    public List<Facet> getFacets()
    {
        return facets;
    }

    public void setFacets( List<Facet> facets )
    {
        this.facets = facets;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }
    //Lambda expression to sort, not available in Java 7
    //(Cluster c1, Cluster c2) -> {((Double)c1.getScore()).compareTo((Double)c2.getScore())
    public void setClusters(List<Cluster> clusters,boolean size) {
        if(!size) {
            Collections.sort(clusters, new Comparator<Cluster>() {
                public int compare(Cluster c1, Cluster c2) {
                    return ((Double) c2.getScore()).compareTo((Double) c1.getScore());
                }
            });
        }
        else{
            Collections.sort(clusters, new Comparator<Cluster>() {
                public int compare(Cluster c1, Cluster c2) {
                    return ((Integer) c2.getSize()).compareTo((Integer) c1.getSize());
                }
            });
        }
        this.clusters = clusters;
    }
}
