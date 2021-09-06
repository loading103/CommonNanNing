package com.daqsoft.commonnanning.ui.entity;


/**
 * 购买协议实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/7/31 0031
 * @since JDK 1.8
 */
public class AgreeMentEntity {


    /**
     * id : 44
     * site : {"id":55,"lang":"cn,en","name":"黔东南州资讯网","code":"qdnzxw","type":1,"pageType":"2",
     * "terminalType":0,
     * "newsReportType":2,"url":"","statUser":"","statId":0,"contacts":"梦梦",
     * "phone":"17761232445","region":"522600",
     * "orgId":"1001004","orgName":"大旗软件","createTime":1507521926000,"updateTime":1507521926000,
     * "status":1,
     * "apiStatus":1,"version":14,"memberMode":1,"shareType":1,"hdcode":"1001004_1",
     * "versionNum":"V1.0.0",
     * "backgroundLogo":"http://file.geeker.com.cn/uploadfile/ptisp/img/1516328879971/logo-qdn.png",
     * "domainName":"localhost,192.168.0.65,127.0.0.1","description":"费费大幅度",
     * "enterpriseName":"黔东南国资企业",
     * "businessLicence":"http://file.geeker.com.cn/uploadfile/ptisp/img/1519873426569/294450.jpg",
     * "creditCodes":"215042545215874","duration":"2099-12-12","address":"黔东南某某区某某地",
     * "customerTel":"028-12549685",
     * "appdomainName":"www.baidu.com","gjc":"","metatag":"","template":0,
     * "appid":"wx4beaea404157e515",
     * "contentIssue":0,"tagShare":0}
     * hdCode : 1001004_1
     * title : 黔东南购买协议
     * content : <span style="white-space:normal;
     * ">十九大角度看了撒娇来得及拉克丝建档立卡撒娇懒得看手机埃里克建档立卡撒娇冬练三九埃里克大家撒禄口街道快乐撒娇了肯德基l拉科技的来看撒娇了肯德基沙拉酱l</span><span
     * style="white-space:normal;">十九大角度看了撒娇来得及拉克丝建档立卡撒娇懒得看手机埃里克建档立卡撒娇冬练三九埃里克大家撒禄口街道快乐撒娇了肯德基l
     * 拉科技的来看撒娇了肯德基沙拉酱l</span
     * ><span style="white-space:normal;
     * ">十九大角度看了撒娇来得及拉克丝建档立卡撒娇懒得看手机埃里克建档立卡撒娇冬练三九埃里克大家撒禄口街道快乐撒娇了肯德基l拉科技的来看撒娇了肯德基沙拉酱l
     * </span>
     * status : 1
     * createTime : 1531375341000
     * lang : cn
     * version : product
     * type : 2
     */

    private int id;
    private SiteBean site;
    /**
     * 互动编码
     */
    private String hdCode;
    /**
     * 标题
     */
    private String title;
    /**
     * 协议内容
     */
    private String content;
    /**
     * 状态
     */
    private int status;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 语言
     */
    private String lang;
    /**
     * 版本号
     */
    private String version;
    /**
     * 1：用户协议 2：商家协议
     */
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SiteBean getSite() {
        return site;
    }

    public void setSite(SiteBean site) {
        this.site = site;
    }

    public String getHdCode() {
        return hdCode;
    }

