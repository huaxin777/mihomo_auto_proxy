package com.sh.mihomo.auto.proxy.util.proxy;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ProxyProviderFactory
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/5/22 9:59
 * @Author: SH
 */
@Slf4j
@Component
public class ProxyProviderFactory {

    private final Map<OSTypeEnum, ProxyProvider> providerMap = new EnumMap<>(OSTypeEnum.class);

    @Resource private List<ProxyProvider> proxyProviders;

    @PostConstruct
    public void init() {

        for (ProxyProvider provider : proxyProviders) {

            providerMap.put(provider.getType(), provider);

            log.info("注册代理实现: {}", provider.getType());
        }
    }

    public ProxyProvider getProvider() {

        OSTypeEnum osType = OSTypeEnum.current();

        return providerMap.get(osType);
    }
}
