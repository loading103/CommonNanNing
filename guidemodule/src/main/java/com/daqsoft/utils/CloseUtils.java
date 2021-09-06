package com.daqsoft.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 流关闭
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */
public final class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Close the io stream.
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Close the io stream quietly.
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
