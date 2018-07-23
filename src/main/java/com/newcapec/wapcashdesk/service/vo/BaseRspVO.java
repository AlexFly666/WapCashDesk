package com.newcapec.wapcashdesk.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Title: 返回结果基类
 * @ClassName: com.newcapec.wapcashdesk.service.vo.BaseRspVO.java
 * @Description:
 *
 * @Copyright 2016-2018 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:  2018-07-21 11:51
 * @version V1.0
 */
@Data
@ApiModel
public class BaseRspVO {

    /**
     *  返回码，表示响应结果
     */
    @ApiModelProperty(value = "返回码",required=true)
    private String returncode;
    /**
     *  返回信息，表示响应信息
     */
    @ApiModelProperty(value = "返回信息",required=true)
    private String returnmsg;
}
