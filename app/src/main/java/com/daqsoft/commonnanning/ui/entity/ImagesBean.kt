package com.daqsoft.commonnanning.ui.entity

/**
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-22
 * @since JDK 1.8.0_191
 */
data class ImagesBean(
        /**
         * 创建时间
         */
        val createTime: String,
        /**
         * 绑定资源名称
         */
        val dataName: String,
        /**
         * 高度
         */
        val height: String,
        val id: String,
        /**
         * 互动数据
         */
        val interactionVo: InteractionVo,
        /**
         * 语言版本
         */
        val lang: String,
        /**
         * 景区等级
         */
        val level: String,
        /**
         * 组织编码
         */
        val orgId: String,
        /**
         * 点赞总数
         */
        val praiseCount: Int,
        /**
         * 排序号
         */
        val rank: String,
        /**
         * 是否推荐
         */
        val recommend: String,
        /**
         * 地区名称
         */
        val region: String,
        /**
         * 站点id
         */
        val siteId: String,
        /**
         * 资源id
         */
        val sourceId: String,
        /**
         * 资源类型
         */
        val sourceType: String,
        /**
         * 状态
         */
        val status: String,
        /**
         * 	图片简介
         */
        val summary: String,
        /**
         * 图片标题
         */
        val title: String,
        /**
         * 	分类
         */
        val type: String,
        /**
         * 图片URL
         */
        val url: String,
        /**
         * 浏览总数
         */
        val viewCount: Int,
        /**
         * 宽度
         */
        val width: String
) {
    data class InteractionVo(
            /**
             * 收藏
             */
            val enshrine: Int,
            /**
             * 想去
             */
            val recordOne: Int,
            /**
             * 去过
             */
            val recordTwo: Int,
            /**
             * 分享
             */
            val share: Int,
            /**
             * 点赞
             */
            val thumb: Int
    )
}

