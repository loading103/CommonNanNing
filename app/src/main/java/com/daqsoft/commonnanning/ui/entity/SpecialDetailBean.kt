package com.daqsoft.commonnanning.ui.entity

/**
 * 特产详情实体类
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-24
 * @since JDK 1.8.0_191
 */
data class SpecialDetailBean(
        /**
         * 地址
         */
        val address: String,
        /**
         * 主键id
         */
        val id: String,
        /**
         * 详细介绍（富文本）
         */
        val introduce: String,
        /**
         * 纬度
         */
        val latitude: String,
        /**
         * 	比例图16：9
         */
        val logo: String,
        /**
         * 比例图4:3
         */
        val logoFourToThree: String,
        /**
         * 比例图1:1
         */
        val logoOneToOne: String,
        /**
         * 比例图2:1
         */
        val logoTwoToOne: String,
        val coverTwoToOne: String,
        /**
         * 比例图4:3
         */
        val logoTwoToThree: String,
        /**
         * 经度
         */
        val longitude: String,
        /**
         * 	名称
         */
        val name: String,
        /**
         * 电话
         */
        val phone: String,
        /**
         * 所属地区
         */
        val region: String,
        /**
         * 简介
         */
        val summary: String,
        val vo: VoBean? = null
) {
    data class VoBean(
            /**
             * recordTwo : 0
             * thumb : 0
             * show : 687
             * comment : 0
             * recommend : 0
             * share : 0
             * enshrine : 1
             * recordOne : 0
             */

            val recordTwo: Int,
            val thumb: Int,
            val show: Int,
            val comment: Int,
            val recommend: Int,
            val share: Int,
            val enshrine: Int,
            val recordOne: Int
    )
}