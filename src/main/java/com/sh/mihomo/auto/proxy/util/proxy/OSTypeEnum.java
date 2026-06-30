package com.sh.mihomo.auto.proxy.util.proxy;

/**
 * @ClassName: OSTypeEnum
 * @Description:
 * @Version: 1.0.0
 * @Date: 2026/5/22 9:52
 * @Author: SH
 */
public enum OSTypeEnum {

	WINDOWS("win"), LINUX("linux"), UNKNOWN("");

	private final String keyword;

	OSTypeEnum(String keyword) {
		this.keyword = keyword;
	}

	public static OSTypeEnum current() {

		String os = System.getProperty("os.name", "").toLowerCase();

		for (OSTypeEnum type : values()) {

			if (type == UNKNOWN) {
				continue;
			}

			if (os.contains(type.keyword)) {
				return type;
			}
		}

		return UNKNOWN;
	}

}
