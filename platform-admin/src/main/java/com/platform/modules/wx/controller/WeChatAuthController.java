package com.platform.modules.wx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.exception.BusinessException;
import com.platform.config.WxMpProperties;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysUserRoleService;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.sys.service.SysUserTokenService;
import com.platform.modules.vo.TissueData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.net.URLEncoder;

/**
 * 微信公众平台授权相关接口
 * Created by lsd
 * 2019-07-26 09:16
 */
@Slf4j
@Api(tags = "微信公众平台授权接口")
@RequestMapping("wx")
//必须使用Controller微信才能跳转页面
@Controller
public class WeChatAuthController {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysUserService sysUserService;
    //    @Autowired
//    private TbUserInfoService tbUserInfoService;
//    @Autowired
//    private AppTbUserInfoService appTbUserInfoService;
//    @Autowired
//    private JwtUtils jwtUtils;
//    @Autowired
//    private WaterOperationConfig waterOperationConfig;
    @Autowired
    private WxMpProperties wxMpProperties;
    private static String hostName;

    @PostConstruct
    public void init() {
        hostName = wxMpProperties.getHostName();
    }
    /**
     * 构造oauth2微信授权登录公众号的url
     *
     * @param returnUrl 1.授权登录成功后重定向的地址，填入本次授权登录请求携带的信息内容 state 中
     *                  2.必须有前缀 http:// 或 https:// ，否则跳转失败
     *                  3.约定前端传入的 url 已经过 URLEncoder 编码，否则进入该接口后丢失参数
     */
    @ApiOperation("传页面跳转")
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        // 用户授权完成后重定向的回调链接地址
        String url = "http://" + hostName + "/platform-admin/wx/userInfo";
        // 调用方法，构造oauth2微信授权登录公众号的url，进入接口的时候会默认把returnUrl解码（原理不清楚），所以此处仍要编码，否则微信回调时丢参数
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code,returnUrl={}，redirectUrl={}", returnUrl, redirectUrl);
        // 回调 构造好的oauth2微信授权登录公众号的url
        return "redirect:" + redirectUrl;
    }

    /**
     * oauth2微信授权登录公众号的url
     * 1、取得用户的 openId  2、重定向到 {returnUrl}?{openId}
     *  @param code  微信回调时查询串中此次授权的 code
     * @param state 微信回调时查询串中携带的东西（oauth2buildAuthorizationUrl方法 的 state），此处放入的是 returnUrl
     * @return
     */
    @ApiOperation("获取用户信息")
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                                 @RequestParam("state") String state) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        // 1 用本次授权的 code 交换 access token，普通access_token每天获取最多次数为2000次，而网页授权的access_token获取次数没有限制。
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】出错：{}", e.getMessage());
        }
        // 2 从网页授权的access token 中取得openId
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("accessToken=" + wxMpOAuth2AccessToken.getAccessToken() + "，state=" + state + "，openId=" + openId);
        // 3 公众号的绿色登录页面，确认登录后，获取openid，然后查询user表，看openid是否存在，存在就跳过，不存在就存起来。默认为没激活状态。可以删除。激活后可用
        SysUserEntity user = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("MP_OPEN_ID", openId));
        System.out.println(sysUserService.isWorker(user.getUserId()));
        if (!sysUserRoleService.isWorker(user.getUserId())) {
//            throw new BusinessException("您没有查看的权限噢~");
            // 返回静态页面
            return "redirect:/errorMessage.html";
        }
        String token = sysUserTokenService.createToken(user.getUserId());

        // 携带token重定向到授权登录后应跳转的地址
        // 若是returnUrl有查询串参数，需要特殊处理
        if (StringUtils.isNotEmpty(StringUtils.substringAfterLast(state, "?"))) {
            return "redirect:" + state + "&token=" + token;
        }
        return "redirect:" + state + "?token=" + token;
    }

    /**
     * 通过 access_token 来获取 jsapi_ticket （公众号用于调用微信JS接口的临时票据，有效期为7200秒）
     *
     * @param url 调用微信扫一扫的页面url，必须是 location.href ，不然签名会无效
     */
    @ApiOperation("获取 Jsapi_ticket")
    @ResponseBody
    @GetMapping(value = "/sign/url")
    public Object getSign(@RequestParam String url) {
        try {
            return wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return null;
        }
    }
}
