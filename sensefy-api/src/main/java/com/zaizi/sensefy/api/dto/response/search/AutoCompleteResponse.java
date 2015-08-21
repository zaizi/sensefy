package com.zaizi.sensefy.api.dto.response.search;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zaizi.sensefy.api.dto.response.AbstractSensefyResponse;
import com.zaizi.sensefy.api.dto.response.ResponseHeader;
import com.zaizi.sensefy.api.dto.response.content.SmartAutoCompleteResponseContent;

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
