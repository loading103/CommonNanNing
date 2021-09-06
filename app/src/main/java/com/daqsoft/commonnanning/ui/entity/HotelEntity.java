package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/*
 * -----------------------------------------------------------------
 * @Description:${todo}
 * @Author 董彧傑
 * @CreateDate 2019-3-21 9:55
 *
 * @version 1.0.1
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */public class HotelEntity {
    /**
     * responseTime : 1553667319265
     * message : success
     * code : 0
     * datas : [{"address":"江苏省句容市锦隆国际酒店(宝华山国家森林公园东北)","level":"hotelStarLevel_5",
     * "intro":"句容锦隆国际酒店位于句容市宝华山森林公园内，312国道旁，西距南京市区23公里，东至镇江市区34公里，宝华山北门千华古村斜对面，紧靠沪宁G42，长深G25
     * 等高速公路，酒店简欧风格，建筑面积3.2万平方米，酒店拥有各类客房231间套，设有西餐厅、中餐厅、豪华包间、餐位近1200个，8
     * 个大小会议室，酒店还设有室内游泳池、健身房、棋牌室、足疗、美容美发室等休闲、康体项目。","content":"","id":"999918846732410753",
     * "prices":"","picture":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店.jpg","pictureOneToOne":"http://file.geeker
     * .com.cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店.jpg","pictureTwoToOne":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店.jpg",
     * "pictureTwoToThree":"http://file.geeker.com.cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店
     * .jpg","pictureFourToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店.jpg","name":"句容锦隆国际酒店 ","commentNum":"1",
     * "commentLevel":"5.0","productNum":0,"recommend":0,"levelName":"五星级",
     * "resourcecode":"321183040200248","sortno":0,"lang":"cn","region":"句容市",
     * "regionCode":"321183","hotelType":"hotelType_2","hotelTypeName":"主题酒店","longitude":"119
     * .108538","latitude":"32.155289","linkAddress":"","shopId":"","distance":"","tagNames":[],
     * "pics":[{"imgPath":"http://file.geeker.com.cn/uploadfile/test/1536823351162/句容锦隆国际酒店.png",
     * "imgName":"句容锦隆国际酒店 "}],"cheap":"http://m.ctrip.com/webapp/hotel/hoteldetail/18409711
     * .html","phone":"0511-87776888","source":"0"},{"address":"江苏省句容市黄梅镇碧桂园大道2号",
     * "level":"hotelStarLevel_5","intro":"句容碧桂园凤凰城酒店位于南京东与句容市交界，周边风景优美，酒店紧邻国际温泉度假胜地\u2014\u2014
     * 汤山镇，地处宝华山2个4A级国家旅游度假景区。","content":"","id":"999918846732410751","prices":"",
     * "picture":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545992491287/句容碧桂园凤凰城酒店.jpg",
     * "pictureOneToOne":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545992491287/句容碧桂园凤凰城酒店
     * .jpg","pictureTwoToOne":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545992491287/句容碧桂园凤凰城酒店.jpg","pictureTwoToThree":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1545992491287/句容碧桂园凤凰城酒店.jpg",
     * "pictureFourToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545992491287/句容碧桂园凤凰城酒店.jpg","name":"句容碧桂园凤凰城酒店",
     * "commentNum":"1","commentLevel":"5.0","productNum":0,"recommend":0,"levelName":"五星级",
     * "resourcecode":"321183040200246","sortno":1,"lang":"cn","region":"句容市",
     * "regionCode":"321183","hotelType":"hotelType_2","hotelTypeName":"主题酒店","longitude":"119
     * .114945","latitude":"32.020453","linkAddress":"","shopId":"","distance":"","tagNames":[],
     * "pics":[{"imgPath":"http://file.geeker.com.cn/uploadfile/test/1536822865430/句容碧桂园凤凰城酒店
     * .png","imgName":"句容碧桂园凤凰城酒店"}],"cheap":"http://m.ctrip.com/webapp/hotel/hoteldetail/667639
     * .html","phone":"0511-87298888","source":"0"},{"address":"江苏省句容市崇明西路818号世茂花园",
     * "level":"hotelStarLevel_5","intro":"江苏句容余坤开元大酒店位于江苏省句容市崇明西路818号世茂花园，毗邻宁杭高速入口，距江苏省省会南京40
     * 分钟车程；至南京禄口国际机场仅为25分钟；距离南京欢乐水魔方水上乐园、道教圣地茅山与中国三大温泉之一的汤山温泉均在15分钟车程之内。酒店距离上海仅2
     * 个小时车程，地段优越，交通便利。","content":"","id":"999918846732410752","prices":"",
     * "picture":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545992777060/句容余坤开元大酒店.jpg",
     * "pictureOneToOne":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545992777060/句容余坤开元大酒店
     * .jpg","pictureTwoToOne":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545992777060/句容余坤开元大酒店.jpg","pictureTwoToThree":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1545992777060/句容余坤开元大酒店.jpg",
     * "pictureFourToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545992777060/句容余坤开元大酒店.jpg","name":"句容余坤开元大酒店","commentNum":"1",
     * "commentLevel":"5.0","productNum":0,"recommend":0,"levelName":"五星级",
     * "resourcecode":"321183040200247","sortno":1,"lang":"cn","region":"句容市",
     * "regionCode":"321183","hotelType":"hotelType_2","hotelTypeName":"主题酒店","longitude":"119
     * .154879","latitude":"31.959653","linkAddress":"","shopId":"","distance":"","tagNames":[],
     * "pics":[{"imgPath":"http://file.geeker.com.cn/uploadfile/test/1536823104704/句容余坤开元大酒店
     * .png","imgName":"句容余坤开元大酒店"}],"cheap":"http://m.ctrip.com/webapp/hotel/hoteldetail/368161
     * .html","phone":"0511-87799999 ","source":"0"},{"address":"江苏省句容市茅山镇大茅路68号（茅山半岛）",
     * "level":"hotelStarLevel_4","intro":"涵田茅山半岛酒店位于江苏句容茅山5A级风景区车程5分钟处，占地2000
     * 多亩的茅山半岛风情小镇之中，建筑面积近4万平方米；酒店由一栋高档综合性主楼与二十栋豪华酒店别墅组成；景观怡人，设备齐全，24
     * 小时管家式专业服务。酒店的中餐厅、西餐厅及素食餐厅，涵田系列美味佳肴各具特色，定会让您的美食之旅完美无憾！无论是养生、度假、旅游、商务交通均十分便利。","content":"",
     * "id":"999918846732413127","prices":"","picture":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1546055418315/茅山涵田半岛酒店.jpg","pictureOneToOne":"http://file.geeker
     * .com.cn/uploadfile/ptisp/img/1546055418315/茅山涵田半岛酒店.jpg","pictureTwoToOne":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1546055418315/茅山涵田半岛酒店.jpg",
     * "pictureTwoToThree":"http://file.geeker.com.cn/uploadfile/ptisp/img/1546055418315/茅山涵田半岛酒店
     * .jpg","pictureFourToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1546055418315/茅山涵田半岛酒店.jpg","name":"茅山涵田半岛酒店","commentNum":"0",
     * "commentLevel":"0.0","productNum":0,"recommend":0,"levelName":"四星级",
     * "resourcecode":"321183040200256","sortno":2,"lang":"cn","region":"句容市",
     * "regionCode":"321183","hotelType":"hotelType_2","hotelTypeName":"主题酒店","longitude":"119
     * .271413","latitude":"31.813042","linkAddress":"","shopId":"","distance":"","tagNames":[],
     * "pics":[],"cheap":"http://m.ctrip.com/webapp/hotel/hoteldetail/6608527.html",
     * "phone":"0511-87779999","source":"0"},{"address":"江苏省句容市华阳镇华阳北路9号（近东昌中路）",
     * "level":"hotelStarLevel_4","intro":"句容宾馆位于市中心的华阳北路，地理位置优越，交通便捷。","content":"",
     * "id":"999918846732410750","prices":"","picture":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545992693780/句容宾馆.jpg","pictureOneToOne":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545992693780/句容宾馆.jpg","pictureTwoToOne":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545992693780/句容宾馆.jpg","pictureTwoToThree":"http://file.geeker
     * .com.cn/uploadfile/ptisp/img/1545992693780/句容宾馆.jpg","pictureFourToThree":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1545992693780/句容宾馆.jpg","name":"句容宾馆（华阳北路）",
     * "commentNum":"1","commentLevel":"5.0","productNum":0,"recommend":0,"levelName":"四星级",
     * "resourcecode":"321183040200245","sortno":2,"lang":"cn","region":"句容市",
     * "regionCode":"321183","hotelType":"hotelType_1","hotelTypeName":"星级饭店","longitude":"119
     * .172705","latitude":"31.956929","linkAddress":"","shopId":"","distance":"","tagNames":[],
     * "pics":[{"imgPath":"http://file.geeker.com.cn/uploadfile/test/1536822785049/句容宾馆.png",
     * "imgName":"句容宾馆（华阳北路）"}],"cheap":"http://m.ctrip.com/webapp/hotel/hoteldetail/1674494
     * .html","phone":"0511-87263555","source":"0"},{"address":"江苏省句容市华阳镇文昌东路2号（近华阳西路）",
     * "level":"hotelStarLevel_4","intro":"句容曙光国际大酒店位于人杰地灵的优秀旅游城市\u2014\u2014
     * 江苏省句容市，毗邻宁杭高速入口，前往南京、南京禄口国际机场都很方便。","content":"","id":"999918846732410749","prices":"",
     * "picture":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545295502389/句容曙光大酒店.jpg",
     * "pictureOneToOne":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545295502389/句容曙光大酒店
     * .jpg","pictureTwoToOne":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545295502389/句容曙光大酒店.jpg","pictureTwoToThree":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1545295502389/句容曙光大酒店.jpg",
     * "pictureFourToThree":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545295502389/句容曙光大酒店
     * .jpg","name":"句容曙光国际大酒店","commentNum":"3","commentLevel":"5.0","productNum":0,
     * "recommend":0,"levelName":"四星级","resourcecode":"321183040200125","sortno":2,"lang":"cn",
     * "region":"句容市","regionCode":"321183","hotelType":"hotelType_1","hotelTypeName":"星级饭店",
     * "longitude":"119.171145","latitude":"31.967533","linkAddress":"","shopId":"",
     * "distance":"","tagNames":[],"pics":[{"imgPath":"http://file.geeker.com
     * .cn/uploadfile/test/1536822231448/句容曙光国际大酒店.png","imgName":"句容曙光国际大酒店"}],"cheap":"http://m
     * .ctrip.com/webapp/hotel/hoteldetail/663730.html","phone":"0511-85099999 ","source":"0"},{
     * "address":"江苏省句容市文昌西路8号","level":"hotelStarLevel_4",
     * "intro":"句容皇冠酒店位于句容市文昌西路，距南京、镇江主城区约40分钟车程，距南京汤山景区约15分钟车程，距著名道教圣地茅山风景区（5A景区）、宝华山风景区约25
     * 分钟车程。句容市在建中的儿童欢乐城与皇冠酒店毗邻而立。","content":"","id":"999918846732410754","prices":"",
     * "picture":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545992323828/句容皇冠酒店
     * .jpg?x-oss-process=image/crop,x_0,y_0,w_543,h_305","pictureOneToOne":"http://file.geeker
     * .com.cn/uploadfile/ptisp/img/1545992323828/句容皇冠酒店.jpg","pictureTwoToOne":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1545992323828/句容皇冠酒店.jpg?x-oss-process=image/crop,x_0,
     * y_21,w_543,h_272","pictureTwoToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545992323828/句容皇冠酒店.jpg?x-oss-process=image/crop,x_0,y_0,w_271,
     * h_407","pictureFourToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545992323828/句容皇冠酒店.jpg?x-oss-process=image/crop,x_0,y_0,w_543,
     * h_407","name":"句容皇冠酒店 ","commentNum":"0","commentLevel":"0.0","productNum":0,
     * "recommend":0,"levelName":"四星级","resourcecode":"321183040200091","sortno":2,"lang":"cn",
     * "region":"句容市","regionCode":"321183","hotelType":"hotelType_2","hotelTypeName":"主题酒店",
     * "longitude":"119.155453","latitude":"31.964735","linkAddress":"","shopId":"",
     * "distance":"","tagNames":[],"pics":[{"imgPath":"http://file.geeker.com
     * .cn/uploadfile/test/1536823574461/句容皇冠酒店.png","imgName":"句容皇冠酒店 "}],"cheap":"http://m
     * .ctrip.com/webapp/hotel/hoteldetail/1601214.html","phone":"0511-87331118","source":"0"},{
     * "address":"江苏省句容市经济开发区碧桂园大道3号","level":"hotelStarLevel_4","intro":"鹏天国际公寓酒店(句容碧桂园店)
     * 地处经济开发区碧桂园大道，紧邻汤山温泉小镇、百联奥特莱斯、水魔方，四周空气清新自然，实乃度假、旅行、养生、放松的优选之地。","content":"",
     * "id":"999918846732410755","prices":"","picture":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1546049246960/鹏天国际公寓酒店.jpg","pictureOneToOne":"http://file.geeker
     * .com.cn/uploadfile/ptisp/img/1546049246960/鹏天国际公寓酒店.jpg","pictureTwoToOne":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1546049246960/鹏天国际公寓酒店.jpg",
     * "pictureTwoToThree":"http://file.geeker.com.cn/uploadfile/ptisp/img/1546049246960/鹏天国际公寓酒店
     * .jpg","pictureFourToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1546049246960/鹏天国际公寓酒店.jpg","name":"鹏天国际公寓酒店","commentNum":"0",
     * "commentLevel":"0.0","productNum":0,"recommend":0,"levelName":"四星级",
     * "resourcecode":"321183040200249","sortno":2,"lang":"cn","region":"句容市",
     * "regionCode":"321183","hotelType":"hotelType_2","hotelTypeName":"主题酒店","longitude":"119
     * .115998","latitude":"32.019594","linkAddress":"","shopId":"","distance":"","tagNames":[],
     * "pics":[{"imgPath":"http://file.geeker.com.cn/uploadfile/test/1536823814106/鹏天国际公寓酒店句容碧桂园店
     * .png","imgName":"鹏天国际公寓酒店"}],"cheap":"http://m.ctrip.com/webapp/hotel/hoteldetail/11160404
     * .html","phone":"025-69781818","source":"0"},{"address":"江苏省句容市上杆村上杆水库南5号",
     * "level":"hotelStarLevel_4","intro":"句容梵净花开养生度假酒店占地120亩，位于江苏镇江句容茅山4A级风景区内，酒店内设4间餐厅和8
     * 间客房，包含一个天然源头湖泊与一个自然深潭，客房内设施齐全，均以星级标准配备。","content":"","id":"999918846732410756",
     * "prices":"","picture":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1546049130098/句容梵净花开养生度假酒店.jpg","pictureOneToOne":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1546049130098/句容梵净花开养生度假酒店.jpg",
     * "pictureTwoToOne":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1546049130098/句容梵净花开养生度假酒店.jpg","pictureTwoToThree":"http://file
     * .geeker.com.cn/uploadfile/ptisp/img/1546049130098/句容梵净花开养生度假酒店.jpg",
     * "pictureFourToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1546049130098/句容梵净花开养生度假酒店.jpg","name":"句容梵净花开养生度假酒店",
     * "commentNum":"0","commentLevel":"0.0","productNum":0,"recommend":0,"levelName":"四星级",
     * "resourcecode":"321183040200250","sortno":2,"lang":"cn","region":"句容市",
     * "regionCode":"321183","hotelType":"hotelType_2","hotelTypeName":"主题酒店","longitude":"119
     * .263707","latitude":"31.65748","linkAddress":"","shopId":"","distance":"","tagNames":[],
     * "pics":[{"imgPath":"http://file.geeker.com.cn/uploadfile/test/1536824211219/句容梵净花开养生度假酒店
     * .png","imgName":"句容梵净花开养生度假酒店"}],"cheap":"http://hotels.ctrip.com/hotel/21713833
     * .html#ctm_ref=hod_hp_sb_lst","phone":"18921589931","source":"0"},{"address
     * ":"江苏省句容市句茅路春城镇南山子社","level":"hotelStarLevel_3",
     * "intro":"怡景湾度假村，位于江苏省句容市茅山镇，毗邻国家风景名胜道教圣地茅山和佛教胜地宝华山，整个度假村占地面积400亩，现有建筑面积约5000
     * 平方米，是一座具有田园风光的绿色生态度假村。","content":"","id":"999918846732412519","prices":"",
     * "picture":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545295402621/客房2.jpg",
     * "pictureOneToOne":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545295402621/客房2.jpg",
     * "pictureTwoToOne":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545295402621/客房2.jpg",
     * "pictureTwoToThree":"http://file.geeker.com.cn/uploadfile/ptisp/img/1545295402621/客房2
     * .jpg","pictureFourToThree":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1545295402621/客房2.jpg","name":"怡景湾度假村","commentNum":"0",
     * "commentLevel":"0.0","productNum":0,"recommend":0,"levelName":"三星级",
     * "resourcecode":"321183040200253","sortno":3,"lang":"cn","region":"句容市",
     * "regionCode":"321183","hotelType":"hotelType_10","hotelTypeName":"度假村","longitude":"119
     * .266276","latitude":"31.922351","linkAddress":"","shopId":"","distance":"","tagNames":[],
     * "pics":[],"cheap":"http://m.ctrip.com/webapp/hotel/hoteldetail/1944342.html",
     * "phone":"0511-87872222","source":"0"}]
     * page : {"total":77,"currPage":1,"pageSize":10,"totalPage":8}
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
         * total : 77
         * currPage : 1
         * pageSize : 10
         * totalPage : 8
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
         * address : 江苏省句容市锦隆国际酒店(宝华山国家森林公园东北)
         * level : hotelStarLevel_5
         * intro : 句容锦隆国际酒店位于句容市宝华山森林公园内，312国道旁，西距南京市区23公里，东至镇江市区34公里，宝华山北门千华古村斜对面，紧靠沪宁G42，长深G25
         * 等高速公路，酒店简欧风格，建筑面积3.2万平方米，酒店拥有各类客房231间套，设有西餐厅、中餐厅、豪华包间、餐位近1200个，8
         * 个大小会议室，酒店还设有室内游泳池、健身房、棋牌室、足疗、美容美发室等休闲、康体项目。
         * content :
         * id : 999918846732410753
         * prices :
         * picture : http://file.geeker.com.cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店.jpg
         * pictureOneToOne : http://file.geeker.com
         * .cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店.jpg
         * pictureTwoToOne : http://file.geeker.com
         * .cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店.jpg
         * pictureTwoToThree : http://file.geeker.com
         * .cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店.jpg
         * pictureFourToThree : http://file.geeker.com
         * .cn/uploadfile/ptisp/img/1546048850521/句容锦隆国际酒店.jpg
         * name : 句容锦隆国际酒店
         * commentNum : 1
         * commentLevel : 5.0
         * productNum : 0
         * recommend : 0
         * levelName : 五星级
         * resourcecode : 321183040200248
         * sortno : 0
         * lang : cn
         * region : 句容市
         * regionCode : 321183
         * hotelType : hotelType_2
         * hotelTypeName : 主题酒店
         * longitude : 119.108538
         * latitude : 32.155289
         * linkAddress :
         * shopId :
         * distance :
         * tagNames : []
         * pics : [{"imgPath":"http://file.geeker.com.cn/uploadfile/test/1536823351162/句容锦隆国际酒店
         * .png","imgName":"句容锦隆国际酒店 "}]
         * cheap : http://m.ctrip.com/webapp/hotel/hoteldetail/18409711.html
         * phone : 0511-87776888
         * source : 0
         */

        private String address;
        private String level;
        private String intro;
        private String content;
        private String id;
        private String prices;
        private String picture;
        private String pictureOneToOne;
        private String pictureTwoToOne;
        private String pictureTwoToThree;
        private String pictureFourToThree;
        private String name;
        private String commentNum;
        private String commentLevel;
        private int productNum;
        private int recommend;
        private String levelName;
        private String resourcecode;
        private int sortno;
        private String lang;
        private String region;
        private String regionCode;
        private String hotelType;
        private String hotelTypeName;
        private String longitude;
        private String latitude;
        private String linkAddress;
        private String shopId;
        private String distance;
        private String cheap;
        private String phone;
        private String source;
        private List<Object> tagNames;
        private List<PicsBean> pics;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrices() {
            return prices;
        }

        public void setPrices(String prices) {
            this.prices = prices;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getPictureOneToOne() {
            return pictureOneToOne;
        }

        public void setPictureOneToOne(String pictureOneToOne) {
            this.pictureOneToOne = pictureOneToOne;
        }

        public String getPictureTwoToOne() {
            return pictureTwoToOne;
        }

        public void setPictureTwoToOne(String pictureTwoToOne) {
            this.pictureTwoToOne = pictureTwoToOne;
        }

        public String getPictureTwoToThree() {
            return pictureTwoToThree;
        }

        public void setPictureTwoToThree(String pictureTwoToThree) {
            this.pictureTwoToThree = pictureTwoToThree;
        }

        public String getPictureFourToThree() {
            return pictureFourToThree;
        }

        public void setPictureFourToThree(String pictureFourToThree) {
            this.pictureFourToThree = pictureFourToThree;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getCommentLevel() {
            return commentLevel;
        }

        public void setCommentLevel(String commentLevel) {
            this.commentLevel = commentLevel;
        }

        public int getProductNum() {
            return productNum;
        }

        public void setProductNum(int productNum) {
            this.productNum = productNum;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getResourcecode() {
            return resourcecode;
        }

        public void setResourcecode(String resourcecode) {
            this.resourcecode = resourcecode;
        }

        public int getSortno() {
            return sortno;
        }

        public void setSortno(int sortno) {
            this.sortno = sortno;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getHotelType() {
            return hotelType;
        }

        public void setHotelType(String hotelType) {
            this.hotelType = hotelType;
        }

        public String getHotelTypeName() {
            return hotelTypeName;
        }

        public void setHotelTypeName(String hotelTypeName) {
            this.hotelTypeName = hotelTypeName;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLinkAddress() {
            return linkAddress;
        }

        public void setLinkAddress(String linkAddress) {
            this.linkAddress = linkAddress;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getCheap() {
            return cheap;
        }

        public void setCheap(String cheap) {
            this.cheap = cheap;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public List<Object> getTagNames() {
            return tagNames;
        }

        public void setTagNames(List<Object> tagNames) {
            this.tagNames = tagNames;
        }

        public List<PicsBean> getPics() {
            return pics;
        }

        public void setPics(List<PicsBean> pics) {
            this.pics = pics;
        }

        public static class PicsBean {
            /**
             * imgPath : http://file.geeker.com.cn/uploadfile/test/1536823351162/句容锦隆国际酒店.png
             * imgName : 句容锦隆国际酒店
             */

            private String imgPath;
            private String imgName;

            public String getImgPath() {
                return imgPath;
            }

            public void setImgPath(String imgPath) {
                this.imgPath = imgPath;
            }

            public String getImgName() {
                return imgName;
            }

            public void setImgName(String imgName) {
                this.imgName = imgName;
            }
        }
    }
}
