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

    private static final String GROUP_NAME = "一分机场";

    private final MihomoApi mihomoApi;

    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void executeEveryFiveMinutes() {
        // 检测代理状态
        if (!WindowsProxyUtil.getProxyInfo().isEnabled() && !mihomoApi.getConfigs().getTun().isEnable()) {
            log.info("未开启代理或Tun, 任务结束!");
            return;
        }

        // 测速
        Map<String, Integer> delayMap = mihomoApi.delay(GROUP_NAME, "https://www.gstatic.com/generate_204", 5000);

        // 获取当前节点
        ProxyGroupDto currentProxy = mihomoApi.getProxy(GROUP_NAME);
        String currentNow = currentProxy.getNow();

        // 获取最佳节点
        ProxySwitchStrategy proxySwitchStrategy = new ProxySwitchStrategy(delayMap, currentNow);
        String bestProxy = proxySwitchStrategy.getBestProxy();

        // 判断计算出的最佳节点是否等于当前节点
        if (currentNow.equals(bestProxy)) {
            log.info("当前节点已为最佳节点, 跳过切换.");
            return;
        }

        // 切换节点
        mihomoApi.switchProxy(GROUP_NAME, Map.of("name", bestProxy));
        log.info("节点切换成功.");
    }

}
