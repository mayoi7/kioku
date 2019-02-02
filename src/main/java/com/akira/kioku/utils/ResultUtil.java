package com.akira.kioku.utils;

import com.akira.kioku.enums.ResultEnum;
import com.akira.kioku.vo.ResultVo;

/**
 * 返回ResultVo类型的结果对象
 * @author Kripath
 * @date Created in 11:50 2019/2/2
 */
public class ResultUtil {

    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo success(Object data){
        return new ResultVo(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), data);
    }

    public static ResultVo error(Integer errorCode,String msg){
        return new ResultVo(errorCode, msg, null);
    }

    public static ResultVo error(){
        return error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
    }

    public static ResultVo error(String errorMsg){
        return error(ResultEnum.ERROR.getCode(), errorMsg);
    }
}
