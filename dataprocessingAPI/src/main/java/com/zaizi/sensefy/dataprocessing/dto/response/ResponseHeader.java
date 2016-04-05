package com.zaizi.sensefy.dataprocessing.dto.response;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType( propOrder = {"status"} )
public class ResponseHeader
{
    private int status;

    public ResponseHeader() {
        status=Response.Status.OK.getStatusCode();
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ResponseHeader [status=" + status+ "]";
    }
}
