package com.platform.modules.protocol.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdOrderEntity;


public interface ProtocolRefoundService extends IService<AdOrderEntity> {

    RestResponse refund(AdOrderEntity order,Integer pushSuccessNumber);
}
