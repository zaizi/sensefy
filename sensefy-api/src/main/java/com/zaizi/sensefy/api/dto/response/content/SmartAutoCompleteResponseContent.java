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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.zaizi.sensefy.api.dto.semantic.EntityType;

/**
 * Smart Auto Complete AbstractSensefyResponse Content
 * Contains :
 * numberOfSuggestions - number of suggestions to show for each type of suggestion
 * entities - named entity infix suggestions
 * entityTypes - entity types infix suggestions
 * titles - infix suggestions coming from the document titles
 *
 *
 * @author Alessandro Benedetti
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = { "numberOfSuggestions","suggestions","entities","entityTypes","titles"} )
public class SmartAutoCompleteResponseContent
    extends AutoCompleteResponseContent

{
    private List<String> suggestions;
    private List<SolrDocument> titles;
    private List<SolrDocument> entities;
    private List<EntityType> entityTypes;

    public SmartAutoCompleteResponseContent()
    {
        super();
        suggestions=new ArrayList<String>();
        entities = new ArrayList<SolrDocument>();
        entityTypes = new ArrayList<EntityType>();
        titles=new SolrDocumentList();
    }

    public List<EntityType> getEntityTypes()
    {
        return entityTypes;
    }

    public void setEntityTypes( List<EntityType> entityTypes )
    {
        this.entityTypes = entityTypes;
    }

    public List<SolrDocument> getEntities()
    {
        return entities;
    }

    public void setEntities( List<SolrDocument> entities )
    {
        this.entities = entities;
    }

    public List<SolrDocument> getTitles()
    {
        return titles;
    }

    public void setTitles( List<SolrDocument> titles )
    {
        this.titles = titles;
    }

    @Override
    public List<String> getSuggestions() {
        return suggestions;
    }

    @Override
    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}
