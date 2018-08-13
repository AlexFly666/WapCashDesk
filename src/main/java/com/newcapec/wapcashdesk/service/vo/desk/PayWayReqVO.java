package com.newcapec.wapcashdesk.service.vo.desk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PayWayReqVO {

    /**
     * 合作者编号
     */
    @ApiModelProperty(value = "合作者编号",required=true)
    private String partnerid;

    /**
     * 商户编号
     */
    @ApiModelProperty(value = "商户编号",required=true)
    private String merchantno;
    /**
     * 渠道编号
     */
    @ApiModelProperty(value = "渠道编号",required=true)
    private String channelno;


    /**
     * 版本号<br/>
     * 聚合支付平台会提供小于等于这个版本号的支付方式，用于客户端做过滤<br/>
     */
    @ApiModelProperty(value = "版本号",required=true)
    private String version;


    /**
     * 业务系统-支付方式列表
     */
    @ApiModelProperty(value = "业务系统-支付方式列表")
    private List<PayWay> projectPaywayList;
}
