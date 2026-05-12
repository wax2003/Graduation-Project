package com.example.demo.rental.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.rental.dto.LoginUser;
import com.example.demo.rental.dto.QueryUser;
import com.example.demo.rental.dto.UpdateUser;
import com.example.demo.rental.entity.User;
import com.example.demo.rental.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sdx2009
 * @since 2025-01-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public JSONObject login(@RequestBody LoginUser loginUser) {
        // 根据用户名查询用户
        User user = userService.login(loginUser);



        JSONObject jsonObject = new JSONObject();
        if (ObjectUtils.isEmpty(user)) {
            jsonObject.put("code", 500);
            jsonObject.put("msg", "用户名或密码或身份错误");

        } else {
            jsonObject.put("code", 200);
            jsonObject.put("msg", "登录成功");
            jsonObject.put("id",user.getId());
        }
        return jsonObject;

    }

    @PostMapping("/register")
    public JSONObject register(@RequestBody User user) {
        boolean result = userService.register(user);

        JSONObject jsonObject = new JSONObject();
        if(result){
            jsonObject.put("code", 200);
            jsonObject.put("msg","注册成功");
        }else{
            jsonObject.put("code", 500);
            jsonObject.put("msg","注册失败");
        }
        return jsonObject;
    }

    @GetMapping("/page")
    public JSONObject page(QueryUser queryUser) {
        Page<User> page = userService.pageUser(queryUser);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("msg","操作成功");
        jsonObject.put("total", page.getTotal());
        jsonObject.put("rows", page.getRecords());
        return jsonObject;
    }

    @PostMapping("/add")
    public JSONObject add(@RequestBody User user) {
        boolean result = userService.addUser(user);
        JSONObject jsonObject = new JSONObject();
        if(result){
            jsonObject.put("code", 200);
            jsonObject.put("msg","添加用户成功");
        }else{
            jsonObject.put("code", 500);
            jsonObject.put("msg","添加用户失败");
        }
        return jsonObject;
    }

    @PostMapping("/update")
    public JSONObject update(@RequestBody UpdateUser updateUser) {
        boolean result = userService.updateUser(updateUser);
        JSONObject jsonObject = new JSONObject();
        if(result){
            jsonObject.put("code", 200);
            jsonObject.put("msg","修改用户成功");
        }else{
            jsonObject.put("code", 500);
            jsonObject.put("msg","修改用户失败");
        }
        return jsonObject;
    }

    @GetMapping("/delete")
    public JSONObject delete(Integer id) {
        boolean result = userService.deleteUser(id);
        JSONObject jsonObject = new JSONObject();
        if(result){
            jsonObject.put("code", 200);
            jsonObject.put("msg","删除用户成功");
        }else{
            jsonObject.put("code", 500);
            jsonObject.put("msg","删除用户失败");
        }
        return jsonObject;
    }
}
