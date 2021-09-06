package com.daqsoft.commonnanning.ui.entity

/**
 * 多媒体视频资料，宣传视频
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-19
 * @since JDK 1.8.0_191
 */
data class VideoBean(
        /**
         * 作者
         */
        val author: String?,
        /**
         * 封面
         */
        val coverpicture: String?,
        val coverpictureFourToThree: String?,
        val coverpictureOneToOne: String?,
        val coverpictureTwoToOne: String?,
        val coverpictureTwoToThree: String?,
        /**
         * 创建时间
         */
        val createTime: String?,
        /**
         * 	是否生成 1生成 0不生成
         */
        val createmp4: String?,
        val id: String?,
        /**
         * 互动数据
         */
        val interactionVo: InteractionVo?,
        val lang: String?,
        /**
         * 视频时长
         */
        val mins: String?,
        /**
         * MP4连接
         */
        val mp4url: String?,
        /**
         * 组织编码
         */
        val orgId: String?,
        /**
         * 浏览总数
         */
        val playCount: Int?,
        /**
         * 点赞总数
         */
        val praiseCount: Int?,
        /**
         * 排序号
         */
        val rank: String?,
        /**
         * 	站点id
         */
        val siteId: String?,
        /**
         * 资源id
         */
        val sourceId: String?,
        /**
         * 所属资源名称
         */
        val sourceName: String?,
        /**
         * 资源类型
         */
        val sourceType: String?,
        /**
         * 状态
         */
        val status: String?,
        /**
         * 	视频简介
         */
        val summary: String?,
        /**
         * 标签
         */
        val tags: String?,
        /**
         * 视频标题
         */
        val title: String?,
        /**
         * 播放视频URL
         */
        val upload: String?,
        /**
         * 	上传时间
         */
        val uploadTime: String?,
        /**
         * 网站
         */
        val url: String?,
        /**
         * 视频分类
         */
        val videotype: String?
) {
    data class InteractionVo(
            /**
             * 收藏
             */
            val enshrine: Int?,
            /**
             * 想去
             */
            val recordOne: Int?,
            /**
             * 	去过
             */
            val recordTwo: Int?,
            /**
             * 分享
             */
            val share: Int?,
            /**
             * 	点赞
             */
            val thumb: Int?
    )
}

