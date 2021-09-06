package com.daqsoft.utils;

/**
 * 导游导览的接口公共类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/5/23
 * @since JDK 1.8
 */
public class Constant {
    /**
     * 导游导览的详情页的分享url地址
     */
    public static String GUIDE_DESC_HTML = "guide-desc.html?id=";
    /**
     * 获取地图配置
     */
    public static final String MAP_CONFIG = "api/mapGuide/getMapGuideSet";
    /**
     * 获取地图下某类基础资源的所有点位数据
     */
    public static final String GUIDE_MARKET_SOURCE = "api/mapGuide/getAudioGuideListAllBySourceType";
    /**
     * 获取地图下某类基础资源的所有点位数据
     */
    public static final String GUIDE_CONFIG = "api/mapGuide/getAllSourceTypeListByMapGuideSetId";
    /**
     * 获得景区语音包
     */
    public static final String GUIDE_AUDIO = "api/mapGuide/audioList";
    /**
     * 获得路线列表
     */
    public static final String GUIDE_WALK = "api/mapGuide/getLineListAllByMapGuideSetId";
    /**
     * 获得路线详情
     */
    public static final String GUIDE_WALK_DETAIL = "api/mapGuide/getLinePointListAllByLineId";


    public static class IntentKey {
        public static final String BEAN = "BEAN";
        public static final String ID = "ID";
        public static final String CONTENT = "CONTENT";
    }

    /**
     * linkType	手绘地图访问地址类型（1：系统选择，2：外部链接）
     * 系统选择
     */
    public static final String LINKTYPE_SYSTEM = "1";
    /**
     * 2：外部链接
     */
    public static final String LINKTYPE_OUTSIDE = "2";

}

