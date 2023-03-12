package com.azp.core.sys.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_activity_type_four
 * @author eamon
 */
public class ActivityTypeFourDO implements Serializable {
    /**
     * 类型4活动ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 组队日期
     */
    private Date groupDate;

    /**
     * 活动小类ID
     */
    private String activitySubCategoryId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 积分奖励
     */
    private Integer point;

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 队伍积分
     */
    private Integer groupPoint;

    /**
     * 期数
     */
    private Integer periodsNumber;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(Date groupDate) {
        this.groupDate = groupDate;
    }

    public String getActivitySubCategoryId() {
        return activitySubCategoryId;
    }

    public void setActivitySubCategoryId(String activitySubCategoryId) {
        this.activitySubCategoryId = activitySubCategoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getGroupPoint() {
        return groupPoint;
    }

    public void setGroupPoint(Integer groupPoint) {
        this.groupPoint = groupPoint;
    }

    public Integer getPeriodsNumber() {
        return periodsNumber;
    }

    public void setPeriodsNumber(Integer periodsNumber) {
        this.periodsNumber = periodsNumber;
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
        ActivityTypeFourDO other = (ActivityTypeFourDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getGroupDate() == null ? other.getGroupDate() == null : this.getGroupDate().equals(other.getGroupDate()))
            && (this.getActivitySubCategoryId() == null ? other.getActivitySubCategoryId() == null : this.getActivitySubCategoryId().equals(other.getActivitySubCategoryId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPoint() == null ? other.getPoint() == null : this.getPoint().equals(other.getPoint()))
            && (this.getRank() == null ? other.getRank() == null : this.getRank().equals(other.getRank()))
            && (this.getGroupPoint() == null ? other.getGroupPoint() == null : this.getGroupPoint().equals(other.getGroupPoint()))
            && (this.getPeriodsNumber() == null ? other.getPeriodsNumber() == null : this.getPeriodsNumber().equals(other.getPeriodsNumber()))
            && (this.getGmtUpdate() == null ? other.getGmtUpdate() == null : this.getGmtUpdate().equals(other.getGmtUpdate()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getGroupDate() == null) ? 0 : getGroupDate().hashCode());
        result = prime * result + ((getActivitySubCategoryId() == null) ? 0 : getActivitySubCategoryId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPoint() == null) ? 0 : getPoint().hashCode());
        result = prime * result + ((getRank() == null) ? 0 : getRank().hashCode());
        result = prime * result + ((getGroupPoint() == null) ? 0 : getGroupPoint().hashCode());
        result = prime * result + ((getPeriodsNumber() == null) ? 0 : getPeriodsNumber().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", groupDate=").append(groupDate);
        sb.append(", activitySubCategoryId=").append(activitySubCategoryId);
        sb.append(", status=").append(status);
        sb.append(", point=").append(point);
        sb.append(", rank=").append(rank);
        sb.append(", groupPoint=").append(groupPoint);
        sb.append(", periodsNumber=").append(periodsNumber);
        sb.append(", gmtUpdate=").append(gmtUpdate);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}