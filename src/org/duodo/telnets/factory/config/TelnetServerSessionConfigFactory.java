package org.duodo.telnets.factory.config;
/**
 * 
 */


import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.session.config.DuplexstreamServerSessionConfigFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class TelnetServerSessionConfigFactory<T> extends
		DuplexstreamServerSessionConfigFactory<T> {

	public TelnetServerSessionConfigFactory(SessionConfig defaultSessionConfig) {
		super(defaultSessionConfig);
	}

}
