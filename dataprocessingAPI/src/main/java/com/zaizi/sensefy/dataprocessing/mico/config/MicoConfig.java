package com.zaizi.sensefy.dataprocessing.mico.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zaizi.mico.client.MicoClientFactory;
import org.zaizi.mico.client.QueryClient;
import org.zaizi.mico.client.exception.MicoClientException;

import com.zaizi.sensefy.dataprocessing.mico.dbpedia.DbpediaQueryClient;

@Configuration
public class MicoConfig {
	
	@Bean
	public MicoClientFactory micoClientFactory(@Value("${mico.host}") String host, @Value("${mico.username}") String username,
			@Value("${mico.password}") String password){
		return new MicoClientFactory(host, username, password);
	}
	
	@Bean
	@Autowired
	public QueryClient queryClient(MicoClientFactory micoClientFactory) throws MicoClientException{
		return micoClientFactory.createQueryServiceClient();
	}
	
	@Bean
	public DbpediaQueryClient dbpediaQueryClient(){
		return new DbpediaQueryClient();
	}

}
