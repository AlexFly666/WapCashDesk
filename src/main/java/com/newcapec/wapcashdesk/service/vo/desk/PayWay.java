package com.newcapec.wapcashdesk.service.vo.desk;

import lombok.Data;

/**
 * @Title: 支付方式列表
 * @ClassName: com.newcapec.wapcashdesk.service.vo.desk.PayWay.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-21 11:38
 * @version V1.0
 */
@Data
public class PayWay {

    /**
     * 支付方式编号
     */
    private  String paywayid;
    /**
     * 支付方式账户
     */
    private  String accountid;

}
