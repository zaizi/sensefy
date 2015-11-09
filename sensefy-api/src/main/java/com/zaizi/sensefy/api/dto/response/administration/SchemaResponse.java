package com.zaizi.sensefy.api.dto.response.administration;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zaizi.sensefy.api.dto.response.AbstractSensefyResponse;
import com.zaizi.sensefy.api.dto.response.content.SchemaResponseContent;


/**
 * Schema API AbstractSensefyResponse
 * It contains :
 *
 * fields - all the fields in Solr, if required
 * responseContent - the body of the response
 * error - any error
 *
 * @author Alessandro Benedetti
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = { "responseContent", "error" } )
public class SchemaResponse
    extends AbstractSensefyResponse
{
    private SchemaResponseContent responseContent;

    public SchemaResponseContent getResponseContent()
    {
        return responseContent;
    }

    public void setResponseContent( SchemaResponseContent responseContent )
    {
        this.responseContent = responseContent;
    }

    public SchemaResponse()
    {
        this.setError( null );
    }
}
