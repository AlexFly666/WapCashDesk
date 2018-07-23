package com.newcapec.wapcashdesk.service.vo.pay;

import com.newcapec.wapcashdesk.service.vo.BaseRspVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Title: 支付服务-获取支付方式列表-返回结果
 * @ClassName: com.newcapec.wapcashdesk.service.vo.pay.GetPayWaysRspVO.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-21 16:29
 * @version V1.0
 */
@Data
@ToString(callSuper = true)
public class GetPayWaysRspVO extends BaseRspVO {

    /**
     * 支付方式列表
     */
    @ApiModelProperty(value = "支付方式列表",required=true)
    private List<PayWays> payways;
}
