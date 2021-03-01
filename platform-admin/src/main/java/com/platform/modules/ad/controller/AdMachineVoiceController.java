/*
 *
 *
 */
package com.platform.modules.ad.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdMachineVoiceEntity;
import com.platform.modules.ad.service.AdMachineVoiceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 机柜语音表Controller
 *
 * @author dxd
 * @date 2020-11-06 09:52:48
 */
@RestController
@RequestMapping("ad/machinevoice")
public class AdMachineVoiceController extends AbstractController {
    @Autowired
    private AdMachineVoiceService adMachineVoiceService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:machinevoice:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMachineVoiceEntity> list = adMachineVoiceService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询机柜语音表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:machinevoice:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMachineVoiceService.queryPage(params, getUser());

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:machinevoice:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdMachineVoiceEntity adMachineVoice = adMachineVoiceService.queryById(id);
        return RestResponse.success().put("machinevoice", adMachineVoice);
    }

    /**
     * 新增机柜语音表
     *
     * @param adMachineVoice adMachineVoice
     * @return RestResponse
     */
    @SysLog("新增机柜语音表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:machinevoice:save")
    public RestResponse save(@RequestBody AdMachineVoiceEntity adMachineVoice) {

        adMachineVoiceService.add(adMachineVoice);

        return RestResponse.success();
    }

    /**
     * 新增临时语音
     *
     * @param adMachineVoice adMachineVoice
     * @return RestResponse
     */
    @SysLog("新增临时语音")
    @RequestMapping("/addProvisionalVoice")
    @RequiresPermissions("ad:machinevoice:save")
    public RestResponse addProvisionalVoice(@RequestBody AdMachineVoiceEntity adMachineVoice) {

        adMachineVoiceService.addProvisionalVoice(adMachineVoice);

        return RestResponse.success();
    }
    /**
     * 修改机柜语音表
     *
     * @param adMachineVoice adMachineVoice
     * @return RestResponse
     */
    @SysLog("修改机柜语音表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:machinevoice:update")
    public RestResponse update(@RequestBody AdMachineVoiceEntity adMachineVoice) {

        adMachineVoiceService.update(adMachineVoice);

        return RestResponse.success();
    }

    /**
     * 根据主键删除机柜语音表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除机柜语音表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:machinevoice:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMachineVoiceService.delete(ids[0]);

        return RestResponse.success();
    }
}
