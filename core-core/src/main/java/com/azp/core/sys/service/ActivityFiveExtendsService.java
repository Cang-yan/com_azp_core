package com.azp.core.sys.service;

import com.azp.core.sys.model.*;
import com.horsecoder.common.status.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/1/28 5:18 下午
 **/
@Service
public class ActivityFiveExtendsService {

    @Autowired
    private AwardSkillExcellenceService awardSkillExcellenceService;

    @Autowired
    private AwardSpecialTimeService awardSpecialTimeService;

    @Autowired
    private AwardRainAidService awardRainAidService;

    @Autowired
    private AwardSnowHeatService awardSnowHeatService;

    @Autowired
    private AwardGoodEyeService awardGoodEyeService;

    @Autowired
    private YearExcellentService yearExcellentService;

    @Autowired
    private MonthExcellentService monthExcellentService;

    @Autowired
    private ThreeMonthExcellentService threeMonthExcellentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ActivityFiveExtendsToolService activityFiveExtendsToolService;

    public YearQuarterMonth getSkillExcellenceDateFilterList() {
        List<AwardSkillExcellence> listByFilter = awardSkillExcellenceService.getListByFilter(new AwardSkillExcellenceFilterMapper());
        YearQuarterMonth yearQuarterMonth = new YearQuarterMonth();
        if (CollectionUtils.isEmpty(listByFilter)) return yearQuarterMonth;
        listByFilter.forEach((e) -> {
            buildWithMonth(yearQuarterMonth, e.getYear(), e.getMonth());
        });
        return yearQuarterMonth;
    }

    public YearQuarterMonth getAwardSpecialTimeDateFilterList() {
        List<AwardSpecialTime> listByFilter = awardSpecialTimeService.getListByFilter(new AwardSpecialTimeFilterMapper());
        YearQuarterMonth yearQuarterMonth = new YearQuarterMonth();
        if (CollectionUtils.isEmpty(listByFilter)) return yearQuarterMonth;
        listByFilter.forEach((e) -> {
            buildWithMonth(yearQuarterMonth, e.getYear(), e.getMonth());
        });
        return yearQuarterMonth;
    }

    public YearQuarterMonth getRainAidDateFilterList() {
        List<AwardRainAid> listByFilter = awardRainAidService.getListByFilter(new AwardRainAidFilterMapper());
        YearQuarterMonth yearQuarterMonth = new YearQuarterMonth();
        if (CollectionUtils.isEmpty(listByFilter)) return yearQuarterMonth;
        listByFilter.forEach((e) -> {
            buildWithMonth(yearQuarterMonth, e.getYear(), e.getMonth());
        });
        return yearQuarterMonth;
    }

    public YearQuarterMonth getSnowHeatDateFilterList() {
        List<AwardSnowHeat> listByFilter = awardSnowHeatService.getListByFilter(new AwardSnowHeatFilterMapper());
        YearQuarterMonth yearQuarterMonth = new YearQuarterMonth();
        if (CollectionUtils.isEmpty(listByFilter)) return yearQuarterMonth;
        listByFilter.forEach((e) -> {
            buildWithMonth(yearQuarterMonth, e.getYear(), e.getMonth());
        });
        return yearQuarterMonth;
    }

    public YearQuarterMonth getGoodEyeDateFilterList() {
        List<AwardGoodEye> listByFilter = awardGoodEyeService.getListByFilter(new AwardGoodEyeFilterMapper());
        YearQuarterMonth yearQuarterMonth = new YearQuarterMonth();
        if (CollectionUtils.isEmpty(listByFilter)) return yearQuarterMonth;
        listByFilter.forEach((e) -> {
            buildWithQuarter(yearQuarterMonth, e.getYear(), e.getQuarter());
        });
        return yearQuarterMonth;
    }

