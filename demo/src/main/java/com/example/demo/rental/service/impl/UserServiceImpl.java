package com.example.demo.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.rental.dto.LoginUser;
import com.example.demo.rental.dto.QueryUser;
import com.example.demo.rental.dto.UpdateUser;
import com.example.demo.rental.entity.User;
import com.example.demo.rental.mapper.UserMapper;
import com.example.demo.rental.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sdx2009
 * @since 2025-01-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Override
  public User login(LoginUser loginUser) {
      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginUser.getUsername());
        queryWrapper.eq("password", loginUser.getPassword());
        queryWrapper.eq("role", loginUser.getRole());
        return this.getOne(queryWrapper);
  }

  @Override
  public boolean register(User user) {
    //1.检测用户名是否存在
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", user.getUsername());
    User existUser = this.getOne(queryWrapper);
    if (existUser != null) {
      return false;
    }

    user.setCreatedAt(LocalDateTime.now());

    return this.save(user);

  }

  @Override
  public Page<User> pageUser(QueryUser queryUser) {
    Page<User> page = new Page<>(queryUser.getPageNumber(), queryUser.getPageSize());

    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    if(!ObjectUtils.isEmpty(queryUser.getUsername())){
      queryWrapper.like("username", queryUser.getUsername());
    }

    return this.page(page, queryWrapper);
  }

  @Override
  public boolean addUser(User user) {
    User newUser = new User();
    user.setCreatedAt(LocalDateTime.now());
    BeanUtils.copyProperties(user, newUser);
    return this.save(newUser);
  }

  @Override
  public boolean updateUser(UpdateUser updateUser) {
    User user = new User();
    BeanUtils.copyProperties(updateUser, user);
    return this.updateById(user);
  }

  @Override
  public boolean deleteUser(Integer id) {
    return this.removeById(id);
  }
}
