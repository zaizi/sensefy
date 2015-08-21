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
