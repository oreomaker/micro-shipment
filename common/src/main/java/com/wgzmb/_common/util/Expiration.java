package com.wgzmb._common.util;

/**
 * @author RuKunHe(jom4ker @ aliyun.com)
 * @version com.whms.framework.common.constant 0.0.1
 */
public class Expiration {
    public final static long WEEK_ONE_EXPIRE = 60 * 60 * 24L * 7;
    /**
     * 过期时长为24小时，单位：秒
     */
    public final static long DAY_ONE_EXPIRE = 60 * 60 * 24L;
    /**
     * 过期时长为1小时，单位：秒
     */
    public final static long HOUR_ONE_EXPIRE = 60 * 60 * 1L;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1L;
}
