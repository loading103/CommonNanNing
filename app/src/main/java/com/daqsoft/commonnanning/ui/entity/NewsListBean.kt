package com.daqsoft.commonnanning.ui.entity

/**
 * 新闻列表实体类
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-29
 * @since JDK 1.8.0_191
 */
data class NewsListBean(
        /**
         * 作者
         */
        val author: String,
        /**
         * 栏目代码
         */
        val channelCode: String,
        /**
         * 栏目id
         */
        val channelId: String,
        /**
         * 栏目名称
         */
        val channelName: String,
        /**
         * 内容
         */
        val content: String,
        /**
         * 封面图
         */
        val cover: String,
        val coverFourToThree: String,
        val coverOneToOne: String,
        val coverTwoToOne: String,
        val coverTwoToThree: String,
        /**
         * 创建时间
         */
        val createTime: String,
        /**
         * 扩展信息
         */
        val exInfo: ExInfo,
        /**
         * 	外部链接
         */
        val externalChain: String,
        val id: Int,
        val lang: String,
        /**
         * 点赞量
         */
        val praiseCount: Int,
        /**
         * 发布时间
         */
        val publishtime: String,
        /**
         * 来源
         */
        val src: String,
        /**
         * 摘要
         */
        val summary: String,
        /**
         * 标题
         */
        val title: String,
        /**
         * 推荐游玩时间
         */
        val travelTime: String,
        /**
         * 	分类
         */
        val type: Int,
        /**
         * 	视频链接
         */
        val video: String,
        /**
         * 浏览量
         */
        val viewCount: Int,

        val tags:String
) {
    data class ExInfo(
            /**
             * 图片集
             */
            val imgs: List<String>,
            /**
             * 开放时间
             */
            val opentime: String,
            /**
             * 游玩天数
             */
            val travelDays: String,
            /**
             * 游玩类型
             */
            val travelType: String
    )
}

