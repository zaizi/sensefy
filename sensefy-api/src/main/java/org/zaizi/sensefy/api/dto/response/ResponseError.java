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

import org.zaizi.sensefy.api.exception.ComponentCode;

/**
 * This class models an error with the related information to recognize the causes.
 * 
 * @author aayala
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = {"statusCode","componentCode","msg"} )
public class ResponseError
{
    private int statusCode;

    private ComponentCode componentCode;

    private String msg;

    public ResponseError(){}
    
    public ResponseError(int statusCode,ComponentCode componentCode, String msg)
    {
        super();
        this.statusCode = statusCode;
        this.msg = msg;
        this.componentCode=componentCode;
    }

    public ComponentCode getComponentCode()
    {
        return componentCode;
    }

    public void setComponentCode( ComponentCode componentCode )
    {
        this.componentCode = componentCode;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode( int statusCode )
    {
        this.statusCode = statusCode;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return "ResponseError [statusCode=" + statusCode + ", msg=" + msg + "]";
    }

}
