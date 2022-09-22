package com.example.ezkeeper.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户自定账单分类表

 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomBillCategory extends BillCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;


}
