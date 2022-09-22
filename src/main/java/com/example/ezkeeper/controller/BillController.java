package com.example.ezkeeper.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ezkeeper.JSONResult;
import com.example.ezkeeper.Util.JWTUtil;
import com.example.ezkeeper.model.Bill;
import com.example.ezkeeper.model.BillCategory;
import com.example.ezkeeper.model.CustomBillCategory;
import com.example.ezkeeper.service.BillService;
import com.example.ezkeeper.service.CustomBillCategoryService;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 账单表

 前端控制器
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillService billService;

    @Autowired
    CustomBillCategoryService customBillCategoryService;

    /**
     * 记帐
     * @return
     */
    @PostMapping("/logging")
    public JSONResult logging(Bill bill){

        if(bill == null || bill.getCategoryId() == null || bill.getNumber() == null ) return JSONResult.failMsg("请输入内容");

        if(bill.getUserCustom()  == null) bill.setUserCustom(false);

        int userId = JWTUtil.getUserIdBySubject();
        bill.setUserId(userId);

        return JSONResult.success(billService.loggingBill(bill),"记录成功");
    }

    /**
     * 据页数浏览
     * @param pageIndex
     * @return
     */
    @RequestMapping("/browse")
    public JSONResult browse(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex){
        return JSONResult.success(billService.browseBills(pageIndex));
    }


    /**
     * excel导出
     * @param request
     * @param response
     */
    @RequestMapping("/export_excel")
    public void typeExport(HttpServletRequest request, HttpServletResponse response){
        int userId = Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString());
        try {
            String filename = "export_excel";
            String userAgent = request.getHeader("User-Agent");
            if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
                filename = URLEncoder.encode(filename,"UTF-8");            }else {
                filename = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
            }
            response.setContentType("application/json.ms-exce");
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition","filename = " + filename + ".xlsx");
            EasyExcel.write(response.getOutputStream(),Bill.class).sheet("sheet").doWrite(billService.lambdaQuery().eq(Bill::getUserId,userId).list());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改账目
     * @param bill
     * @return
     */
    @PostMapping("/update")
    public JSONResult update(Bill bill){
        if(bill == null || bill.getId() == null) return JSONResult.failMsg("调用错误");
        int userId = JWTUtil.getUserIdBySubject();
        if(billService.getById(bill.getId()).getUserId() != userId) return JSONResult.failMsg("只能修改自己的账目");
        if(billService.updateById(bill)) return JSONResult.success(bill,"修改成功");
        return JSONResult.failMsg("出错了,请稍后重试");
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Transactional
    @PostMapping("/del")
    public JSONResult del(@RequestParam("ids")List<Integer> ids){
        int userId = Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString());
        for (int id: ids) {

            Bill bill = billService.getById(id);

            if(bill == null) continue;

            if(bill.getUserId() != userId){
                throw new RuntimeException();
            }
            if(!billService.removeById(id)){
                return JSONResult.failMsg("删除失败");
            }
        }
        return JSONResult.success("删除成功");
    }

    /**
     * 备份上传
     * @param bills 账单列表
     * @return
     */
    @PostMapping("upload_backup")
    public JSONResult uploadBackup(@RequestParam(value = "bills") List<Bill> bills,
                                   @RequestParam(value = "categories")List<BillCategory> categories){
        int userId = JWTUtil.getUserIdBySubject();
        for (Bill bill: bills) {
            if(bill.getUserId() != userId) continue;
            billService.saveOrUpdate(bill);
        }
        for (BillCategory category : categories){
            CustomBillCategory customBillCategory = (CustomBillCategory) category;
            customBillCategory.setUserId(userId);
            customBillCategoryService.saveOrUpdate(customBillCategory);
        }
        return JSONResult.success("信息上传成功");
    }

    /**
     * 备份下载
     * @return 账单列表
     */
    @PostMapping("download_backup")
    public JSONResult downloadBackup(){
        BackupInfo backupInfo = new BackupInfo();
        backupInfo.setBills(billService.getBillsByUser());
        backupInfo.setCustomBillCategories(customBillCategoryService
                .lambdaQuery()
                .eq(CustomBillCategory::getUserId,JWTUtil.getUserIdBySubject())
                .list());
        return JSONResult.success(backupInfo,"备份信息下传成功");
    }

}

/**
 * 备份信息
 */
@Data
class BackupInfo{
    private List<Bill> bills;
    private List<CustomBillCategory> customBillCategories;
};

