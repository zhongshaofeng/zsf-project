package com.zsf.controller;

import com.sun.javaws.IconUtil;
import com.zsf.entity.ApiResponseData;
import com.zsf.utils.ApiResponseUtils;
import com.zsf.utils.OssApiUtil;
import com.zsf.utils.PostUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * @Author zhongshao
 * @Description 用户管理
 * @Date 2020/3/15 21:17
 */
@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping("imageUpload")
    public ApiResponseData imageUpload(@RequestBody MultipartFile file, HttpServletResponse response){
        PostUtils.cross(response);
        if(file ==null || file.isEmpty()){
            return ApiResponseUtils.successRespnse("图片为空");
        }
        String fileName=file.getOriginalFilename();
        String wei="";
        if(fileName.contains(".")){
            wei=fileName.substring(fileName.indexOf("."));
        }
        String path= OssApiUtil.upload(file,"images/"+new Date().getTime()+ UUID.randomUUID()+wei);
        System.out.println(path);
        if(path.length()<=0){
            return ApiResponseUtils.successRespnse("网络异常,上传错误");
        }
        return ApiResponseUtils.successRespnse("");
    }
}
