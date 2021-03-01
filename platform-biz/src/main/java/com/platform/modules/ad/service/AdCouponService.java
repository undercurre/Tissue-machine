package com.platform.modules.ad.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.ad.entity.AdCouponEntity;

import java.util.List;
import java.util.Map;

/**
 * 优惠券列表Service接口
 *
 * @author gjw
 * @date 2020-12-11 10:51:39
 */
public interface AdCouponService extends IService<AdCouponEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdCouponEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询优惠券列表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增优惠券列表
     *
     * @param adCoupon 优惠券列表
     * @return 新增结果
     */
    boolean add(AdCouponEntity adCoupon);

    /**
     * 根据主键更新优惠券列表
     *
     * @param adCoupon 优惠券列表
     * @return 更新结果
     */
    boolean update(AdCouponEntity adCoupon);

    /**
     * 根据主键删除优惠券列表
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);

    /**
     * 发放优惠券
     *
     * @param parmas
     * @return
     */
    boolean sendUser(Map<String, Object> parmas);

    /**
     * 优惠券过期
     */
    void expireCoupon();
}
