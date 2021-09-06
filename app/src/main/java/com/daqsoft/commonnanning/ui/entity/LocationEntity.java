package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * 地区编码的实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/20 0020
 * @since JDK 1.8
 */

public class LocationEntity {


    /**
     * region : 510000
     * name : 四川省
     * sub : [{"region":"510100","name":"成都市","sub":[{"region":"","name":"请选择","sub":[],"child":false}],"child":false}]
     * child : false
     */

    private String region;
    private String name;
    private boolean child;
    private List<SubBeanX> sub;


    @Override
    public String toString() {
        return "LocationEntity{" +
                "region='" + region + '\'' +
                ", name='" + name + '\'' +
                ", child=" + child +
                ", sub=" + sub +
                '}';
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    public List<SubBeanX> getSub() {
        return sub;
    }

    public void setSub(List<SubBeanX> sub) {
        this.sub = sub;
    }

    public static class SubBeanX {
        /**
         * region : 510100
         * name : 成都市
         * sub : [{"region":"","name":"请选择","sub":[],"child":false}]
         * child : false
         */

        private String region;
        private String name;
        private boolean child;
        private List<SubBean> sub;

        @Override
        public String toString() {
            return "SubBeanX{" +
                    "region='" + region + '\'' +
                    ", name='" + name + '\'' +
                    ", child=" + child +
                    ", sub=" + sub +
                    '}';
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChild() {
            return child;
        }

        public void setChild(boolean child) {
            this.child = child;
        }

        public List<SubBean> getSub() {
            return sub;
        }

        public void setSub(List<SubBean> sub) {
            this.sub = sub;
        }

        public static class SubBean {
            /**
             * region :
             * name : 请选择
             * sub : []
             * child : false
             */

            private String region;
            private String name;
            private boolean child;
            private List<?> sub;


            @Override
            public String toString() {
                return "SubBean{" +
                        "region='" + region + '\'' +
                        ", name='" + name + '\'' +
                        ", child=" + child +
                        ", sub=" + sub +
                        '}';
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isChild() {
                return child;
            }

            public void setChild(boolean child) {
                this.child = child;
            }

            public List<?> getSub() {
                return sub;
            }

            public void setSub(List<?> sub) {
                this.sub = sub;
            }
        }
    }
}
