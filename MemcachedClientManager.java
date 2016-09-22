package com.wafersystems.cloud.helper.service.cache;

import java.io.IOException;

import lombok.extern.log4j.Log4j;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import com.google.code.yanf4j.core.impl.StandardSocketOption;
import com.wafersystems.basic.util.ConfigUtil;

/**
 * memcached客户端管理器
 * 
 * @author wangyun
 * 
 */
@Log4j
public enum MemcachedClientManager {

	/**
	 * 单例对象
	 */
	instance;

	private MemcachedClient client;

	/**
	 * 获取MemcachedClient
	 * 
	 * 会抛出MemcachedClient exception
	 * 
	 * @return
	 */
	public synchronized MemcachedClient getClient() {
		if (client == null) {
			try {

				MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(ConfigUtil
						.getInstance().getMemcachedMainserver()));
				builder.setSocketOption(StandardSocketOption.SO_RCVBUF, 32 * 1024);// 设置接收缓存区为32K，默认16K
				builder.setSocketOption(StandardSocketOption.SO_SNDBUF, 16 * 1024); // 设置发送缓冲区为16K，默认为8K
				builder.setSocketOption(StandardSocketOption.TCP_NODELAY, false); // 启用nagle算法，提高吞吐量，默认关闭
				client = builder.build();
			} catch (IOException e) {
				log.error("init MemcachedClient error", e);
				throw new RuntimeException("MemcachedClient init error");
			}
		}
		return client;
	}

	public void putValue2Memcache(String memKey, String memVlue) {
		getClient();
		try {
			client.set(memKey, 0, memVlue);
		} catch (Exception e) {
			log.error("putValue2Memcache memKey :" + memKey + ",memVlue:" + memVlue, e);
		}
	}

	public String getValueFromMemcache(String memKey) {
		getClient();
		try {
			return client.get(memKey);
		} catch (Exception e) {
			log.error("getValueFromMemcache memKey :" + memKey, e);
		}
		return null;
	}
}
