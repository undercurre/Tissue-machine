package com.platform.modules.ad.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdCouponEntity;
import com.platform.modules.ad.service.AdCouponService;
import com.platform.modules.sys.entity.SysUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 优惠券列表Controller
 *
 * @author gjw
 * @date 2020-12-11 10:51:39
 */
@Api(tags = "Admin优惠券")
@RestController
@RequestMapping("ad/coupon")
public class AdCouponController extends AbstractController {
    @Autowired
    private AdCouponService adCouponService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:coupon:list")
    @ApiOperation("查看所有列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "优惠券标题", dataType = "String"),
            @ApiImplicitParam(name = "couponSn", value = "优惠券编号", dataType = "String"),
            @ApiImplicitParam(name = "couponType", value = "优惠券类型：1:代金券 2:折扣", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "状态 1:正常 2：过期 3：禁用", dataType = "int")
    })
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdCouponEntity> list = adCouponService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询优惠券列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:coupon:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adCouponService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("ad:coupon:info")
    @ApiOperation("根据主键查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "String")
    })
    public RestResponse info(@PathVariable("id") String id) {
        AdCouponEntity mallCoupon = adCouponService.getById(id);

        return RestResponse.success().put("coupon", mallCoupon);
    }

    /**
     * 新增优惠券列表
     *
     * @param adCoupon adCoupon
     * @return RestResponse
     */
    @SysLog("新增优惠券列表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:coupon:save")
    public RestResponse save(@RequestBody AdCouponEntity adCoupon) {
        SysUserEntity sysUser = getUser();
        String sysUserId = sysUser.getUserId();
        adCoupon.setAgentId(sysUserId);
        adCouponService.add(adCoupon);

        return RestResponse.success();
    }

    /**
     * 修改优惠券列表
     *
     * @param adCoupon adCoupon
     * @return RestResponse
     */
    @SysLog("修改优惠券列表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:coupon:update")
    public RestResponse update(@RequestBody AdCouponEntity adCoupon) {

        adCouponService.update(adCoupon);

        return RestResponse.success();
    }

    /**
     * 根据主键删除优惠券列表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除优惠券列表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:coupon:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adCouponService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 发放优惠券
     *
     * @param parmas
     * @return
     */
    @SysLog("发放优惠券")
    @PostMapping("/sendUser")
    @RequiresPermissions("ad:coupon:sendUser")
    @ApiOperation("发放优惠券")
    public RestResponse sendUser(@RequestBody Map<String, Object> parmas) {

        adCouponService.sendUser(parmas);
        return RestResponse.success();
    }
}
