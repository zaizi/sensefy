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
package org.zaizi.sensefy.api.dto.response.search;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.zaizi.sensefy.api.dto.response.AbstractSensefyResponse;
import org.zaizi.sensefy.api.dto.response.ResponseHeader;
import org.zaizi.sensefy.api.dto.response.content.SmartAutoCompleteResponseContent;

/**
 * Semantic Search AbstractSensefyResponse
 * It contains :
 *
 * header - the header of the response
 * responseContent - the body of the response containing documents
 * facets - the facets associated to the response
 * semanticFacets - the semantic facets associated to the response, entity type -> list of entities with occurrence
 * error - any error
 *
 * @author Alessandro Benedetti
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = {"error","header", "responseContent" } )
public class AutoCompleteResponse
    extends AbstractSensefyResponse
{
    private SmartAutoCompleteResponseContent responseContent;

    public AutoCompleteResponse()
    {
        super.header=new ResponseHeader();
        super.error=null;
    }

    public SmartAutoCompleteResponseContent getResponseContent()
    {
        return responseContent;
    }

    public void setResponseContent( SmartAutoCompleteResponseContent responseContent )
    {
        this.responseContent = responseContent;
    }
}
