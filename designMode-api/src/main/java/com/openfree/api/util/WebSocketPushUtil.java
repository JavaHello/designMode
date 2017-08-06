package com.openfree.api.util;

import com.openfree.api.controller.v1.WebSocketDemo;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketPushUtil {
    private static WebSocketPushUtil webSocketPushUtil;

    private final Set<WebSocketDemo> webSocketDemoSet;

    private WebSocketPushUtil() {
        this.webSocketDemoSet = new CopyOnWriteArraySet<>();
    }

    public static WebSocketPushUtil getIns() {
        if (webSocketPushUtil == null) {
            synchronized (WebSocketPushUtil.class) {
                if (webSocketPushUtil == null) {
                    webSocketPushUtil = new WebSocketPushUtil();
                }
            }
        }
        return webSocketPushUtil;
    }

    public static Set<WebSocketDemo> getSet(){
        return getIns().webSocketDemoSet;
    }

    public static void pushAll(String message) {
        if (!isEmpty()) {
            try {
                for (WebSocketDemo webSocketDemo : getSet()) {

                    webSocketDemo.sendMessage(message);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isEmpty() {
        return getIns().webSocketDemoSet.isEmpty();
    }

    public static boolean add(WebSocketDemo webSocketDemo) {
        return getIns().webSocketDemoSet.add(webSocketDemo);
    }

    public static boolean remove(WebSocketDemo webSocketDemo) {
        return getIns().webSocketDemoSet.remove(webSocketDemo);
    }
}
