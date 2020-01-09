package com.stylefeng.guns.modular.houseManager.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.HouseManagerMapper;
import com.stylefeng.guns.common.persistence.model.HouseManager;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.houseManager.service.IHouseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 房屋管理系统控制器
 *
 * @author jiangzh
 * @Date 2017-11-30 01:39:51
 */
@Controller
@RequestMapping("/houseManager")
public class HouseManagerController extends BaseController {

    @Autowired
    private IHouseManagerService service;
    @Autowired
    private HouseManagerMapper mapper;
    private String PREFIX = "/housemanager/houseManager/";

    /**
     * 跳转到房屋管理系统首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "houseManager.html";
    }

    /**
     * 跳转到添加房屋管理系统
     */
    @RequestMapping("/houseManager_add")
    public String houseManagerAdd() {

        return PREFIX + "houseManager_add.html";
    }

    /**
     * 跳转到修改房屋管理系统
     */
    @RequestMapping("/houseManager_update/{houseManagerId}")
    public String houseManagerUpdate(@PathVariable Integer houseManagerId, Model model) {
        if (ToolUtil.isEmpty(houseManagerId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        HouseManager houseManager = mapper.selectById(houseManagerId);

        model.addAttribute("houseManager",houseManager);

        return PREFIX + "houseManager_edit.html";
    }

    /**
     * 获取房屋管理系统列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        if(ToolUtil.isNotEmpty(condition)){
            EntityWrapper<HouseManager> houseManagerEntityWrapper = new EntityWrapper<>();
            Wrapper<HouseManager> house_user = houseManagerEntityWrapper.like("house_user", "%" + condition + "%");
            List<HouseManager> houseManagers = mapper.selectList(house_user);
            return houseManagers;
        }else{
            List<HouseManager> houseManagers = mapper.selectList(null);
            return houseManagers;
        }

    }

    /**
     * 新增房屋管理系统
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(HouseManager manager) {
        System.out.println(manager);
        if (ToolUtil.isNotEmpty(manager)) {
            mapper.insert(manager);
        }

        return super.SUCCESS_TIP;
    }

    /**
     * 删除房屋管理系统
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer houseManagerId) {

        if (ToolUtil.isNotEmpty(houseManagerId)) {
            mapper.deleteById(houseManagerId);
        }

        return SUCCESS_TIP;
    }


    /**
     * 修改房屋管理系统
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(HouseManager manager) {

        if(ToolUtil.isNotEmpty(manager)){
            mapper.updateById(manager);
        }

        return super.SUCCESS_TIP;
    }

    /**
     * 房屋管理系统详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
