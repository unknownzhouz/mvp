package com.nick.mpv.io;


/**
 * 请求基础参数构建器类
 *
 * @author zhengz
 * @Date 2017/6/9 9:32
 */
public class M3Params {

    /**
     * 客户端请求参数
     */
    public final static class Request {
        /**
         * 设备类型 0=ios  1=android
         */
        public static final String DEV_TYPE = "X-DeviceType";

        /**
         * android 版本
         */
        public static final String APP_VERSION = "X-Version";

        /**
         * 时间戳
         */
        public static final String TIMESTAMP = "X-Timestamp";

        /**
         * 签名
         */
        public static final String SIGN = "X-Sign";

        /**
         * UUID
         */
        public static final String UUID = "X-Uuid";

        /**
         * accessToken
         */
        public static final String ACCESS_TOKEN = "X-AccessToken";

    }

    /**
     * 服务端响应参数
     */
    public final static class Response {
        /**
         * code
         */
        public static final String RESULT_ID = "code";
        /**
         * msg
         */
        public static final String RESULT_MSG = "msg";
        /**
         * data
         */
        public static final String RESULT_DATA = "data";
    }

    /**
     * 登录方式
     */
    public final static class SignType {
        /**
         * 验证码
         */
        public static final String CAPTCHA = "captcha";

        /**
         * 密码
         */
        public static final String PASSWORD = "password";

        /**
         * 微信
         */
        public static final String WEIXIN = "weixin";

        /**
         * 凭证
         */
        public static final String CREDENTIALS = "credentials";
    }
}
