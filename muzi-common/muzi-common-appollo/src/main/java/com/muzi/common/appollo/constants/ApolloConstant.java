package com.muzi.common.appollo.constants;

import cn.hutool.core.util.StrUtil;
import com.muzi.common.core.constant.AppConstant;

/**
 * Apollo 常量
 *
 * @author Jiali.Cen <jiali.cen@geely.com>
 */
public interface ApolloConstant {
	/**
	 * 开发环境
	 */
	String DEV_CODE = "DEV";
	/**
	 * apollo dev 地址
	 */
	String APOllO_DEV_ADDR = "http://apollo-meta-dev.test.geely.com";
	/**
	 * 生产环境
	 */
	String PROD_CODE = "PRO";

	/**
	 * apollo prod 地址
	 */
	String APOllO_PROD_ADDR = "http://apollo-meta-pro.sgw.geely.svc";
	/**
	 * 测试环境
	 */
	String TEST_CODE = "FAT";

	/**
	 * apollo test 地址
	 */
	String APOllO_TEST_ADDR = "http://apollo-meta-fat.sgw.test.geely.svc";
	/**
	 * 预发环境
	 */
	String UAT_CODE = "UAT";

	/**
	 * apollo uat 地址
	 */
	String APOllO_UAT_ADDR = "http://apollo-meta-uat.sgw.test.geely.svc";



	/**
	 * apollo 配置文件类型
	 */
	String APOLLO_CONFIG_FORMAT = "yaml";

	/**
	 * 动态获取apollo地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String apolloAddr(String profile) {
		switch (profile) {
			case (AppConstant.PROD_CODE):
				return ApolloConstant.APOllO_PROD_ADDR;
			case (AppConstant.TEST_CODE):
				return ApolloConstant.APOllO_TEST_ADDR;
			case (AppConstant.UAT_CODE):
				return ApolloConstant.APOllO_UAT_ADDR;
			default:
				return ApolloConstant.APOllO_DEV_ADDR;
		}
	}

	/**
	 * 动态获取apollo地址
	 *
	 * @param profile 环境变量
	 * @return env
	 */
	static String apolloEnv(String profile) {
		switch (profile) {
			case (AppConstant.PROD_CODE):
				return ApolloConstant.PROD_CODE;
			case (AppConstant.TEST_CODE):
				return ApolloConstant.TEST_CODE;
			case (AppConstant.UAT_CODE):
				return ApolloConstant.UAT_CODE;
			default:
				return ApolloConstant.DEV_CODE;
		}
	}

	/**
	 * 构建服务对应的 namespaces
	 * @param appName 服务名
	 * @return dataId
	 */
	static String namespaces(String appName) {
		return StrUtil.join(StrUtil.COMMA, "muzi-application." + ApolloConstant.APOLLO_CONFIG_FORMAT,
//				"crm-" + profile + "." + ApolloConstant.APOLLO_CONFIG_FORMAT,
				namespaces(appName,  ApolloConstant.APOLLO_CONFIG_FORMAT));
	}

	/**
	 * 构建服务对应的 namespaces
	 * @param appName 服务名
	 * @param profile 环境变量
	 * @param format 文件类型
	 * @return dataId
	 */
	static String namespaces(String appName, String profile, String format) {
		return appName + "-" + profile + "." + format;
	}

	/**
	 * 构建服务对应的 namespaces
	 * @param appName 服务名
	 * @param format 文件类型
	 * @return dataId
	 */
	static String namespaces(String appName,String format) {
		return appName  + "." + format;
	}

}
