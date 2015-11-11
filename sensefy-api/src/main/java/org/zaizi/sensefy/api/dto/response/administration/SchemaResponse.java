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
package org.zaizi.sensefy.api.dto.response.administration;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.zaizi.sensefy.api.dto.response.AbstractSensefyResponse;
import org.zaizi.sensefy.api.dto.response.content.SchemaResponseContent;


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
