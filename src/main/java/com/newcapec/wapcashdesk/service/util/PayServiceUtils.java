package com.newcapec.wapcashdesk.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newcapec.wapcashdesk.constant.SysConstant;
import com.newcapec.wapcashdesk.utils.encrypt.DesUtil;
import com.newcapec.wapcashdesk.utils.encrypt.RsaUtil;
import com.newcapec.wapcashdesk.utils.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @version V1.0
 * @Title: 调用支付服务工具类
 * @ClassName: com.newcapec.wapcashdesk.service.util.PayServiceUtils.java
 * @Description:
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date: 2018-07-20 18:00
 */
@Component
public class PayServiceUtils {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     *金融支付平台分配的合作伙伴ID
     */
    @Value("${pay.service.partnerid}")
    private String partnerid;

    /**
     *  云缴费平台(Wap收银台)私钥
     */
    @Value("${pay.service.privatekey}")
    private String privatekey;

    /**
     * 支付平台公钥
     */
    @Value("${pay.service.publickey}")
    private String publickey;

    /**
     * 支付平台DES密钥
     */
    @Value("${pay.service.deskey}")
    private String deskey;

    public JSONObject getContent(String url, String param) throws Exception {

        // 返回数据
        JSONObject returnJson = new JSONObject();

        // 校验
        if (StringUtils.isBlank(url)) {
            log.error("【调用支付服务异常】,请求地址为空");
            returnJson.put(SysConstant.RETURN_CODE, SysConstant.ERROR);
            returnJson.put(SysConstant.RETURN_MSG, "【调用支付服务异常】,请求地址为空");
            return returnJson;
        }

        if (StringUtils.isBlank(param)) {
            log.error("【调用支付服务异常】,业务数据为空");
            returnJson.put(SysConstant.RETURN_CODE, SysConstant.ERROR);
            returnJson.put(SysConstant.RETURN_MSG, "【调用支付服务异常】,业务数据为空");
            return returnJson;
        }

        log.info("【调用支付服务】请求地址：{}，业务数据：{}", url, param);


        //请求参数-签名
        String sign = null;
        try {
            sign = RsaUtil.sign(param, privatekey, SysConstant.CHARSET_UTF8);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【调用支付服务异常】,业务数据-签名失败，异常信息：{}",e);
            returnJson.put(SysConstant.RETURN_CODE, SysConstant.ERROR);
            returnJson.put(SysConstant.RETURN_MSG, "【调用支付服务异常】,业务数据-签名失败");
            return returnJson;
        }
        sign = java.net.URLEncoder.encode(sign, SysConstant.CHARSET_UTF8);

        // 请求参数-加密
        String jsonData = null;
        try {
            jsonData = DesUtil.encrypt(param, "0", deskey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【调用支付服务异常】,业务数据-加密失败，异常信息：{}",e);
            returnJson.put(SysConstant.RETURN_CODE, SysConstant.ERROR);
            returnJson.put(SysConstant.RETURN_MSG, "【调用支付服务异常】,业务数据-加密失败");
            return returnJson;
        }
        jsonData = java.net.URLEncoder.encode(jsonData, SysConstant.CHARSET_UTF8);
        String requestparam = "partnerid=" + partnerid + "&jsonData=" + jsonData + "&sign=" + sign;

        // 支付服务-返回值
        String remsg;
        try {
            remsg = HttpClientUtil.doPostJson(url, requestparam);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【失败调用支付服务接口】请求地址：{},请求参数：{},异常信息", url, requestparam,e);

            returnJson.put(SysConstant.RETURN_CODE, SysConstant.ERROR);
            returnJson.put(SysConstant.RETURN_MSG, "失败调用支付服务接口");
            return returnJson;
        }

        if (StringUtils.isEmpty(remsg)) {
            log.error("【调用支付服务异常】，返回值为空；请求地址：{},请求参数：{}", url, requestparam);
            returnJson.put(SysConstant.RETURN_CODE, SysConstant.ERROR);
            returnJson.put(SysConstant.RETURN_MSG, "【调用支付服务异常】，返回值为空");
            return returnJson;
        }

        log.info("【调用支付服务】请求地址：{},请求参数：{},返回值<未解密>：{}", url, requestparam,remsg);

        //支付服务-返回数据
        JSONObject payJson = JSON.parseObject(remsg);
        //支付服务-返回签名
        String paysign = payJson.getString("sign");
        //支付服务-返回加密数据
        String paybody = payJson.getString("body");
        //支付服务-返回解密数据
        try {
            paybody = DesUtil.decrypt(paybody, deskey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【调用支付服务异常】,返回数据-解密失败，异常信息：{}",e);
            returnJson.put(SysConstant.RETURN_CODE, SysConstant.ERROR);
            returnJson.put(SysConstant.RETURN_MSG, "【调用支付服务异常】,返回数据-解密失败");
            return returnJson;
        }
        log.info("【调用支付服务】请求地址：{},请求参数：{},返回值<解密后>：{}", url, requestparam,remsg);

        //验证签名
        boolean verify;
        try {
            verify = RsaUtil.verify(paybody, paysign, publickey, SysConstant.CHARSET_UTF8);
        } catch (Exception e) {
            e.printStackTrace();

            log.error("【调用支付服务异常】,返回数据-验签失败，异常信息：{}",e);
            returnJson.put(SysConstant.RETURN_CODE, SysConstant.ERROR);
            returnJson.put(SysConstant.RETURN_MSG, "【调用支付服务异常】,返回数据-验签失败");
            return returnJson;
        }

        if (verify) {
            returnJson = JSON.parseObject(paybody);
        } else {
            log.error("【调用支付服务异常】,返回数据-验签不通过");
            returnJson.put(SysConstant.RETURN_CODE, SysConstant.ERROR);
            returnJson.put(SysConstant.RETURN_MSG, "【调用支付服务异常】,返回数据-验签不通过");
            return returnJson;
        }

        return returnJson;
    }
}
