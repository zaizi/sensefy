package com.zaizi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.zaizi.sensefy.api.SensefyResourceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SensefyResourceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SensefyResourceApplicationTests
{

    @Value("${local.server.port}")
    private int port;
    // @Value("${server.contePath;

    private RestTemplate template = new TestRestTemplate();

    @Test
    public void testKeywordSearchAnauthorized()
    {
        // keywordSearch
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/keywordSearch",
                String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

}
