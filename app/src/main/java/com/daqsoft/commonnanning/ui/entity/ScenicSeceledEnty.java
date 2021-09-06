package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-5-8.15:17
 * @since JDK 1.8
 */

public class ScenicSeceledEnty {

    private List<ScenicThemeBean> scenicTheme;
    private List<ResourceLevelBean> resourceLevel;
    private List<TypeBean> type;
    private List<MatterCrowdBean> matterCrowd;

    public List<ScenicThemeBean> getScenicTheme() {
        return scenicTheme;
    }

    public void setScenicTheme(List<ScenicThemeBean> scenicTheme) {
        this.scenicTheme = scenicTheme;
    }

    public List<ResourceLevelBean> getResourceLevel() {
        return resourceLevel;
    }

    public void setResourceLevel(List<ResourceLevelBean> resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public List<MatterCrowdBean> getMatterCrowd() {
        return matterCrowd;
    }

    public void setMatterCrowd(List<MatterCrowdBean> matterCrowd) {
        this.matterCrowd = matterCrowd;
    }

    public static class ScenicThemeBean {
        /**
         * skey : viewType_00
         * skeyName : 非A级景区
         */

        private String skey;
        private String skeyName;

        public String getSkey() {
            return skey;
        }

        public void setSkey(String skey) {
            this.skey = skey;
        }

        public String getSkeyName() {
            return skeyName;
        }

        public void setSkeyName(String skeyName) {
            this.skeyName = skeyName;
        }
    }

    public static class ResourceLevelBean {
        /**
         * skey : viewType_00
         * skeyName : 非A级景区
         */

        private String skey;
        private String skeyName;

        public String getSkey() {
            return skey;
        }

        public void setSkey(String skey) {
            this.skey = skey;
        }

        public String getSkeyName() {
            return skeyName;
        }

        public void setSkeyName(String skeyName) {
            this.skeyName = skeyName;
        }
    }

    public static class TypeBean {
        /**
         * skey : viewType_00
         * skeyName : 非A级景区
         */

        private String skey;
        private String skeyName;

        public String getSkey() {
            return skey;
        }

        public void setSkey(String skey) {
            this.skey = skey;
        }

        public String getSkeyName() {
            return skeyName;
        }

        public void setSkeyName(String skeyName) {
            this.skeyName = skeyName;
        }
    }

    public static class MatterCrowdBean {
        /**
         * skey : viewType_00
         * skeyName : 非A级景区
         */

        private String skey;
        private String skeyName;

        public String getSkey() {
            return skey;
        }

        public void setSkey(String skey) {
            this.skey = skey;
        }

        public String getSkeyName() {
            return skeyName;
        }

        public void setSkeyName(String skeyName) {
            this.skeyName = skeyName;
        }
    }
}
