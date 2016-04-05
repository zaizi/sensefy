package com.zaizi.sensefy.dataprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;

@SpringBootApplication(exclude={SolrAutoConfiguration.class,FreeMarkerAutoConfiguration.class})
public class DataprocessingApiApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(DataprocessingApiApplication.class, args);
    }
}
