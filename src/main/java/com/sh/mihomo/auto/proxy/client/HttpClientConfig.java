package com.sh.mihomo.auto.proxy.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @ClassName: HttpClientConfig
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/3/20 11:27
 * @Author: SH
 */
@Configuration
public class HttpClientConfig {

    @Bean
    MihomoApi mihomoApi(@Value("${mihomo.api.base-url}") String baseUrl,

            @Value("${mihomo.api.secret}") String secret) {

        RestClient restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + secret)
            .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
            .build();

        return factory.createClient(MihomoApi.class);
    }

}
