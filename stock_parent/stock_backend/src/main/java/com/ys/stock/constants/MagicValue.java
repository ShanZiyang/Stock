package com.ys.stock.constants;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/14 8:46
 * @description 定义魔法值常量
 */
public class MagicValue {
    //SQL语句执行成功常量
    public static final int SUCCESSVAL = 1;
    //SQL语句返回为0
    public static final int SQL_ZERO = 0;
    //用户正常状态
    public static final int STATUS_OPEN = 1;
    //用户锁定状态
    public static final int STATUS_CLOSE = 2;
    //角色、权限正常状态
    public static final int STATUS_NORMAL = 1;
    //角色、权限非正常状态
    public static final int STATUS_NOT_NORMAL = 0;
    //未删除
    public static final int STATUS_NOT_DEL = 1;
    //已删除
    public static final int STATUS_DEL = 0;
    //重置密码
    public static final String PASSWORD = "123456";

    //时间
    public static final String TIME = "2022-01-05 09:47:00";

    //预测股票权限id
    public static final String PERMISSION_ID = "1692364532019720192";

    //模拟时间
    public static final String MOCKDATE = "2023-01-03 11:15:00";

}
