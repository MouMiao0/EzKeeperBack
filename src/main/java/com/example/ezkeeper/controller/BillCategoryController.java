package com.example.ezkeeper.controller;


import com.example.ezkeeper.JSONResult;
import com.example.ezkeeper.model.BillCategory;
import com.example.ezkeeper.service.BillCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 默认账目分类
 前端控制器
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@RestController
@RequestMapping("/bill_category")
public class BillCategoryController {

    @Autowired
    BillCategoryService billCategoryService;

    /**
     * 获取分类
     * @param id 获取的分类id,若不输入则是获取全部id
     * @return
     */
    @RequestMapping("")
    public JSONResult allCategory(@RequestParam(value = "id", defaultValue = "-1") int id){
        return id == -1 ? JSONResult.success(billCategoryService.list()) :
                JSONResult.success(billCategoryService.getById(id));
    }


}

