package com.wottui.wlogger.upload;

import com.wottui.wlogger.common.WLoggerInfo;
import com.wottui.wlogger.vo.QueryVO;

import java.util.List;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 14:10
 */
public interface ILoggerDataHandler {
    /**
     * 日志上传
     *
     * @param text
     */
    void upload(String text);

    /**
     * 日志控制台
     *
     * @param vo
     * @return
     */
    List<WLoggerInfo> logConsole(QueryVO vo);
}