    public YearQuarterMonth getYearDateFilterList() {
        List<YearExcellent> listByFilter = yearExcellentService.getListByFilter(new YearExcellentFilterMapper());
        YearQuarterMonth yearQuarterMonth = new YearQuarterMonth();
        if (CollectionUtils.isEmpty(listByFilter)) return yearQuarterMonth;
        listByFilter.forEach((e) -> {
            yearQuarterMonth.getYears().add(e.getYear());
        });
        return yearQuarterMonth;
    }

    public YearQuarterMonth getMonthDateFilterList() {
        List<MonthExcellent> listByFilter = monthExcellentService.getListByFilter(new MonthExcellentFilterMapper());
        YearQuarterMonth yearQuarterMonth = new YearQuarterMonth();
        if (CollectionUtils.isEmpty(listByFilter)) return yearQuarterMonth;
        listByFilter.forEach((e) -> {
            buildWithMonth(yearQuarterMonth, e.getYear(), e.getMonth());
        });
        return yearQuarterMonth;
    }

    public YearQuarterMonth getThreeMonthDateFilterList() {
        List<ThreeMonthExcellent> listByFilter = threeMonthExcellentService.getListByFilter(new ThreeMonthExcellentFilterMapper());
        YearQuarterMonth yearQuarterMonth = new YearQuarterMonth();
        if (CollectionUtils.isEmpty(listByFilter)) return yearQuarterMonth;
        listByFilter.forEach((e) -> {
            buildWithQuarter(yearQuarterMonth, e.getYear(), e.getThreeMonth());
        });
        return yearQuarterMonth;
    }

    private void buildWithQuarter(YearQuarterMonth yearQuarterMonth, String year, String quarter) {
        yearQuarterMonth.getYears().add(year);
        yearQuarterMonth.getQuarters().add(quarter);
        Set<String> quarterSet = yearQuarterMonth.getTime().get(year);
        if (CollectionUtils.isEmpty(quarterSet)) {
            quarterSet = new HashSet<>();
        }
        quarterSet.add(quarter);
        yearQuarterMonth.getTime().put(year, quarterSet);
    }

    private void buildWithMonth(YearQuarterMonth yearQuarterMonth, String year, String month) {
        yearQuarterMonth.getYears().add(year);
        yearQuarterMonth.getMonths().add(month);
        Set<String> monthSet = yearQuarterMonth.getTime().get(year);
        if (CollectionUtils.isEmpty(monthSet)) {
            monthSet = new HashSet<>();
        }
        monthSet.add(month);
        yearQuarterMonth.getTime().put(year, monthSet);
    }
//以下是各表格里部门类型的下拉列表
    /*
     *月度优秀奖的部门类型下拉列表和批量删除
     *
     *
     */

    public List<Department> getMonthExcellenceDepartmentDropDownList(String year, String quarter, String month,String type){
        MonthExcellentFilterMapper monthExcellentFilterMapper = new MonthExcellentFilterMapper();
        monthExcellentFilterMapper.year = year;
        monthExcellentFilterMapper.month = month;
        monthExcellentFilterMapper.type = type;
        List<MonthExcellent> monthExcellentList=monthExcellentService.getListByFilter(monthExcellentFilterMapper);
        List<Department> departments =new ArrayList<>();
        for (MonthExcellent monthExcellent:monthExcellentList) {
            addDepartment(monthExcellent.getDepartment(),departments);
        }
        return departments;
    }


    public List<String> getMonthExcellenceTypeDropDownList(String year,String quarter,String month,String department){
        MonthExcellentFilterMapper monthExcellentFilterMapper = new MonthExcellentFilterMapper();
        monthExcellentFilterMapper.year = year;
        monthExcellentFilterMapper.month = month;
        monthExcellentFilterMapper.department = department;

        List<MonthExcellent> monthExcellentList=monthExcellentService.getListByFilter(monthExcellentFilterMapper);
        List<String> types =new ArrayList<>();
        for (int i = 0; i < monthExcellentList.size(); i++) {
            String type = monthExcellentList.get(i).getType();
            if(CollectionUtils.isEmpty(types)) types.add(type);
            if(!isExit(type,types)) types.add(type);
        }
        
        return types;

    }

