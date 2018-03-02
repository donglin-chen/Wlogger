package com.wottui.wlogger.common;

import com.wottui.wlogger.upload.LoggerDataHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 14:03
 */
@Controller
@RequestMapping("/wlogger/api")
public class LoggerGateway {

   // @Resource
    private LoggerDataHandler handler;

    //@Resource
    //private UIMongo ui;

    @RequestMapping("/gateway")
    @ResponseBody
    public Map<String, Object> dispatch(HttpServletRequest request, HttpServletResponse response) {
       /* String text = request.getParameter("text");
        String type = request.getParameter("apitype");
        Map<String, Object> ret = new HashMap<>();
        if (LOG_UPLOAD.name().equals(type))
            handler.upload(text);
        if (DASHBOARD.name().equals(type)) {
            ret.put("res", ui.logConsole(JSON.toJavaObject(JSON.parseObject(text), QueryVO.class)));
        }
        ret.put("status", 200);
        ret.put("return_msg", "success");
        response.setHeader("Access-Control-Allow-Origin", "*");
        return ret;*/
        return null;
    }
}
