package com.kolosov.weatheradviserbot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import com.kolosov.weatheradviserbot.openMeteo.OpenMeteoAPIClient;


@Configuration
public class OpenMeteoConfiguration {

    @Bean
    OpenMeteoAPIClient openMeteoAPIService(){
        RestClient restClient = RestClient.create(OpenMeteoAPIClient.OPEN_METEO_API_URL);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder()
                .exchangeAdapter(RestClientAdapter.create(restClient))
                .build();
        return httpServiceProxyFactory.createClient(OpenMeteoAPIClient.class);
    }

}
