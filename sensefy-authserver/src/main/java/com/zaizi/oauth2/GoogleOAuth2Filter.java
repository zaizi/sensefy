package com.zaizi.oauth2;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleOAuth2Filter extends AbstractOAuth2Filter
{
    private static final Logger LOG = Logger.getLogger(GoogleOAuth2Filter.class);

    public GoogleOAuth2Filter(final AbstractOAuth2Conf oauth2Config)
    {
        super(oauth2Config);
    }

    @Override
    ExtAOth2Credential buildUser(final JSONObject userJson) throws ServletException
    {
        final String domainTmp = (String) userJson.opt("domain");
        final String domain = domainTmp != null ? domainTmp : "gmail.com";
        final String email = parseEmail(userJson, domain);
        return new ExtAOth2Credential(oauth2Config.getProviderType(), email, null, domain);
    }

    private String parseEmail(final JSONObject jsonUser, final String domain) throws ServletException
    {
        String res = null;
        JSONException error = null;
        try
        {
            final JSONArray emails = jsonUser.getJSONArray("emails");
            for (int i = 0, s = emails.length(); i < s; i++)
            {
                final String email = ((JSONObject) emails.get(i)).getString("value");
                if (email.endsWith(domain))
                {
                    res = email;
                    break;
                }
            }
        }
        catch (final JSONException e)
        {
            error = e;
        }

        if (res == null)
        {
            final String errmsg = "Goole Login internal error: error parsing emails";
            LOG.error(errmsg + ", google profile:\n" + jsonUser, error);
            throw new ServletException(errmsg, error);
        }
        return res;
    }
}
