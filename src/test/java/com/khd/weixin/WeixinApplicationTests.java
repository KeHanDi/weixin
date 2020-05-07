package com.khd.weixin;

import com.khd.weixin.entity.*;
import com.khd.weixin.service.WxService;
import static org.assertj.core.api.Assertions.*;

import com.khd.weixin.util.Util;
import net.sf.json.JSONArray;
import org.junit.jupiter.api.Test;

import net.sf.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class WeixinApplicationTests {

    @Test
    public void deleteButton() {
        String deleteUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
        deleteUrl = deleteUrl.replace("ACCESS_TOKEN", WxService.getAccessToken());
        String s = Util.get(deleteUrl);
        System.out.println(s);
    }

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
        JSONArray button = jsonObject.getJSONArray("button");
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
