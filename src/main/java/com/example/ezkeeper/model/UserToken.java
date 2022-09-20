package com.example.ezkeeper.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 密码表
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 密码id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户密码
     */
    private String pw;

    /**
     * 用户实体
     */
    @TableField(exist = false)
    private Users user;


}
