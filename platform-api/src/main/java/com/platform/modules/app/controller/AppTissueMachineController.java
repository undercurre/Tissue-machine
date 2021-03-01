package com.platform.modules.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.Constant;
import com.platform.common.utils.ObjectToMapUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.*;

import com.platform.modules.ad.service.*;
import com.platform.modules.protocol.entity.TissueProtocol;
import com.platform.modules.protocol.utils.HttpPostUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/tissueMachine")
@Api(tags = "AppTissueMachineController|APP纸巾机接口")
public class AppTissueMachineController {
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdGoodsService adGoodsService;
    @Autowired
    private AdMachineStockService adMachineStockService;
    @Autowired
    private AdPatchService adPatchService;
    @Autowired
    private AdOrderTissueService adOrderTissueService;

    /**
     * 获取纸巾机列表
     *
     * @return RestResponse
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取纸巾机列表", notes = "获取纸巾机列表")
    @IgnoreAuth
    public RestResponse getTissueMachine() {
        List<AdTissueMachineEntity> adMachine = adTissueMachineService.list();
        return RestResponse.success().put("data", adMachine);
    }

    /**
     * 根据ID获取纸巾机详情信息
     *
     * @param machineId machineId
     * @return RestResponse
     */
    @IgnoreAuth
    @GetMapping("/queryById")
    @ApiOperation(value = "获取纸巾机详情", notes = "获取纸巾机详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "machineId", value = "机器ID", required = true, dataType = "int", example = "1")
    })
    public RestResponse getMachineDetailById(String machineId) {
        AdTissueMachineEntity adMachine = adTissueMachineService.getById(machineId);
        List<AdPatchEntity> adPatch = adPatchService.list(new QueryWrapper<AdPatchEntity>()
                .eq("TYPE", Constant.PatchType.XDTP.getValue())
                .last("LIMIT 0,3")
                .eq("AVAIL",1)
        );
        if(adPatch.size()>0) {
            adMachine.setOrderPatch(adPatch);
        }
        return RestResponse.success().put("data", adMachine);
    }


    /**
     * 根据Sn码获取纸巾机详情信息
     *
     * @param sn sn码
     * @return RestResponse
     */
    @GetMapping("/queryBySn")
    @IgnoreAuth
    @ApiOperation(value = "根据Sn码获取纸巾机详情", notes = "根据Sn码获取纸巾机详情")
    public RestResponse getMachineDetailBySn(String sn) {
        AdTissueMachineEntity adMachine = adTissueMachineService.getBySn(sn);
        List<AdPatchEntity> adPatch = adPatchService.list(new QueryWrapper<AdPatchEntity>()
                .eq("TYPE", Constant.PatchType.XDTP.getValue())
                .last("LIMIT 0,3")
                .eq("AVAIL",1)
        );
        if(adPatch.size()>0) {
            adMachine.setOrderPatch(adPatch);
        }
        return RestResponse.success().put("data", adMachine);
    }


    /**
     * 获取当前机柜商品
     */
    @GetMapping("goodsInfo")
    @ApiOperation(value = "获取当前机柜商品", notes = "获取当前机柜商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "sn", value = "机器Sn码", required = true, dataType = "String")
    })
    public RestResponse goodsInfo(String sn) {
        List<AdMachineStockEntity> goodsInfo = adMachineStockService.queryByMachineSn(sn);
        return RestResponse.success().put("data",goodsInfo);
    }



    @PostMapping("pushTissue")
    @ApiOperation(value = "控制设备出纸巾", notes = "控制设备出纸巾")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "sn", value = "机柜编号"),
                    @ExampleProperty(mediaType = "orderId", value = "订单Id"),
            }), required = true, dataType = "string")
    })
    @Transactional(rollbackFor = Exception.class)

    public RestResponse pushTissue(@RequestBody JSONObject jsonParam) {
        String orderId = jsonParam.getString("orderId");
        String sn = jsonParam.getString("sn");
        List<AdOrderTissueEntity> tissueOrder = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>().eq("ORDER_ID", orderId));
        tissueOrder.forEach(e -> {
                    if (e.getStatus() == Constant.ShipmentStatus.INIT.getValue()) {
                        for (int i = 0; i < e.getGoodsCount(); i++) {
                            TissueProtocol pushInfo = adTissueMachineService.pushGoods(sn);
                            System.out.println(pushInfo);
                        }
                        e.setStatus(Constant.ShipmentStatus.SUCCESS.getValue());
                        adOrderTissueService.update(e);
                    }
                }
        );
        return RestResponse.success();
    }

}
