package com.daqsoft.commonnanning.ui.entity

/**
 * 娱乐场所列表实体类
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-6-21
 * @since JDK 1.8.0_191
 */
data class EntertainmentListBean(
        /**
         * 地址
         */
        val address: String,
        /**
         * 封面图（后台形象标识）
         */
        val cover: String,
        /**
         * 	封面图（后台形象标识）4:3
         */
        val coverFourToThree: String,
        val coverOneToOne: String,
        val coverTwoToOne: String,
        val coverTwoToThree: String,
        val id: Long,
        /**
         * 纬度
         */
        val latitude: String,
        /**
         * 经度
         */
        val longitude: String,
        /**
         * 名称
         */
        val name: String,
        /**
         * 	电话
         */
        val phone: String,
        /**
         * 	资源编码
         */
        val resourcecode: String,
        /**
         * 官方网站
         */
        val site: String,
        /**
         * 数据来源（0：平台 1：网传）
         */
        val source: Int
)