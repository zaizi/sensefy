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

import com.zaizi.controller.ServiceController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SensefyResourceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SensefyResourceApplicationTests
{
    public static final String CREDENDIAL_HEADER_SEPARATOR = ":";
    public static final String CREDENDIAL_HEADER_KEY = "authorization";
    public static final String CREDENDIAL_HEADER_PREFIX_VALUE = "Basic ";
    public static final String alfrescoUser = "zuser1";
    public static final String alfrescoPwd = "zaizi321#$%";

    @Value("${local.server.port}")
    private int port;

    // private RestTemplate template = new TestRestTemplate();

    @Autowired
    ServiceController serviceController;

    private String baseTestServerUrl()
    {
        return "http://localhost:" + port + "/";
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
        URIBuilder uriBuilder = new URIBuilder(baseTestServerUrl() + "keywordSearch");
        uriBuilder.setParameter("query", "");
        uriBuilder.setParameter("fields", "");
        uriBuilder.setParameter("filters", "");
        uriBuilder.setParameter("facet", "false");
        uriBuilder.setParameter("start", "0");
        uriBuilder.setParameter("rows", "0");
        uriBuilder.setParameter("order", "dummy-order");
        uriBuilder.setParameter("spellcheck", "false");
        uriBuilder.setParameter("clustering", "false");
        uriBuilder.setParameter("clusteringSort", "dummy-clusteringSort");

        RestTemplate template = new TestRestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        String accessToken = TestOAuthUtils.getDefOAuth2AccessToken();
        TestOAuthUtils.addOAuth2AccessTokenToHeader(headers, accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(uriBuilder.toString(), HttpMethod.GET, httpEntity,
                String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
