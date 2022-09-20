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
public class CustomBillCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账单分类Id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分类名称

     */
    private String name;

    /**
     * 是否为收入

     */
    private Boolean incomed;


}
