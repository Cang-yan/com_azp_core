package com.azp.core.sys.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_activity_sub_category
 * @author eamon
 */
public class ActivitySubCategoryDO implements Serializable {
    /**
     * 活动小类ID
     */
    private String id;

    /**
     * 大类ID
     */
    private String activityCategoryId;

    /**
     * 名称
     */
    private String name;

    /**
     * 规则说明
     */
    private String rule;

    /**
     * 获得积分数（只有类型3需要）
     */
    private Integer point;

    /**
     * 是否置顶
     */
    private Integer onTop;

    /**
     * 置顶时间
     */
    private Date onTopDate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date gmtUpdate;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityCategoryId() {
        return activityCategoryId;
    }

    public void setActivityCategoryId(String activityCategoryId) {
        this.activityCategoryId = activityCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getOnTop() {
        return onTop;
    }

    public void setOnTop(Integer onTop) {
        this.onTop = onTop;
    }

    public Date getOnTopDate() {
        return onTopDate;
    }

    public void setOnTopDate(Date onTopDate) {
        this.onTopDate = onTopDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ActivitySubCategoryDO other = (ActivitySubCategoryDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getActivityCategoryId() == null ? other.getActivityCategoryId() == null : this.getActivityCategoryId().equals(other.getActivityCategoryId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getRule() == null ? other.getRule() == null : this.getRule().equals(other.getRule()))
            && (this.getPoint() == null ? other.getPoint() == null : this.getPoint().equals(other.getPoint()))
            && (this.getOnTop() == null ? other.getOnTop() == null : this.getOnTop().equals(other.getOnTop()))
            && (this.getOnTopDate() == null ? other.getOnTopDate() == null : this.getOnTopDate().equals(other.getOnTopDate()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getGmtUpdate() == null ? other.getGmtUpdate() == null : this.getGmtUpdate().equals(other.getGmtUpdate()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getActivityCategoryId() == null) ? 0 : getActivityCategoryId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getRule() == null) ? 0 : getRule().hashCode());
        result = prime * result + ((getPoint() == null) ? 0 : getPoint().hashCode());
        result = prime * result + ((getOnTop() == null) ? 0 : getOnTop().hashCode());
        result = prime * result + ((getOnTopDate() == null) ? 0 : getOnTopDate().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getGmtUpdate() == null) ? 0 : getGmtUpdate().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", activityCategoryId=").append(activityCategoryId);
        sb.append(", name=").append(name);
        sb.append(", rule=").append(rule);
        sb.append(", point=").append(point);
        sb.append(", onTop=").append(onTop);
        sb.append(", onTopDate=").append(onTopDate);
        sb.append(", status=").append(status);
        sb.append(", gmtUpdate=").append(gmtUpdate);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}