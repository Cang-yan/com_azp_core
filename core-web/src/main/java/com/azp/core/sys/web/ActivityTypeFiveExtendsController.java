package com.azp.core.sys.web;

import com.azp.core.sys.model.ActivityTypeFiveExtendsDetele;
import com.azp.core.sys.model.Department;
import com.azp.core.sys.service.ActivityFiveExtendsService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Api(
        value = "activity_type_five_extends",
        tags = "类型5活动管理扩展"
)
@RestController
@RequestMapping("api/sys/activity/type/five/extends")
public class ActivityTypeFiveExtendsController {
    @Autowired
    private ActivityFiveExtendsService activityFiveExtendsService;

    @AuthGroup("admin")
    @ApiOperation(
            value = "特殊时节时间筛选列表",
            notes = "特殊时节时间筛选列表"
    )
    @RequestMapping(
            value = "award/special/time",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getAwardSpecialTimeDateFilterList() {
        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.getAwardSpecialTimeDateFilterList())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "技能突出时间筛选列表",
            notes = "技能突出时间筛选列表"
    )
    @RequestMapping(
            value = "skill/excellence/time",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getSkillExcellenceDateFilterList() {
        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.getSkillExcellenceDateFilterList())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "雨中送援时间筛选列表",
            notes = "雨中送援时间筛选列表"
    )
    @RequestMapping(
            value = "rain/aid/time",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getRainAidDateFilterList() {
        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.getRainAidDateFilterList())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "雪中送碳时间筛选列表",
            notes = "雪中送碳时间筛选列表"
    )
    @RequestMapping(
            value = "snow/heat/time",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getSnowHeatDateFilterList() {
        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.getSnowHeatDateFilterList())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "火眼金睛时间筛选列表",
            notes = "火眼金睛时间筛选列表"
    )
    @RequestMapping(
            value = "good/eye/time",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getGoodEyeDateFilterList() {
        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.getGoodEyeDateFilterList())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "年度优秀时间筛选列表",
            notes = "年度优秀时间筛选列表"
    )
    @RequestMapping(
            value = "year/excellence/time",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getYearExcellenceDateFilterList() {
        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.getYearDateFilterList())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "月度优秀时间筛选列表",
            notes = "月度优秀时间筛选列表"
    )
    @RequestMapping(
            value = "month/excellence/time",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getMonthExcellenceDateFilterList() {
        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.getMonthDateFilterList())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "季度优秀时间筛选列表",
            notes = "季度优秀时间筛选列表"
    )
    @RequestMapping(
            value = "quarter/excellence/time",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getQuarterExcellenceDateFilterList() {
        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.getThreeMonthDateFilterList())
                .map();
    }


