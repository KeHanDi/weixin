package com.khd.weixin.controller;

import com.khd.weixin.service.WxService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author mac
 * @title: ApiController
 * @projectName weixin
 * @description: TODO
 * @date 2020-05-07 13:50
 */
@Controller
public class ApiController {

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("test");
        return "我们家婷宝最棒棒👍";
    }

    /**
     * 接口配置信息提交 会访问这个接口
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
         * timestamp	时间戳
         * nonce	    随机数
         * echostr	    随机字符串
         */
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        System.out.println("echostr: " + echostr);
        //校验证签名
        if(WxService.check(timestamp,nonce,signature)) {
            System.out.println("接入成功");
            PrintWriter out = response.getWriter();
            //原样返回echostr参数
            out.print(echostr);
            out.flush();
            out.close();
        }else {
            System.out.println("接入失败");
        }
    }

    @PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");
        //处理消息和事件推送
        Map<String, String> requestMap = WxService.parseRequest(request.getInputStream());
        System.out.println(requestMap);
        //准备回复的数据包
        String respXml = WxService.getRespose(requestMap);
        System.out.println(respXml);
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.flush();
        out.close();
    }

    @GetMapping("getUserInfo")
    public void getUserInfo() {


    }
}
