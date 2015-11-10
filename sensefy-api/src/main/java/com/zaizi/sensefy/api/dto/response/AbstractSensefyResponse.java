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
package com.zaizi.sensefy.api.dto.response;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Author: Alessandro Benedetti
 * Date: 08/07/2014
 * @since 1.4
 */

@XmlRootElement
@XmlType( propOrder = { "error","header"} )
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public abstract class AbstractSensefyResponse
{
    @XmlElement( nillable = true )
    protected ResponseError error;

    protected ResponseHeader header;

    public ResponseError getError()
    {
        return error;
    }

    public void setError( ResponseError error )
    {
        this.error = error;
    }
    public ResponseHeader getHeader()
    {
        return header;
    }

    public void setHeader( ResponseHeader header )
    {
        this.header = header;
    }

    public void setStatus( int status )
    {
        header.setStatus( status );
    }

    public void setTime( long time )
    {
        header.setQTime( time );
    }

    public void setQuery(String q){
        header.setQuery( q );
    }

}
