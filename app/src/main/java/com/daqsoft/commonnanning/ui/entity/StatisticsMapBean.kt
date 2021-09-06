package com.daqsoft.commonnanning.ui.entity

/**
 * 足迹统计信息实体类
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-11
 * @since JDK 1.8.0_191
 */
data class StatisticsMapBean(
        /**
         * head
         */
        val head: String?,
        /**
         * 景区等级分类统计
         */
        val levelList: List<Level?>?,
        /**
         * nickName
         */
        val nickName: String?,
        /**
         * total
         */
        val total: Int?
) {
    data class Level(
            /**
             * 等级skey
             */
            val level: String?,
            /**
             * 等级名称
             */
            val levelName: String?,
            /**
             * 数量
             */
            val num: Int?
    )
}

