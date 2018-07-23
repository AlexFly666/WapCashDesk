package com.newcapec.wapcashdesk.service.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Title: 支付服务-订单状态查询-请求参数
 * @ClassName: com.newcapec.wapcashdesk.service.vo.pay.QueryOrderReqVO.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-23 18:31
 * @version V1.0
 */
@Data
@ApiModel
public class QueryOrderReqVO {

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", required = true)
    private String orderno;
    /**
     * 订单编号类型<br/>
     * 1：商户订单编号<br/>
     * 2：聚合支付平台订单编号<br/>
     */
    @ApiModelProperty(value = "订单编号类型", required = true)
    private String ordernotype;
    /**
     *商户编号<br/>
     * ordernotype=1时必传
     */
    @ApiModelProperty(value = "商户编号" )
    private String merchantno;
    /**
     * 随机字符串
     */
    @ApiModelProperty(value = "随机字符串")
    private String noncestr;

}
