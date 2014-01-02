package org.duodo.telnets.factory.session;
/**
 * 
 */


import java.util.concurrent.ScheduledExecutorService;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.session.DefaultServerSessionFactory;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.jboss.netty.channel.Channel;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class TelnetServerSessionFactory<T> extends DefaultServerSessionFactory<T> {

	public TelnetServerSessionFactory(
			BdbQueueMap<Long, QFuture<Message>> requestQueue,
			BdbQueueMap<Long, QFuture<Message>> responseQueue,
			BdbQueueMap<Long, QFuture<Message>> deliverQueue,
			ScheduledExecutorService scheduleExecutor, SessionPool sessionPool) {
		super(requestQueue, responseQueue, deliverQueue, scheduleExecutor, sessionPool);
	}

	/**
	 * @param config
	 * @param requestQueue
	 * @param responseQueue
	 * @param deliverQueue
	 * @param scheduleExecutor
	 * @param sessionPool
	 * @param channel
	 */
	public TelnetServerSessionFactory(SessionConfig config,
			BdbQueueMap<Long, QFuture<Message>> requestQueue,
			BdbQueueMap<Long, QFuture<Message>> responseQueue,
			BdbQueueMap<Long, QFuture<Message>> deliverQueue,
			ScheduledExecutorService scheduleExecutor, SessionPool sessionPool,
			Channel channel) {
		super(config, requestQueue, responseQueue, deliverQueue, scheduleExecutor,
				sessionPool, channel);
	}

}
