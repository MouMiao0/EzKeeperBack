package com.example.ezkeeper.controller;


import com.example.ezkeeper.JSONResult;
import com.example.ezkeeper.Util.JWTUtil;
import com.example.ezkeeper.model.BillCategory;
import com.example.ezkeeper.model.CustomBillCategory;
import com.example.ezkeeper.service.CustomBillCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户自定账单分类表
 前端控制器
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@RestController
@RequestMapping("/custom_bill_category")
public class CustomBillCategoryController {

    @Autowired
    CustomBillCategoryService customBillCategoryService;

    @RequestMapping("")
    public JSONResult getCategory(@RequestParam(value = "id", defaultValue = "-1") int id){
        if(id != -1) return  JSONResult.success(customBillCategoryService.getById(id));
        List<CustomBillCategory> customBillCategoryList = customBillCategoryService.lambdaQuery()
                .eq(CustomBillCategory::getUserId,JWTUtil.getUserIdBySubject())
                .list();
        return JSONResult.success(customBillCategoryList);
    }

    @PostMapping("/save_or_update")
    public JSONResult saveOrUpdateCategory(BillCategory billCategory){
        if(billCategory.getIncomed() == null) billCategory.setIncomed(false);
        CustomBillCategory customBillCategory = CustomBillCategory.castToCustomBillCategory(billCategory, JWTUtil.getUserIdBySubject());
        if(customBillCategoryService.saveOrUpdate(customBillCategory)) return JSONResult.success(customBillCategory,"保存成功");
        return JSONResult.failMsg("保存失败,请稍后重试");
    }

    @DeleteMapping("/del")
    public JSONResult delCategory(@RequestBody List<Integer> ids){
        for (int id : ids) {
            customBillCategoryService.removeByIds(ids);
        }
        return JSONResult.success("删除成功");
    }

}

