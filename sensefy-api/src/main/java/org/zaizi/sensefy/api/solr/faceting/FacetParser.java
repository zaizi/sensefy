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
package org.zaizi.sensefy.api.solr.faceting;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.zaizi.sensefy.api.dto.faceting.Facet;
import org.zaizi.sensefy.api.dto.faceting.FacetItem;
import org.zaizi.sensefy.api.dto.faceting.configuration.FacetConfiguration;
import org.zaizi.sensefy.api.dto.faceting.configuration.FacetConfigurationList;
import org.zaizi.sensefy.api.dto.faceting.configuration.FacetIntervals;
import org.zaizi.sensefy.api.dto.faceting.facetcomparators.FacetConfigOrderComparator;
import org.zaizi.sensefy.api.dto.faceting.facetcomparators.FacetLabelComparator;
import org.zaizi.sensefy.api.dto.response.search.SearchResponse;

/**
 * This class has the responsability to manage the Facet configurations and rendering.
 * THe facets will be parsed from the solr response and rendered accordingly the Sensefy standards.
 * @author Alessandro Benedetti
 *         22/12/2014
 *         search-api
 */
public class FacetParser
{

    /**
     * Parses the facets in the solr response accordingly with the Facet Configuration xml.
     * It keeps the order in that xml and for the semantic facets it keeps the alphabetical order.
     * In the Sensefy response for each field a label is associated. Lately an internationalization layer would be able to internationalize that.
     *
     * @param docsByEntityResponse
     * @return
     */
    public static void parseFacets(QueryResponse docsByEntityResponse, SearchResponse semanticResponse,
                             FacetConfigurationList facetConfigurationList)
    {
        List<String> orderedFacetFields=generateFacetOrderList( facetConfigurationList );
        List<Facet> outputSensefyFacets = new LinkedList<Facet>();
        Set<Facet> outputSensefyClassicFacets=new TreeSet<Facet>( new FacetConfigOrderComparator( orderedFacetFields ) );
        Set<Facet> outputSensefySemanticFacets =new TreeSet<Facet>(new FacetLabelComparator());
        //Field Facet parsing
        List<FacetField> solrFacetFields = docsByEntityResponse.getFacetFields();
        if (solrFacetFields != null)
        {
            for (FacetField solrFacetField : solrFacetFields)
            {
                String solrFieldName = solrFacetField.getName();
                FacetConfiguration solrFacetFieldConfig = facetConfigurationList.getFacetConfig(solrFieldName);
                if (solrFacetFieldConfig.isSemantic())
                {
                    List<FacetField.Count> inputSolrFacetItems = solrFacetField.getValues();
                    addSemanticFacetFromSolrFacet(outputSensefySemanticFacets, inputSolrFacetItems, solrFieldName, solrFacetFieldConfig );
                }
                else
                {
                    addFacets(outputSensefyClassicFacets, solrFacetField, solrFieldName, solrFacetFieldConfig);
                }
            }
        }
        //Range Facets Parsing
        List<RangeFacet> solrFacetRanges = docsByEntityResponse.getFacetRanges();
        if(solrFacetRanges!=null)
        for(RangeFacet solrFacetRange:solrFacetRanges)
        {
            String solrFieldName = solrFacetRange.getName();
            FacetConfiguration solrRangeFacetConfig = facetConfigurationList.getFacetConfig( solrFieldName );
            addRangeFacets( outputSensefyClassicFacets, solrFacetRange, solrFieldName, solrRangeFacetConfig );
        }

        //Date Facets Parsing
        Map<String, Integer> query2Count = docsByEntityResponse.getFacetQuery();
        if(query2Count!=null)
            for(String query:query2Count.keySet())
            {
                String[] facetQueryParts = query.split( "\\:" );
                String solrFieldName=facetQueryParts[0];
                String facetQuery=facetQueryParts[1];
                String interval= FacetIntervals.query2interval.get( facetQuery );
                FacetConfiguration solrDateFacetConfig = facetConfigurationList.getFacetConfig( solrFieldName );
                addDateFacets( outputSensefyClassicFacets, interval, query2Count.get( query ), query,
                               solrDateFacetConfig.getLabel() );
            }
        outputSensefyFacets.addAll( outputSensefyClassicFacets );
        outputSensefyFacets.addAll( outputSensefySemanticFacets );
        semanticResponse.setFacets(outputSensefyFacets);
    }


    /**
     * Generate the order of the facets configured in the config file to be applied to the results.
     * @param facetConfigurationList
     * @return
     */
    public static List<String> generateFacetOrderList( FacetConfigurationList facetConfigurationList )
    {
        List<String> facetOrderList=new LinkedList<String>();
        List<FacetConfiguration> facetConfigurations = facetConfigurationList.getFacetConfigurations();
        for(FacetConfiguration fc:facetConfigurations){
            facetOrderList.add( fc.getLabel() );
        }
        return facetOrderList;
    }

