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
package com.zaizi.sensefy.api.dto.response.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.solr.common.SolrDocument;

import com.zaizi.sensefy.api.dto.semantic.EntityType;

/**
 * Semantic Search AbstractSensefyResponse Content
 * It contains the body for the Semantic Search AbstractSensefyResponse
 *
 * numFound - the number of documents retrieved
 * start" - the starting number of document for pagination
 * entity" - the named entity that eventually drove the query
 * entityType - the entity type that eventually drove the query
 * documents - the documents satisfying the query
 *
 * @author Alessandro Benedetti
 * @since 1.4
 */
@XmlRootElement
@XmlType(propOrder = { "numFound", "start","documents","highlight","collationQuery", "entity", "entityType"})
public class SearchResults
{
    private List<SolrDocument> documents;

    private long numFound;

    private int start;

    private String collationQuery;

    private Map<String, Map<String, List<String>>> highlight;

    private SolrDocument entity;

    private EntityType entityType;


    public SearchResults()
    {
        documents = new ArrayList<SolrDocument>();

    }

    public long getNumFound()
    {
        return numFound;
    }

    public void setNumFound( long numFound )
    {
        this.numFound = numFound;
    }

    public String getCollationQuery()
    {
        return collationQuery;
    }

    public void setCollationQuery( String collationQuery )
    {
        this.collationQuery = collationQuery;
    }

    public int getStart()
    {
        return start;
    }

    public void setStart( int start )
    {
        this.start = start;
    }

    public List<SolrDocument> getDocuments()
    {
        return documents;
    }

    public void setDocuments( List<SolrDocument> documents )
    {
        this.documents = documents;
    }

    public SolrDocument getEntity()
    {
        return entity;
    }

    public void setEntity( SolrDocument entity )
    {
        this.entity = entity;
    }

    public EntityType getEntityType()
    {
        return entityType;
    }

    public void setEntityType( EntityType entityType )
    {
        this.entityType = entityType;
    }

    public Map<String, Map<String, List<String>>> getHighlight()
    {
        return highlight;
    }

    public void setHighlight( Map<String, Map<String, List<String>>> highlight )
    {
        this.highlight = highlight;
    }
}
