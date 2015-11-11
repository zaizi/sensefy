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

import org.zaizi.sensefy.api.dto.semantic.booleanquery.clause.BooleanClause;

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
