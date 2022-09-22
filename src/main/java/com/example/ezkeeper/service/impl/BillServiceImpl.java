package com.example.ezkeeper.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ezkeeper.Util.JWTUtil;
import com.example.ezkeeper.model.Bill;
import com.example.ezkeeper.mappers.BillMapper;
import com.example.ezkeeper.service.BillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 账单表

 服务实现类
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {
    @Value("${bill.pageSize}")
    Integer pageSize;


    @Override
    public Page<Bill> loggingBill(Bill bill) {
        save(bill);
        //据用户id查询账目
        return browseBills(1);
    }

    @Override
    public Page<Bill> browseBills(int pageIndex) {
        Page<Bill> billPage = new Page<>(pageIndex,pageSize);
        //据用户id查询账目
        baseMapper.getPageByUserId(billPage, JWTUtil.getUserIdBySubject());
        return billPage;
    }

    @Override
    public List<Bill> getBillsByUser() {
        return baseMapper.getAllByUserId(JWTUtil.getUserIdBySubject());
    }
}
