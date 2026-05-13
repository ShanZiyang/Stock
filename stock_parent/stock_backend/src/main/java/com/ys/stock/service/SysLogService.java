package com.ys.stock.service;

import com.ys.stock.pojo.SysLog;
import com.ys.stock.vo.req.QueryLogsReqVo;
import com.ys.stock.vo.resp.LogRows;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.R;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/15 9:39
 */
public interface SysLogService {


    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param vo 操作日志对象
     * @return 操作日志集合
     */
    R<PageResult<LogRows>> getLogs(QueryLogsReqVo vo);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public SysLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();
}
