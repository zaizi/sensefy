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
package org.zaizi.sensefy.api.dto.faceting.facetcomparators;

import java.util.Comparator;

import org.zaizi.sensefy.api.dto.faceting.Facet;

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
