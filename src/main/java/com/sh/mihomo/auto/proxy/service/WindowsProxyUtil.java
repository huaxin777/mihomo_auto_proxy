package com.sh.mihomo.auto.proxy.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @ClassName: WindowsProxyUtil
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/3/20 16:37
 * @Author: SH
 */
@Slf4j
public class WindowsProxyUtil {

    private static final String REG_PATH =
            "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings";

    /**
     * 获取代理配置
     */
    public static ProxyInfo getProxyInfo() {

        boolean enabled = readProxyEnable();
        String server = readProxyServer();

        ProxyInfo info = new ProxyInfo();
        info.setEnabled(enabled);
        info.setRaw(server);

        if (enabled && server != null) {
            parseProxy(server, info);
        }

        log.info("系统代理状态: {}", info);
        return info;
    }

    /**
     * 是否开启代理
     */
    private static boolean readProxyEnable() {
        String result = execReg("ProxyEnable");
        return result != null && result.trim().endsWith("0x1");
    }

    /**
     * 获取代理地址
     */
    private static String readProxyServer() {
        return execReg("ProxyServer");
    }

    /**
     * 执行 reg query
     */
    private static String execReg(String key) {
        try {

            ProcessBuilder pb = new ProcessBuilder(
                    "reg",
                    "query",
                    REG_PATH,
                    "/v",
                    key
            );

            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(key)) {
                        String[] arr = line.trim().split("\\s+");
                        return arr[arr.length - 1];
                    }
                }
            }

            process.waitFor(); // ✅ 等待执行完成

        } catch (Exception e) {
            log.error("读取注册表失败", e);
        }
        return null;
    }

    /**
     * 解析 ProxyServer
     */
    private static void parseProxy(String server, ProxyInfo info) {

        // 情况1：统一代理
        // 127.0.0.1:7890
        if (!server.contains("=")) {
            String[] arr = server.split(":");
            if (arr.length == 2) {
                info.setHost(arr[0]);
                info.setPort(Integer.parseInt(arr[1]));
                info.setType("ALL");
            }
            return;
        }

        // 情况2：多协议
        // http=127.0.0.1:7890;https=127.0.0.1:7890
        String[] items = server.split(";");

        for (String item : items) {
            if (item.startsWith("http=")) {
                String value = item.substring(5);
                String[] arr = value.split(":");
                if (arr.length == 2) {
                    info.setHost(arr[0]);
                    info.setPort(Integer.parseInt(arr[1]));
                    info.setType("HTTP");
                    return;
                }
            }
        }
    }

    /**
     * 返回对象
     */
    @Data
    public static class ProxyInfo {
        private boolean enabled;
        private String host;
        private Integer port;
        private String type;
        private String raw;
    }
}
