package com.zaizi.sensefy.api.dto.semantic.booleanquery;

import com.zaizi.sensefy.api.dto.semantic.booleanquery.clause.BooleanClause;

/**
 * @author Alessandro Benedetti
 *         27/10/2014
 *         search-api
 * @since 1.4
 */
public class BooleanOpClause
{
    private BooleanOperator operator;
    private BooleanClause clause;

    public BooleanOpClause( BooleanOperator operator, BooleanClause clause )
    {
        this.operator = operator;
        this.clause = clause;
    }

    public BooleanOperator getOperator()
    {
        return operator;
    }

    public void setOperator( BooleanOperator operator )
    {
        this.operator = operator;
    }

    public String getClause()
    {
        return clause.getClause();
    }

    public void setClause( BooleanClause clause )
    {
        this.clause = clause;
    }
}
