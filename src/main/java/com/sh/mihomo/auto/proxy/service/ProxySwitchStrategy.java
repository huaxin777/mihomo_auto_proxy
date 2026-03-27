package com.sh.mihomo.auto.proxy.service;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @ClassName: ProxySwitchStrategy
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/3/20 14:15
 * @Author: SH
 */
@Slf4j
public class ProxySwitchStrategy {

    private static final String LOW_RANGE_NAME = "0.1倍率";

    // 防抖阈值
    private static final int SWITCH_THRESHOLD = 40;

    private final Map<String, Integer> delayMap;
    private final String currentNow;

    public ProxySwitchStrategy(Map<String, Integer> delayMap, String currentNow) {
        log.info("初始化: 节点信息:【{}】", JSON.toJSONString(delayMap));
        this.delayMap = delayMap;
        this.currentNow = currentNow;

        log.info("初始化: 当前节点:【{}】 延迟:【{}ms】",
                currentNow,
                delayMap.getOrDefault(currentNow, -1));
    }

    /**
     * 主入口
     */
    public String getBestProxy() {

        String bestNode = null;
        double bestScore = Double.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : delayMap.entrySet()) {

            String node = entry.getKey();
            int delay = entry.getValue();

            double score = calculateScore(node, delay);

            if (score < bestScore) {
                bestScore = score;
                bestNode = node;
            }
        }

        return decideSwitch(bestNode);
    }

    /**
     * 🧠 加权评分（核心）
     */
    private double calculateScore(String node, int delay) {

        boolean isLow = node.contains(LOW_RANGE_NAME);
        boolean isCurrent = node.equals(currentNow);
        boolean currentLow = currentNow.contains(LOW_RANGE_NAME);

        double score = delay;

        // =========================
        // 1️⃣ 低倍率分段权重
        // =========================
        if (isLow) {

            if (delay <= 200) {
                // 强优先
                score *= 0.3;
            } else if (delay <= 300) {
                // 弱优先
                score *= 0.6;
            } else {
                // 惩罚
                score *= 1.3;
            }
        }

        // =========================
        // 2️⃣ 当前节点粘性
        // =========================
        if (isCurrent) {
            score *= 0.85;
        }

        // =========================
        // 3️⃣ 当前是低倍率 → 惩罚普通节点
        // =========================
        if (currentLow && !isLow) {
            score *= 1.15;
        }

        return score;
    }

    /**
     * 🚦 是否切换（只在这里打印评分）
     */
    private String decideSwitch(String bestNode) {

        if (bestNode == null) {
            log.warn("❌ 无可用节点，保持当前 -> {}", currentNow);
            return currentNow;
        }

        int currentDelay = delayMap.getOrDefault(currentNow, Integer.MAX_VALUE);
        int bestDelay = delayMap.getOrDefault(bestNode, Integer.MAX_VALUE);

        double currentScore = calculateScore(currentNow, currentDelay);
        double bestScore = calculateScore(bestNode, bestDelay);

        int delayDiff = currentDelay - bestDelay;
        double scoreDiff = currentScore - bestScore;

        boolean currentLow = currentNow.contains(LOW_RANGE_NAME);
        boolean bestLow = bestNode.contains(LOW_RANGE_NAME);

        // =========================
        // 🚀 1️⃣ 普通 → 低倍率：优先
        // =========================
        if (!currentLow && bestLow && bestScore < currentScore) {
            log.info("🚀 最终决策: 切换节点 【{} {}ms】 -> 【{} {}ms】 | 原因: 普通→低倍率优先",
                    currentNow, currentDelay,
                    bestNode, bestDelay);
            return bestNode;
        }

        // =========================
        // ❌ 2️⃣ 双重防抖
        // =========================
        if (delayDiff < SWITCH_THRESHOLD && scoreDiff < 5) {
            log.info("🛑 最终决策: 保持节点 【{} {}ms】 | 原因: 防抖（延迟差={}ms, 评分差={})",
                    currentNow,
                    currentDelay,
                    delayDiff,
                    String.format("%.2f", scoreDiff));
            return currentNow;
        }

        // =========================
        // ✅ 3️⃣ 正常切换
        // =========================
        log.info("✅ 最终决策: 切换节点 【{} {}ms】 -> 【{} {}ms】 | 原因: 综合评分更优（delayDiff={}ms, scoreDiff={}）",
                currentNow, currentDelay,
                bestNode, bestDelay,
                delayDiff,
                String.format("%.2f", scoreDiff));

        return bestNode;
    }
}
