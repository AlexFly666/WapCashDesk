package com.newcapec.wapcashdesk.service.vo.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @version V1.0
 * @Title: 支付服务-获取支付方式列表-返回结果-支付方式列表
 * @ClassName: com.newcapec.wapcashdesk.service.vo.pay.PayWays.java
 * @Description:
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date: 2018-07-21 16:28
 */
@Data

public class PayWays {


    /**
     * 支付方式编号
     */
    @ApiModelProperty(value = "支付方式编号",required=true)
    private String paywayid;
    /**
     * 支付方式分类
     */
    @ApiModelProperty(value = "支付方式分类",required=true)
    private String paywayclass;
    /**
     * 支付方式名称
     */
    @ApiModelProperty(value = "支付方式名称",required=true)
    private String paywayname;
    /**
     * 支付方式服务端名称<br/>
     * 当请求参数payserver=1时返回此字段
     */
    @ApiModelProperty(value = "支付方式服务端名称",required=true)
    private String payservername;
    /**
     * 支付方式图标
     */
    @ApiModelProperty(value = "支付方式图标",required=true)
    private String paywaylogo;
    /**
     * 支付方式描述
     */
    @ApiModelProperty(value = "",required=true)
    private String paywaydesc;
    /**
     * 支付方式排序编号
     */
    @ApiModelProperty(value = "支付方式排序编号",required=true)
    private String sortid;
    /**
     * 支付方式版本号
     */
    @ApiModelProperty(value = "支付方式版本号",required=true)
    private String version;
    /**
     * 支付账户列表
     */
    @ApiModelProperty(value = "支付账户列表",required=true)
    private List<PayAccount> payacclist;


}
