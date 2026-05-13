package com.ys.stock.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.ys.stock.mapper.SysLogMapper;
import com.ys.stock.pojo.SysLog;
import com.ys.stock.service.SysLogService;
import com.ys.stock.vo.req.QueryLogsReqVo;
import com.ys.stock.vo.resp.LogRows;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.R;
import com.ys.stock.vo.resp.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/15 9:39
 */
@Service("SysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;


    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysLog operLog) {
        sysLogMapper.insertOperlog(operLog);
    }


    /**
     * 查询系统操作日志集合
     *
     * @param vo 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public R<PageResult<LogRows>> getLogs(QueryLogsReqVo vo) {
        //设置分页参数
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<LogRows> logInfo;
        //根据传入的参数进行查询
        //
        if (Strings.isNullOrEmpty(vo.getUsername()) || Strings.isNullOrEmpty(vo.getOperation()) || Strings.isNullOrEmpty(vo.getStartTime()) || Strings.isNullOrEmpty(vo.getEndTime())) {
            logInfo = sysLogMapper.getLogs();
            System.out.println(logInfo);

        } else {
            //根据传入的条件查询所有的用户
            logInfo = sysLogMapper.getLogsBy(vo.getUsername(), vo.getOperation(), vo.getStartTime(), vo.getEndTime());
        }
        //判断得到数据后是否为空数据
        if (CollectionUtils.isEmpty(logInfo)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        //把得到的数据封装到PageInfo里面
        PageInfo<LogRows> pageInfo = new PageInfo<>(logInfo);
        //把数据封装到pageResult里面
        PageResult<LogRows> pageResult = new PageResult<>(pageInfo);
        return R.ok(pageResult);
    }


    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return sysLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysLog selectOperLogById(Long operId) {
        return sysLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        sysLogMapper.cleanOperLog();
    }




}
