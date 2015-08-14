package com.zaizi.dto.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zaizi.exception.ComponentCode;

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