    public void setHdCode(String hdCode) {
        this.hdCode = hdCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class SiteBean {
        /**
         * id : 55
         * lang : cn,en
         * name : 黔东南州资讯网
         * code : qdnzxw
         * type : 1
         * pageType : 2
         * terminalType : 0
         * newsReportType : 2
         * url :
         * statUser :
         * statId : 0
         * contacts : 梦梦
         * phone : 17761232445
         * region : 522600
         * orgId : 1001004
         * orgName : 大旗软件
         * createTime : 1507521926000
         * updateTime : 1507521926000
         * status : 1
         * apiStatus : 1
         * version : 14
         * memberMode : 1
         * shareType : 1
         * hdcode : 1001004_1
         * versionNum : V1.0.0
         * backgroundLogo : http://file.geeker.com.cn/uploadfile/ptisp/img/1516328879971/logo-qdn
         * .png
         * domainName : localhost,192.168.0.65,127.0.0.1
         * description : 费费大幅度
         * enterpriseName : 黔东南国资企业
         * businessLicence : http://file.geeker.com.cn/uploadfile/ptisp/img/1519873426569/294450.jpg
         * creditCodes : 215042545215874
         * duration : 2099-12-12
         * address : 黔东南某某区某某地
         * customerTel : 028-12549685
         * appdomainName : www.baidu.com
         * gjc :
         * metatag :
         * template : 0
         * appid : wx4beaea404157e515
         * contentIssue : 0
         * tagShare : 0
         */

        private int id;
        private String lang;
        private String name;
        private String code;
        private int type;
        private String pageType;
        private int terminalType;
        private int newsReportType;
        private String url;
        private String statUser;
        private int statId;
        private String contacts;
        private String phone;
        private String region;
        private String orgId;
        private String orgName;
        private long createTime;
        private long updateTime;
        private int status;
        private int apiStatus;
        private int version;
        private int memberMode;
        private int shareType;
        private String hdcode;
        private String versionNum;
        private String backgroundLogo;
        private String domainName;
        private String description;
        private String enterpriseName;
        private String businessLicence;
        private String creditCodes;
        private String duration;
        private String address;
        private String customerTel;
        private String appdomainName;
        private String gjc;
        private String metatag;
        private int template;
        private String appid;
        private int contentIssue;
        private int tagShare;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPageType() {
            return pageType;
        }

        public void setPageType(String pageType) {
            this.pageType = pageType;
        }

        public int getTerminalType() {
            return terminalType;
        }

        public void setTerminalType(int terminalType) {
            this.terminalType = terminalType;
        }

        public int getNewsReportType() {
            return newsReportType;
        }

        public void setNewsReportType(int newsReportType) {
            this.newsReportType = newsReportType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStatUser() {
            return statUser;
        }

        public void setStatUser(String statUser) {
            this.statUser = statUser;
        }

        public int getStatId() {
            return statId;
        }

        public void setStatId(int statId) {
            this.statId = statId;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getApiStatus() {
            return apiStatus;
        }

        public void setApiStatus(int apiStatus) {
            this.apiStatus = apiStatus;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getMemberMode() {
            return memberMode;
        }

        public void setMemberMode(int memberMode) {
            this.memberMode = memberMode;
        }

        public int getShareType() {
            return shareType;
        }

        public void setShareType(int shareType) {
            this.shareType = shareType;
        }

        public String getHdcode() {
            return hdcode;
        }

        public void setHdcode(String hdcode) {
            this.hdcode = hdcode;
        }

        public String getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(String versionNum) {
            this.versionNum = versionNum;
        }

        public String getBackgroundLogo() {
            return backgroundLogo;
        }

        public void setBackgroundLogo(String backgroundLogo) {
            this.backgroundLogo = backgroundLogo;
        }

        public String getDomainName() {
            return domainName;
        }

        public void setDomainName(String domainName) {
            this.domainName = domainName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getBusinessLicence() {
            return businessLicence;
        }

        public void setBusinessLicence(String businessLicence) {
            this.businessLicence = businessLicence;
        }

        public String getCreditCodes() {
            return creditCodes;
        }

        public void setCreditCodes(String creditCodes) {
            this.creditCodes = creditCodes;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCustomerTel() {
            return customerTel;
        }

        public void setCustomerTel(String customerTel) {
            this.customerTel = customerTel;
        }

        public String getAppdomainName() {
            return appdomainName;
        }

        public void setAppdomainName(String appdomainName) {
            this.appdomainName = appdomainName;
        }

        public String getGjc() {
            return gjc;
        }

        public void setGjc(String gjc) {
            this.gjc = gjc;
        }

        public String getMetatag() {
            return metatag;
        }

        public void setMetatag(String metatag) {
            this.metatag = metatag;
        }

        public int getTemplate() {
            return template;
        }

        public void setTemplate(int template) {
            this.template = template;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public int getContentIssue() {
            return contentIssue;
        }

        public void setContentIssue(int contentIssue) {
            this.contentIssue = contentIssue;
        }

        public int getTagShare() {
            return tagShare;
        }

        public void setTagShare(int tagShare) {
            this.tagShare = tagShare;
        }
    }
}
