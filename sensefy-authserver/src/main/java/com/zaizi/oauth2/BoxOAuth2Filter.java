package com.zaizi.oauth2;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class BoxOAuth2Filter extends AbstractOAuth2Filter
{
    private static final Logger LOG = Logger.getLogger(BoxOAuth2Filter.class);

    public BoxOAuth2Filter(final AbstractOAuth2Conf oauth2Config)
    {
        super(oauth2Config);
    }

    /*
     * {"space_used":25685,"max_upload_size":2147483648,"address":"","timezone": "America/Los_Angeles"
     * ,"created_at":"2015-06-16T01:46:59-07:00","language":
     * "en","type":"user","login":"asantacroce@zaizi.com","avatar_url": "https://app.box.com/api/avatar/large/240279117"
     * ,"phone":"","space_amount" :10737418240,"name":"alessio","id":"240279117","modified_at"
     * :"2015-08-18T05:00:05-07:00","job_title":"","status":"active"}
     */
    @Override
    ExtAOth2Credential buildUser(final JSONObject userJson) throws ServletException
    {
        final String email = userJson.getString("login");
        final String domain = email.split("@")[1];
        return new ExtAOth2Credential(oauth2Config.getProviderType(), email, null, domain);
    }
}
