/**
 * (C) Copyright 2015 Zaizi Limited (http://www.zaizi.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at 
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 **/
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
