package com.newcapec.wapcashdesk.service.pay.impl;


import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.newcapec.wapcashdesk.constant.SysConstant;
import com.newcapec.wapcashdesk.service.pay.PayService;
import com.newcapec.wapcashdesk.service.util.PayServiceUtils;
import com.newcapec.wapcashdesk.service.vo.desk.PayWay;
import com.newcapec.wapcashdesk.service.vo.desk.PayWayReqVO;
import com.newcapec.wapcashdesk.service.vo.pay.GetPayWaysReqVO;
import com.newcapec.wapcashdesk.service.vo.pay.GetPayWaysRspVO;
import com.newcapec.wapcashdesk.service.vo.pay.PayWays;
import com.newcapec.wapcashdesk.utils.common.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @version V1.0
 * @Title:
 * @ClassName: com.newcapec.wapcashdesk.service.pay.impl.PayServiceImpl.java
 * @Description:
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date: 2018-07-21 15:14
 */
@Service
public class PayServiceImpl implements PayService {

    /**
     * 调用支付服务工具类
     */
    @Autowired
    private PayServiceUtils payServiceUtils;


    /**
     * 项目地址 <支付服务>
     */
    @Value("${pay.service.apiurl}")
    private String payServiceApiURL;

    /**
     * 接口名称 <获取支付方式>
     */
    @Value("${pay.service.payways.url}")
    private String payWaysURL;


    /**
     * @Title: 获取支付方式列表
     * @methodName: payWays
     * @param payWay
     * @return com.newcapec.wapcashdesk.service.vo.pay.GetPayWaysRspVO
     * @Description:
     *
     * @author: 王延飞
     * @date: 2018-07-23 9:58
     */
    @Override
    public GetPayWaysRspVO payWays(PayWayReqVO payWay) {

        GetPayWaysRspVO getPayWaysRspVO;


        // 参数校验
        if (StringUtils.isBlank(payServiceApiURL)) {
            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.ERROR);
            getPayWaysRspVO.setReturnmsg("【获取支付方式列表】,支付服务-项目地址为空");
            return getPayWaysRspVO;
        }
        if (StringUtils.isBlank(payWaysURL)) {
            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.ERROR);
            getPayWaysRspVO.setReturnmsg("【获取支付方式列表】,支付服务-接口名称为空");
            return getPayWaysRspVO;
        }
        /**
         * 合作者编号
         */
        String partnerid = payWay.getPartnerid();

        if (StringUtils.isBlank(partnerid)) {
            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.ERROR);
            getPayWaysRspVO.setReturnmsg("【获取支付方式列表】,请求参数-合作者编号为空");
            return getPayWaysRspVO;
        }
        /**
         * 商户编号
         */
        String merchantno = payWay.getMerchantno();
        if (StringUtils.isBlank(merchantno)) {
            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.ERROR);
            getPayWaysRspVO.setReturnmsg("【获取支付方式列表】,请求参数-商户编号为空");
            return getPayWaysRspVO;
        }
        /**
         * 渠道编号
         */
        String channelno = payWay.getChannelno();
        if (StringUtils.isBlank(channelno)) {
            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.ERROR);
            getPayWaysRspVO.setReturnmsg("【获取支付方式列表】,请求参数-渠道编号为空");
            return getPayWaysRspVO;
        }


        // 请求参数
        GetPayWaysReqVO getPayWaysReqVO = new GetPayWaysReqVO();
        getPayWaysReqVO.setPartnerid(partnerid);
        getPayWaysReqVO.setMerchantno(merchantno);
        getPayWaysReqVO.setChannelno(channelno);
        getPayWaysReqVO.setNoncestr(DateTimeUtils.getTimeStamp());
        getPayWaysReqVO.setPayserver(SysConstant.STATUS_ONE);

        String payWaysParam = JSONObject.toJSONString(getPayWaysReqVO);

        JSONObject payWaysReturn = null;
        try {
            String url = new StringBuilder().append(payServiceApiURL).append(payWaysURL).toString().trim();
            payWaysReturn = payServiceUtils.getContent(url, payWaysParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getPayWaysRspVO = JSONObject.parseObject(payWaysReturn.toJSONString(), GetPayWaysRspVO.class);
        String returncode = getPayWaysRspVO.getReturncode();
        if (!SysConstant.SUCCESS.equals(returncode)) {
            return getPayWaysRspVO;
        }

        // 业务系统支付方式列表 ∩ 支付服务支付方式列表【业务系统支付方式列表=NULL或者空时，展示全部】

        // 1.业务系统支付方式列表=NULL或者空时，展示全部
        List<PayWay> projectPaywayList = payWay.getProjectPaywayList();
        if (projectPaywayList == null || projectPaywayList.size() < 0) {
            return getPayWaysRspVO;
        }



        // 2.业务系统支付方式列表!=空时，业务系统支付方式列表 ∩ 支付服务支付方式列表
        ArrayList<String> projectPayWayIdList = new ArrayList<>();
        for (PayWay projectPayway : projectPaywayList) {
            String paywayid = projectPayway.getPaywayid();
            projectPayWayIdList.add(paywayid);

        }
        List<PayWays> payways = getPayWaysRspVO.getPayways();


        if (payways != null && payways.size() > 0) {
            // 过滤--业务系统支付方式列表 ∩ 支付服务支付方式列表 :
            Collection<PayWays> filterCollection =
                    Collections2.filter(payways, new Predicate<PayWays>() {
                        @Override
                        public boolean apply(PayWays input) {
                            String paywayid = input.getPaywayid();
                            boolean contains = projectPayWayIdList.contains(paywayid);
                            if (contains) {
                                return true;
                            } else {
                                return false;
                            }

                        }
                    });
            PayWays[] payArr = new PayWays[filterCollection.size()];
            filterCollection.toArray(payArr);
            payways = Arrays.asList(payArr);
            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.SUCCESS);
            getPayWaysRspVO.setReturnmsg("成功获取支付方式列表");
            getPayWaysRspVO.setPayways(payways);
            return getPayWaysRspVO;

        }


        return getPayWaysRspVO;
    }

}
