package com.zsf.utils;

import com.zsf.entity.ApiResponseData;
import com.zsf.entity.PageEntity;

public class ApiResponseUtils {

    public static ApiResponseData manangerRespnse(){
        ApiResponseData responseData = new ApiResponseData();


        return responseData;
    }

    public static ApiResponseData successRespnse(Object object){
        return successRespnse(object,null);
    }

    public static ApiResponseData successRespnse(Object object, PageEntity page){
        ApiResponseData responseData = new ApiResponseData();
        responseData.setCode(200);
        responseData.setMessage("");
        responseData.setPage(page);
        responseData.setData(object);
        return responseData;
    }

    public static ApiResponseData successRespnse(String message){
        ApiResponseData responseData = new ApiResponseData();
        responseData.setCode(200);
        responseData.setMessage(message);
        responseData.setPage(null);
        responseData.setData(null);
        return responseData;
    }

    public static ApiResponseData successRespnse(String message,Object object){
        ApiResponseData responseData = new ApiResponseData();
        responseData.setCode(200);
        responseData.setMessage(message);
        responseData.setPage(null);
        responseData.setData(object);
        return responseData;
    }



    /**
     * 执行返回错误
     * @param code
     * @param msg
     * @return
     */
    public static ApiResponseData failRespnse(int code,String msg){
        ApiResponseData responseData = new ApiResponseData();
        responseData.setCode(code);
        responseData.setMessage(msg);
        responseData.setPage(null);
        responseData.setData(null);
        return responseData;
    }

    /**
     * 登录错误返回
     * @param msg
     * @return
     */
    public static ApiResponseData failRespnseLogin(String msg){
        return failRespnse(10014,msg);
    }

    /**
     * 500错误返回
     * @param msg
     * @return
     */
    public static ApiResponseData failRespnse(String msg){
        return failRespnse(500,msg);
    }

}
