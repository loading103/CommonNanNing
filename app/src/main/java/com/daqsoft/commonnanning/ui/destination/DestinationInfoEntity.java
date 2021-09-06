package com.daqsoft.commonnanning.ui.destination;

import com.example.tomasyb.baselib.base.net.entity.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * 目的地列表页面实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/7/23 0023
 * @since JDK 1.8
 */
public class DestinationInfoEntity extends BaseResponse<DestinationInfoEntity> implements Serializable {

    /**
     * id : 27
     * siteCode : qdnzxwApp
     * lang : cn
     * content : <p style="text-align:center;">
     * <span> 三穗县</span><span>三穗县</span><span>三穗县</span><span>三穗县</span>
     * </p>
     * <p>
     * <span><span>&nbsp; &nbsp; &nbsp;三穗县</span><span>三穗县</span><span>三穗县</span></span>
     * </p>
     * <p>
     * <span><span> 三穗县<span>三穗县</span><span>三穗县</span><span>三穗县</span><span>三<strong>穗县</strong
     * ></span><span><strong>三穗县</strong></span><span><strong>三穗县</strong></span><span><strong
     * >三穗</strong>县</span><span>三穗<span style="background-color:#E53333;">县</span></span><span
     * style="background-color:#E53333;">三穗县</span><span style="background-color:#E53333;
     * ">三穗县</span><span>三穗县</span><span>三穗<span style="color:#FF9900;">县</span></span><span
     * style="color:#FF9900;">三穗县</span><span style="color:#FF9900;
     * ">三穗县</span><span>三穗县</span><span style="font-size:24px;">三穗县</span><span
     * style="font-size:24px;">三穗县</span><span style="font-size:24px;">三穗县</span></span></span>
     * </p>
     * <p style="text-align:center;">
     * <span><span><span><img src="http://file.geeker.com
     * .cn/uploadfile/ptisp/imgs/1515048221187/bb40e7f7-8042-48a9-855d-fcfe6c21912a.jpg" alt=""
     * /><br />
     * </span></span></span>
     * </p>
     * <p>
     * <span><span>三穗县</span><span>三穗县</span><span>三穗县</span><span>三穗县</span><br />
     * </span>
     * </p>
     * region : 522624
     * regionName : 三穗县
     * regionPinyin : SanSuiXian
     * longitude : 108.681874
     * latitude : 26.95868
     * travelIndex : 30
     * recommend : 0
     * travelDays : 10
     * travelTime : 全天
     * viewCount : 715
     * images : [{"imgPath":"http://file.geeker.com.cn/uploadfile/test/1514172891203/timg 3.jpg",
     * "imgName":"timg (3)"},{"imgPath":"http://file.geeker.com
     * .cn/uploadfile/test/1515035564300/火车1.jpg","imgName":"火车1"}]
     * cover : http://file.geeker.com.cn/uploadfile/test/1515035564300/火车1.jpg
     * video :
     * panorama :
     * praise : 0
     * want : 0
     * gone : 0
     * collect : 0
     * share : 0
     * createTime :
     * updateTime :
     * introduction :
     * status : 1
     * weather : {"date":"2018-07-23","hum":85,"uv":"5","vis":20,"week":"星期一","pres":1004,
     * "pcpn":"1.0","cond":{"pic_n":"http://file.geeker.com
     * .cn/file/images/2016/9/1/19-39-1/1472729941017.png","unicode_d":"&#xE020;",
     * "pic_d":"http://file.geeker.com.cn/file/images/2016/9/1/19-51-52/1472730712509.png",
     * "txt_n":"多云","code_n":101,"code_d":300,"unicode_n":"&#xE002;","txt_d":"阵雨"},"pop":56,
     * "astro":{"ss":"19:37","sr":"06:05"},"tmp":{"min":22,"max":33},"aqi":{"no2":24,"o3":34,
     * "pm25":31,"qlty":"良","so2":15,"aqi":55,"pm10":59,"time":"2018-07-23 10:00","co":0},"wind
     * ":{"sc":"1-2","spd":11,"deg":350,"dir":"北风"}}
     */

