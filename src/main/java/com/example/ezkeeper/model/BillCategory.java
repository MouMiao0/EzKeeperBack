package com.example.ezkeeper.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 默认账目分类

 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BillCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否为收入

     */
    private Boolean incomed;


}
