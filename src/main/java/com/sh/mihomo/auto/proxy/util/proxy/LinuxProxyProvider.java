package com.sh.mihomo.auto.proxy.util.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName: LinuxProxyProvider
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/5/22 9:56
 * @Author: SH
 */
@Slf4j
@Component
public class LinuxProxyProvider implements ProxyProvider {

    @Override
    public OSTypeEnum getType() {
        return OSTypeEnum.LINUX;
    }

    @Override
    public boolean isEnableProxyInfo() {
        return false;
    }

}
