package com.daqsoft.commonnanning.ui.entity

/**
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-22
 * @since JDK 1.8.0_191
 */
data class SpecialListBean(
        /**
         * 地址
         */
        val address: String,
        val id: String,
        /**
         * 纬度
         */
        val latitude: String,
        /**
         * 比例图16:9
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
        /**
         * 比例图2:3
         */
        val logoTwoToThree: String,
        /**
         * 	经度
         */
        val longitude: String,
        /**
         * 名称
         */
        val name: String,
        /**
         * 电话
         */
        val phone: String,
        /**
         * 所属地区编码
         */
        val region: String,
        /**
         * 简介
         */
        val summary: String,
        val coverTwoToOne: String,
        val coverFourToThree: String
)