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
package org.zaizi.sensefy.auth.exception;

/**
 * Generic Exception for the project
 * 
 * @author aayala
 * 
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
