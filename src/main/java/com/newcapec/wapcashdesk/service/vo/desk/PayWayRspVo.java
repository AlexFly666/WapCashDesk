package com.newcapec.wapcashdesk.service.vo.desk;

import com.newcapec.wapcashdesk.service.vo.BaseRspVO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class PayWayRspVo extends BaseRspVO {

    /**
     * 合作者编号
     */
    private String partnerid;

}
