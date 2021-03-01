/*
 *
 *
 */
package com.platform.modules.ad.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdMachineProvisionalVoiceEntity;
import com.platform.modules.ad.service.AdMachineProvisionalVoiceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 临时语音表Controller
 *
 * @author zqh
 * @date 2021-01-06 14:52:52
 */
@RestController
@RequestMapping("ad/machineprovisionalvoice")
public class AdMachineProvisionalVoiceController extends AbstractController {
    @Autowired
    private AdMachineProvisionalVoiceService adMachineProvisionalVoiceService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:machineprovisionalvoice:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMachineProvisionalVoiceEntity> list = adMachineProvisionalVoiceService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询临时语音表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:machineprovisionalvoice:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMachineProvisionalVoiceService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:machineprovisionalvoice:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdMachineProvisionalVoiceEntity adMachineProvisionalVoice = adMachineProvisionalVoiceService.getById(id);

        return RestResponse.success().put("machineprovisionalvoice", adMachineProvisionalVoice);
    }

    /**
     * 新增临时语音表
     *
     * @param adMachineProvisionalVoice adMachineProvisionalVoice
     * @return RestResponse
     */
    @SysLog("新增临时语音表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:machineprovisionalvoice:save")
    public RestResponse save(@RequestBody AdMachineProvisionalVoiceEntity adMachineProvisionalVoice) {

        adMachineProvisionalVoiceService.add(adMachineProvisionalVoice);

        return RestResponse.success();
    }

    /**
     * 修改临时语音表
     *
     * @param adMachineProvisionalVoice adMachineProvisionalVoice
     * @return RestResponse
     */
    @SysLog("修改临时语音表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:machineprovisionalvoice:update")
    public RestResponse update(@RequestBody AdMachineProvisionalVoiceEntity adMachineProvisionalVoice) {

        adMachineProvisionalVoiceService.update(adMachineProvisionalVoice);

        return RestResponse.success();
    }

    /**
     * 根据主键删除临时语音表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除临时语音表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:machineprovisionalvoice:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMachineProvisionalVoiceService.deleteBatch(ids);

        return RestResponse.success();
    }
}
