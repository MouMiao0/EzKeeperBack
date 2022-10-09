package com.example.ezkeeper.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 账单表


 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */

@ColumnWidth(20) //列宽
@ContentRowHeight(20)//数据行高
@HeadRowHeight(30)//表头高
@Data
@EqualsAndHashCode(callSuper = false)
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账目id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户Id

     */
    private Integer userId;

    /**
     * 是否为用户自定义分类
     */
    private Boolean userCustom;

    /**
     * 分类id
     */
    private Integer categoryId;


    /**
     * 金额
     */
    @ExcelProperty(value = "金额", index = 0)
    private Integer number;

    /**
     * 时间
     */
    @ExcelProperty(value = "时间", index = 2)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private LocalDateTime time;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 1)
    private String note;


    @TableField(exist = false)
    private BillCategory category;

}
