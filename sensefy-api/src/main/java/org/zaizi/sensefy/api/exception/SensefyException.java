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
package org.zaizi.sensefy.api.exception;

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
