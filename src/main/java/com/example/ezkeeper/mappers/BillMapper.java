package com.example.ezkeeper.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ezkeeper.model.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 账单表

 Mapper 接口
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
public interface BillMapper extends BaseMapper<Bill> {

    Page<Bill> getPageByUserId(Page<Bill> page, @Param("userId") int userId);

    List<Bill> getAllByUserId(@Param("userId")int userId);
}
