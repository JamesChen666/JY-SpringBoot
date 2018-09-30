package com.boot.controller.serve;

import com.boot.util.FileUploadTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 * 2018.8.30
 */
@Controller
@RequestMapping("/uploadflv")
public class UploadController {

    @ResponseBody
    @RequestMapping(value = "/upload", method={RequestMethod.POST,RequestMethod.GET})
    public Object upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
            HttpServletRequest request) {
        Map map = new HashMap();
        String message;
        Map entity;
        FileUploadTool fileUploadTool = new FileUploadTool();
        try {
            entity = fileUploadTool.createFile(multipartFile, request);
            if (entity != null) {
                message = "上传成功";
                map.put("entity", entity);
                map.put("result", message);
            } else {
                message = "上传失败";
                map.put("result", message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
