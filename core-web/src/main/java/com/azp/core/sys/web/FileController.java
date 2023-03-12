package com.azp.core.sys.web;

import com.horsecoder.auth.AuthGroup;
import com.horsecoder.common.status.Status;
import com.horsecoder.storage.domain.FileDownloadCoreFacade;
import com.horsecoder.storage.domain.FileUpdateCoreFacade;
import com.horsecoder.storage.domain.model.StringFile;
import com.horsecoder.storage.util.FeignFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/1/14 11:47 上午
 **/
@Api(
        value = "file",
        tags = "文件管理"
)
@RestController
@RequestMapping("api/sys/file")
public class FileController {

    @Resource
    private FileUpdateCoreFacade fileUpdateCoreFacade;

    @Resource
    private FileDownloadCoreFacade fileDownloadCoreFacade;

    @AuthGroup("admin")
    @ApiOperation(
            value = "上传图片",
            notes = "上传图片"
    )
    @RequestMapping(
            value = "image",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestPart(value = "file") MultipartFile file) {
        return Status.successBuilder()
                .addDataValue(fileUpdateCoreFacade.uploadImageFile(file, "azp", null, null))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "上传文本",
            notes = "上传文本"
    )
    @RequestMapping(
            value = "string",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> uploadString(
            @RequestBody String file) {
        StringFile uploadStringFile = new StringFile();
        uploadStringFile.setFile(file);
        uploadStringFile.setUser("azp");
        return Status.successBuilder()
                .addDataValue(fileUpdateCoreFacade.uploadString(uploadStringFile))
                .map();
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "下载图片",
            notes = "下载图片"
    )
    @RequestMapping(
            value = "download/image",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public void downloadImage(
            @RequestParam(value = "fileName") String fileName, HttpServletResponse httpServletResponse) throws IOException {
        FeignFileUtils.transferResponseStream(httpServletResponse, fileDownloadCoreFacade.downloadFile("azp", fileName, null));
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "下载富文本",
            notes = "下载富文本"
    )
    @RequestMapping(
            value = "download/string",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public String downloadString(
            @RequestParam(value = "fileName") String fileName) throws IOException {
        return fileDownloadCoreFacade.downloadStringFile("azp", fileName, null);
    }

    @AuthGroup("admin")
    @ApiOperation(
            value = "测试",
            notes = "测试"
    )
    @RequestMapping(
            value = "test",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam(value = "url") String url) {
        return Status.successBuilder()
                .addDataValue(fileUpdateCoreFacade.uploadFileByUrl(url, "azp", null, null))
                .map();
    }
}
