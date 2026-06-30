package com.sh.mihomo.auto.proxy.util.proxy;

/**
 * @ClassName: ProxyProvider
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/5/22 9:51
 * @Author: SH
 */
public interface ProxyProvider {

	/**
	 * 支持的系统
	 */
	OSTypeEnum getType();

	/**
	 * 判断是否开启代理
	 */
	boolean isEnableProxyInfo();

}
