package com.platform.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.ad.entity.AdCouponEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 优惠券列表Dao
 *
 * @author gjw
 * @date 2020-12-11 10:51:39
 */
@Mapper
public interface AdCouponDao extends BaseMapper<AdCouponEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdCouponEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<AdCouponEntity> selectAdCouponPage(IPage page, @Param("params") Map<String, Object> params);
}
