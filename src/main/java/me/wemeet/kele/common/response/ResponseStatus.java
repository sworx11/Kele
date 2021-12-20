package me.wemeet.kele.common.response;

public enum ResponseStatus {

    SUCCESS(200, "成功"),
    ERROR(500, "系统请求错误"),
    UNKNOWN_ERROR(998,"未知异常"),
    SYSTEM_ERROR(999, "系统异常"),


    INSUFFICIENT_PERMISSION(403, "权限不足"),

    WARN(900, "失败"),
    REQUEST_PARAMETER_ERROR(102, "请求参数错误"),

    LOGIN_EXPIRE(201, "未登录或者登录失效"),
    LOGIN_CODE_ERROR(202, "登录验证码错误"),
    LOGIN_ERROR(203, "用户名不存在或密码错误"),
    LOGIN_USER_STATUS_ERROR(204, "用户状态不正确"),
    LOGOUT_ERROR(205, "退出失败，token不存在"),
    LOGIN_USER_NOT_EXIST(206, "该用户不存在"),
    LOGIN_USER_EXIST(207, "该用户已存在");

    public final int code;
    public final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
