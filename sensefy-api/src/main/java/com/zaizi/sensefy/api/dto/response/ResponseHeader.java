package com.zaizi.sensefy.api.dto.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Response Header with the details related to the response.
 * 
 * @author aayala
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = {"QTime", "status", "query"} )
public class ResponseHeader
{
    private long QTime;

    private int status;

    private String query;

    public ResponseHeader() {
        status=200;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery( String query )
    {
        this.query = query;
    }

    public long getQTime()
    {
        return QTime;
    }

    public int getStatus()
    {
        return status;
    }

    public void setQTime(long qTime)
    {
        QTime = qTime;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ResponseHeader [status=" + status + ", QTime=" + QTime + "]";
    }
}
