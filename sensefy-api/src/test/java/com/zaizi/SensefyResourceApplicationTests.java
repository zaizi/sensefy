package com.zaizi;

import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.zaizi.sensefy.api.SensefyResourceApplication;
import com.zaizi.sensefy.api.controller.ServiceController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SensefyResourceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SensefyResourceApplicationTests
{

    @Value("${local.server.port}")
    int port;

    @Autowired
    ServiceController serviceController;

    String baseTestServerUrl()
    {
        return "http://localhost:" + port + "/";
    }

    abstract class ApiHelper
    {
        abstract URIBuilder getQueryParams() throws URISyntaxException;

        public ResponseEntity<String> callApi(String userName, String password) throws URISyntaxException
        {
            return callApi(userName, password, HttpStatus.OK);
        }

        public ResponseEntity<String> callApi(String userName, String password, HttpStatus expectedHttpStatus)
                throws URISyntaxException
        {
            URIBuilder uriBuilder = getQueryParams();

            RestTemplate template = new TestRestTemplate();
            final HttpHeaders headers = new HttpHeaders();
            String accessToken = TestOAuthUtils.getOAuth2AccessToken(userName, password);
            TestOAuthUtils.addOAuth2AccessTokenToHeader(headers, accessToken);
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = template.exchange(uriBuilder.toString(), HttpMethod.GET, httpEntity,
                    String.class);
            Assert.assertEquals(expectedHttpStatus, response.getStatusCode());
            return response;
        }
    }

    class KeywordSearchApiHelper extends ApiHelper
    {
        String query = "";
        String fields = "";
        String filters = "";
        boolean facet = false;
        int start = 0;
        int rows = 0;
        String order = "";
        boolean spellcheck = false;
        boolean clustering = false;
        String clusteringSort = "";

        @Override
        URIBuilder getQueryParams() throws URISyntaxException
        {
            URIBuilder uriBuilder = new URIBuilder(baseTestServerUrl() + "keywordSearch");
            uriBuilder.setParameter("query", query);
            uriBuilder.setParameter("fields", fields);
            uriBuilder.setParameter("filters", filters);
            uriBuilder.setParameter("facet", facet + "");
            uriBuilder.setParameter("start", start + "");
            uriBuilder.setParameter("rows", rows + "");
            uriBuilder.setParameter("order", order);
            uriBuilder.setParameter("spellcheck", spellcheck + "");
            uriBuilder.setParameter("clustering", clustering + "");
            uriBuilder.setParameter("clusteringSort", clusteringSort);
            return uriBuilder;
        }
    }

    @Test
    public void testKeywordSearchAnauthorized()
    {
        RestTemplate template = new TestRestTemplate();
        // keywordSearch
        ResponseEntity<String> response = template.getForEntity(baseTestServerUrl() + "keywordSearch", String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testRootPathAnauthorized()
    {
        RestTemplate template = new TestRestTemplate();
        ResponseEntity<String> response = template.getForEntity(baseTestServerUrl(), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testRootPathAuthorized()
    {
        RestTemplate template = new TestRestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        String accessToken = TestOAuthUtils.getDefOAuth2AccessToken();
        TestOAuthUtils.addOAuth2AccessTokenToHeader(headers, accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(baseTestServerUrl(), HttpMethod.GET, httpEntity,
                String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testKeywordSearchPathAuthorized() throws URISyntaxException
    {
        KeywordSearchApiHelper keywordSearchApiHelper = new KeywordSearchApiHelper();
        ResponseEntity<String> response = keywordSearchApiHelper.callApi(TestOAuthUtils.ZAIZI_USER,
                TestOAuthUtils.ZAIZI_USER_PWD, HttpStatus.OK);
        Assert.assertNotNull(response.getBody());
    }
}
