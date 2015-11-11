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
