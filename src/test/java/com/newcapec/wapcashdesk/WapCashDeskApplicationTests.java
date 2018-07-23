package com.newcapec.wapcashdesk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WapCashDeskApplicationTests {

    @Value("${pay.service.apiurl}")
    private String payServiceApiurl;


    /**
     * 接口名称 <获取支付方式>
     */
    @Value("${pay.service.payways.url}")
    private String payWaysURL;

    @Test
    public void valueTest() {

        System.out.println("【pay.service.apiurl】："+payServiceApiurl+payWaysURL);
    }

}
