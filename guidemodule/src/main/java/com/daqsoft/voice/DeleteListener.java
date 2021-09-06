package com.daqsoft.voice;

/**
 * @author 严博
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 * 语音接口
 */

public interface DeleteListener {
    public void success();
    public void failed(String error);
}
