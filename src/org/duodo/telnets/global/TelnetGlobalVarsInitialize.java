package org.duodo.telnets.global;
/**
 * 
 */


import java.util.List;

import org.duodo.netty3ext.global.DefaultGlobalVarsInitialize;
import org.duodo.netty3ext.global.GlobalVars;
import org.duodo.netty3ext.global.GlobalVarsInitialize;
import org.duodo.telnets.factory.config.TelnetSessionConfigMapFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class TelnetGlobalVarsInitialize extends DefaultGlobalVarsInitialize {

	public TelnetGlobalVarsInitialize() {
		super("telnetsession");
	}
	/**
	 * @param configName
	 */
	public TelnetGlobalVarsInitialize(String configName) {
		super(configName);
	}
	
	@Override
	public GlobalVarsInitialize duplexstreamSessionConfigInitialize(
			List<String> configList) throws Exception {
		new TelnetSessionConfigMapFactory<>(GlobalVars.config, GlobalVars.duplexSessionConfigMap, configList).create();
		return this;
	}
	
	
	
	
}
