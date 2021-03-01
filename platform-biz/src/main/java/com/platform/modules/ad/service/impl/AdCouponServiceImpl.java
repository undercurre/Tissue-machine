package com.platform.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdCouponDao;
import com.platform.modules.ad.entity.AdCouponEntity;
import com.platform.modules.ad.entity.AdUserCouponEntity;
import com.platform.modules.ad.service.AdCouponService;
import com.platform.modules.ad.service.AdUserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 优惠券列表Service实现类
 *
 * @author gjw
 * @date 2020-12-11 10:51:39
 */
@Service("adCouponService")
public class AdCouponServiceImpl extends ServiceImpl<AdCouponDao, AdCouponEntity> implements AdCouponService {

    @Autowired
    AdUserCouponService adUserCouponService;

    @Override
    public List<AdCouponEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.ID");
        params.put("asc", false);
        Page<AdCouponEntity> page = new Query<AdCouponEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdCouponPage(page, params));
    }

    @Override
    public boolean add(AdCouponEntity adCoupon) {
        return this.save(adCoupon);
    }

    @Override
    public boolean update(AdCouponEntity adCoupon) {
        return this.updateById(adCoupon);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public boolean sendUser(Map<String, Object> parmas) {
        String id = (String) parmas.get("id");
        AdCouponEntity couponEntity = this.getById(id);

        List<String> userIds = (List<String>) parmas.get("userIds");

        List<AdUserCouponEntity> userCouponEntityList = new ArrayList<>();
        AdUserCouponEntity userCouponEntity;
        for (int i = 0; i < userIds.size(); i++) {
            userCouponEntity = new AdUserCouponEntity();
            userCouponEntity.setUserId(userIds.get(i));
            userCouponEntity.setCouponId(id);
            userCouponEntity.setAddTime(new Date());
            userCouponEntity.setType(0);
            userCouponEntity.setStatus(0);
            userCouponEntityList.add(userCouponEntity);

            // 检查会员拥有的优惠券是否超过限制数量
            if (adUserCouponService.checkUserCouponLimit(userIds.get(i), id, couponEntity.getLimitUser())) {
                throw new BusinessException("用户拥有的优惠券超出限制！");
            }
            // 发放后 + 1
            couponEntity.setSendCount(couponEntity.getSendCount() + 1);
            if (couponEntity.getSendCount() > couponEntity.getTotalCount()) {
                throw new BusinessException("优惠券数量不足！");
            }
            this.updateById(couponEntity);
        }
        return adUserCouponService.saveBatch(userCouponEntityList);
    }

    /**
     * 优惠券过期
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void expireCoupon() {
        List<AdCouponEntity> couponEntities = baseMapper.selectList(new QueryWrapper<AdCouponEntity>().eq("status", 1));
        if (couponEntities != null && couponEntities.size() > 0) {
            List<AdUserCouponEntity> userCouponEntityList = new ArrayList<>();

            List<AdCouponEntity> list = new ArrayList<>();
            couponEntities.stream().filter(item -> new Date().after(item.getEndTime())).forEach(coupon -> {
                coupon.setStatus(2);
                list.add(coupon);

                // 会员已领用的优惠券
                userCouponEntityList.addAll(adUserCouponService.list(new QueryWrapper<AdUserCouponEntity>().eq("COUPON_ID", coupon.getId()).eq("STATUS", 0)));
            });
            if (list.size() > 0) {
                this.updateBatchById(list);
            }
            if (userCouponEntityList.size() > 0) {
                userCouponEntityList.forEach(item -> item.setStatus(2));
                adUserCouponService.updateBatchById(userCouponEntityList);
            }
        }
    }
}
