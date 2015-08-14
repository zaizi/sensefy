package com.zaizi.dto.response.content;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import com.zaizi.dto.semantic.EntityType;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

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
