package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description ${todo}
 * @Author 董彧傑
 * @CreateDate 2019-3-23 15:31
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public class SelectEntity {
    /**
     * responseTime : 1553325929133
     * message : success
     * code : 0
     * datas : [{"skey":"viewType_18","name":"世界文化遗产"},{"skey":"viewType_19","name":"世界自然遗产"},{"skey":"viewType_20","name":"世界文化和自然双遗产"},{"skey":"viewType_21","name":"国家级-风景名胜"},{"skey":"viewType_22","name":"省级-风景名胜"},{"skey":"viewType_23","name":"国家级-森林公园"},{"skey":"viewType_24","name":"省级-森林公园"},{"skey":"viewType_37","name":"旅游城镇-县级"},{"skey":"viewType_40","name":"国家级-对外开放城市和地区"},{"skey":"viewType_41","name":"省级-历史文化名城"},{"skey":"viewType_10","name":"全国重点文物保护单位"},{"skey":"viewType_42","name":"国家级-文物保护单位"},{"skey":"viewType_39","name":"国家级-乡村旅游区"},{"skey":"viewType_25","name":"国家级-自然保护区"},{"skey":"viewType_26","name":"省级-自然保护区"},{"skey":"viewType_27","name":"国家级-湿地公园"},{"skey":"viewType_28","name":"省级-湿地公园"},{"skey":"viewType_29","name":"国家级-地质公园"},{"skey":"viewType_30","name":"省级-地质公园"},{"skey":"viewType_38","name":"其他"}]
     */

    private long responseTime;
    private String message;
    private int code;
    private List<DatasBean> datas;
    /**
     * data : {"酒店餐饮服务":[{"skey":"CateringServices_01","name":"中餐厅"},{"skey":"CateringServices_02","name":"西餐厅"},{"skey":"CateringServices_03","name":"咖啡厅"}],"酒店特殊服务":[{"skey":"SpecialService_01","name":"宽带上网"},{"skey":"SpecialService_02","name":"机场接送"},{"skey":"SpecialService_03","name":"餐厅服务"},{"skey":"SpecialService_04","name":"游泳池"},{"skey":"SpecialService_05","name":"停车场"},{"skey":"SpecialService_06","name":"健身室"}],"服务包含":[{"skey":"serverInclude01","name":"健身房"},{"skey":"serverInclude02","name":"棋牌室"},{"skey":"serverInclude03","name":"休闲会所"},{"skey":"serverInclude04","name":"茶室"},{"skey":"serverInclude05","name":"美容美发"}],"酒店休闲娱乐":[{"skey":"Recreation_01","name":"卡拉OK厅"},{"skey":"Recreation_02","name":"健身室"},{"skey":"Recreation_03","name":"按摩室"}],"酒店服务项目":[{"skey":"ServicesItem_01","name":"商务中心服务"},{"skey":"ServicesItem_02","name":"大堂吧"}],"酒店房间设施":[{"skey":"RoomFacilities_01","name":"国内长途电话"},{"skey":"RoomFacilities_02","name":"国际长途电话"}]}
     */

    private DataBean data;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DatasBean {
        public DatasBean(String name, String skey) {
            this.skey = skey;
            this.name = name;
        }
        /**
         * skey : viewType_18 scenic_theme02 matter_crowd01 viewType_00
         * name : 世界文化遗产
         * 人群 {"skey":"matter_crowd01","name":"家庭"},{"skey":"matter_crowd02","name":"情侣"},{"skey":"matter_crowd03","name":"个人"},{"skey":"matter_crowd04","name":"老人"},{"skey":"matter_crowd05","name":"商务"},{"skey":"matter_crowd06","name":"公司"},{"skey":"matter_crowd07","name":"朋友"},{"skey":"matter_crowd08","name":"同学"}]}
         * 主题 {"skey":"scenic_theme02","name":"赏花踏青"},{"skey":"scenic_theme03","name":"展览演出"},{"skey":"scenic_theme04","name":"自然风光"},{"skey":"scenic_theme05","name":"民俗风情"},{"skey":"scenic_theme06","name":"亲子休闲"},{"skey":"scenic_theme07","name":"漂流"},{"skey":"scenic_theme08","name":"水乡"},{"skey":"scenic_theme09","name":"游船"},{"skey":"scenic_theme10","name":"避暑"},{"skey":"scenic_theme11","name":"古迹"},{"skey":"scenic_theme12","name":"古镇"},{"skey":"scenic_theme13","name":"主题乐园"},{"skey":"scenic_theme14","name":"海滨海岛"},{"skey":"scenic_theme15","name":"森林公园"},{"skey":"scenic_theme01","name":"名胜古迹"}]}
         * 等级 {"skey":"viewType_16","name":"AAAAA"},{"skey":"viewType_15","name":"AAAA"},{"skey":"viewType_14","name":"AAA"},{"skey":"viewType_13","name":"AA"},{"skey":"viewType_12","name":"A"},{"skey":"viewType_00","name":"非A级景区"}]}
         * 类型 {"skey":"viewType_18","name":"世界文化遗产"},{"skey":"viewType_19","name":"世界自然遗产"},{"skey":"viewType_20","name":"世界文化和自然双遗产"},{"skey":"viewType_21","name":"国家级-风景名胜"},{"skey":"viewType_22","name":"省级-风景名胜"},{"skey":"viewType_23","name":"国家级-森林公园"},{"skey":"viewType_24","name":"省级-森林公园"},{"skey":"viewType_37","name":"旅游城镇-县级"},{"skey":"viewType_40","name":"国家级-对外开放城市和地区"},{"skey":"viewType_41","name":"省级-历史文化名城"},{"skey":"viewType_10","name":"全国重点文物保护单位"},{"skey":"viewType_42","name":"国家级-文物保护单位"},{"skey":"viewType_39","name":"国家级-乡村旅游区"},{"skey":"viewType_25","name":"国家级-自然保护区"},{"skey":"viewType_26","name":"省级-自然保护区"},{"skey":"viewType_27","name":"国家级-湿地公园"},{"skey":"viewType_28","name":"省级-湿地公园"},{"skey":"viewType_29","name":"国家级-地质公园"},{"skey":"viewType_30","name":"省级-地质公园"},{"skey":"viewType_38","name":"其他"}]}
         */

        private String skey;
        private String name;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        private boolean selected;




        public String getSkey() {
            return skey;
        }

        public void setSkey(String skey) {
            this.skey = skey;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // 数据范例  {"responseTime":1553563693600,"message":"success","code":0,"data":{"酒店餐饮服务":[{"skey":"CateringServices_01","name":"中餐厅"},{"skey":"CateringServices_02","name":"西餐厅"},{"skey":"CateringServices_03","name":"咖啡厅"}],"酒店特殊服务":[{"skey":"SpecialService_01","name":"宽带上网"},{"skey":"SpecialService_02","name":"机场接送"},{"skey":"SpecialService_03","name":"餐厅服务"},{"skey":"SpecialService_04","name":"游泳池"},{"skey":"SpecialService_05","name":"停车场"},{"skey":"SpecialService_06","name":"健身室"}],"服务包含":[{"skey":"serverInclude01","name":"健身房"},{"skey":"serverInclude02","name":"棋牌室"},{"skey":"serverInclude03","name":"休闲会所"},{"skey":"serverInclude04","name":"茶室"},{"skey":"serverInclude05","name":"美容美发"}],"酒店休闲娱乐":[{"skey":"Recreation_01","name":"卡拉OK厅"},{"skey":"Recreation_02","name":"健身室"},{"skey":"Recreation_03","name":"按摩室"}],"酒店服务项目":[{"skey":"ServicesItem_01","name":"商务中心服务"},{"skey":"ServicesItem_02","name":"大堂吧"}],"酒店房间设施":[{"skey":"RoomFacilities_01","name":"国内长途电话"},{"skey":"RoomFacilities_02","name":"国际长途电话"}]}}

    public static class DataBean {

        private String name;
        private List<DatasBean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DatasBean> getList() {
            return list;
        }

        public void setList(List<DatasBean> list) {
            this.list = list;
        }

    }
}
