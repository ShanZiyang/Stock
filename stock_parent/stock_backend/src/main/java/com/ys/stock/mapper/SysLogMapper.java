package com.ys.stock.mapper;

import com.ys.stock.pojo.SysLog;
import com.ys.stock.vo.resp.LogRows;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author YS
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2023-06-15 18:06:09
* @Entity com.ys.stock.pojo.SysLog
*/
public interface SysLogMapper {


    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @return 操作日志集合
     */
    List<LogRows> getLogsBy(@Param("username") String username, @Param("operation") String operation, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<LogRows> getLogs();

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
