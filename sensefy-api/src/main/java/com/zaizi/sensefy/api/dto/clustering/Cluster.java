package com.zaizi.sensefy.api.dto.clustering;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a clustering, specified by a label, with a filter query associated and a set of documents
 *
 *
 * @author Alessandro Benedetti
 *         17/10/2014
 *         search-api
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = { "label","score", "docs" ,"size","filterQuery"} )
public class Cluster
{
    private String label;

    private List<String> docIds;

    private String filterQuery;

    private double score;

    public Cluster()
    {
        docIds=new LinkedList<String>(  );
    }

    public Cluster(String label)
    {
        this.label = label;
        docIds=new LinkedList<String>(  );
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }

    public List<String> getDocs()
    {
        return docIds;
    }

    public int getSize()
    {
        return docIds.size();
    }

    public void setDocIds( List<String> docIds )
    {
        this.docIds = docIds;
        this.filterQuery=this.buildSolrFilterQuery();
    }
    
    public void addDocIds( String docId )
    {
        this.docIds.add( docId);
        this.filterQuery=this.buildSolrFilterQuery();
    }

    @Override
    public String toString()
    {
        return "Cluster{" +
            "label='" + label + '\'' +
                " , docs='" + docIds + '\'' +
            "'}'";
    }

    private String buildSolrFilterQuery(){
        StringBuilder queryBuilder=new StringBuilder();
        queryBuilder.append("id:(");
        for(String docId:docIds){
            queryBuilder.append("\""+docId+"\" ");
        }
        String partialFilterQuery=queryBuilder.toString().trim();
        return partialFilterQuery+")";
    }


    public String getFilterQuery() {
        return filterQuery;
    }

    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
