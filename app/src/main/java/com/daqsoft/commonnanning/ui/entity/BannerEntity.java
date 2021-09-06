package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/*
 * -----------------------------------------------------------------
 * @Description:所有banner的类
 * @Author 董彧傑
 * @CreateDate 2019-3-22 14:18
 *
 * @version 1.0.1
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */public class BannerEntity {
    /**
     * responseTime : 1553234298501
     * message : success
     * code : 0
     * datas : [{"id":"390","lang":"cn","title":"茅山景区","url":"","pos_id":null,"description":"茅山景区","pics":["http://file.geeker.com.cn/uploadfile/ptisp/img/1545989994974/茅山景区1.jpg"],"videos":"","indexOrder":"1","beginTime":"2018-12-13 00:00:00","endTime":"2030-12-13 00:00:00","clickCount":"0","site_id":null,"createTime":"2018-12-13 21:46:41","updateTime":"2018-12-28 17:39:58","lastOpUser":"jsjr_admin","status":1,"price":0},{"id":"391","lang":"cn","title":"句容印象","url":"","pos_id":null,"description":"句容印象","pics":["http://file.geeker.com.cn/uploadfile/ptisp/img/1545990023421/句容印象1.jpg"],"videos":"","indexOrder":"999","beginTime":"2018-12-13 21:47:35","endTime":"2030-12-13 00:00:00","clickCount":"0","site_id":null,"createTime":"2018-12-13 21:47:47","updateTime":"2018-12-28 17:40:25","lastOpUser":"jsjr_admin","status":1,"price":0},{"id":"389","lang":"cn","title":"茅山福地","url":"","pos_id":null,"description":"茅山福地","pics":["http://file.geeker.com.cn/uploadfile/ptisp/img/1545990040891/茅山福地1.jpg"],"videos":"","indexOrder":"999","beginTime":"2018-12-13 00:00:00","endTime":"2030-12-13 00:00:00","clickCount":"0","site_id":null,"createTime":"2018-12-13 21:45:22","updateTime":"2018-12-28 17:40:43","lastOpUser":"jsjr_admin","status":1,"price":0}]
     * page : {"total":3,"currPage":1,"pageSize":10,"totalPage":1}
     */

    private long responseTime;
    private String message;
    private int code;
    private PageBean page;
    private List<DatasBean> datas;

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

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class PageBean {
        /**
         * total : 3
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

    public static class DatasBean {
        /**
         * id : 390
         * lang : cn
         * title : 茅山景区
         * url :
         * pos_id : null
         * description : 茅山景区
         * pics : ["http://file.geeker.com.cn/uploadfile/ptisp/img/1545989994974/茅山景区1.jpg"]
         * videos :
         * indexOrder : 1
         * beginTime : 2018-12-13 00:00:00
         * endTime : 2030-12-13 00:00:00
         * clickCount : 0
         * site_id : null
         * createTime : 2018-12-13 21:46:41
         * updateTime : 2018-12-28 17:39:58
         * lastOpUser : jsjr_admin
         * status : 1
         * price : 0
         */

        private String id;
        private String lang;
        private String title;
        private String url;
        private Object pos_id;
        private String description;
        private String videos;
        private String indexOrder;
        private String beginTime;
        private String endTime;
        private String clickCount;
        private Object site_id;
        private String createTime;
        private String updateTime;
        private String lastOpUser;
        private int status;
        private int price;
        private List<String> pics;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getPos_id() {
            return pos_id;
        }

        public void setPos_id(Object pos_id) {
            this.pos_id = pos_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVideos() {
            return videos;
        }

        public void setVideos(String videos) {
            this.videos = videos;
        }

        public String getIndexOrder() {
            return indexOrder;
        }

        public void setIndexOrder(String indexOrder) {
            this.indexOrder = indexOrder;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getClickCount() {
            return clickCount;
        }

        public void setClickCount(String clickCount) {
            this.clickCount = clickCount;
        }

        public Object getSite_id() {
            return site_id;
        }

        public void setSite_id(Object site_id) {
            this.site_id = site_id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getLastOpUser() {
            return lastOpUser;
        }

        public void setLastOpUser(String lastOpUser) {
            this.lastOpUser = lastOpUser;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
