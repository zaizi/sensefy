/**
 * (C) Copyright 2015 Zaizi Limited (http://www.zaizi.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at 
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 **/
package org.zaizi.sensefy.api.dto.faceting.facetcomparators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zaizi.sensefy.api.dto.faceting.Facet;

/**
 * A simple comparator for lexicographically sort labels for the facets
 * @author Alessandro Benedetti
 *         20/10/2014
 *         search-api
 * @since 1.4        
 */
public class FacetConfigOrderComparator
    implements Comparator<Facet>
{
    private Map<String,Integer> field2position;

    public FacetConfigOrderComparator(List<String> fieldOrder){
        field2position=new HashMap<String, Integer>(  );
        for(int i=0;i<fieldOrder.size();i++){
            field2position.put(fieldOrder.get( i ) ,i );
        }
    }
    @Override
    public int compare( Facet o1, Facet o2 )
    {
        return (field2position.get(o1.getLabel())).compareTo(field2position.get( o2.getLabel()));
    }
}
