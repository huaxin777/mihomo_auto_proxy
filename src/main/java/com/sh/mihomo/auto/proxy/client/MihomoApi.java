package com.sh.mihomo.auto.proxy.client;

import com.sh.mihomo.auto.proxy.dto.MihomoConfigDto;
import com.sh.mihomo.auto.proxy.dto.ProxyGroupDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.Map;

/**
 * @ClassName: MihomoApi
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/3/20 11:20
 * @Author: SH
 */
@HttpExchange(url = "http://127.0.0.1:9090", headers = "Authorization=Bearer set-your-secret")
public interface MihomoApi {

	/**
	 * PUT /proxies/{group} 切节点
	 */
	@PutExchange(value = "/proxies/{group}", contentType = MediaType.APPLICATION_JSON_VALUE)
	String switchProxy(@PathVariable("group") String group, @RequestBody Map<String, String> body);

	/**
	 * GET /proxies/{group} 查当前节点
	 */
	@GetExchange("/proxies/{group}")
	ProxyGroupDto getProxy(@PathVariable("group") String group);

	/**
	 * GET /group/{group}/delay 测速
	 */
	@GetExchange("/group/{group}/delay")
	Map<String, Integer> delay(@PathVariable("group") String group, @RequestParam("url") String url,
			@RequestParam("timeout") int timeout);

	/**
	 * GET /configs 查配置
	 */
	@GetExchange("/configs")
	MihomoConfigDto getConfigs();

	/**
	 * PATCH /configs 改配置
	 */
	@PatchExchange(value = "/configs", contentType = MediaType.APPLICATION_JSON_VALUE)
	String updateConfigs(@RequestBody Map<String, Object> body);

}
