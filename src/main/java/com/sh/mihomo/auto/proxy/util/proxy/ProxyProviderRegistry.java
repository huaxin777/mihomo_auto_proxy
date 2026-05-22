package com.sh.mihomo.auto.proxy.util.proxy;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: ProxyProviderRegistry
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/5/22 9:55
 * @Author: SH
 */
@Slf4j
public class ProxyProviderRegistry {

	private static final Map<OSTypeEnum, ProxyProvider> PROVIDER_MAP = new ConcurrentHashMap<>();

	private ProxyProviderRegistry() {
	}

	public static void register(ProxyProvider provider) {

		if (provider == null) {
			return;
		}

		PROVIDER_MAP.put(provider.getType(), provider);

		log.info("注册代理实现: {}", provider.getType());
	}

	public static ProxyProvider getProvider() {

		OSTypeEnum current = OSTypeEnum.current();

		return PROVIDER_MAP.get(current);
	}

}
