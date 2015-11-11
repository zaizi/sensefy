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
package org.zaizi.sensefy.api.dto.response;

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
