package com.newcapec.wapcashdesk.service.vo.pay;

import lombok.Data;
/**
 * @Title: 支付服务-获取支付方式列表-请求参数
 * @ClassName: com.newcapec.wapcashdesk.service.vo.pay.GetPayWaysReqVO.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-21 16:28
 * @version V1.0
 */
@Data
public class GetPayWaysReqVO {

    /**
     * 合作者编号
     */
    private String partnerid;

    /**
     * 商户编号
     */
    private String merchantno;
    /**
     * 渠道编号<br/>
     * 多个用|隔开
     */
    private String channelno;
    /**
     * 版本号
     */
    private String version;
    /**
     * 是否获取全部
     */
    private String paywaytype;
    /**
     * 是否获取支付方式服务端名称<br/>
     * 固定值1：是
     */
    private String payserver;
    /**
     * 随机字符串
     */
    private String noncestr;


}
