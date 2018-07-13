package com.fsmflying.handler;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fsmflying.model.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageEventHandler {

    static Logger log = LoggerFactory.getLogger(MessageEventHandler.class);
    private final SocketIOServer server;


    @Autowired
    public MessageEventHandler(SocketIOServer server) {
        this.server = server;
    }

    /**
     * 添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
     * 方便后面发送消息时查找到对应的目标client,
     */

    @OnConnect
    public void onConnect(SocketIOClient client) {
        //String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        log.info("客户端[" + client.getRemoteAddress() + "]连接到服务器!");

    }

    /**
     * 添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        //String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        log.info("客户端[" + client.getRemoteAddress() + "]断开服务器!");
    }

    /**
     * 消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
     */
    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data) {
        //String targetClientId = data.getTargetClientId();
        log.info("收到客户端[" + client.getRemoteAddress() + "]消息:" + data.getMsgContent());

    }
}

