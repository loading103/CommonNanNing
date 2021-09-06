package com.daqsoft.commonnanning.ui.entity

/**
 * 足迹列表
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-11
 * @since JDK 1.8.0_191
 */
data class FootPrintListBean(
        /**
         * 创建时间
         */
        val gmtCreate: String?,
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
         * 当前足迹包含景区数量
         */
        val num: Int?,
        /**
         * 景区id，逗号连接
         */
        val resourceIds: String?,
        /**
         * 	简介
         */
        val summary: String?
)