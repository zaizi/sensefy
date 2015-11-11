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
package org.zaizi.sensefy.api.dto.semantic.booleanquery;

import java.util.ArrayList;
import java.util.List;

import org.zaizi.sensefy.api.dto.response.search.SearchResponse;
import org.zaizi.sensefy.api.dto.semantic.booleanquery.clause.BooleanClause;

/**
 * @author Alessandro Benedetti
 *         27/10/2014
 *         search-api
 * @since 1.4
 */
public class BooleanEntityQuery
{
    /* Entity index */
    public static final String ID_FIELD = "id";
    public static final String DOC_ID_FIELD = "doc_ids";

    /* Index Names */
    public static final String SOLR_ENTITY_CORE = "entity";

    BooleanClause firstClause;
    List<BooleanOpClause> clauses;

    public BooleanEntityQuery( BooleanClause firstClause, List<BooleanOpClause> clauses )
{
    this.firstClause = firstClause;
    this.clauses = clauses;
}

    public BooleanEntityQuery( BooleanClause firstClause)
    {
        this.firstClause = firstClause;
        this.clauses = new ArrayList<BooleanOpClause>();
    }

    public void addOpClause(BooleanOperator op,BooleanClause clause){
        BooleanOpClause opClause=new BooleanOpClause( op,clause );
        clauses.add(opClause  );

    }
    public BooleanClause getFirstClause()
    {
        return firstClause;
    }

    public void setFirstClause( BooleanClause firstClause )
    {
        this.firstClause = firstClause;
    }

    public List<BooleanOpClause> getClauses()
    {
        return clauses;
    }

    public void setClauses( List<BooleanOpClause> clauses )
    {
        this.clauses = clauses;
    }

    public String getBooleanEntityQuery(SearchResponse searchResponse){
        String query;
        StringBuilder baseQuery=new StringBuilder(firstClause.getClause());
        for(BooleanOpClause opClause:clauses){
            baseQuery.append(" "+opClause.getOperator()+" "+opClause.getClause());
        }

        query =getJoinQuery( DOC_ID_FIELD, ID_FIELD, SOLR_ENTITY_CORE, baseQuery.toString() );
        searchResponse.setQuery( baseQuery.toString() );
        return query;
    }

    /**
     * Return the join Solr query
     *
     * @param from
     * @param to
     * @param fromIndex
     * @param query
     * @return
     */
    private static String getJoinQuery( String from, String to, String fromIndex, String query )
    {
        String joinQuery;
        StringBuilder joinStringBuilder = new StringBuilder();
        joinStringBuilder.append( "{!join from=" );
        joinStringBuilder.append( from );
        joinStringBuilder.append( " to=" );
        joinStringBuilder.append( to );
        joinStringBuilder.append( " fromIndex=" );
        joinStringBuilder.append( fromIndex + "}" );
        joinStringBuilder.append( query );
        joinQuery = joinStringBuilder.toString();
        return joinQuery;
    }
}
