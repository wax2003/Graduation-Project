package com.example.demo.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.rental.dto.InsertAccounts;
import com.example.demo.rental.dto.QueryAccounts;
import com.example.demo.rental.dto.QueryAccountx;
import com.example.demo.rental.dto.UpdateAccounts;
import com.example.demo.rental.entity.GameAccounts;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sdx2009
 * @since 2025-01-07
 */
public interface GameAccountsService extends IService<GameAccounts> {

    Page<GameAccounts>  pageAccounts(QueryAccounts queryAccounts);

    boolean addAccounts(InsertAccounts insertAccounts);

    boolean updateAccounts(UpdateAccounts updateAccounts);

    boolean deleteAccounts(Integer id);

    Page<GameAccounts> pageAccountx(QueryAccountx queryAccounts);




}
