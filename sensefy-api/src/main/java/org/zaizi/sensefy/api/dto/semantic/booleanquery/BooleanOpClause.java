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
