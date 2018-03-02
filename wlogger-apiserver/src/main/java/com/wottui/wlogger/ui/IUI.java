package com.wottui.wlogger.ui;

import com.wottui.wlogger.common.WLoggerInfo;
import com.wottui.wlogger.vo.QueryVO;

import java.util.List;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/30
 * @Time: 10:21
 */
public interface IUI {

    /**
     * @param vo
     * @return
     */
    List<WLoggerInfo> logConsole(QueryVO vo);

}
