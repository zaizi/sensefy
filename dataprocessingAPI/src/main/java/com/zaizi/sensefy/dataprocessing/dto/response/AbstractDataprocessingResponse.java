package com.zaizi.sensefy.dataprocessing.dto.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlRootElement
@XmlType( propOrder = { "error","header"} )
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public abstract class AbstractDataprocessingResponse {
	
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

}
