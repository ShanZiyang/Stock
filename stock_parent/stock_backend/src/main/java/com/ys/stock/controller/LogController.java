package com.ys.stock.controller;

import com.ys.stock.annotation.Log;
import com.ys.stock.enums.BusinessType;
import com.ys.stock.service.SysLogService;
import com.ys.stock.vo.req.QueryLogsReqVo;
import com.ys.stock.vo.resp.LogRows;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/15 9:37
 */
@RestController
@RequestMapping("/api")
@Api(tags = "日志控制")
public class LogController {
    @Autowired
    private SysLogService sysLogService;

    @ApiOperation("获取日志列表")
    @ApiImplicitParam(name="vo",value = "查询日志VO")
    @PostMapping("/logs")
    public R<PageResult<LogRows>> getLogs(@RequestBody QueryLogsReqVo vo) {
        return sysLogService.getLogs(vo);
    }

    @ApiOperation("删除日志")
    @ApiImplicitParam(name = "logIds",value = "日志id")
    @DeleteMapping("/log")
    @Log(title = "删除日志",businessType = BusinessType.DELETE)
    public R<String> deleteLog(@RequestBody Long[] operId){
        int res = sysLogService.deleteOperLogByIds(operId);
        return R.ok(String.valueOf(res));
    }


}
