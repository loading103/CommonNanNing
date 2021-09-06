package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-20.9:56
 * @since JDK 1.8
 */

public class IndexNotify {

    /**
     * responseTime : 1553046975714
     * message : success
     * code : 0
     * datas : [{"id":26,"title":"紧急通知紧急通知","createDate":"2019-03-20","viewCount":0,
     * "cover":"http://file.geeker.com.cn/uploadfile/ptisp/img/1553046962326/96x96.png",
     * "coverOneToOne":"http://file.geeker.com.cn/uploadfile/ptisp/img/1553046962326/96x96.png",
     * "coverTwoToOne":"http://file.geeker.com.cn/uploadfile/ptisp/img/1553046962326/96x96.png",
     * "coverTwoToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1553046962326/96x96.png","coverFourToThree":"http://file.geeker
     * .com.cn/uploadfile/ptisp/img/1553046962326/96x96.png","summary":""},{"id":25,"title":"测试",
     * "createDate":"2019-03-20","viewCount":0,"cover":"","coverOneToOne":"","coverTwoToOne":"",
     * "coverTwoToThree":"","coverFourToThree":"",
     * "summary":"简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介"}]
     * page : {"total":2,"currPage":1,"pageSize":3,"totalPage":1}
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
         * total : 2
         * currPage : 1
         * pageSize : 3
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
         * id : 26
         * title : 紧急通知紧急通知
         * createDate : 2019-03-20
         * viewCount : 0
         * cover : http://file.geeker.com.cn/uploadfile/ptisp/img/1553046962326/96x96.png
         * coverOneToOne : http://file.geeker.com.cn/uploadfile/ptisp/img/1553046962326/96x96.png
         * coverTwoToOne : http://file.geeker.com.cn/uploadfile/ptisp/img/1553046962326/96x96.png
         * coverTwoToThree : http://file.geeker.com.cn/uploadfile/ptisp/img/1553046962326/96x96.png
         * coverFourToThree : http://file.geeker.com.cn/uploadfile/ptisp/img/1553046962326/96x96.png
         * summary :
         */

        private int id;
        private String title;
        private String createDate;
        private int viewCount;
        private String cover;
        private String coverOneToOne;
        private String coverTwoToOne;
        private String coverTwoToThree;
        private String coverFourToThree;
        private String summary;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCoverOneToOne() {
            return coverOneToOne;
        }

        public void setCoverOneToOne(String coverOneToOne) {
            this.coverOneToOne = coverOneToOne;
        }

        public String getCoverTwoToOne() {
            return coverTwoToOne;
        }

        public void setCoverTwoToOne(String coverTwoToOne) {
            this.coverTwoToOne = coverTwoToOne;
        }

        public String getCoverTwoToThree() {
            return coverTwoToThree;
        }

        public void setCoverTwoToThree(String coverTwoToThree) {
            this.coverTwoToThree = coverTwoToThree;
        }

        public String getCoverFourToThree() {
            return coverFourToThree;
        }

        public void setCoverFourToThree(String coverFourToThree) {
            this.coverFourToThree = coverFourToThree;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
