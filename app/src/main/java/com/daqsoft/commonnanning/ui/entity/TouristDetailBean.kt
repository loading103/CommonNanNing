package com.daqsoft.commonnanning.ui.entity

/**
 * 足迹详情实体类
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-12
 * @since JDK 1.8.0_191
 */
data class TouristDetailBean(
        /**
         * 足迹id
         */
        val id: String?,
        /**
         * 封面图
         */
        val logo: String?,
        /**
         * 标题
         */
        val name: String?,
        /**
         * 包含的景区名称集合
         */
        val nameList: List<String?>?,
        /**
         * 足迹包含景区信息
         */
        val sceneryList: List<Scenery?>?,
        /**
         * 足迹简介
         */
        val summary: String?
) {
    data class Scenery(
            /**
             * 纬度
             */
            val latitude: String?,
            /**
             * 等级skey
             */
            val level: String?,
            /**
             * 等级名称
             */
            val levelName: String?,
            /**
             * 经度
             */
            val longitude: String?,
            /**
             * 地区编码
             */
            val region: String?,
            /**
             * 地区名称
             */
            val regionName: String?,
            /**
             * 	景区id
             */
            val resourceId: String?,
            /**
             * 景区名称
             */
            val resourceName: String?
    )
}

