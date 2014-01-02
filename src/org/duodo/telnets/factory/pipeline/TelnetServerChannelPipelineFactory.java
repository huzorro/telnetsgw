package org.duodo.telnets.factory.pipeline;
/**
 * 
 */


import static org.jboss.netty.channel.Channels.pipeline;

import java.util.concurrent.TimeUnit;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.session.DefaultServerSessionFactory;
import org.duodo.netty3ext.factory.session.config.DefaultServerSessionConfigFactory;
import org.duodo.netty3ext.session.Session;
import org.duodo.telnets.handler.TelnetIdleStateHandler;
import org.duodo.telnets.handler.TelnetServerHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class TelnetServerChannelPipelineFactory implements
		ChannelPipelineFactory {

	private DefaultServerSessionFactory<Session> sessionFactory;
	private DefaultServerSessionConfigFactory<SessionConfig> configFactory;
	private SessionConfig config;
	private Timer timer;
	/**
	 * 
	 */
	public TelnetServerChannelPipelineFactory(
			DefaultServerSessionFactory<Session> sessionFactory,
			DefaultServerSessionConfigFactory<SessionConfig> configFactory,
			SessionConfig config) {
		this(sessionFactory, configFactory, config, new HashedWheelTimer());
	}
	public TelnetServerChannelPipelineFactory(
			DefaultServerSessionFactory<Session> sessionFactory,
			DefaultServerSessionConfigFactory<SessionConfig> configFactory,
			SessionConfig config,
			Timer timer) {
		this.sessionFactory = sessionFactory;
		this.configFactory = configFactory;
		this.config = config;
		this.timer = timer;
	}	
	

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();

        // Add the text line codec combination first,
		pipeline.addLast("IdleStateHandler", new IdleStateHandler(timer, 0, 0,
				config.getIdleTime(), TimeUnit.SECONDS));
		pipeline.addLast("TelnetIdleStateHandler", new TelnetIdleStateHandler());
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(
                8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        
        pipeline.addLast("handler", new TelnetServerHandler(sessionFactory, configFactory));
		return pipeline;
	}

}