    public String deleteMonthExcellenceRecordInBatch(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList){



        monthExcellentService.deleteList(activityFiveExtendsToolService.getTargetedIdList(activityTypeFiveExtendsDeteleList));
        activityFiveExtendsToolService.deletePointAndNotifyUser(activityTypeFiveExtendsDeteleList);

        return "DONE";
    }



    /*
     *季度优秀奖的部门类型下拉列表以及批量删除
     *
     *
     */

    public List<Department> getThreeMonthExcellenceDepartmentDropDownList(String year,String quarter,String month,String type){
        ThreeMonthExcellentFilterMapper threeMonthExcellentFilterMapper = new ThreeMonthExcellentFilterMapper();
        threeMonthExcellentFilterMapper.year = year;
        threeMonthExcellentFilterMapper.threeMonth = quarter;
        threeMonthExcellentFilterMapper.type = type;

        List<ThreeMonthExcellent> threeMonthExcellentList=threeMonthExcellentService.getListByFilter(threeMonthExcellentFilterMapper);
        List<Department> departments =new ArrayList<>();
        for (ThreeMonthExcellent threeMonthExcellent:threeMonthExcellentList) {
            addDepartment(threeMonthExcellent.getDepartment(),departments);
        }
        return departments;
       
    }

    public List<String> getThreeMonthExcellenceTypeDropDownList(String year,String quarter,String month,String department){
        ThreeMonthExcellentFilterMapper threeMonthExcellentFilterMapper = new ThreeMonthExcellentFilterMapper();
        threeMonthExcellentFilterMapper.year = year;
        threeMonthExcellentFilterMapper.threeMonth = quarter;
        threeMonthExcellentFilterMapper.department = department;

        List<ThreeMonthExcellent> threeMonthExcellentList=threeMonthExcellentService.getListByFilter(threeMonthExcellentFilterMapper);
        List<String> types =new ArrayList<>();
        for (int i = 0; i < threeMonthExcellentList.size(); i++) {
            String type = threeMonthExcellentList.get(i).getType();
            if(CollectionUtils.isEmpty(types)) types.add(type);
            if(!isExit(type,types)) types.add(type);
        }

        return types;

    }
    public String deleteThreeMonthExcellenceRecordInBatch(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList){



        threeMonthExcellentService.deleteList(activityFiveExtendsToolService.getTargetedIdList(activityTypeFiveExtendsDeteleList));
        activityFiveExtendsToolService.deletePointAndNotifyUser(activityTypeFiveExtendsDeteleList);

        return "DONE";
    }


    /*
     *年度优秀奖的部门类型下拉列表以及批量删除
     *
     *
     */

    public List<Department>  getYearExcellenceDepartmentDropDownList(String year,String quarter,String month,String type){
        YearExcellentFilterMapper yearExcellentFilterMapper = new YearExcellentFilterMapper();
        yearExcellentFilterMapper.year = year;
        yearExcellentFilterMapper.type = type;

        List<YearExcellent> yearExcellentList=yearExcellentService.getListByFilter(yearExcellentFilterMapper);
        List<Department> departments =new ArrayList<>();
        for (YearExcellent yearExcellent:yearExcellentList) {
            addDepartment(yearExcellent.getDepartment(),departments);
        }
        return departments;
    }

    public List<String> getYearExcellenceTypeDropDownList(String year,String quarter,String month,String department){
        YearExcellentFilterMapper yearExcellentFilterMapper = new YearExcellentFilterMapper();
        yearExcellentFilterMapper.year = year;
        yearExcellentFilterMapper.department = department;

        List<YearExcellent> yearExcellentList=yearExcellentService.getListByFilter(yearExcellentFilterMapper);
        List<String> types =new ArrayList<>();
        for (int i = 0; i < yearExcellentList.size(); i++) {
            String type = yearExcellentList.get(i).getType();
            if(CollectionUtils.isEmpty(types)) types.add(type);
            if(!isExit(type,types)) types.add(type);
        }
        return types;

    }

