package com.khd.weixin;

import com.baidu.aip.ocr.AipOcr;
import com.khd.weixin.entity.*;
import com.khd.weixin.service.WxService;
import static org.assertj.core.api.Assertions.*;

import com.khd.weixin.util.Util;
import net.sf.json.JSONArray;
import org.junit.jupiter.api.Test;

import net.sf.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

//@SpringBootTest
class WeixinApplicationTests {
    //设置APPID/AK/SK
    public static final String APP_ID = "11519092";
    public static final String API_KEY = "q3TlGWWqEBG9uGvlFIBtpvY5";
    public static final String SECRET_KEY = "A14W5VRNG8my1GXYYAyNND0RjzBwxI8A";

    /**
     * 获取用户信息--前提是该用户已经关注了这个公众号
     */
    @Test
    public void testUserInfo() {
        String userInfo = WxService.getUserInfo("o88RTw0_WJvLEPqPBl1me346smvY");
        System.out.println(userInfo);
    }

    // 测试 通过生成临时二维码的ticket
    @Test
    public void testQrCode() {
        String qrCodeTicket = WxService.getQrCodeTicket();
        System.out.println(qrCodeTicket);
        // gQEk8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyTUF5Y1lBUDRkajMxQXVnUk51YzYAAgTGTbVeAwRYAgAA

        /*
         *说明：
         * 通过ticket换取二维码
         * 获取二维码ticket后，开发者可用ticket换取二维码图片。请注意，本接口无须登录态即可调用。
         *
         * 请求说明
         * HTTP GET请求（请使用https协议）https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET 提醒：TICKET记得进行UrlEncode
         *
         * 返回说明
         * ticket正确情况下，http 返回码是200，是一张图片，可以直接展示或者下载。
         */
    }

    @Test
    public void testUpload() {
        String file = "/Users/mac/Documents/1.png";
        String result = WxService.upload(file, "image");
        System.out.println(result);
        //aR3SKwGCFBCFYolZRVL8QvpBUjbFjqDghjQxQ5GembjRsVNmbyjO1E0SPAlduXn_
    }

    /**
     * 调用百度AI接口对本地文件进行文字识别
     */
    @Test
    public void testPic() {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port); // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port); // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = "/Users/mac/Documents/20200508101636.jpg";
        org.json.JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));
    }


    /**
     * 测试 删除菜单
     */
    @Test
    public void deleteButton() {
        String deleteUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
        deleteUrl = deleteUrl.replace("ACCESS_TOKEN", WxService.getAccessToken());
        String s = Util.get(deleteUrl);
        System.out.println(s);
    }


    /**
     * 测试 新建菜单对象
     */
    @Test
    public void testButton() {
        // 菜单对象
        Button btn = new Button();
        // 第一个一级菜单
        btn.getButton().add(new ClickButton("一级点击", "1"));
        // 第二个一级菜单
        btn.getButton().add(new ViewButton("一级跳转", "http://www.baidu.com"));
        // 创建第三个一级菜单
        SubButton sb = new SubButton("有子菜单");
        // 为第三个一级菜单增加子菜单
        sb.getSub_button().add(new PhotoOrAlbumButton("传图", "31"));
        sb.getSub_button().add(new ClickButton("点击", "32"));
        sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));
        // 加入第三个一级菜单
        btn.getButton().add(sb);
        // 转为json
        JSONObject jsonObject = JSONObject.fromObject(btn);
        System.out.println(jsonObject.toString());
    }

    @Test
    public void testGetToken() {
        String accessToken1 = WxService.getAccessToken();
        System.out.println(accessToken1);

        String accessToken2 = WxService.getAccessToken();
        System.out.println(accessToken2);

        assertThat(accessToken1).isEqualTo(accessToken2);

    }

}
