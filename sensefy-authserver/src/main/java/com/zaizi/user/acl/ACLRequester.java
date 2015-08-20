package com.zaizi.user.acl;

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
