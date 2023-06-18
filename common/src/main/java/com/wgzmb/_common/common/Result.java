package com.wgzmb._common.common;

import lombok.Data;

/**
 * @author RuKunHe(jom4ker @ aliyun.com)
 * @version com.wgzmb.common 0.0.1
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(Constant.CODE_OK);
        result.setMsg(Constant.MSG_OK);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(T data) {
        Result<T> result = new Result<>();
        result.setCode(Constant.CODE_ERROR);
        result.setMsg(Constant.MSG_ERROR);
        result.setData(data);
        return result;
    }
}
