package com.sh.mihomo.auto.proxy.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: MihomoConfigDto
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/4/15 13:27
 * @Author: SH
 */
@Data
public class MihomoConfigDto {

	private int port;

	private int socksPort;

	private int redirPort;

	private int tproxyPort;

	private int mixedPort;

	private TunConfig tun;

	private TuicServerConfig tuicServer;

	private String ssConfig;

	private String vmessConfig;

	private List<String> authentication;

	private List<String> skipAuthPrefixes;

	private List<String> lanAllowedIps;

	private List<String> lanDisallowedIps;

	private boolean allowLan;

	private String bindAddress;

	private boolean inboundTfo;

	private boolean inboundMptcp;

	private String mode;

	private boolean unifiedDelay;

	private String logLevel;

	private boolean ipv6;

	private String interfaceName;

	private int routingMark;

	private GeoxUrlConfig geoxUrl;

	private boolean geoAutoUpdate;

	private int geoUpdateInterval;

	private boolean geodataMode;

	private String geodataLoader;

	private String geositeMatcher;

	private boolean tcpConcurrent;

	private String findProcessMode;

	private boolean sniffing;

	private String globalClientFingerprint;

	private String globalUa;

	private boolean etagSupport;

	private int keepAliveIdle;

	private int keepAliveInterval;

	private boolean disableKeepAlive;

	@Data
	public static class TunConfig {

		private boolean enable;

		private String device;

		private String stack;

		private List<String> dnsHijack;

		private boolean autoRoute;

		private boolean autoDetectInterface;

		private int mtu;

		private int gsoMaxSize;

		private List<String> inet4Address;

		private int fileDescriptor;

		private boolean recvmsgx;

	}

	@Data
	public static class TuicServerConfig {

		private boolean enable;

		private String listen;

		private String certificate;

		private String privateKey;

		private String echKey;

		private TuicMuxOption muxOption;

	}

	@Data
	public static class TuicMuxOption {

		private TuicBrutalOption brutal;

	}

	@Data
	public static class TuicBrutalOption {

		private boolean enabled;

	}

	@Data
	public static class GeoxUrlConfig {

		private String geoIp;

		private String mmdb;

		private String asn;

		private String geoSite;

	}

}