    public String deleteYearExcellenceRecordInBatch(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList){



        yearExcellentService.deleteList(activityFiveExtendsToolService.getTargetedIdList(activityTypeFiveExtendsDeteleList));
        activityFiveExtendsToolService.deletePointAndNotifyUser(activityTypeFiveExtendsDeteleList);

        return "DONE";
    }

    /*
     *技能突出的部门类型下拉列表以及批量删除
     *
     *
     */

    public List<Department> getSkillExcellenceDepartmentDropDownList(String year,String quarter,String month,String type){
        AwardSkillExcellenceFilterMapper awardSkillExcellenceFilterMapper = new AwardSkillExcellenceFilterMapper();
        awardSkillExcellenceFilterMapper.year = year;
        awardSkillExcellenceFilterMapper.month = month;
        awardSkillExcellenceFilterMapper.type = type;

        List<AwardSkillExcellence> awardSkillExcellenceList=awardSkillExcellenceService.getListByFilter(awardSkillExcellenceFilterMapper);
        List<Department> departments =new ArrayList<>();
        for (AwardSkillExcellence awardSkillExcellence:awardSkillExcellenceList) {
            addDepartment(awardSkillExcellence.getDepartment(),departments);
        }
        return departments;
    }

    public List<String> getSkillExcellenceTypeDropDownList(String year,String quarter,String month,String department){
        AwardSkillExcellenceFilterMapper awardSkillExcellenceFilterMapper = new AwardSkillExcellenceFilterMapper();
        awardSkillExcellenceFilterMapper.year = year;
        awardSkillExcellenceFilterMapper.month = month;
        awardSkillExcellenceFilterMapper.department = department;

        List<AwardSkillExcellence> awardSkillExcellenceList=awardSkillExcellenceService.getListByFilter(awardSkillExcellenceFilterMapper);
        List<String> types =new ArrayList<>();
        for (int i = 0; i < awardSkillExcellenceList.size(); i++) {
            String type = awardSkillExcellenceList.get(i).getType();
            if(CollectionUtils.isEmpty(types)) types.add(type);
            if(!isExit(type,types)) types.add(type);
        }

        return types;

    }

    public String deleteSkillExcellenceRecordInBatch(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList){



        awardSkillExcellenceService.deleteList(activityFiveExtendsToolService.getTargetedIdList(activityTypeFiveExtendsDeteleList));
        activityFiveExtendsToolService.deletePointAndNotifyUser(activityTypeFiveExtendsDeteleList);

        return "DONE";
    }




    /*
     *特殊时节的部门类型下拉列表以及批量删除
     *
     *
     */

    public List<Department> getSpecialTimeDepartmentDropDownList(String year,String quarter,String month,String type){
        AwardSpecialTimeFilterMapper awardSpecialTimeFilterMapper = new AwardSpecialTimeFilterMapper();
        awardSpecialTimeFilterMapper.year = year;
        awardSpecialTimeFilterMapper.month = month;
        awardSpecialTimeFilterMapper.type = type;

        List<AwardSpecialTime> awardSpecialTimeList=awardSpecialTimeService.getListByFilter(awardSpecialTimeFilterMapper);
        List<Department> departments =new ArrayList<>();
        for (AwardSpecialTime awardSpecialTime:awardSpecialTimeList) {
            addDepartment(awardSpecialTime.getDepartment(),departments);
        }
        return departments;
    }

