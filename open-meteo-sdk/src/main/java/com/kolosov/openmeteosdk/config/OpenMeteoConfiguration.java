package com.kolosov.openmeteosdk.config;

import com.kolosov.openmeteosdk.api.OpenMeteoAPIClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


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
