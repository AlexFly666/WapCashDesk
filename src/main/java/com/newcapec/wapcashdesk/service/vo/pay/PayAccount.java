package com.newcapec.wapcashdesk.service.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class PayAccount {

    /**
     * 支付账户编号
     */
    @ApiModelProperty(value = "支付账户编号",required=true)
    private String accountname;
    /**
     * 支付账户名称
     */
    @ApiModelProperty(value = "支付账户名称",required=true)
    private String accountid;
}
