/*
 *
 *
 */
package com.platform.modules.ad.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.*;
import com.platform.modules.ad.service.*;
import com.platform.modules.ad.entity.AdAdvertEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.vo.TissueByDateVo;
import com.platform.modules.vo.TissueData;
import com.platform.modules.vo.MachineTotalVo;
import com.platform.modules.vo.VipData;
import com.platform.modules.vo.*;
import com.platform.modules.ad.entity.AdAboutEntity;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 关于我们Controller
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@RestController
@RequestMapping("ad/about")
@Api(tags = "关于我们后台管理接口")
public class AdAboutController extends AbstractController {
    @Autowired
    private AdAboutService adAboutService;
    @Autowired
    private AdAdvertService adAdvertService;
    @Autowired
    private AdOrderService adOrderService;
    @Autowired
    private AdGoodsService adGoodsService;
    @Autowired
    private MallUserService mallUserService;
    @Autowired
    private AdAdvertAdvertTypeService adAdvertAdvertTypeService;
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:about:list")
    @ApiOperation(notes="查询关于我们信息" ,value = "查询关于我们信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "params", value = "请求参数", examples = @Example({
                    @ExampleProperty(mediaType = "mobile", value = "15209831990"),
                    @ExampleProperty(mediaType = "password", value = "admin")
            }), required = true, dataType = "string")
    })
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdAboutEntity> list = adAboutService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询关于我们
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:about:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adAboutService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:about:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdAboutEntity adAbout = adAboutService.getById(id);

        return RestResponse.success().put("about", adAbout);
    }

    /**
     * 新增关于我们
     *
     * @param adAbout adAbout
     * @return RestResponse
     */
    @SysLog("新增关于我们")
    @RequestMapping("/save")
    @RequiresPermissions("ad:about:save")
    @ApiOperation(notes="新增关于我们信息" ,value = "新增关于我们信息", httpMethod = "POST")
    public RestResponse save(@RequestBody @ApiParam(value="AdAbout实体类", required = true) AdAboutEntity adAbout) {

        adAboutService.add(adAbout);

        return RestResponse.success();
    }

    /**
     * 修改关于我们
     *
     * @param adAbout adAbout
     * @return RestResponse
     */
    @SysLog("修改关于我们")
    @RequestMapping("/update")
    @RequiresPermissions("ad:about:update")
    public RestResponse update(@RequestBody AdAboutEntity adAbout) {

        adAboutService.update(adAbout);

        return RestResponse.success();
    }

    /**
     * 根据主键删除关于我们
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除关于我们")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:about:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adAboutService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 广告类报表数据
     *
     * @param
     * @return RestResponse
     */
    @RequestMapping("/queryShowAdvertData")
    @ApiOperation(notes = "广告类报表数据" ,value = "广告类报表数据", httpMethod = "GET")
    public RestResponse queryShowAdvertData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        // 提交意向数量
        List<AdAdvertEntity> advertingList = adAdvertService.list(new QueryWrapper<AdAdvertEntity>());
        AdAdvertTypeVo advertingVo = adAdvertService.newAdvertVo("提交意向数量", advertingList.size());
        hashMap.put("advertingListVo", advertingVo);

        // 已回访数量
        List<AdAdvertEntity> finishAdvertList = adAdvertService.list(new QueryWrapper<AdAdvertEntity>().eq("STATUS", 1));
        AdAdvertTypeVo finishAdvertVo = adAdvertService.newAdvertVo("已回访数量", finishAdvertList.size());
        hashMap.put("finishAdvertListVo", finishAdvertVo);

        // 不同类型的广告投放数量
        List<AdAdvertTypeVo> everyAdvertNumber = adAdvertAdvertTypeService.everyAdvertNumber();

        IntStream.range(0, everyAdvertNumber.size()).forEach(i -> {
            AdAdvertTypeVo everyAdvertNumberVo = adAdvertService.newAdvertVo(everyAdvertNumber.get(i).getName(), everyAdvertNumber.get(i).getNumber());
            hashMap.put("data" + i, everyAdvertNumberVo);
        });

        return RestResponse.success().put("data", hashMap);
    }

    /**
     * 用户类报表数据
     *
     * @param
     * @return RestResponse
     */
    @RequestMapping("/queryShowRegisterUserData")
    @ApiOperation(notes = "用户类报表数据" ,value = "用户类报表数据", httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200,message = "ok", response = RegisterUserVo.class)
    })
    public RestResponse queryShowRegisterUserData() {
        RegisterUserVo registerUserVo = new RegisterUserVo();
        // 平台用户注册总数
        List<MallUserEntity> registerUserTotal = mallUserService.list();
        Integer registerTotal = registerUserTotal.size();
        // 本年度用户注册数量
        Integer thisYearRegisterTotal = mallUserService.queryRegisterTotal(1);
        // 本季度注册总数
        Integer thisSeasonRegisterTotal = mallUserService.queryRegisterTotal(2);
        // 本月注册总数
        Integer thisMonthRegisterTotal = mallUserService.queryRegisterTotal(3);
        // 本周注册总数
        Integer thisWeekRegisterTotal = mallUserService.queryRegisterTotal(4);
        // 本日注册总数
        Integer todayRegisterTotal = mallUserService.queryRegisterTotal(5);
        // 用户男女比例(男/女)
        Integer maleCount = Math.toIntExact(registerUserTotal.stream().filter(e -> e.getGender() == 1).count());
        Integer femaleCount = Math.toIntExact(registerUserTotal.stream().filter(e -> e.getGender() == 2).count());
        BigDecimal maleRatio = new BigDecimal(0);
        BigDecimal femaleRatio = new BigDecimal(0);
        // 男性比例
        if (maleCount > 0) {
            maleRatio = new BigDecimal(maleCount).divide(new BigDecimal(registerTotal), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        // 女性比例
        if (femaleCount > 0) {
            femaleRatio = new BigDecimal(femaleCount).divide(new BigDecimal(registerTotal), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        // 未知性别比例
        BigDecimal unKnownRatio = new BigDecimal(100).subtract(maleRatio).subtract(femaleRatio);
        // 会员地区分布与数量
        List<RegionSortVo> cityUserVoList = mallUserService.groupByUserInCity();
        List<RegionSortVo> districtUserVoList = mallUserService.groupByUserInDistinct();

        registerUserVo.setRegisterTotal(registerTotal);
        registerUserVo.setThisYearRegisterTotal(thisYearRegisterTotal);
        registerUserVo.setThisSeasonRegisterTotal(thisSeasonRegisterTotal);
        registerUserVo.setThisMonthRegisterTotal(thisMonthRegisterTotal);
        registerUserVo.setThisWeekRegisterTotal(thisWeekRegisterTotal);
        registerUserVo.setTodayRegisterTotal(todayRegisterTotal);
        registerUserVo.setMaleRatio(maleRatio);
        registerUserVo.setFemaleRatio(femaleRatio);
        registerUserVo.setUnKnownRatio(unKnownRatio);
        registerUserVo.setCityUserSort(cityUserVoList);
        registerUserVo.setDistrictUserSort(districtUserVoList);

        return RestResponse.success().put("data", registerUserVo);
    }

    /**
     * 查看会员类信息
     *
     * @return RestResponse
     */
    @RequestMapping("/queryVipData")
    @ApiOperation(notes="查询会员类展示数据" ,value = "查询会员类展示数据", httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200,message = "ok", response = VipData.class)
    })
    public RestResponse queryVipData() {
        VipData vipData = new VipData();
        List<MallUserEntity> userList = mallUserService.list();
        // 会员数
        List<MallUserEntity> vipUserList = mallUserService.list(new QueryWrapper<MallUserEntity>().eq("USER_LEVEL_ID","2"));
        BigDecimal userVipProportion = BigDecimal.ZERO;
        if(vipUserList.size() > 0) {
            userVipProportion = BigDecimal.valueOf(vipUserList.size()).divide(BigDecimal.valueOf(userList.size()),2,BigDecimal.ROUND_HALF_UP);
        }
        Integer dayAddVipCount = adOrderService.queryVipCountByDate(1);
        Integer weekAddVipCount = adOrderService.queryVipCountByDate(2);
        Integer monthAddVipCount = adOrderService.queryVipCountByDate(3);
        Integer quarterAddVipCount = adOrderService.queryVipCountByDate(4);
        Integer yearAddVipCount = adOrderService.queryVipCountByDate(5);

        int man = 0;
        int lady = 0;
        int vipTissueCount = 0;
        // 男女比例
        for (MallUserEntity mallUserEntity : vipUserList) {
            vipTissueCount += mallUserEntity.getTissueCount();
            if(mallUserEntity.getGender() == 1){
                man += 1;
            }
            if(mallUserEntity.getGender() == 2){
                lady += 1;
            }
        }
        int count = vipUserList.size();
        BigDecimal manProportion = BigDecimal.valueOf(0);
        BigDecimal ladyProportion = BigDecimal.valueOf(0);
        BigDecimal unknowProportion = BigDecimal.valueOf(0);
        if(count != 0) {
            BigDecimal valueOfCount = BigDecimal.valueOf(count);
             manProportion = BigDecimal.valueOf(man).divide(valueOfCount,2,BigDecimal.ROUND_HALF_UP);
             ladyProportion = BigDecimal.valueOf(lady).divide(valueOfCount,2,BigDecimal.ROUND_HALF_UP);
             unknowProportion = BigDecimal.valueOf((count-man-lady)).divide(valueOfCount,2,BigDecimal.ROUND_HALF_UP);
        }
        vipData.setVipUserCount(count);
        vipData.setManProportion(manProportion);
        vipData.setLadyProportion(ladyProportion);
        vipData.setUnknowProportion(unknowProportion);
        vipData.setManCount(man);
        vipData.setLadyCount(lady);
        vipData.setUnknowCount(count-man-lady);
        vipData.setVipTissueCount(vipTissueCount);
        vipData.setUserVipProportion(userVipProportion);
        vipData.setDayAddVipCount(dayAddVipCount);
        vipData.setWeekAddVipCount(weekAddVipCount);
        vipData.setMonthAddVipCount(monthAddVipCount);
        vipData.setQuarterAddVipCount(quarterAddVipCount);
        vipData.setYearAddVipCount(yearAddVipCount);

        return RestResponse.success().put("data", vipData);
    }

    /**
     * 设备类报表数据
     *
     * @param
     * @return RestResponse
     */
    @RequestMapping("/queryShowMachineData")
    @ApiOperation(notes = "设备类报表数据" ,value = "设备类报表数据", httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200,message = "ok", response = MachineTotalVo.class)
    })
    public RestResponse queryShowMachineData() {
        MachineTotalVo machineTotalVo = new MachineTotalVo();
        List<AdTissueMachineEntity> adTissueMachineEntityList = adTissueMachineService.list();
        // 设备投放总数量
        Integer machineTotal = adTissueMachineEntityList.size();

        // 在线设备数量
        Integer onlineMachineTotal = Math.toIntExact(adTissueMachineEntityList.stream().filter(e ->e.getStatus() != null && e.getStatus().equals(Constant.MachineStatus.SBZC.getValue())).count());

        // 异常设备数量
        Integer unusualMachineTotal = machineTotal - onlineMachineTotal;

        // 设备地区分布与数量
        List<RegionSortVo> cityTissueMachineVoList = adTissueMachineService.groupByTissueMachineInCity();
        List<RegionSortVo> districtTissueMachineVoList = adTissueMachineService.groupByTissueMachineInDistrict();

        machineTotalVo.setMachineTotal(machineTotal);
        machineTotalVo.setOnlineMachineTotal(onlineMachineTotal);
        machineTotalVo.setUnusualMachineTotal(unusualMachineTotal);
        machineTotalVo.setCityTissueMachineSort(cityTissueMachineVoList);
        machineTotalVo.setDistrictTissueMachineSort(districtTissueMachineVoList);

        return RestResponse.success().put("data", machineTotalVo);
    }

    /**
     * 查看会员类信息
     *
     * @param
     * @return RestResponse
     */
    @RequestMapping("/queryTissueData")
    @ApiOperation(notes="查询纸巾类展示数据" ,value = "查询纸巾类展示数据", httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 200,message = "ok", response = TissueData.class)
    })
    public RestResponse queryTissueData() {
        TissueData tissueData = new TissueData();
        List<AdGoodsEntity> goodsList = adGoodsService.list(new QueryWrapper<AdGoodsEntity>().eq("STATUS", 1));
        int tissueGoodsCount = goodsList.stream().mapToInt(AdGoodsEntity::getStock).sum();
        List<AdOrderEntity> orderList = adOrderService.list(new QueryWrapper<AdOrderEntity>().eq("ORDER_TYPE", Constant.OrderType.PTDD.getValue())
                .or()
                .eq("ORDER_TYPE", Constant.OrderType.MFLQ.getValue())
                .eq("ORDER_STATUS", Constant.OrderStatus.YZF));
        int tissueSellCount = orderList.parallelStream().mapToInt(AdOrderEntity::getNumber).sum();
        //查询本日纸巾派发量以及人数信息
        TissueByDateVo dayTissueVo = adOrderService.queryTissueByDate(1);
        tissueData.setDaySellTissueCount(dayTissueVo.getTissueSum());
        if(dayTissueVo.getUserCount() != 0) {
            tissueData.setDayPerSellTissueCount(BigDecimal.valueOf(dayTissueVo.getTissueSum()).divide(BigDecimal.valueOf(dayTissueVo.getUserCount()),2,BigDecimal.ROUND_HALF_UP));
        } else {
            tissueData.setDayPerSellTissueCount(BigDecimal.ZERO);
        }
        //查询本周纸巾派发量以及人数信息
        TissueByDateVo weekTissueVo = adOrderService.queryTissueByDate(2);
        tissueData.setWeekSellTissueCount(weekTissueVo.getTissueSum());
        if(weekTissueVo.getUserCount() != 0) {
            tissueData.setWeekPerSellTissueCount(BigDecimal.valueOf(weekTissueVo.getTissueSum()).divide(BigDecimal.valueOf(weekTissueVo.getUserCount()),2,BigDecimal.ROUND_HALF_UP));
        }else {
            tissueData.setWeekPerSellTissueCount(BigDecimal.ZERO);
        }
        //查询本月纸巾派发量以及人数信息
        TissueByDateVo monthTissueVo = adOrderService.queryTissueByDate(3);
        tissueData.setMonthSellTissueCount(monthTissueVo.getTissueSum());
        if(monthTissueVo.getUserCount() != 0) {
            tissueData.setMonthPerSellTissueCount(BigDecimal.valueOf(monthTissueVo.getTissueSum()).divide(BigDecimal.valueOf(monthTissueVo.getUserCount()),2,BigDecimal.ROUND_HALF_UP));
        }else{
            tissueData.setMonthPerSellTissueCount(BigDecimal.ZERO);
        }
        //查询本季度纸巾派发量以及人数信息
        TissueByDateVo quarterTissueVo = adOrderService.queryTissueByDate(4);
        tissueData.setQuarterSellTissueCount(quarterTissueVo.getTissueSum());
        if(quarterTissueVo.getUserCount() != 0) {
            tissueData.setQuarterPerSellTissueCount(BigDecimal.valueOf(quarterTissueVo.getTissueSum()).divide(BigDecimal.valueOf(quarterTissueVo.getUserCount()),2,BigDecimal.ROUND_HALF_UP));
        }else {
            tissueData.setQuarterPerSellTissueCount(BigDecimal.ZERO);
        }
        //查询本年纸巾派发量以及人数信息
        TissueByDateVo yearTissueVo = adOrderService.queryTissueByDate(5);
        tissueData.setYearSellTissueCount(yearTissueVo.getTissueSum());
        if(yearTissueVo.getUserCount() != 0) {
            tissueData.setYearPerSellTissueCount(BigDecimal.valueOf(yearTissueVo.getTissueSum()).divide(BigDecimal.valueOf(yearTissueVo.getUserCount()),2,BigDecimal.ROUND_HALF_UP));
        }else {
            tissueData.setYearPerSellTissueCount(BigDecimal.ZERO);
        }
        // 地区总派发排行
        List<RegionSortVo> cityTissueVoList = adOrderService.groupByTissueInCity();
        List<RegionSortVo> districtTissueVoList = adOrderService.groupByTissueInDistrict();

        tissueData.setTissueGoodsCount(tissueGoodsCount);
        tissueData.setTissueSellCount(tissueSellCount);
        tissueData.setCityTissueSellSort(cityTissueVoList);
        tissueData.setDistrictTissueSellSort(districtTissueVoList);
        return RestResponse.success().put("data", tissueData);
    }
}
