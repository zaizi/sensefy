package com.zaizi.user.acl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zaizi.exception.SensefyException;

/**
 * ACL Requester. Make requests to ManifoldCF Authority Service
 * 
 * @author aayala
 * 
 */
@Component
public class ManifoldACLRequester implements ACLRequester {
	/** A logger we can use */
	private static final Logger logger = LoggerFactory.getLogger(ManifoldACLRequester.class);

	@Value("${sensefy.manifold.authority.endpoint}")
	private String authorityBaseURL;
	private int socketTimeOut = 300000;
	private PoolingClientConnectionManager httpConnectionManager = null;
	private HttpClient client = null;
	private int poolSize = 50;

	@Override
	public List<String> getACLs(String userName) {
		try {
			if (userName != null) {
				return getAccessTokens(userName);
			}
			logger.warn(
					"[Retrieving Access Token For User Name] No UserName param provided. Doing query with no security token");
		} catch (Exception e) {
			logger.error("[Retrieving Access Token For User Name] Error when getting ACLS for user: " + userName, e);
		}
		// Empty ACLS response
		return new ArrayList<String>();
	}

	@PostConstruct
	public void init() {
		socketTimeOut = 300000;
		poolSize = 50;

		// Initialize the connection pool
		httpConnectionManager = new PoolingClientConnectionManager();
		httpConnectionManager.setMaxTotal(poolSize);
		httpConnectionManager.setDefaultMaxPerRoute(poolSize);
		BasicHttpParams params = new BasicHttpParams();
		params.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
		params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeOut);
		DefaultHttpClient clientAux = new DefaultHttpClient(httpConnectionManager, params);
		clientAux.setRedirectStrategy(new DefaultRedirectStrategy());
		client = clientAux;
	}

	/**
	 * Setter for the http client
	 * 
	 * @param client
	 */
	public void setClient(HttpClient client) {
		this.client = client;
	}

	/**
	 * Get access tokens given a username
	 * 
	 * @throws SensefyException
	 */
	protected List<String> getAccessTokens(String authenticatedUserName) throws IOException, SensefyException {
		// We can make this more complicated later, with support for https etc.,
		// but this is enough to demonstrate how
		// it all should work.
		String theURL = authorityBaseURL + "/UserACLs?username=" + URLEncoder.encode(authenticatedUserName, "utf-8");

		HttpGet method = new HttpGet(theURL);
		try {
			HttpResponse httpResponse = client.execute(method);
			int rval = httpResponse.getStatusLine().getStatusCode();
			if (rval != 200) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				throw new SensefyException(rval,
						"Couldn't fetch user's access tokens from ManifoldCF authority service: "
								+ Integer.toString(rval) + "; " + response);
			}
			InputStream is = httpResponse.getEntity().getContent();
			try {
				String charSet = ContentType.getOrDefault(httpResponse.getEntity()).getCharset().displayName();

				if (charSet == null)
					charSet = "utf-8";
				Reader r = new InputStreamReader(is, charSet);
				try {
					BufferedReader br = new BufferedReader(r);
					try {
						// Read the tokens, one line at a time. If any
						// authorities are down, we have no current way to
						// note that, but someday we will.
						List<String> tokenList = new ArrayList<String>();
						while (true) {
							String line = br.readLine();
							if (line == null)
								break;
							if (line.startsWith("TOKEN:")) {
								tokenList.add(line.substring("TOKEN:".length()));
							} else {
								// It probably says something about the state of
								// the authority(s) involved, so log it
								logger.info("For user '" + authenticatedUserName + "', saw authority response " + line);
							}
						}
						return tokenList;
					} finally {
						br.close();
					}
				} finally {
					r.close();
				}
			} finally {
				is.close();
			}
		} finally {
			// method.releaseConnection();
		}
	}

}
