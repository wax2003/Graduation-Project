package com.example.demo.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.rental.dto.InsertAccounts;
import com.example.demo.rental.dto.QueryAccounts;
import com.example.demo.rental.dto.QueryAccountx;
import com.example.demo.rental.dto.UpdateAccounts;
import com.example.demo.rental.entity.GameAccounts;
import com.example.demo.rental.mapper.GameAccountsMapper;
import com.example.demo.rental.service.GameAccountsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sdx2009
 * @since 2025-01-07
 */
@Service
public class GameAccountsServiceImpl extends ServiceImpl<GameAccountsMapper, GameAccounts> implements GameAccountsService {

    @Override
    public Page<GameAccounts> pageAccounts(QueryAccounts queryAccounts) {
        Page<GameAccounts> p = new Page<>(queryAccounts.getPageNumber(), queryAccounts.getPageSize());

        QueryWrapper<GameAccounts> w = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(queryAccounts.getGameName())){
            //模糊查询
            w.like("game_name", queryAccounts.getGameName());
        }

        return this.page(p, w);
    }

    @Override
    public boolean addAccounts(InsertAccounts insertAccounts) {

        GameAccounts gameaccounts = new GameAccounts();

        insertAccounts.setCreatedAt(LocalDateTime.now());

        BeanUtils.copyProperties(insertAccounts, gameaccounts);

        return this.save(gameaccounts);
    }

    @Override
    public boolean updateAccounts(UpdateAccounts updateAccounts) {

        GameAccounts gameaccounts = new GameAccounts();

        BeanUtils.copyProperties(updateAccounts, gameaccounts);

        return this.updateById(gameaccounts);
    }

    @Override
    public boolean deleteAccounts(Integer id) {

        return this.removeById(id);
    }

    @Override
    public Page<GameAccounts> pageAccountx(QueryAccountx queryAccounts) {
        Page<GameAccounts> p = new Page<>(queryAccounts.getPageNumber(), queryAccounts.getPageSize());

        QueryWrapper<GameAccounts> w = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(queryAccounts.getGameName())){
            w.eq("game_name", queryAccounts.getGameName());

            if(!(Objects.equals(queryAccounts.getPriceMin(), queryAccounts.getPriceMax()))) {
                w.ge("price_hour", queryAccounts.getPriceMin());
                w.le("price_hour", queryAccounts.getPriceMax());
            }
            if(!ObjectUtils.isEmpty(queryAccounts.getDescription())) {
                w.like("description", queryAccounts.getDescription());
            }
        }

        return this.page(p, w);
    }
}
