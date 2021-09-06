package com.example.tomasyb.baselib.base.net.entity;

import java.util.List;

/**
 * 服务器返回数据基类
 * 这里我们统一对数据基类处理
 * 如：
 * {
 * "responseTime":1530755365407,
 * "message":"success",
 * "code":0,
 * "data":Object{...}
 * }
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-7-2.17:09
 * @since JDK 1.8
 */

public class BaseResponse<T> {
    private long responseTime;
    private String message;
    private int code;
    private T data;
    private List<T> datas;

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }


    public static class PageBean {
        /**
         * total : 2
         * currPage : 1
         * pageSize : 10
         * totalPage : 1
         */

        private int total;
        private int currPage;
        private int pageSize;
        private int totalPage;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
