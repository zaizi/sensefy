package com.zaizi.sensefy.api.dto.faceting.facetcomparators;

import java.util.Comparator;

import com.zaizi.sensefy.api.dto.faceting.Facet;

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
