package com.daqsoft.voice;

/**
 * @author 严博
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 * 异步语音管理
 */

public class IMAudioBuilder {
    private String name;
    private int age;
    private double height;
    private double weight;

    public IMAudioBuilder name(String name) {
        this.name = name;
        return this;
    }

    public IMAudioBuilder age(int age) {
        this.age = age;
        return this;
    }

    public IMAudioBuilder height(double height) {
        this.height = height;
        return this;
    }

    public IMAudioBuilder weight(double weight) {
        this.weight = weight;
        return this;
    }

    public IMAudioManager build() {
        return IMAudioManager.instance();
    }


}