    public List<String> getSpecialTimeTypeDropDownList(String year,String quarter,String month,String department){
        AwardSpecialTimeFilterMapper awardSpecialTimeFilterMapper = new AwardSpecialTimeFilterMapper();
        awardSpecialTimeFilterMapper.year = year;
        awardSpecialTimeFilterMapper.month = month;
        awardSpecialTimeFilterMapper.department = department;

        List<AwardSpecialTime> awardSpecialTimeList=awardSpecialTimeService.getListByFilter(awardSpecialTimeFilterMapper);
        List<String> types =new ArrayList<>();
        for (int i = 0; i < awardSpecialTimeList.size(); i++) {
            String type = awardSpecialTimeList.get(i).getType();
            if(CollectionUtils.isEmpty(types)) types.add(type);
            if(!isExit(type,types)) types.add(type);
        }

        return types;

    }

    public String deleteSpecialTimeRecordInBatch(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList){



        awardSpecialTimeService.deleteList(activityFiveExtendsToolService.getTargetedIdList(activityTypeFiveExtendsDeteleList));
        activityFiveExtendsToolService.deletePointAndNotifyUser(activityTypeFiveExtendsDeteleList);

        return "DONE";
    }



    /*
    *雨中送援的部门类型下拉列表以及批量删除
    *
    *
     */

    public List<Department> getRainAidDepartmentDropDownList(String year,String quarter,String month,String type){
        AwardRainAidFilterMapper awardRainAidFilterMapper = new AwardRainAidFilterMapper();
        awardRainAidFilterMapper.year = year;
        awardRainAidFilterMapper.month = month;
        awardRainAidFilterMapper.type = type;

        List<AwardRainAid> awardRainAidList=awardRainAidService.getListByFilter(awardRainAidFilterMapper);
        List<Department> departments =new ArrayList<>();
        for (AwardRainAid awardRainAid:awardRainAidList) {
            addDepartment(awardRainAid.getDepartment(),departments);
        }
        return departments;
    }

    public List<String> getRainAidTypeDropDownList(String year,String quarter,String month,String department){
        AwardRainAidFilterMapper awardRainAidFilterMapper = new AwardRainAidFilterMapper();
        awardRainAidFilterMapper.year = year;
        awardRainAidFilterMapper.month = month;
        awardRainAidFilterMapper.department = department;

        List<AwardRainAid> awardRainAidList=awardRainAidService.getListByFilter(awardRainAidFilterMapper);
        List<String> types =new ArrayList<>();
        for (int i = 0; i < awardRainAidList.size(); i++) {
            String type = awardRainAidList.get(i).getType();
            if(CollectionUtils.isEmpty(types)) types.add(type);
            if(!isExit(type,types)) types.add(type);
        }
        return types;

    }

    public String deleteRainAidRecordInBatch(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList){



        awardRainAidService.deleteList(activityFiveExtendsToolService.getTargetedIdList(activityTypeFiveExtendsDeteleList));
        activityFiveExtendsToolService.deletePointAndNotifyUser(activityTypeFiveExtendsDeteleList);

        return "DONE";
    }

    /**
     * 雪中送炭的类型，部门下拉列表以及批量删除
     * @return
     */
    public List<Department> getSnowHeatDepartmentDropDownList(String year,String quarter,String month,String type){
        AwardSnowHeatFilterMapper awardSnowHeatFilterMapper = new AwardSnowHeatFilterMapper();
        awardSnowHeatFilterMapper.year = year;
        awardSnowHeatFilterMapper.month = month;
        awardSnowHeatFilterMapper.type = type;

        List<AwardSnowHeat> awardSnowHeatList=awardSnowHeatService.getListByFilter(awardSnowHeatFilterMapper);
        List<Department> departments =new ArrayList<>();
        for (AwardSnowHeat awardSnowHeat:awardSnowHeatList) {
            addDepartment(awardSnowHeat.getDepartment(),departments);
        }
        return departments;
    }

