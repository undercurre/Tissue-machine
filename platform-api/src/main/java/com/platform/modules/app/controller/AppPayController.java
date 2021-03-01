package com.platform.modules.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdOrderService;
import com.platform.modules.app.service.PayService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "ApiPayController|支付管理控制器")
@RestController
@RequestMapping("/app/pay")
public class AppPayController {
    @Autowired
    private AdOrderService orderService;
    @Autowired
    private PayService payService;
    /**
     * 商品统一下单请求
     */
    @PostMapping("prepay")
    @ApiOperation(value = "商品统一下单请求", notes = "商品统一下单请求")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "orderId", value = "orderId"),
                    @ExampleProperty(mediaType = "tradeType", value = "JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付")
            }), required = true, dataType = "string")
    })
    public RestResponse payPrepay(@LoginUser MallUserEntity loginUser, @RequestBody JSONObject jsonParam) {
        return payService.payPrepay(loginUser, jsonParam);
    }

    /**
     * 商品统一下单请求回调接口
     *
     * @return
     */
    @ApiOperation(value = "商品统一下单请求回调接口", notes = "商品统一下单请求回调接口")
    @PostMapping(value = "/notify", produces = "text/html;charset=UTF-8")
    @IgnoreAuth
    @Transactional(rollbackFor = Exception.class)
    public String notify(@RequestBody String xmlData) {
        log.info("----------商品统一下单请求回调接口----------");
        log.info(xmlData);
        return payService.notify(xmlData);
    }

    /**
     * 余额充值统一下单请求
     */
    @PostMapping("prepayYue")
    @ApiOperation(value = "余额充值统一下单请求", notes = "余额充值统一下单请求")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "rechargeId", value = "rechargeId"),
                    @ExampleProperty(mediaType = "fromType", value = "用户下单来源类型 1:微信小程序 2:微信公众号 3:app 4:H5 5:支付宝小程序 6:QQ小程序"),
                    @ExampleProperty(mediaType = "tradeType", value = "JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付")
            }), required = true, dataType = "string")
    })
    @Transactional(rollbackFor = Exception.class)
    public RestResponse prepayYue(@LoginUser MallUserEntity loginUser, @RequestBody JSONObject jsonParam) {
        return payService.prepayYue(loginUser, jsonParam);
    }
    /**
     * 余额统一下单请求回调接口
     *
     * @return
     */
    @ApiOperation(value = "余额统一下单请求回调接口", notes = "余额统一下单请求回调接口")
    @IgnoreAuth
    @PostMapping(value = "/prepayYueNotify", produces = "text/html;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String prepayYueNotify(@RequestBody String xmlData) {
        log.info("----------余额统一下单请求回调接口----------");
        log.info(xmlData);
        return payService.prepayYueNotify(xmlData);
    }
    /**
     * 余额支付（购买商品）
     */
    @PostMapping("buyByYue")
    @ApiOperation(value = "余额支付", notes = "余额支付（购买商品）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "orderId", value = "订单ID"),
                    @ExampleProperty(mediaType = "fromType", value = "用户下单来源类型 1:微信小程序 2:微信公众号 3:app 4:H5 5:支付宝小程序 6:QQ小程序")
            }), required = true, dataType = "string")
    })
    @Transactional(rollbackFor = Exception.class)
    public RestResponse buyByYue(@LoginUser MallUserEntity loginUser, @RequestBody JSONObject jsonParam) {
        return payService.buyByYue(loginUser, jsonParam);
    }
}
