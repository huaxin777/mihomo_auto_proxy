package com.sh.mihomo.auto.proxy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ProxyGroupDto
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/3/20 14:00
 * @Author: SH
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProxyGroupDto {

    /**
     * 当前代理组是否在线。
     */
    @JsonProperty("alive")
    private boolean alive;

    /**
     * 所有代理节点的列表。 包含不同的节点名称以及其他状态信息。
     */
    @JsonProperty("all")
    private List<String> all;

    /**
     * 拨号代理相关设置。可能为空。
     */
    @JsonProperty("dialer-proxy")
    private String dialerProxy;

    /**
     * 额外的配置信息，可能为空。
     */
    @JsonProperty("extra")
    private Map<String, Object> extra;

    /**
     * 是否隐藏该代理组。
     */
    @JsonProperty("hidden")
    private boolean hidden;

    /**
     * 历史记录，可能为空。
     */
    @JsonProperty("history")
    private List<String> history;

    /**
     * 图标设置，可能为空。
     */
    @JsonProperty("icon")
    private String icon;

    /**
     * 接口相关设置，可能为空。
     */
    @JsonProperty("interface")
    private String interfaceType;

    /**
     * 是否启用 MPTCP（多路径 TCP），通常用于提高多链路带宽利用率。
     */
    @JsonProperty("mptcp")
    private boolean mptcp;

    /**
     * 当前代理组的名称。
     */
    @JsonProperty("name")
    private String name;

    /**
     * 当前选择的代理节点，表示该代理组当前使用的节点。
     */
    @JsonProperty("now")
    private String now;

    /**
     * 提供商名称，可能为空。
     */
    @JsonProperty("provider-name")
    private String providerName;

    /**
     * 路由标记，通常用于指定特定的路由策略。
     */
    @JsonProperty("routing-mark")
    private int routingMark;

    /**
     * 是否启用 SMUX（多路复用协议）。
     */
    @JsonProperty("smux")
    private boolean smux;

    /**
     * 测试用 URL，可能为空。
     */
    @JsonProperty("testUrl")
    private String testUrl;

    /**
     * 是否启用 TFO（TCP Fast Open）。
     */
    @JsonProperty("tfo")
    private boolean tfo;

    /**
     * 代理类型，通常为 "Selector" 表示选择代理组。
     */
    @JsonProperty("type")
    private String type;

    /**
     * 是否启用 UDP 转发。
     */
    @JsonProperty("udp")
    private boolean udp;

    /**
     * 是否启用 UOT（UDP over TCP），可能为空。
     */
    @JsonProperty("uot")
    private boolean uot;

    /**
     * 是否启用 XUDP（扩展 UDP）。
     */
    @JsonProperty("xudp")
    private boolean xudp;

}
