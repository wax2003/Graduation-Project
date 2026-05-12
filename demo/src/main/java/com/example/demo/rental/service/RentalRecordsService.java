package com.example.demo.rental.service;

import com.example.demo.rental.dto.AddRental;
import com.example.demo.rental.entity.RentalRecords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sdx2009
 * @since 2025-01-07
 */
public interface RentalRecordsService extends IService<RentalRecords> {
    boolean addRentalRecords(AddRental rentalRecords);
}
