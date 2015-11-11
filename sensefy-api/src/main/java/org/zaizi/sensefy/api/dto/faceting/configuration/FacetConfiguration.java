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
package org.zaizi.sensefy.api.dto.faceting.configuration;

import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class models the configuration for one facet, in all the details.
 * @author Alessandro Benedetti
 *         17/10/2014
 *         search-api
 * @since 1.4
 */
@XmlRootElement
//@XmlType(propOrder = { "field","range","date", "label","sort","limit", "mincount", "start", "end", "gap"})
public class FacetConfiguration
{
    @XmlAttribute
    private String field;
    @XmlElement
    private boolean range;
    @XmlElement
    private boolean date;
    @XmlElement
    private boolean semantic;
    @XmlElement
    private String label;
    @XmlElement
    private String sort;
    @XmlElement
    private int limit;
    @XmlElement
    private int mincount;

    /* Range facets param*/
    @XmlElement
    private String start;
    @XmlElement
    private String end;
    @XmlElement
    private String gap;

    @XmlElement
    private String intervals;

    /* Date Facets param*/
    
    private ModifiableSolrParams solrFacetParams;

    public String getIntervals()
    {
        return intervals;
    }

    public String getField()
    {
        return field;
    }

    public boolean isRange()
    {
        return range;
    }

    public boolean isDate()
    {
        return date;
    }

    public String getLabel()
    {
        return label;
    }

    public String getSort()
    {
        return sort;
    }

    public int getLimit()
    {
        return limit;
    }

    public int getMincount()
    {
        return mincount;
    }

    public String getStart()
    {
        return start;
    }

    public String getEnd()
    {
        return end;
    }

    public String getGap()
    {
        return gap;
    }

    public boolean isSemantic()
    {
        return semantic;
    }

    public SolrParams getSolrFacetParams(ModifiableSolrParams solrFacetParams,String tag)
    {
        //solrFacetParams=new ModifiableSolrParams();
        if(this.isRange()) {
            if(tag!=null)
                solrFacetParams.add("facet.range", "{!ex=" + tag + "}" + field);
            else
                solrFacetParams.add( "facet.range",field );
            if(this.start!=null)
                solrFacetParams.set("f." + field + ".facet.range.start", start);
            if(this.end!=null)
                solrFacetParams.set("f."+field+".facet.range.end",end  );
            if(this.gap!=null)
                solrFacetParams.set("f."+field+".facet.range.gap",gap  );
        }
        else if(this.isDate()) {
            String[] intervalsArray = this.intervals.split( "\\," );
            for(String interval:intervalsArray){
                solrFacetParams.add( "facet.query", field + ":" + FacetIntervals.interval2query.get( interval ) );
            }
        }else{
            if(tag!=null)
                solrFacetParams.add( "facet.field","{!ex="+tag+"}"+field );
            else
                solrFacetParams.add( "facet.field",field );
        }
        if(this.limit!=0)
            solrFacetParams.set("f."+field+".facet.limit",limit  );
        if(this.mincount!=0)
            solrFacetParams.set("f."+field+".facet.mincount",mincount);
        if(this.sort!=null)
            solrFacetParams.set("f."+field+".facet.sort",sort);


        return solrFacetParams;
    }
}
