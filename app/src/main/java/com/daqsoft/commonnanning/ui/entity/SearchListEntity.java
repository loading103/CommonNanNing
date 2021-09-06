package com.daqsoft.commonnanning.ui.entity;
import java.util.List;

/**
 * 全局搜索
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */

public class SearchListEntity {
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private List<SearchItem> mItemList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SearchItem> getmItemList() {
        return mItemList;
    }

    public void setmItemList(List<SearchItem> mItemList) {
        this.mItemList = mItemList;
    }

    public static class SearchItem{
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        // 名称
        private String name;
        // id
        private String id;
        // 景区等级
        private String leveName;
        // 资源编码
        private String resourcecode;
        // 是否选中
        private boolean isSelect;
        // 图片地址
        private String imgUrl;
        // 视频地址
        private String videoUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getLeveName() {
            return leveName;
        }

        public void setLeveName(String leveName) {
            this.leveName = leveName;
        }

        public String getResourcecode() {
            return resourcecode;
        }

        public void setResourcecode(String resourcecode) {
            this.resourcecode = resourcecode;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
