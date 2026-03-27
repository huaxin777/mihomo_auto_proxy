package com.sh.mihomo.auto.proxy.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public MihomoApi clashApi() {

        RestClient restClient = RestClient.builder()
                .build();

        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory
                        .builder()
                        .exchangeAdapter(
                                RestClientAdapter.create(restClient)
                        )
                        .build();

        return factory.createClient(MihomoApi.class);
    }
}
