package com.example.lee.leeframe.bean.requestbean;

import java.util.List;

/**
 * Created by tomGuo on 2018/5/22.
 */

public class PublishImmigrationRequestBean {

    /**
     * objectId : 0
     * objectType : 0
     * tagId : 2
     * content : 汶川地震十周年国际研讨会暨第四届大陆地震国际研讨会12日在四川省成都市开幕
     * fileIds : [11]
     */

    private int objectId;
    private int objectType;
    private List<Integer> tagIds;
    private String content;
    private List<Integer> fileIds;

    public PublishImmigrationRequestBean(int objectId, int objectType, List<Integer> tagIds, String content, List<Integer> fileIds) {
        this.objectId = objectId;
        this.objectType = objectType;
        this.tagIds = tagIds;
        this.content = content;
        this.fileIds = fileIds;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagId) {
        this.tagIds = tagId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Integer> fileIds) {
        this.fileIds = fileIds;
    }
}
