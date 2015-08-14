package com.zaizi.dto.faceting.facetcomparators;

import com.zaizi.dto.faceting.Facet;

import java.util.Comparator;

/**
 * A simple comparator for lexicographically sort labels for the facets
 * @author Alessandro Benedetti
 *         20/10/2014
 *         search-api
 * @since 1.4
 */
public class FacetLabelComparator implements Comparator<Facet>
{
    @Override
    public int compare( Facet o1, Facet o2 )
    {
        return o1.getLabel().compareTo( o2.getLabel() );
    }
}
