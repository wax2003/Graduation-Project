package com.example.demo.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.rental.dto.LoginUser;
import com.example.demo.rental.dto.QueryUser;
import com.example.demo.rental.dto.UpdateUser;
import com.example.demo.rental.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sdx2009
 * @since 2025-01-07
 */
public interface UserService extends IService<User> {
    User login(LoginUser loginUser);

    boolean register(User user);

    Page<User> pageUser(QueryUser queryUser);

    boolean addUser(User user);

    boolean updateUser(UpdateUser updateUser);

    boolean deleteUser(Integer id);
}
