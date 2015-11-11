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
package org.zaizi.sensefy.auth.user.acl;

import java.util.List;

/**
 * ACL Requester. Make requests to get ACLs for an user
 * 
 * @author aayala
 * 
 */
public interface ACLRequester
{
    /**
     * Retuns a list of ACLs for an userName
     * 
     * @param userName
     * @return
     */
    List<String> getACLs(String userName);
}
