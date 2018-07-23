package com.newcapec.wapcashdesk.service.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Title: 支付服务-预支付-请求参数
 * @ClassName: com.newcapec.wapcashdesk.service.vo.pay.PrepayOrderReqVO.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-23 16:11
 * @version V1.0
 */
@Data
@ApiModel
public class PrepayOrderReqVO {

    /**
     * 商户编号
     */
    @ApiModelProperty(value = "商户编号", required = true)
    private String merchantno;
    /**
     * 商户订单编号
     */
    @ApiModelProperty(value = "商户订单编号", required = true)
    private String journo;
    /**
     *支付方式编号
     */
    @ApiModelProperty(value = "支付方式编号", required = true)
    private String paywayid;
    /**
     *支付渠道编号
     */
    @ApiModelProperty(value = "支付渠道编号")
    private String channelno;
    /**
     *支付账户编号
     */
    @ApiModelProperty(value = "支付账户编号")
    private String accountid;
    /**
     * 终端ip<br/>
     * 微信相关支付方式时必填
     */
    @ApiModelProperty(value = "终端ip")
    private String ip;
    /**
     *微信openid<br/>
     * 微信相关支付方式时必填(微信APP除外)
     */
    @ApiModelProperty(value = "微信openid")
    private String openid;
    /**
     * 设备类型<br/>
     * 翼支付时必传
     */
    @ApiModelProperty(value = "设备类型")
    private String device;
    /**
     * 聚合支付平台授权token<br/>
     * ZF201接口返回该值，以下支付方式时必传：
     * 虚拟卡支付，
     * 招行一网通支付，
     * 中原银行二类户
     */
    @ApiModelProperty(value = "聚合支付平台授权token")
    private String token;
    /**
     * 支付场景<br/>
     * 微信H5支付必传
     */
    @ApiModelProperty(value = "支付场景")
    private String senceno;
    /**
     * 随机字符串
     */
    @ApiModelProperty(value = "随机字符串")
    private String noncestr;

}
