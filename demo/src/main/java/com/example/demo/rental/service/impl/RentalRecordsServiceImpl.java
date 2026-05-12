package com.example.demo.rental.service.impl;

import com.example.demo.rental.dto.AddRental;
import com.example.demo.rental.dto.UpdateAccounts;
import com.example.demo.rental.entity.GameAccounts;
import com.example.demo.rental.entity.RentalRecords;
import com.example.demo.rental.entity.User;
import com.example.demo.rental.mapper.RentalRecordsMapper;
import com.example.demo.rental.service.GameAccountsService;
import com.example.demo.rental.service.RentalRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sdx2009
 * @since 2025-01-07
 */
@Service
public class RentalRecordsServiceImpl extends ServiceImpl<RentalRecordsMapper, RentalRecords> implements RentalRecordsService {

    @Resource
    private GameAccountsService gameAccountsService;

    @Override
    public boolean addRentalRecords(AddRental rentalRecords) {
        //完成游戏记录状态的更新
        RentalRecords records = new RentalRecords();
        GameAccounts ga=gameAccountsService.getById(rentalRecords.getGameAccountId());
        ga.setStatus(rentalRecords.getStatus());
        gameAccountsService.updateById(ga);
        BeanUtils .copyProperties(rentalRecords, records);
        return this.save(records);

    }
}
