package com.ys.stock.vo.resp;

public enum ResponseCode {
    ERROR(0,"操作失败"),
    SUCCESS(1,"操作成功"),
    DATA_ERROR(400,"参数异常"),
    NEED_LOGIN(401, "需要登录后操作"),
    NO_RESPONSE_DATA(404,"无响应数据"),
    SYSTEM_ERROR(500, "出现错误"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    SYSTEM_VERIFY_CODE_NOT_EMPTY(5001,"验证码不能为空"),
    SYSTEM_VERIFY_CODE_ERROR(5002,"验证码错误"),
    SYSTEM_USERNAME_NOT_EMPTY(5003,"账号不能为空"),
    SYSTEM_USERNAME_NOT_EXISTS(5004,"账号不存在"),
    SYSTEM_USERNAME_EXPIRED(5005,"账户过期"),
    SYSTEM_USERNAME_LOCKED(5006,"账户被锁"),
    SYSTEM_USERNAME_DISABLED(5007,"账户被禁用"),
    SYSTEM_PASSWORD_ERROR(5008,"账号或密码错误"),
    SYSTEM_PASSWORD_EXPIRED(5009,"密码过期"),
    SYSTEM_USERNAME_OFFLINE(5010,"已下线，请重新登录"),
    ACCOUNT_EXISTS_ERROR(5012,"该账号已存在"),
    TOKEN_ERROR(2,"用户未登录，请先登录"),
    NOT_PERMISSION(3,"没有权限访问该资源"),
    TOKEN_NOT_NULL(-1,"token 不能为空"),
    TOKEN_NO_AVAIL(-1,"token无效或过期"),
    TOKEN_PAST_DUE(-1,"登录失效,请重新登录"),
    TOKEN_EXISTS(-1,"账号异地登录，你已被迫退出"),
    OPERATION_MENU_PERMISSION_CATALOG_ERROR(100,"操作后的菜单类型是目录，所属菜单必须为默认顶级菜单或者目录"),
    OPERATION_MENU_PERMISSION_MENU_ERROR(101,"操作后的菜单类型是菜单，所属菜单必须为目录类型"),
    OPERATION_MENU_PERMISSION_BTN_ERROR(102,"操作后的菜单类型是按钮，所属菜单必须为菜单类型"),
    OPERATION_MENU_PERMISSION_URL_NOT_NULL(103,"菜单权限的url不能为空"),
    OPERATION_MENU_PERMISSION_URL_PERMS_NULL(104,"菜单权限的标识符不能为空"),
    OPERATION_MENU_PERMISSION_URL_METHOD_NULL(105,"菜单权限的请求方式不能为空"),
    OPERATION_MENU_PERMISSION_URL_CODE_NULL(106,"菜单权限的按钮标识不能为空"),
    OPERATION_MENU_PERMISSION_UPDATE(107,"操作的菜单权限存在子集关联不允许变更"),
    ROLE_PERMISSION_RELATION(108, "该菜单权限存在子集关联，不允许删除"),
    OLD_PASSWORD_ERROR(109,"旧密码不匹配"),
    GET_DATA_FAIL(110,"获取股票失败"),
    GET_API_FAIL(111,"接口服务调用出错"),
    TRAIN_STOCK_FAIL(112,"训练模型失败"),
    PREDICT_STOCK_FAIL(113,"股票预测失败"),
    ROLE_FAIL(114,"角色权限更新失败"),
    UPLOAD_IMG_FAIL(115,"图片格式错误,请上传JPG/JPEG/PNG 格式!"),
    UPLOAD_FAIL(116,"图片地址更新失败"),
    UPLOAD_SUCCESS(200,"图片上传成功")
    ;

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
