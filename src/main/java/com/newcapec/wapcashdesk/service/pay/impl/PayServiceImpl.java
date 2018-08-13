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
import com.newcapec.wapcashdesk.service.vo.pay.PrepayOrderReqVO;
import com.newcapec.wapcashdesk.service.vo.pay.PrepayOrderRspVO;
import com.newcapec.wapcashdesk.service.vo.pay.QueryOrderReqVO;
import com.newcapec.wapcashdesk.service.vo.pay.QueryOrderRspVO;
import com.newcapec.wapcashdesk.utils.common.DateTimeUtils;
import com.newcapec.wapcashdesk.utils.common.SysUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
     * 接口名称 <预支付>
     */
    @Value("${pay.service.prepay.url}")
    private String prepayOrderURL;


    /**
     * 接口名称 <订单状态查询>
     */
    @Value("${pay.service.query.url}")
    private String queryOrderURL;


    /**
     * @param payWay
     * @return com.newcapec.wapcashdesk.service.vo.pay.GetPayWaysRspVO
     * @Title: 获取支付方式列表
     * @methodName: payWays
     * @Description:
     * @author: 王延飞
     * @date: 2018-07-23 9:58
     */
    @Override
    public GetPayWaysRspVO payWays(PayWayReqVO payWay) {

        GetPayWaysRspVO getPayWaysRspVO;


        // 参数校验
        if (payWay == null) {
            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.ERROR);
            getPayWaysRspVO.setReturnmsg("【获取支付方式列表】,请求参数为空");
            return getPayWaysRspVO;
        }
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

        /**
         * 版本号<br/>
         * 聚合支付平台会提供小于等于这个版本号的支付方式，用于客户端做过滤<br/>
         */
        String version = payWay.getVersion();
        version = StringUtils.isEmpty(version) ? "10.0" : version;
        /*if (StringUtils.isBlank(version)) {
            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.ERROR);
            getPayWaysRspVO.setReturnmsg("【获取支付方式列表】,请求参数-版本号为空");
            return getPayWaysRspVO;
        }*/

        // 请求参数
        GetPayWaysReqVO getPayWaysReqVO = new GetPayWaysReqVO();
        getPayWaysReqVO.setPartnerid(partnerid);
        getPayWaysReqVO.setMerchantno(merchantno);
        getPayWaysReqVO.setChannelno(channelno);
        getPayWaysReqVO.setNoncestr(DateTimeUtils.getTimeStamp());
        getPayWaysReqVO.setPayserver(SysConstant.STATUS_ONE);
        getPayWaysReqVO.setVersion(version);

        String payWaysParam = JSONObject.toJSONString(getPayWaysReqVO);

        JSONObject payWaysReturn = null;
        String url = new StringBuilder().append(payServiceApiURL).append(payWaysURL).toString().trim();
        try {
            payWaysReturn = payServiceUtils.getContent(url, payWaysParam);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("【获取支付方式列表-调用支付平台异常】,请求地址：{}，业务数据：{},异常信息：{}", url, payWaysParam, e);
            getPayWaysRspVO = new GetPayWaysRspVO();
            getPayWaysRspVO.setReturncode(SysConstant.ERROR);
            getPayWaysRspVO.setReturnmsg("【获取支付方式列表】,调用支付平台异常");
            return getPayWaysRspVO;

        }

        getPayWaysRspVO = JSONObject.parseObject(payWaysReturn.toJSONString(), GetPayWaysRspVO.class);
        String returncode = getPayWaysRspVO.getReturncode();
        if (!SysConstant.SUCCESS.equals(returncode)) {
            return getPayWaysRspVO;
        }

        // 业务系统支付方式列表 ∩ 支付服务支付方式列表【业务系统支付方式列表=NULL或者空时，展示全部】

        // 支付服务支付方式列表
        List<PayWays> payways = getPayWaysRspVO.getPayways();
        if (payways == null || payways.size() < 0) {
            return getPayWaysRspVO;
        }

        // 1.业务系统支付方式列表=NULL或者空时，展示全部
        List<PayWay> projectPaywayList = payWay.getProjectPaywayList();
        if (projectPaywayList == null || projectPaywayList.size() < 0) {
            return getPayWaysRspVO;
        }

        log.info("【获取支付方式列表】,业务系统支付方式列表：{}", projectPaywayList);

        // 2.业务系统支付方式列表!=空时，业务系统支付方式列表 ∩ 支付服务支付方式列表
        ArrayList<String> projectPayWayIdList = new ArrayList<>();
        for (PayWay projectPayway : projectPaywayList) {
            String paywayid = projectPayway.getPaywayid();
            projectPayWayIdList.add(paywayid);

        }


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
        log.info("【获取支付方式列表】,交集--业务系统支付方式列表 ∩ 支付服务支付方式列表：{}", filterCollection);
        PayWays[] payArr = new PayWays[filterCollection.size()];
        filterCollection.toArray(payArr);
        payways = Arrays.asList(payArr);

        Collections.sort(payways, new Comparator<PayWays>() {
            @Override
            public int compare(PayWays o1, PayWays o2) {
                return Integer.valueOf(o1.getSortid()) - Integer.valueOf(o2.getSortid());
            }
        });

        log.info("【获取支付方式列表】,排序--支付方式列表：{}", payways);

        getPayWaysRspVO = new GetPayWaysRspVO();
        getPayWaysRspVO.setReturncode(SysConstant.SUCCESS);
        getPayWaysRspVO.setReturnmsg("成功获取支付方式列表");
        getPayWaysRspVO.setPayways(payways);
        return getPayWaysRspVO;


    }


    /**
     * @param orderReq
     * @return com.newcapec.wapcashdesk.service.vo.pay.PrepayOrderRspVO
     * @Title: 预支付
     * @methodName: prepayOrder
     * @Description:
     * @author: 王延飞
     * @date: 2018-07-23 17:07
     */
    @Override
    public PrepayOrderRspVO prepayOrder(PrepayOrderReqVO orderReq) {


        PrepayOrderRspVO orderRsp;

        //  参数校验
        if (orderReq == null) {
            orderRsp = new PrepayOrderRspVO();
            orderRsp.setReturncode(SysConstant.ERROR);
            orderRsp.setReturnmsg("【预支付】,请求参数为空");
            return orderRsp;
        }

        /**
         * 商户编号
         */
        String merchantno = orderReq.getMerchantno();
        if (StringUtils.isBlank(merchantno)) {
            orderRsp = new PrepayOrderRspVO();
            orderRsp.setReturncode(SysConstant.ERROR);
            orderRsp.setReturnmsg("【预支付】,请求参数-商户编号为空");
            return orderRsp;
        }

        /**
         * 商户订单编号
         */
        String journo = orderReq.getJourno();
        if (StringUtils.isBlank(journo)) {
            orderRsp = new PrepayOrderRspVO();
            orderRsp.setReturncode(SysConstant.ERROR);
            orderRsp.setReturnmsg("【预支付】,请求参数-商户订单编号为空");
            return orderRsp;
        }
        /**
         *支付方式编号
         */
        String paywayid = orderReq.getPaywayid();
        if (StringUtils.isBlank(paywayid)) {
            orderRsp = new PrepayOrderRspVO();
            orderRsp.setReturncode(SysConstant.ERROR);
            orderRsp.setReturnmsg("【预支付】,请求参数-支付方式编号为空");
            return orderRsp;
        }
        /**
         *支付渠道编号
         */
        String channelno = orderReq.getChannelno();
        if (StringUtils.isBlank(channelno)) {
            orderRsp = new PrepayOrderRspVO();
            orderRsp.setReturncode(SysConstant.ERROR);
            orderRsp.setReturnmsg("【预支付】,请求参数-支付渠道编号为空");
            return orderRsp;
        }

        orderReq.setNoncestr(DateTimeUtils.getTimeStamp());
        try {
            orderReq.setIp(SysUtils.getIpAddress());
        } catch (UnknownHostException e) {
            log.warn("【预支付】,获取本机IP异常,异常信息：{}", e);
            e.printStackTrace();
            orderRsp = new PrepayOrderRspVO();
            orderRsp.setReturncode(SysConstant.ERROR);
            orderRsp.setReturnmsg("【预支付】,获取本机IP异常");
            return orderRsp;
        }

        String prepayOrderParam = JSONObject.toJSONString(orderReq);

        JSONObject prepayOrderReturn = null;
        String url = new StringBuilder().append(payServiceApiURL).append(prepayOrderURL).toString().trim();
        try {
            prepayOrderReturn = payServiceUtils.getContent(url, prepayOrderParam);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("【预支付-调用支付平台异常】,请求地址：{}，业务数据：{},异常信息：{}", url, prepayOrderParam, e);
            orderRsp = new PrepayOrderRspVO();
            orderRsp.setReturncode(SysConstant.ERROR);
            orderRsp.setReturnmsg("【获取支付方式列表】,调用支付平台异常");
            return orderRsp;
        }

        orderRsp = JSONObject.parseObject(prepayOrderReturn.toJSONString(), PrepayOrderRspVO.class);

        return orderRsp;
    }

    /**
     * @param queryReq
     * @return com.newcapec.wapcashdesk.service.vo.pay.QueryOrderRspVO
     * @Title: 订单状态查询
     * @methodName: queryOrder
     * @Description:
     * @author: 王延飞
     * @date: 2018-07-23 18:47
     */
    @Override
    public QueryOrderRspVO queryOrder(QueryOrderReqVO queryReq) {

        QueryOrderRspVO queryRsp = null;

        // 参数校验
        if (queryReq == null) {
            queryRsp = new QueryOrderRspVO();
            queryRsp.setReturncode(SysConstant.ERROR);
            queryRsp.setReturnmsg("【订单状态查询】,请求参数为空");
            return queryRsp;
        }

        String orderno = queryReq.getOrderno();
        if (StringUtils.isBlank(orderno)) {
            queryRsp = new QueryOrderRspVO();
            queryRsp.setReturncode(SysConstant.ERROR);
            queryRsp.setReturnmsg("【订单状态查询】,请求参数-订单编号为空");
            return queryRsp;
        }
        String ordernotype = queryReq.getOrdernotype();
        if (StringUtils.isBlank(ordernotype)) {
            queryRsp = new QueryOrderRspVO();
            queryRsp.setReturncode(SysConstant.ERROR);
            queryRsp.setReturnmsg("【订单状态查询】,请求参数-订单编号类型为空");
            return queryRsp;
        }
        queryReq.setNoncestr(DateTimeUtils.getTimeStamp());

        String queryOrderParam = JSONObject.toJSONString(queryReq);
        JSONObject prepayOrderReturn;
        String url = new StringBuilder().append(payServiceApiURL).append(queryOrderURL).toString().trim();
        try {
            prepayOrderReturn = payServiceUtils.getContent(url, queryOrderParam);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("【订单状态查询-调用支付平台异常】,请求地址：{}，业务数据：{},异常信息：{}", url, queryOrderParam, e);
            queryRsp = new QueryOrderRspVO();
            queryRsp.setReturncode(SysConstant.ERROR);
            queryRsp.setReturnmsg("【订单状态查询】,调用支付平台异常");
            return queryRsp;
        }

        queryRsp = JSONObject.parseObject(prepayOrderReturn.toJSONString(), QueryOrderRspVO.class);

        return queryRsp;
    }

}
