package com.daqsoft.commonnanning.utils.annotation;

/**
 * 数据结构
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-4-8.
 * @since JDK 1.8
 */

public class ViewInfo {
    public int value;
    public int parentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ViewInfo viewInfo = (ViewInfo) o;
        if (value != viewInfo.value) {
            return false;
        }
        return parentId == viewInfo.parentId;

    }


    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + parentId;
        return result;
    }
}
