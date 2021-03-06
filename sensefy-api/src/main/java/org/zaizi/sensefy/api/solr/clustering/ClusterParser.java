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
package org.zaizi.sensefy.api.solr.clustering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.zaizi.sensefy.api.dto.clustering.Cluster;
import org.zaizi.sensefy.api.dto.response.search.SearchResponse;

/**
 * This class parses the Clusters in the response, directly accessing the NamedList objects.
 * This will be replaced when SolrJ will provide proper API.
 *
 * @author Alessandro Benedetti
 *         06/05/2015
 *         SensefySearchAPI
 */
public class ClusterParser {
    /**
     * Parses the clusters from the solr response, and map them to the sensefy clustering object.
     * @param primaryIndexResponse
     * @param response
     */
    public static void parseClusters(QueryResponse primaryIndexResponse, SearchResponse response,boolean clusterSortedBySize) {
        List<Cluster> resultingClusters = new LinkedList<Cluster>();
        NamedList<Object> responseFromSolr = primaryIndexResponse.getResponse();
        ArrayList<NamedList<Object>> clusters = (ArrayList<NamedList<Object>>) responseFromSolr.get("clusters");
        if (clusters != null)
            for (NamedList<Object> cluster : clusters) {
                ArrayList<String> labels = (ArrayList<String>) cluster.get("labels");
                ArrayList<String> docIds = (ArrayList<String>) cluster.get("docs");
                Double score= (Double)cluster.get("score");
                if (labels.size() > 0) {
                    Cluster currentCluster = new Cluster(labels.get(0));
                    currentCluster.setDocIds(docIds);
                    currentCluster.setScore(score);
                    resultingClusters.add(currentCluster);
                }
            }
        response.setClusters(resultingClusters,clusterSortedBySize);
    }
}
