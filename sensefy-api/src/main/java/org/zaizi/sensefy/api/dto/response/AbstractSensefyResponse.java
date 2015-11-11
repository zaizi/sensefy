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
