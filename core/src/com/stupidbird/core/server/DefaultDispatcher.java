package com.stupidbird.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stupidbird.core.actor.NodeDispatcher;
import com.stupidbird.core.concurrent.NodeExecutor;
import com.stupidbird.core.msg.Internal;
import com.stupidbird.core.msg.Message;
import com.stupidbird.core.msg.ProtobufMessage;
import com.stupidbird.core.obj.SceneActiveObject;
import com.stupidbird.core.os.GameMonitor;
import com.stupidbird.core.session.NodeBindableSession;

/**
 * 默认的分发器;
 * 
 * @author crazyjohn
 *
 */
public class DefaultDispatcher implements NodeDispatcher {
	private Logger logger = LoggerFactory.getLogger("Server");
	NodeExecutor executor;

	public DefaultDispatcher(NodeExecutor executor) {
		this.executor = executor;
	}

	@Override
	public void dispatch(Message msg) {
		if (msg instanceof ProtobufMessage) {
			// 处理外部消息
			final ProtobufMessage protobufMsg = (ProtobufMessage) msg;
			final SceneActiveObject object = protobufMsg.getSession().object();
			if (object == null) {
				return;
			}
			// 执行
			executor.execute(object, () -> {
				try {
					object.onMessage(protobufMsg);
				} catch (Exception e) {
					GameMonitor.catchException(e);
					logger.error(String.format("Handle external message error, objectId: %d, msgType: %s",
							object.getId(), protobufMsg.getType()));
				}
			});
		} else if (msg instanceof Internal) {
			// 处理内部消息
			@SuppressWarnings("unchecked")
			Internal<NodeBindableSession<?>> internal = (Internal<NodeBindableSession<?>>) msg;
			final SceneActiveObject object = internal.getSession().object();
			if (object == null) {
				return;
			}
			// 执行
			executor.execute(object, () -> {
				try {
					internal.execute();
				} catch (Exception e) {
					GameMonitor.catchException(e);
					logger.error(String.format("Handle internal message error, objectId: %d, msgType: %s",
							object.getId(), internal.getClass().getSimpleName()));
				}
			});
		} else {
			logger.warn(String.format("Unknown msg type, name: %s", msg.getClass().getSimpleName()));
		}

	}
}
