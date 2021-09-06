package com.daqsoft.commonnanning.ui.country.entity

/**
 * 乡村旅游列表实体类
 * @author 黄熙
 * @date 2019/5/18 0018
 * @version 1.0.0
 * @since JDK 1.8
 */
data class CountryBean(
        /**
         * id
         */
        val id: String,
        /**
         * 名称
         */
        val name: String,
        /**
         * 封面图
         */
        val cover: String,
        /**
         * 地址
         */
        val address: String,
        /**
         * 电话
         */
        val phone: String,
        /**
         * 数据来源（0：平台 1：网传）
         */
        val source: Int,
        /**
         * 资源编码
         */
        val resourcecode: String,
        /**
         * 封面图1:1
         */
        val coverOneToOne: String,
        /**
         * 封面图2:1
         */
        val coverTwoToOne: String,
        /**
         * 封面图2:3
         */
        val coverTwoToThree: String,
        /**
         * 封面图4:3
         */
        val coverFourToThree: String,
        /**
         * 纬度
         */
        val latitude: String,
        /**
         * 经度
         */
        val longitude: String,
        /**
         * 电话
         */
        val summary: String,

        /**
         * 介绍
         */
        val introduce: String
)