package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description ${todo}
 * @Author 董彧傑
 * @CreateDate 2019-3-28 16:21
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public class CommentEntity {

    /**
     * responseTime : 1553760785215
     * message : success
     * code : 0
     * datas : [{"id":1258,"comment":"很不错，味道特别好，很营养又实惠","target":"古月豆腐","sourceType":"sourceType_8","sourceTypeName":"","star":5,"time":"2018-09-17 16:43:21","reId":"890","name":"Ptbye","headPath":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoWQD3Hicg7junFTYiahCkdicY1ADcVydL1w1re2zUbkKtHHZfozvbicG1EB0mpORSsFrCDM8WFI8Ty3A/132","pageview":0,"useful":0,"useless":0,"title":"","account":"","userType":2,"siteCode":"psxwwz","lang":"cn","titleApp":"","pics":"","picArr":[],"userId":970}]
     * page : {"total":1,"currPage":1,"pageSize":4,"totalPage":1}
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
         * total : 1
         * currPage : 1
         * pageSize : 4
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
         * id : 1258
         * comment : 很不错，味道特别好，很营养又实惠
         * target : 古月豆腐
         * sourceType : sourceType_8
         * sourceTypeName :
         * star : 5
         * time : 2018-09-17 16:43:21
         * reId : 890
         * name : Ptbye
         * headPath : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoWQD3Hicg7junFTYiahCkdicY1ADcVydL1w1re2zUbkKtHHZfozvbicG1EB0mpORSsFrCDM8WFI8Ty3A/132
         * pageview : 0
         * useful : 0
         * useless : 0
         * title :
         * account :
         * userType : 2
         * siteCode : psxwwz
         * lang : cn
         * titleApp :
         * pics :
         * picArr : []
         * userId : 970
         */

        private int id;
        private String comment;
        private String target;
        private String sourceType;
        private String sourceTypeName;
        private int star;
        private String time;
        private String reId;
        private String name;
        private String headPath;
        private int pageview;
        private int useful;
        private int useless;
        private String title;
        private String account;
        private int userType;
        private String siteCode;
        private String lang;
        private String titleApp;
        private String pics;
        private int userId;
        private List<?> picArr;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getSourceType() {
            return sourceType;
        }

        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }

        public String getSourceTypeName() {
            return sourceTypeName;
        }

        public void setSourceTypeName(String sourceTypeName) {
            this.sourceTypeName = sourceTypeName;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getReId() {
            return reId;
        }

        public void setReId(String reId) {
            this.reId = reId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadPath() {
            return headPath;
        }

        public void setHeadPath(String headPath) {
            this.headPath = headPath;
        }

        public int getPageview() {
            return pageview;
        }

        public void setPageview(int pageview) {
            this.pageview = pageview;
        }

        public int getUseful() {
            return useful;
        }

        public void setUseful(int useful) {
            this.useful = useful;
        }

        public int getUseless() {
            return useless;
        }

        public void setUseless(int useless) {
            this.useless = useless;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getSiteCode() {
            return siteCode;
        }

        public void setSiteCode(String siteCode) {
            this.siteCode = siteCode;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getTitleApp() {
            return titleApp;
        }

        public void setTitleApp(String titleApp) {
            this.titleApp = titleApp;
        }

        public String getPics() {
            return pics;
        }

        public void setPics(String pics) {
            this.pics = pics;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<?> getPicArr() {
            return picArr;
        }

        public void setPicArr(List<?> picArr) {
            this.picArr = picArr;
        }
    }
}
