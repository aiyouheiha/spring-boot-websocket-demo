package com.heiha.demo.springboot.websocket.web;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author singoasher
 * @date 2017/9/8
 */
@Slf4j
@EqualsAndHashCode
@ServerEndpoint(value = "/websocket")
@Component
public class DemoWebSocket {
    private final static String STATUS_CONNECTED = "connected";

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent 包的线程安全 Set，用来存放每个客户端对应的 DemoWebSocket 对象。
     */
    private static CopyOnWriteArraySet<DemoWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        // 加入set中
        webSocketSet.add(this);
        // 在线数加1
        addOnlineCount();

        log.info("有新连接加入！当前在线人数 {}", getOnlineCount());
        try {
            sendMessage(STATUS_CONNECTED);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 从 set 中删除
        webSocketSet.remove(this);
        // 在线数减 1
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数 {}", getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session Session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String sessionId = session.getId();
        log.info("From client: {}, message: {}", sessionId, message);

        // 群发消息
        String massMessage = message + " | From " + sessionId;
        sendMassMessage(massMessage);
    }

    /**
     * 发生错误时调用
     */
     @OnError
     public void onError(Session session, Throwable error) {
         log.error("Error happened, client: {}, error: {}", session.getId(), error.getMessage());
     }


     private void sendMessage(String message) throws IOException {
         this.session.getBasicRemote().sendText(message);
     }


     /**
      * 群发自定义消息
      *
      * @param massMessage Message to send to all
      */
    private static void sendMassMessage(String massMessage) {
        for (DemoWebSocket item : webSocketSet) {
            try {
                item.sendMessage(massMessage);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        DemoWebSocket.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        DemoWebSocket.onlineCount--;
    }
}
