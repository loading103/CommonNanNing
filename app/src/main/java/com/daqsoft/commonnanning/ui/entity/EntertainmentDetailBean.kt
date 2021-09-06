package com.daqsoft.commonnanning.ui.entity

/**
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-7-8
 * @since JDK 1.8.0_191
 */
data class EntertainmentDetailBean(
        /**
         * 地址
         */
        val address: String,
        /**
         * 是否收藏
         */
        val enshrine: Int,
        val id: Long,
        /**
         * 	详细信息
         */
        val introduce: String,
        /**
         * logo（形象标识）
         */
        val logo: String,
        val logoFourToThree: String,
        val logoOneToOne: String,
        val logoTwoToOne: String,
        val logoTwoToThree: String,
        /**
         * 封面
         */
        val cover: String,
        /**
         * 	名称
         */
        val name: String,
        /**
         * 	联系电话
         */
        val phone: String,
        /**
         * 简介
         */
        val summary: String
)