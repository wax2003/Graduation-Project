package com.example.demo.rental.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author sdx2009
 * @since 2025-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("game_accounts")
public class GameAccounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("game_name")
    private String gameName;

    @TableField("account_name")
    private String accountName;

    @TableField("account_password")
    private String accountPassword;

    @TableField("account_picture")
    private String accountPicture;

    @TableField("price_hour")
    private String priceHour;

    @TableField("status")
    private String status;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("description")
    private String description;

}
