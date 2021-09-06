package com.example.tomasyb.baselib.base.net.common;

/**
 * SESSIONID单例
 */
public class SessionIdSingleton {
    private static SessionIdSingleton sessionIdSingleton = new SessionIdSingleton();
    public String string;

    public SessionIdSingleton() {
    }

    public static SessionIdSingleton getSingleton() {
        return sessionIdSingleton;
    }
}