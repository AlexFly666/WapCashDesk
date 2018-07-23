package com.newcapec.wapcashdesk.controller;

import com.newcapec.wapcashdesk.constant.SysConstant;
import com.newcapec.wapcashdesk.service.pay.PayService;
import com.newcapec.wapcashdesk.service.vo.desk.PayWayReqVO;
import com.newcapec.wapcashdesk.service.vo.desk.PayWayRspVo;
import com.newcapec.wapcashdesk.service.vo.pay.GetPayWaysRspVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version V1.0
 * @Title: WAP收银台
 * @ClassName: com.newcapec.wapcashdesk.controller.PayController.java
 * @Description:
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date: 2018-07-20 16:36
 */
@Api(value = "收银台", description = "接口说明&测试")
@RestController
public class PayController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayService payService;

    /**
     * @param payWay
     * @return java.lang.String
     * @Title: 获取支付方式列表
     * @methodName: payWays
     * @Description: 业务系统支付方式列表 ∩ 支付服务支付方式列表【业务系统支付方式列表=NULL或者空时，展示全部】
     * @author: 王延飞
     * @date: 2018-07-21 11:46
     */
    @ApiOperation(value = "获取支付方式列表", notes = "业务系统支付方式列表 ∩ 支付服务支付方式列表")
    @PostMapping("/payWays")
    public GetPayWaysRspVO payWays(@RequestBody PayWayReqVO payWay) {

        GetPayWaysRspVO getPayWaysRspVO;

        // 参数校验
        if (payWay == null) {

            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.ERROR);
            getPayWaysRspVO.setReturnmsg("【获取支付方式列表】,请求参数为空");
            return getPayWaysRspVO;
        }

        getPayWaysRspVO = payService.payWays(payWay);

        return getPayWaysRspVO;
    }

    public static void main(String[] args) {

    }

}
