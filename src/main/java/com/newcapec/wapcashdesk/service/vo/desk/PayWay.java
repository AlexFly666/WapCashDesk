package com.newcapec.wapcashdesk.service.vo.desk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class PayWay {

    /**
     * 支付方式编号
     */
    @ApiModelProperty(value = "支付方式编号",required=true)
    private  String paywayid;
    /**
     * 支付方式账户
     */
    @ApiModelProperty(value = "支付方式账户",required=true)
    private  String accountid;

}
