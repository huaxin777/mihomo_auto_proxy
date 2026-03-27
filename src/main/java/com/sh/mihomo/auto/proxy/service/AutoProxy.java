package com.sh.mihomo.auto.proxy.service;

import com.sh.mihomo.auto.proxy.client.MihomoApi;
import com.sh.mihomo.auto.proxy.dto.ProxyGroupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName: AutoProxy
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/3/20 11:24
 * @Author: SH
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AutoProxy {

    private final MihomoApi mihomoApi;
    private static final String GROUP_NAME = "一分机场";

    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void executeEveryFiveMinutes() {
        if (!WindowsProxyUtil.getProxyInfo().isEnabled()) {
            log.info("未开启代理, 任务结束!");
            return;
        }
        Map<String, Integer> delayMap = mihomoApi.delay(GROUP_NAME, "https://www.gstatic.com/generate_204", 5000);
        ProxyGroupDto currentProxy = mihomoApi.getProxy(GROUP_NAME);
        String currentNow = currentProxy.getNow();
        ProxySwitchStrategy proxySwitchStrategy = new ProxySwitchStrategy(delayMap, currentNow);
        String bestProxy = proxySwitchStrategy.getBestProxy();
        if (currentNow.equals(bestProxy)) {
            return;
        }
        String name = mihomoApi.switchProxy(GROUP_NAME, Map.of("name", bestProxy));
        log.info("节点切换成功:【{}】", name);
    }
}