    @AuthGroup("admin")
    @ApiOperation(
            value = "月度优秀部门筛选列表",
            notes = "月度优秀部门筛选列表"
    )
    @RequestMapping(
            value = "month/excellence/department",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getMonthExcellenceDepartmentFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String type) {
        List<Department> resultList = activityFiveExtendsService.getMonthExcellenceDepartmentDropDownList(year, "NULL", month, type);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "月度优秀类型筛选列表",
            notes = "月度优秀类型筛选列表"
    )
    @RequestMapping(
            value = "month/excellence/type",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getMonthExcellenceTypeFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String department) {
        List<String> resultList = activityFiveExtendsService.getMonthExcellenceTypeDropDownList(year, "quarter", month, department);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "月度优秀批量删除",
            notes = "月度优秀批量删除"
    )
    @RequestMapping(
            value = "month/excellence/deletebatch",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteMonthExcellenceInBatch(@RequestBody ArrayList<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {

        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.deleteMonthExcellenceRecordInBatch(activityTypeFiveExtendsDeteleList))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "季度优秀部门筛选列表",
            notes = "季度优秀部门筛选列表"
    )
    @RequestMapping(
            value = "quarter/excellence/department",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getQuarterExcellenceDepartmentFilterList(@RequestParam String year, @RequestParam String quarter, @RequestParam(required = false) String type) {
        List<Department> resultList = activityFiveExtendsService.getThreeMonthExcellenceDepartmentDropDownList(year, quarter, "month", type);

        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "季度优秀类型筛选列表",
            notes = "季度优秀类型筛选列表"
    )
    @RequestMapping(
            value = "quarter/excellence/type",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getQuarterExcellenceTypeFilterList(@RequestParam String year, @RequestParam String quarter, @RequestParam(required = false) String department) {
        List<String> resultList = activityFiveExtendsService.getThreeMonthExcellenceTypeDropDownList(year, quarter, "month", department);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "季度优秀批量删除",
            notes = "季度优秀批量删除"
    )
    @RequestMapping(
            value = "quarter/excellence/deletebatch",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteQuarterExcellenceInBatch(@RequestBody ArrayList<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {

        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.deleteThreeMonthExcellenceRecordInBatch(activityTypeFiveExtendsDeteleList))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "年度优秀部门筛选列表",
            notes = "年度优秀部门筛选列表"
    )
    @RequestMapping(
            value = "year/excellence/department",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getYearExcellenceDepartmentFilterList(@RequestParam String year, @RequestParam(required = false) String type) {
        List<Department> resultList = activityFiveExtendsService.getYearExcellenceDepartmentDropDownList(year, "quarter", "month", type);

        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "年度优秀类型筛选列表",
            notes = "年度优秀类型筛选列表"
    )
    @RequestMapping(
            value = "year/excellence/type",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getYearExcellenceTypeFilterList(@RequestParam String year, @RequestParam(required = false) String department) {
        List<String> resultList = activityFiveExtendsService.getYearExcellenceTypeDropDownList(year, "quarter", "month", department);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "年度优秀批量删除",
            notes = "年度优秀批量删除"
    )
    @RequestMapping(
            value = "year/excellence/deletebatch",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteYearExcellenceInBatch(@RequestBody ArrayList<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {

        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.deleteYearExcellenceRecordInBatch(activityTypeFiveExtendsDeteleList))
                .map();
    }


    @AuthGroup("admin")
    @ApiOperation(
            value = "技能突出优秀部门筛选列表",
            notes = "技能突出优秀部门筛选列表"
    )
    @RequestMapping(
            value = "skill/excellence/department",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getSkillExcellenceDepartmentFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String type) {
        List<Department> resultList = activityFiveExtendsService.getSkillExcellenceDepartmentDropDownList(year, "NULL", month, type);

        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "技能突出优秀类型筛选列表",
            notes = "技能突出优秀类型筛选列表"
    )
    @RequestMapping(
            value = "skill/excellence/type",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getSkillExcellenceTypeFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String department) {
        List<String> resultList = activityFiveExtendsService.getSkillExcellenceTypeDropDownList(year, "quarter", month, department);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "技能突出批量删除",
            notes = "技能突出批量删除"
    )
    @RequestMapping(
            value = "skill/excellence/deletebatch",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteSkillExcellenceInBatch(@RequestBody ArrayList<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {

        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.deleteSkillExcellenceRecordInBatch(activityTypeFiveExtendsDeteleList))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "特殊时节部门筛选列表",
            notes = "特殊时节部门筛选列表"
    )
    @RequestMapping(
            value = "award/special/time/department",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getSpecialTimeDepartmentFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String type) {
        List<Department> resultList = activityFiveExtendsService.getSpecialTimeDepartmentDropDownList(year, "NULL", month, type);

        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "特殊时节类型筛选列表",
            notes = "特殊时节类型筛选列表"
    )
    @RequestMapping(
            value = "award/special/time/type",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getSpecialTimeTypeFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String department) {
        List<String> resultList = activityFiveExtendsService.getSpecialTimeTypeDropDownList(year, "quarter", month, department);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "特殊时节批量删除",
            notes = "特殊时节批量删除"
    )
    @RequestMapping(
            value = "award/special/time/deletebatch",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteSpecialTimeInBatch(@RequestBody ArrayList<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {

        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.deleteSpecialTimeRecordInBatch(activityTypeFiveExtendsDeteleList))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "雨中送援部门筛选列表",
            notes = "雨中送援部门筛选列表"
    )
    @RequestMapping(
            value = "rain/aid/department",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getRainAidDepartmentFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String type) {
        List<Department> resultList = activityFiveExtendsService.getRainAidDepartmentDropDownList(year, "NULL", month, type);

        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "雨中送援类型筛选列表",
            notes = "雨中送援类型筛选列表"
    )
    @RequestMapping(
            value = "rain/aid/type",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getRainAidTypeFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String department) {
        List<String> resultList = activityFiveExtendsService.getRainAidTypeDropDownList(year, "quarter", month, department);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "雨中送援批量删除",
            notes = "雨中送援批量删除"
    )
    @RequestMapping(
            value = "rain/aid/deletebatch",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteRainAidInBatch(@RequestBody ArrayList<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {

        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.deleteRainAidRecordInBatch(activityTypeFiveExtendsDeteleList))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "雪中送炭部门筛选列表",
            notes = "雪中送炭部门筛选列表"
    )
    @RequestMapping(
            value = "snow/heat/department",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getSnowHeatDepartmentFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String type) {
        List<Department> resultList = activityFiveExtendsService.getSnowHeatDepartmentDropDownList(year, "NULL", month, type);

        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "雪中送炭类型筛选列表",
            notes = "雪中送炭类型筛选列表"
    )
    @RequestMapping(
            value = "snow/heat/type",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getSnowHeatTypeFilterList(@RequestParam String year, @RequestParam String month, @RequestParam(required = false) String department) {
        List<String> resultList = activityFiveExtendsService.getSnowHeatTypeDropDownList(year, "quarter", month, department);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "雪中送炭批量删除",
            notes = "雪中送炭批量删除"
    )
    @RequestMapping(
            value = "snow/heat/deletebatch",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteSnowHeatInBatch(@RequestBody ArrayList<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {

        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.deleteSnowHeatRecordInBatch(activityTypeFiveExtendsDeteleList))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "火眼金睛部门筛选列表",
            notes = "火眼金睛部门筛选列表"
    )
    @RequestMapping(
            value = "good/eye/department",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getGoodEyeDepartmentFilterList(@RequestParam String year, @RequestParam String quarter, @RequestParam(required = false) String awardName) {
        List<Department> resultList = activityFiveExtendsService.getGoodEyeDepartmentDropDownList(year, quarter, "month", awardName);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "火眼金睛类型筛选列表",
            notes = "火眼金睛类型筛选列表"
    )
    @RequestMapping(
            value = "good/eye/type",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getGoodEyeTypeFilterList(@RequestParam String year, @RequestParam String quarter, @RequestParam(required = false) String department) {
        List<String> resultList = activityFiveExtendsService.getGoodEyeTypeDropDownList(year, quarter, "month", department);
        return Status.successBuilder()
                .addDataValue(resultList)
                .addDataCount((long) resultList.size())
                .map();

    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "火眼金睛批量删除",
            notes = "火眼金睛批量删除"
    )
    @RequestMapping(
            value = "good/eye/deletebatch",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> deleteGoodEyeInBatch(@RequestBody ArrayList<ActivityTypeFiveExtendsDetele> activityTypeFiveExtendsDeteleList) {

        return Status.successBuilder()
                .addDataValue(activityFiveExtendsService.deleteGoodEyeRecordInBatch(activityTypeFiveExtendsDeteleList))
                .map();
    }
}
