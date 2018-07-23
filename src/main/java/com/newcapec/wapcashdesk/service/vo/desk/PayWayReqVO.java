package com.newcapec.wapcashdesk.service.vo.desk;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PayWayReqVO {

    /**
     * 合作者编号
     */
    private String partnerid;

    /**
     * 商户编号
     */
    private String merchantno;
    /**
     * 渠道编号
     */
    private String channelno;


    /**
     * 业务系统-支付方式列表
     */
    private List<PayWay> projectPaywayList;
}