    public List<String> getSnowHeatTypeDropDownList(String year,String quarter,String month,String department){
        AwardSnowHeatFilterMapper awardSnowHeatFilterMapper = new AwardSnowHeatFilterMapper();
        awardSnowHeatFilterMapper.year = year;
        awardSnowHeatFilterMapper.month = month;
        awardSnowHeatFilterMapper.department = department;

        List<AwardSnowHeat> awardSnowHeatList=awardSnowHeatService.getListByFilter(awardSnowHeatFilterMapper);
        List<String> types =new ArrayList<>();
        for (int i = 0; i < awardSnowHeatList.size(); i++) {
            String type = awardSnowHeatList.get(i).getType();
            if(CollectionUtils.isEmpty(types)) types.add(type);
            if(!isExit(type,types)) types.add(type);
        }
        return types;

    }

    public String deleteSnowHeatRecordInBatch(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList){



        awardSnowHeatService.deleteList(activityFiveExtendsToolService.getTargetedIdList(activityTypeFiveExtendsDeteleList));
        activityFiveExtendsToolService.deletePointAndNotifyUser(activityTypeFiveExtendsDeteleList);

        return "DONE";
    }


    /**
     *火眼金睛的部门类型的下拉列表，以及批量删除
     */

    public List<Department> getGoodEyeDepartmentDropDownList(String year,String quarter,String month,String awardName){
        AwardGoodEyeFilterMapper awardGoodEyeFilterMapper = new AwardGoodEyeFilterMapper();
        awardGoodEyeFilterMapper.year = year;
        awardGoodEyeFilterMapper.quarter= quarter;
        awardGoodEyeFilterMapper.awardName = awardName;

        List<AwardGoodEye> awardGoodEyeList=awardGoodEyeService.getListByFilter(awardGoodEyeFilterMapper);
        List<Department> departments =new ArrayList<>();
        for (AwardGoodEye awardGoodEye:awardGoodEyeList) {
            addDepartment(awardGoodEye.getDepartment(),departments);
        }
        return departments;
    }

    public List<String> getGoodEyeTypeDropDownList(String year,String quarter,String month,String department){
        AwardGoodEyeFilterMapper awardGoodEyeFilterMapper = new AwardGoodEyeFilterMapper();
        awardGoodEyeFilterMapper.year = year;
        awardGoodEyeFilterMapper.quarter = quarter;
        awardGoodEyeFilterMapper.department = department;
        List<AwardGoodEye> awardGoodEyeList=awardGoodEyeService.getListByFilter(awardGoodEyeFilterMapper);
        List<String> types =new ArrayList<>();
        for (int i = 0; i < awardGoodEyeList.size(); i++) {
            String awardName = awardGoodEyeList.get(i).getAwardName();
            if(CollectionUtils.isEmpty(types)) types.add(awardName);
            if(!isExit(awardName,types)) types.add(awardName);
        }
        return types;

    }


    public String deleteGoodEyeRecordInBatch(List<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList){


        awardGoodEyeService.deleteList(activityFiveExtendsToolService.getTargetedIdList(activityTypeFiveExtendsDeteleList));
        activityFiveExtendsToolService.deletePointAndNotifyUser(activityTypeFiveExtendsDeteleList);

        return "DONE";
    }


    public void addDepartment(String departmentName,List<Department> departments){
        DepartmentFilterMapper departmentFilterMapper=new DepartmentFilterMapper();
        departmentFilterMapper.name=departmentName;
        for(Department demo:departmentService.getListByFilter(departmentFilterMapper))
            if(CollectionUtils.isEmpty(departments)||!isExit(demo,departments)) departments.add(demo);
    }



    public boolean isExit(Department department,List<Department> departments) {
        boolean bo = false;
        for (Department de : departments) {
            if (de.getName().equals(department.getName())) {
                bo = true;
                break;
            }

        }
        return bo;

    }


    public boolean isExit(String name,List<String> nameList) {
        boolean bo = false;
        for (String str : nameList) {
            if (str.equals(name)) {
                bo = true;
                break;
            }

        }
        return bo;

    }

    }
