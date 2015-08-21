package com.zaizi;

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
import org.springframework.util.Base64Utils;
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

    @Test
    public void testKeywordSearchAnauthorized()
    {
        RestTemplate template = new TestRestTemplate();
        // keywordSearch
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/keywordSearch",
                String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    public String buildCredentialHeaderB64(String userName, String password)
    {
        final String usrPwd = new StringBuffer().append(userName).append(CREDENDIAL_HEADER_SEPARATOR).append(password).toString();
        final String usrPwdB64 = Base64Utils.encodeToString(usrPwd.getBytes());
        return CREDENDIAL_HEADER_PREFIX_VALUE + usrPwdB64;
    }

    private void headerMap(String userName, String password, final HttpHeaders headers)
    {
        headers.add(CREDENDIAL_HEADER_KEY, buildCredentialHeaderB64(userName, password));
    }

    @Test
    public void test123()
    {
        RestTemplate template = new TestRestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headerMap(alfrescoUser, alfrescoPwd, headers);

        String oauth2Url = "http://localhost:8090/ua/oauth/authorize?client_id=sensefy&response_type=code&state=QstSHH&redirect_uri=/";

        ResponseEntity<String> exchange = template.exchange(oauth2Url, HttpMethod.GET, new HttpEntity<String>(headers),
                String.class);
        HttpHeaders headers2 = exchange.getHeaders();
        HttpStatus statusCode = exchange.getStatusCode();
        String body = exchange.getBody();
        int i = 0;

        // // ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/", String.class);
        // // assertNotEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        // String query = "test";
        // String fields = "";
        // String filters = "";
        // boolean facet = false;
        // int start = 0;
        // Integer rows = 5;
        // String order = "";
        // boolean spellcheck = false;
        // boolean clustering = false;
        // String clusteringSort = "";
        // Principal user = new BasicUserPrincipal("admin");
        // SearchResponse keywordBasedSearch = serviceController.keywordBasedSearch(query, fields, filters, facet,
        // start,
        // rows, order, spellcheck, clustering, clusteringSort, user);
        // Assert.assertNotNull(keywordBasedSearch);

    }
}
