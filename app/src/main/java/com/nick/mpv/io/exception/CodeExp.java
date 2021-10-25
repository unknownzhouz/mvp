package com.nick.mpv.io.exception;

/**
 * 自定义错误code类型:注解写法
 */


public class CodeExp {

    /**
     * 客户端异常状态码
     */
    public static class ClientCode {
        public static final int UNKNOWN_ERROR = 1000000001;  // 未知错误
        public static final int NETWORK_ERROR = 1000000002;  // 网络错误
        public static final int RETRY_ERROR = 1000000003;    // 重试失败
        public static final int FORMAT_ERROR = 1000000004;   // 格式错误
    }

    /**
     * 服务端异常状态码
     */
    public final static class ServerCode {
        public static final int DEFAULT = -1000;               // 默认无效code
        public static final int SUCCESS = 0;                   // 请求成功

        public static final int INVALID_SIGN = 111;            // sign无效
        public static final int INVALID_CLIENT_ID = 222;       // client_id或client_secret无效，需要强制更新APP

        public static final int INVALID_ACCESS_TOKEN = 1111;   // access_token过期或无效，需要进行重新获取令牌
        public static final int INVALID_REFRESH_TOKEN = 2222;  // refresh_token过期，需要重新登录授权
        public static final int INVALID_AUTH_CODE = 3333;      // auth_code无效，需要重新登录授权
        public static final int INVALID_UUID = 4444;           // uuid无效
    }

}
