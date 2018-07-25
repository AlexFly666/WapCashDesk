package com.newcapec.wapcashdesk.service.vo.pay;

import com.newcapec.wapcashdesk.service.vo.BaseRspVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @version V1.0
 * @Title: 支付服务-预支付-返回结果
 * @ClassName: com.newcapec.wapcashdesk.service.vo.pay.PrepayOrderRspVO.java
 * @Description:
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date: 2018-07-23 16:11
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class PrepayOrderRspVO extends BaseRspVO {

    /**
     * 订单编号<支付平台>
     */
    @ApiModelProperty(value = "订单编号<支付平台>", required = true)
    private String orderno;
    /**
     * 呼起三方支付的参数
     */
    @ApiModelProperty(value = "呼起三方支付的参数", required = true)
    private String paymsg;
}
