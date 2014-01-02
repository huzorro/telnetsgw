package org.duodo.telnets.factory.config;
/**
 * 
 */


import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.CombinedConfiguration;
import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.session.config.DuplexstreamSessionConfigMapFactory;
import org.duodo.telnets.config.TelnetSessionConfig;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class TelnetSessionConfigMapFactory<T extends Map<String, Map<String, E>>, E extends SessionConfig> extends
		DuplexstreamSessionConfigMapFactory<T, E> {

	@SuppressWarnings("unchecked")
	public TelnetSessionConfigMapFactory(
			CombinedConfiguration configurationBuilder, 
			T sessionConfigMap,
			List<String> configList) {
		this(configurationBuilder, sessionConfigMap,  "duplexstream", "telnetsession", (Class<E>) TelnetSessionConfig.class, configList);
	}

	/**
	 * @param configurationBuilder
	 * @param sessionConfigMap
	 * @param sessionType
	 * @param configName
	 * @param sessionConfig
	 */
	public TelnetSessionConfigMapFactory(
			CombinedConfiguration configurationBuilder, 
			T sessionConfigMap,
			String sessionType, 
			String configName, 
			Class<E> sessionConfig,
			List<String> configList) {
		super(configurationBuilder, sessionConfigMap, sessionType, configName,
				sessionConfig, configList);
	}

}
