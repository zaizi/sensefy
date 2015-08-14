package com.zaizi.exception;

/**
 * Generic Exception for the project
 * 
 * @author aayala
 * @since 1.4
 */
public class SensefyException extends Exception
{
    private static final long serialVersionUID = -950258955179608264L;

    private int status;

    public SensefyException(int status)
    {
        super();
        this.setStatus(status);
    }

    public SensefyException(int status, String msg)
    {
        super(msg);
        this.setStatus(status);
    }

    public SensefyException(int status, String msg, Exception e)
    {
        super(msg, e);
        this.setStatus(status);
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
}