    /**
     * Add the current outputSensefyClassicFacets for a facet field to the resulting facet map
     *
     * @param outputSensefyClassicFacets
     * @param inputSolrFacetField
     * @param facetConfig
     */
    public static void addFacets(Set<Facet> outputSensefyClassicFacets, FacetField inputSolrFacetField, String fieldName,
                           FacetConfiguration facetConfig)
    {
        List<FacetField.Count> inputSolrFacetItems = inputSolrFacetField.getValues();
        Facet outputSensefyFacet = new Facet(facetConfig.getLabel());
        for (FacetField.Count inputSolrFacetItem : inputSolrFacetItems)
        {
            FacetItem outputSensefyFacetItem = new FacetItem(inputSolrFacetItem.getName(), inputSolrFacetItem.getCount(), fieldName
                + ":\"" + inputSolrFacetItem.getName() + "\"");

            outputSensefyFacet.addFacetItems( outputSensefyFacetItem );
        }
        if(!outputSensefyFacet.getFacetItems().isEmpty())
            outputSensefyClassicFacets.add( outputSensefyFacet );
    }

    /**
     * Add the current range outputSensefyClassicFacets for a Range facet field to the resulting facet map
     *
     * @param outputSensefyClassicFacets
     * @param facetConfig
     */
    public static void addRangeFacets(Set<Facet> outputSensefyClassicFacets, RangeFacet solrRangeFacet, String fieldName,
                                FacetConfiguration facetConfig)
    {
        List<RangeFacet.Count> rangeFacetGaps = solrRangeFacet.getCounts();
        int gapSize=Integer.parseInt( facetConfig.getGap() );
        Facet outputSensefyFacet = new Facet(facetConfig.getLabel());
        for (RangeFacet.Count rangeFacetGap : rangeFacetGaps)
        {
            int startingValue= (int)Double.parseDouble(rangeFacetGap.getValue());
            int endingValue=startingValue+gapSize;
            String filter = "[" + rangeFacetGap.getValue() + " TO " + endingValue + "}"; // this is the solr syntax to describe [ inclusive { exclusive
            String value = "[" + rangeFacetGap.getValue() + " TO " + endingValue + "]";
            FacetItem currentFacetItem = new FacetItem( value,new Long(rangeFacetGap.getCount()), fieldName
                + ":" + filter);
            if(currentFacetItem.getOccurrence()!=0)
                outputSensefyFacet.addFacetItems( currentFacetItem );
        }
        if(!outputSensefyFacet.getFacetItems().isEmpty())
            outputSensefyClassicFacets.add( outputSensefyFacet );
    }

    /**
     * Add a date facet to the sensefy facets in output
     * @param outputSensefyClassicFacets  - already generated facets
     * @param interval - the date interval label
     * @param count - the date interval count
     * @param filter - the filter associated to the date interval
     * @param facetLabel - the field label
     */
    public static void addDateFacets(Set<Facet> outputSensefyClassicFacets, String interval, Integer count,String filter,
                                      String facetLabel)
    {
        Facet selectedSensefyFacet=null;
       for(Facet facet:outputSensefyClassicFacets){
           if(facet.getLabel().equals( facetLabel ))
               selectedSensefyFacet=facet;
       }
        if(selectedSensefyFacet==null){
            selectedSensefyFacet=new Facet(facetLabel);

        }
        FacetItem sensefyFacetDateItem=new FacetItem(interval,new Long(count),filter);
        if(sensefyFacetDateItem.getOccurrence()!=0)
            selectedSensefyFacet.addFacetItems(sensefyFacetDateItem);
        if(!selectedSensefyFacet.getFacetItems().isEmpty())
            outputSensefyClassicFacets.add( selectedSensefyFacet );
    }

    /**
     * Add a semantic facet to the list of results starting from a Facet field solr inputSolrFacetItems
     *
     * @param inputSolrFacetItems
     * @param facetConfig
     */
    public static Set<Facet> addSemanticFacetFromSolrFacet(Set<Facet> outputSensefySemanticFacets, List<FacetField.Count> inputSolrFacetItems,
                                                      String fieldName, FacetConfiguration facetConfig)
    {
        Map<String, Facet> type2Facet = new HashMap<String, Facet>();
        for (FacetField.Count inputSolrFacetItem : inputSolrFacetItems)
        {
            Long occurrences = inputSolrFacetItem.getCount();
            String typedLabel = inputSolrFacetItem.getName();
            int lastSeparator = typedLabel.lastIndexOf('/');
            String label = typedLabel.substring(lastSeparator + 1);
            String type = typedLabel.substring(0, lastSeparator);
            FacetItem outputSensefyFacetItem = new FacetItem(label, occurrences, fieldName + ":\"" + typedLabel + "\"");
            Facet ouputSensefyFacet = type2Facet.get(type);
            if (ouputSensefyFacet != null)
            {
                if(ouputSensefyFacet.getFacetItems().size()<facetConfig.getLimit())
                    ouputSensefyFacet.addFacetItems(outputSensefyFacetItem);
            }
            else
            {
                ouputSensefyFacet = new Facet(type);
                ouputSensefyFacet.addFacetItems( outputSensefyFacetItem );
                outputSensefySemanticFacets.add( ouputSensefyFacet );
                type2Facet.put(type, ouputSensefyFacet);

            }
        }
        return outputSensefySemanticFacets;
    }

}
