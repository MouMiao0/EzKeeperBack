package com.example.ezkeeper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ezkeeper.model.Bill;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 账单表

 服务类
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
public interface BillService extends IService<Bill> {

    /**
     *
     * 记录账目
     * @param bill 账目列表
     * @return 历史前十个账目
     */
    Page<Bill> loggingBill(Bill bill);

    Page<Bill> browseBills(int pageIndex);

    List<Bill> getBillsByUser();
}
