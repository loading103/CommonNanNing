package com.daqsoft.http;

import java.util.List;

/**
 * Created by huangx on 2018/3/5.
 *
 * 网络请求封装之后的父类
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 *
 */

public class HttpResultBean<T>{
    /**
     * 返回码
     */
    private int code;
    /**
     * 时间戳
     */
    private long responseTime;
    /**
     * 提示语
     */
    private String message;
    /**
     * 数据集源
     */
    private List<T> datas;
    /**
     * 数据源
     */
    private T data;
    /**
     * page : {"total":2,"currPage":1,"pageSize":10,"totalPage":1}
     */

    private PageBean page;


    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "HttpResultBean{" +
                "code=" + code +
                ", responseTime=" + responseTime +
                ", message='" + message + '\'' +
                ", datas=" + datas +
                ", data=" + data +
                '}';
    }

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
        /**
         * 总数据数
         */
        private int total;
        /**
         * 当前页数
         */
        private int currPage;
        /**
         * 每页数据数
         */
        private int pageSize;
        /**
         * 总页数
         */
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
}
