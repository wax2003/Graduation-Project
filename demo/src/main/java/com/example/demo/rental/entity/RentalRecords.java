package com.example.demo.rental.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("rental_records")
public class RentalRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("game_account_id")
    private Integer gameAccountId;

    @TableField("time")
    private Integer time;

    @TableField("status")
    private String status;

    @TableField("price")
    private Double price;


}
