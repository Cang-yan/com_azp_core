package com.azp.core.sys.web;

import com.azp.core.sys.model.StudyNotePostMapper;
import com.azp.core.sys.service.ActivityTypeTwoUserService;
import com.azp.core.sys.service.StudyNoteExtendsService;
import com.azp.core.sys.service.StudyNoteService;
import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(
        value = "study_note",
        tags = "学习心得自定义"
)
@RestController
@RequestMapping("api/sys/study/note")
public class StudyNoteExtendsController {
    @Autowired
    private StudyNoteService studyNoteService;

    @Autowired
    private StudyNoteExtendsService studyNoteExtendsService;
    @AuthGroup("admin")
    @ApiOperation(
            value = "添加活动二心得",
            notes = "添加活动二心得"
    )
    @RequestMapping(
            value = "typeTwo/add",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> addActivityTypeTwoStudyNotes(@RequestBody StudyNotePostMapper postMapper)
    {
        return Status.successBuilder()
                .addDataValue(studyNoteExtendsService.postTypeTwo(postMapper))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "审核活动二心得",
            notes = "审核活动二心得"
    )
    @RequestMapping(
            value = "typeTwo/check",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> checkActivityTypeTwoStudyNotes(@RequestParam(value = "id", required = true)String studyNotesId)
    {
        return Status.successBuilder()
                .addDataValue(studyNoteExtendsService.checkTypeTwo(studyNotesId))
                .map();
    }
}
