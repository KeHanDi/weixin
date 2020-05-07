package com.khd.weixin;

import com.khd.weixin.service.WxService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class WeixinApplicationTests {

    @Test
    public void contextLoads() {
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
