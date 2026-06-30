package com.sh.mihomo.auto.proxy.util.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ProxyService
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/5/22 9:59
 * @Author: SH
 */
@Component
@RequiredArgsConstructor
public class ProxyService {

	private final ProxyProviderFactory proxyProviderFactory;

	public boolean isEnableProxyInfo() {

		ProxyProvider provider = proxyProviderFactory.getProvider();

		if (provider == null) {
			return false;
		}

		return provider.isEnableProxyInfo();
	}

}