    private int id;
    private String siteCode;
    private String lang;
    private String content;
    private String region;
    private String regionName;
    private String regionPinyin;
    private String longitude;
    private String latitude;
    private int travelIndex;
    private int recommend;
    private String travelDays;
    private String travelTime;
    private int viewCount;
    private String coverTwoToThree;
    private String coverOneToOne;
    private String coverTwoToOne;
    private String video;
    private String panorama;
    private int praise;
    private int want;
    private int gone;
    private int collect;
    private int share;
    private String createTime;
    private String updateTime;
    private String introduction;
    private int status;
    private WeatherBean weather;
    private List<ImagesBean> images;

    public String getCoverTwoToOne() {
        return coverTwoToOne;
    }

    public void setCoverTwoToOne(String coverTwoToOne) {
        this.coverTwoToOne = coverTwoToOne;
    }

    public String getCoverOneToOne() {
        return coverOneToOne;
    }

    public void setCoverOneToOne(String coverOneToOne) {
        this.coverOneToOne = coverOneToOne;
    }

    public String getCoverTwoToThree() {
        return coverTwoToThree;
    }

    public void setCoverTwoToThree(String coverTwoToThree) {
        this.coverTwoToThree = coverTwoToThree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionPinyin() {
        return regionPinyin;
    }

    public void setRegionPinyin(String regionPinyin) {
        this.regionPinyin = regionPinyin;
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

    public int getTravelIndex() {
        return travelIndex;
    }

    public void setTravelIndex(int travelIndex) {
        this.travelIndex = travelIndex;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getTravelDays() {
        return travelDays;
    }

    public void setTravelDays(String travelDays) {
        this.travelDays = travelDays;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }


    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPanorama() {
        return panorama;
    }

    public void setPanorama(String panorama) {
        this.panorama = panorama;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getWant() {
        return want;
    }

    public void setWant(int want) {
        this.want = want;
    }

    public int getGone() {
        return gone;
    }

    public void setGone(int gone) {
        this.gone = gone;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public WeatherBean getWeather() {
        return weather;
    }

    public void setWeather(WeatherBean weather) {
        this.weather = weather;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class WeatherBean implements Serializable {
        /**
         * date : 2018-07-23
         * hum : 85
         * uv : 5
         * vis : 20
         * week : 星期一
         * pres : 1004
         * pcpn : 1.0
         * cond : {"pic_n":"http://file.geeker.com
         * .cn/file/images/2016/9/1/19-39-1/1472729941017.png","unicode_d":"&#xE020;",
         * "pic_d":"http://file.geeker.com.cn/file/images/2016/9/1/19-51-52/1472730712509.png",
         * "txt_n":"多云","code_n":101,"code_d":300,"unicode_n":"&#xE002;","txt_d":"阵雨"}
         * pop : 56
         * astro : {"ss":"19:37","sr":"06:05"}
         * tmp : {"min":22,"max":33}
         * aqi : {"no2":24,"o3":34,"pm25":31,"qlty":"良","so2":15,"aqi":55,"pm10":59,
         * "time":"2018-07-23 10:00","co":0}
         * wind : {"sc":"1-2","spd":11,"deg":350,"dir":"北风"}
         */

        private String date;
        private int hum;
        private String uv;
        private int vis;
        private String week;
        private int pres;
        private String pcpn;
        private CondBean cond;
        private int pop;
        private AstroBean astro;
        private TmpBean tmp;
        private AqiBean aqi;
        private WindBean wind;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getHum() {
            return hum;
        }

        public void setHum(int hum) {
            this.hum = hum;
        }

        public String getUv() {
            return uv;
        }

        public void setUv(String uv) {
            this.uv = uv;
        }

        public int getVis() {
            return vis;
        }

        public void setVis(int vis) {
            this.vis = vis;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public int getPres() {
            return pres;
        }

        public void setPres(int pres) {
            this.pres = pres;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public CondBean getCond() {
            return cond;
        }

        public void setCond(CondBean cond) {
            this.cond = cond;
        }

        public int getPop() {
            return pop;
        }

        public void setPop(int pop) {
            this.pop = pop;
        }

        public AstroBean getAstro() {
            return astro;
        }

        public void setAstro(AstroBean astro) {
            this.astro = astro;
        }

        public TmpBean getTmp() {
            return tmp;
        }

        public void setTmp(TmpBean tmp) {
            this.tmp = tmp;
        }

        public AqiBean getAqi() {
            return aqi;
        }

        public void setAqi(AqiBean aqi) {
            this.aqi = aqi;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class CondBean implements Serializable {
            /**
             * pic_n : http://file.geeker.com.cn/file/images/2016/9/1/19-39-1/1472729941017.png
             * unicode_d : &#xE020;
             * pic_d : http://file.geeker.com.cn/file/images/2016/9/1/19-51-52/1472730712509.png
             * txt_n : 多云
             * code_n : 101
             * code_d : 300
             * unicode_n : &#xE002;
             * txt_d : 阵雨
             */

            private String pic_n;
            private String unicode_d;
            private String pic_d;
            private String txt_n;
            private int code_n;
            private int code_d;
            private String unicode_n;
            private String txt_d;

            public String getPic_n() {
                return pic_n;
            }

            public void setPic_n(String pic_n) {
                this.pic_n = pic_n;
            }

            public String getUnicode_d() {
                return unicode_d;
            }

            public void setUnicode_d(String unicode_d) {
                this.unicode_d = unicode_d;
            }

            public String getPic_d() {
                return pic_d;
            }

            public void setPic_d(String pic_d) {
                this.pic_d = pic_d;
            }

            public String getTxt_n() {
                return txt_n;
            }

            public void setTxt_n(String txt_n) {
                this.txt_n = txt_n;
            }

            public int getCode_n() {
                return code_n;
            }

            public void setCode_n(int code_n) {
                this.code_n = code_n;
            }

            public int getCode_d() {
                return code_d;
            }

            public void setCode_d(int code_d) {
                this.code_d = code_d;
            }

            public String getUnicode_n() {
                return unicode_n;
            }

            public void setUnicode_n(String unicode_n) {
                this.unicode_n = unicode_n;
            }

            public String getTxt_d() {
                return txt_d;
            }

            public void setTxt_d(String txt_d) {
                this.txt_d = txt_d;
            }
        }

        public static class AstroBean {
            /**
             * ss : 19:37
             * sr : 06:05
             */

            private String ss;
            private String sr;

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }
        }

        public static class TmpBean {
            /**
             * min : 22
             * max : 33
             */

            private int min;
            private int max;

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }
        }

        public static class AqiBean {
            /**
             * no2 : 24
             * o3 : 34
             * pm25 : 31
             * qlty : 良
             * so2 : 15
             * aqi : 55
             * pm10 : 59
             * time : 2018-07-23 10:00
             * co : 0
             */

            private int no2;
            private int o3;
            private int pm25;
            private String qlty;
            private int so2;
            private int aqi;
            private int pm10;
            private String time;
            private double co;

            public int getNo2() {
                return no2;
            }

            public void setNo2(int no2) {
                this.no2 = no2;
            }

            public int getO3() {
                return o3;
            }

            public void setO3(int o3) {
                this.o3 = o3;
            }

            public int getPm25() {
                return pm25;
            }

            public void setPm25(int pm25) {
                this.pm25 = pm25;
            }

            public String getQlty() {
                return qlty;
            }

            public void setQlty(String qlty) {
                this.qlty = qlty;
            }

            public int getSo2() {
                return so2;
            }

            public void setSo2(int so2) {
                this.so2 = so2;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public int getPm10() {
                return pm10;
            }

            public void setPm10(int pm10) {
                this.pm10 = pm10;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public double getCo() {
                return co;
            }

            public void setCo(double co) {
                this.co = co;
            }
        }

        public static class WindBean {
            /**
             * sc : 1-2
             * spd : 11
             * deg : 350
             * dir : 北风
             */

            private String sc;
            private int spd;
            private int deg;
            private String dir;

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public int getSpd() {
                return spd;
            }

            public void setSpd(int spd) {
                this.spd = spd;
            }

            public int getDeg() {
                return deg;
            }

            public void setDeg(int deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }
        }
    }

    public static class ImagesBean {
        /**
         * imgPath : http://file.geeker.com.cn/uploadfile/test/1514172891203/timg 3.jpg
         * imgName : timg (3)
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
