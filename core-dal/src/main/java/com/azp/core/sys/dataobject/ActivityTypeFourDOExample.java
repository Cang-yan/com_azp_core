package com.azp.core.sys.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityTypeFourDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    private Boolean forUpdate;

    public ActivityTypeFourDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    public void setForUpdate(Boolean forUpdate) {
        this.forUpdate = forUpdate;
    }

    public Boolean getForUpdate() {
        return forUpdate;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andGroupDateIsNull() {
            addCriterion("group_date is null");
            return (Criteria) this;
        }

        public Criteria andGroupDateIsNotNull() {
            addCriterion("group_date is not null");
            return (Criteria) this;
        }

        public Criteria andGroupDateEqualTo(Date value) {
            addCriterion("group_date =", value, "groupDate");
            return (Criteria) this;
        }

        public Criteria andGroupDateNotEqualTo(Date value) {
            addCriterion("group_date <>", value, "groupDate");
            return (Criteria) this;
        }

        public Criteria andGroupDateGreaterThan(Date value) {
            addCriterion("group_date >", value, "groupDate");
            return (Criteria) this;
        }

        public Criteria andGroupDateGreaterThanOrEqualTo(Date value) {
            addCriterion("group_date >=", value, "groupDate");
            return (Criteria) this;
        }

        public Criteria andGroupDateLessThan(Date value) {
            addCriterion("group_date <", value, "groupDate");
            return (Criteria) this;
        }

        public Criteria andGroupDateLessThanOrEqualTo(Date value) {
            addCriterion("group_date <=", value, "groupDate");
            return (Criteria) this;
        }

        public Criteria andGroupDateIn(List<Date> values) {
            addCriterion("group_date in", values, "groupDate");
            return (Criteria) this;
        }

        public Criteria andGroupDateNotIn(List<Date> values) {
            addCriterion("group_date not in", values, "groupDate");
            return (Criteria) this;
        }

        public Criteria andGroupDateBetween(Date value1, Date value2) {
            addCriterion("group_date between", value1, value2, "groupDate");
            return (Criteria) this;
        }

        public Criteria andGroupDateNotBetween(Date value1, Date value2) {
            addCriterion("group_date not between", value1, value2, "groupDate");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdIsNull() {
            addCriterion("activity_sub_category_id is null");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdIsNotNull() {
            addCriterion("activity_sub_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdEqualTo(String value) {
            addCriterion("activity_sub_category_id =", value, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdNotEqualTo(String value) {
            addCriterion("activity_sub_category_id <>", value, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdGreaterThan(String value) {
            addCriterion("activity_sub_category_id >", value, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("activity_sub_category_id >=", value, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdLessThan(String value) {
            addCriterion("activity_sub_category_id <", value, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("activity_sub_category_id <=", value, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdLike(String value) {
            addCriterion("activity_sub_category_id like", value, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdNotLike(String value) {
            addCriterion("activity_sub_category_id not like", value, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdIn(List<String> values) {
            addCriterion("activity_sub_category_id in", values, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdNotIn(List<String> values) {
            addCriterion("activity_sub_category_id not in", values, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdBetween(String value1, String value2) {
            addCriterion("activity_sub_category_id between", value1, value2, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andActivitySubCategoryIdNotBetween(String value1, String value2) {
            addCriterion("activity_sub_category_id not between", value1, value2, "activitySubCategoryId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPointIsNull() {
            addCriterion("point is null");
            return (Criteria) this;
        }

        public Criteria andPointIsNotNull() {
            addCriterion("point is not null");
            return (Criteria) this;
        }

        public Criteria andPointEqualTo(Integer value) {
            addCriterion("point =", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointNotEqualTo(Integer value) {
            addCriterion("point <>", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointGreaterThan(Integer value) {
            addCriterion("point >", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointGreaterThanOrEqualTo(Integer value) {
            addCriterion("point >=", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointLessThan(Integer value) {
            addCriterion("point <", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointLessThanOrEqualTo(Integer value) {
            addCriterion("point <=", value, "point");
            return (Criteria) this;
        }

        public Criteria andPointIn(List<Integer> values) {
            addCriterion("point in", values, "point");
            return (Criteria) this;
        }

        public Criteria andPointNotIn(List<Integer> values) {
            addCriterion("point not in", values, "point");
            return (Criteria) this;
        }

        public Criteria andPointBetween(Integer value1, Integer value2) {
            addCriterion("point between", value1, value2, "point");
            return (Criteria) this;
        }

        public Criteria andPointNotBetween(Integer value1, Integer value2) {
            addCriterion("point not between", value1, value2, "point");
            return (Criteria) this;
        }

        public Criteria andRankIsNull() {
            addCriterion("`rank` is null");
            return (Criteria) this;
        }

        public Criteria andRankIsNotNull() {
            addCriterion("`rank` is not null");
            return (Criteria) this;
        }

        public Criteria andRankEqualTo(Integer value) {
            addCriterion("`rank` =", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotEqualTo(Integer value) {
            addCriterion("`rank` <>", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThan(Integer value) {
            addCriterion("`rank` >", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThanOrEqualTo(Integer value) {
            addCriterion("`rank` >=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThan(Integer value) {
            addCriterion("`rank` <", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThanOrEqualTo(Integer value) {
            addCriterion("`rank` <=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankIn(List<Integer> values) {
            addCriterion("`rank` in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotIn(List<Integer> values) {
            addCriterion("`rank` not in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankBetween(Integer value1, Integer value2) {
            addCriterion("`rank` between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotBetween(Integer value1, Integer value2) {
            addCriterion("`rank` not between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andGroupPointIsNull() {
            addCriterion("group_point is null");
            return (Criteria) this;
        }

        public Criteria andGroupPointIsNotNull() {
            addCriterion("group_point is not null");
            return (Criteria) this;
        }

        public Criteria andGroupPointEqualTo(Integer value) {
            addCriterion("group_point =", value, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andGroupPointNotEqualTo(Integer value) {
            addCriterion("group_point <>", value, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andGroupPointGreaterThan(Integer value) {
            addCriterion("group_point >", value, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andGroupPointGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_point >=", value, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andGroupPointLessThan(Integer value) {
            addCriterion("group_point <", value, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andGroupPointLessThanOrEqualTo(Integer value) {
            addCriterion("group_point <=", value, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andGroupPointIn(List<Integer> values) {
            addCriterion("group_point in", values, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andGroupPointNotIn(List<Integer> values) {
            addCriterion("group_point not in", values, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andGroupPointBetween(Integer value1, Integer value2) {
            addCriterion("group_point between", value1, value2, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andGroupPointNotBetween(Integer value1, Integer value2) {
            addCriterion("group_point not between", value1, value2, "groupPoint");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberIsNull() {
            addCriterion("periods_number is null");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberIsNotNull() {
            addCriterion("periods_number is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberEqualTo(Integer value) {
            addCriterion("periods_number =", value, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberNotEqualTo(Integer value) {
            addCriterion("periods_number <>", value, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberGreaterThan(Integer value) {
            addCriterion("periods_number >", value, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("periods_number >=", value, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberLessThan(Integer value) {
            addCriterion("periods_number <", value, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberLessThanOrEqualTo(Integer value) {
            addCriterion("periods_number <=", value, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberIn(List<Integer> values) {
            addCriterion("periods_number in", values, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberNotIn(List<Integer> values) {
            addCriterion("periods_number not in", values, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberBetween(Integer value1, Integer value2) {
            addCriterion("periods_number between", value1, value2, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andPeriodsNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("periods_number not between", value1, value2, "periodsNumber");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateIsNull() {
            addCriterion("gmt_update is null");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateIsNotNull() {
            addCriterion("gmt_update is not null");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateEqualTo(Date value) {
            addCriterion("gmt_update =", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateNotEqualTo(Date value) {
            addCriterion("gmt_update <>", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateGreaterThan(Date value) {
            addCriterion("gmt_update >", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_update >=", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateLessThan(Date value) {
            addCriterion("gmt_update <", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_update <=", value, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateIn(List<Date> values) {
            addCriterion("gmt_update in", values, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateNotIn(List<Date> values) {
            addCriterion("gmt_update not in", values, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateBetween(Date value1, Date value2) {
            addCriterion("gmt_update between", value1, value2, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtUpdateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_update not between", value1, value2, "gmtUpdate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}