package com.newcapec.wapcashdesk.service.vo.pay;

import lombok.Data;

/**
 * @Title: 支付服务-获取支付方式列表-返回结果-支付账户列表
 * @ClassName: com.newcapec.wapcashdesk.service.vo.pay.PayAccount.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-21 16:30
 * @version V1.0
 */
@Data
public class PayAccount {

    /**
     * 支付账户编号
     */

    private String accountname;
    /**
     * 支付账户名称
     */
    private String accountid;
}
