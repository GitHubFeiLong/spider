package com.cfl.jd.constant;

/**
 * 缓存相关常量,最优格式(使用时不需要拼接格式符号) + sessionID
 * @ClassName CacheConsts
 * @Author msi
 * @Date 2020/6/9 11:46
 * @Version 1.0
 */
public class CacheConsts {
    /**
     * 注册账号，保存验证码的到redis(+sessionID)
     * 保存redis格式：jd:user:regist:captcha:sessionID
     * 项目名:模块:业务:用途:
     */
    public static final String REGIST_CAPTCHA = "jd:user:regist:captcha:";

    /**
     * 设置该key到redis中，防止重复提交(+路径:sessionID)
     * 保存格式：jd:all:repeat_submit:路径:sessionID
     * 项目名:所有模块:防止重复提交:路径:sessionID
     */
    public static final String REPEAT_SUBMIT = "jd:all:repeat_submit:";

}
