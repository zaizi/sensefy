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
