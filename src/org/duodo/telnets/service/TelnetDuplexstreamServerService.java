package org.duodo.telnets.service;
/**
 * 
 */


import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.session.config.DefaultServerSessionConfigFactory;
import org.duodo.netty3ext.factory.tcp.NettyTcpServerFactory;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.duodo.netty3ext.service.Service;
import org.duodo.netty3ext.session.Session;
import org.duodo.netty3ext.tcp.server.NettyTcpServer;
import org.duodo.telnets.factory.config.TelnetServerSessionConfigFactory;
import org.duodo.telnets.factory.pipeline.TelnetServerChannelPipelineFactory;
import org.duodo.telnets.factory.session.TelnetServerSessionFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class TelnetDuplexstreamServerService implements Service {

    private final Logger logger = LoggerFactory.getLogger(TelnetDuplexstreamServerService.class);
    private Map<String, SessionConfig> configMap;
    private Map<SessionConfig, ServerBootstrap> serverBootstrapMap;
    private Map<Object, BdbQueueMap<Long, QFuture<Message>>> requestMsgQueueMap;
    private Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap;
    private Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap;
    private Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap;
    private Map<SessionConfig, SessionPool>  sessionPoolMap;      
	/**
	 * @param serverBootstrapMap 
	 * 
	 */
	public TelnetDuplexstreamServerService(            
            Map<String, SessionConfig> configMap,
            Map<SessionConfig, ServerBootstrap> serverBootstrapMap,
            Map<Object, BdbQueueMap<Long, QFuture<Message>>> requestMsgQueueMap,
            Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap,
            Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap,
            Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap,
            Map<SessionConfig, SessionPool>  sessionPoolMap
            ) {
        this.configMap = configMap;
        this.serverBootstrapMap = serverBootstrapMap;
        this.requestMsgQueueMap = requestMsgQueueMap;
        this.responseMsgQueueMap = requestMsgQueueMap;
        this.deliverMsgQueueMap = deliverMsgQueueMap;
        this.scheduleExecutorMap = scheduleExecutorMap;
        this.sessionPoolMap = sessionPoolMap;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
        try {
            process();
        } catch (Exception e) {
            logger.error("Telnet duplexstream Server Service failed {}", e.getCause());
            Runtime.getRuntime().exit(-1);
        }
	}

	/* (non-Javadoc)
	 * @see me.huzorro.gateway.Service#process()
	 */
	@Override
	public void process() throws Exception {
        for(SessionConfig config : configMap.values()) {
        	TelnetServerSessionFactory<Session> sessionFactory = new TelnetServerSessionFactory<Session>(
                    requestMsgQueueMap.get(config), 
                    responseMsgQueueMap.get(config),
                    deliverMsgQueueMap.get(config), 
                    scheduleExecutorMap.get(config),
                    sessionPoolMap.get(config));
        	DefaultServerSessionConfigFactory<SessionConfig> configFactory = new TelnetServerSessionConfigFactory<SessionConfig>(config);
            ChannelPipelineFactory pipelineFactory = new TelnetServerChannelPipelineFactory(sessionFactory, configFactory, config);
            NettyTcpServerFactory<NettyTcpServer<Channel>> tcpServerFactory = 
            		 new NettyTcpServerFactory<NettyTcpServer<Channel>>(
                            config.getHost(), config.getPort(), pipelineFactory, serverBootstrapMap.get(config));
            NettyTcpServer<Channel> tcpServer = tcpServerFactory.create();
            
            tcpServer.bind();
        }
	}

}
