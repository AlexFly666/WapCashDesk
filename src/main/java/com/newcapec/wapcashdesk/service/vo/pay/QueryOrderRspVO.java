package com.newcapec.wapcashdesk.service.vo.pay;

import com.newcapec.wapcashdesk.service.vo.BaseRspVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Title: 支付服务-订单状态查询-返回结果
 * @ClassName: com.newcapec.wapcashdesk.service.vo.pay.QueryOrderRspVO.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-23 18:31
 * @version V1.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class QueryOrderRspVO extends BaseRspVO {


    /**
     *合作者编号
     */
    @ApiModelProperty(value = "合作者编号", required = true)
    private String partnerid;
    /**
     *商户编号
     */
    @ApiModelProperty(value = "商户编号", required = true)
    private String merchantno;

    /**
     *聚合支付平台机构编号
     */
    @ApiModelProperty(value = "聚合支付平台机构编号", required = true)
    private String orgcode;
    /**
     *订单编号
     */
    @ApiModelProperty(value = "订单编号", required = true)
    private String orderno;
    /**
     *商户订单编号
     */
    @ApiModelProperty(value = "商户订单编号", required = true)
    private String partnerjourno;
    /**
     *交易状态<br/>
     * SUCCESS：交易成功<br/>
     * USERPAYING：交易中<br/>
     * FAIL：交易失败<br/>
     */
    @ApiModelProperty(value = "", required = true)
    private String tradestate;
    /**
     *支付时间<br/>
     * 支付成功时必返，格式：yyyyMMddHHmmss
     */
    @ApiModelProperty(value = "支付时间", required = true)
    private String paytime;
    /**
     *交易金额<br/>
     * 单位：分
     */
    @ApiModelProperty(value = "交易金额", required = true)
    private String txamt;
    /**
     *商品名称
     */
    @ApiModelProperty(value = "商品名称", required = true)
    private String goodsname;
    /**
     *商品描述
     */
    @ApiModelProperty(value = "商品描述", required = true)
    private String goodsdesc;
    /**
     *交易分类
     */
    @ApiModelProperty(value = "交易分类", required = true)
    private String tradesort;
    /**
     *支付方式编号
     */
    @ApiModelProperty(value = "支付方式编号", required = true)
    private String paywayid;
    /**
     *支付方式描述
     */
    @ApiModelProperty(value = "支付方式描述", required = true)
    private String paywaydesc;
    /**
     *扩展字段
     */
    @ApiModelProperty(value = "扩展字段", required = true)
    private String attachdata;
    /**
     *异步通知地址
     */
    @ApiModelProperty(value = "异步通知地址", required = true)
    private String notifyurl;
    /**
     *前端跳转地址
     */
    @ApiModelProperty(value = "前端跳转地址", required = true)
    private String returnurl;
}
