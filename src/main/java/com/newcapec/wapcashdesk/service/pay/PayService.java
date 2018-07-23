package com.newcapec.wapcashdesk.service.pay;


import com.newcapec.wapcashdesk.service.vo.desk.PayWayReqVO;
import com.newcapec.wapcashdesk.service.vo.pay.GetPayWaysRspVO;

/**
 * @Title:
 * @ClassName: com.newcapec.wapcashdesk.service.pay.PayService.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-21 15:31
 * @version V1.0
 */
public interface PayService {


    /**
     * @Title: 获取支付方式列表
     * @methodName: payWays
     * @param payWay
     * @return com.newcapec.wapcashdesk.service.vo.pay.GetPayWaysRspVO
     * @Description:
     *
     * @author: 王延飞
     * @date: 2018-07-23 9:58
     */
    public GetPayWaysRspVO payWays(PayWayReqVO payWay);
}
