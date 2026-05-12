package com.example.demo.rental.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.rental.dto.InsertAccounts;
import com.example.demo.rental.dto.QueryAccounts;
import com.example.demo.rental.dto.QueryAccountx;
import com.example.demo.rental.dto.UpdateAccounts;
import com.example.demo.rental.entity.GameAccounts;
import com.example.demo.rental.service.GameAccountsService;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/accounts")
public class GameAccountsController {

    @Resource
    private GameAccountsService gameAccountsService;


    @GetMapping("/page")
    public JSONObject page(QueryAccounts queryAccounts){
        Page<GameAccounts> userPage = gameAccountsService.pageAccounts(queryAccounts);
//        获取service返回的结果，进行封装
        JSONObject obj=new JSONObject();
        obj.put("code",200);
        obj.put("msg","操作成功");
        obj.put("total", userPage.getTotal());
        obj.put("rows", userPage.getRecords());
        return obj;
    }

    @PostMapping("/add")
    public JSONObject addAccounts(@RequestBody InsertAccounts insertAccounts){
        boolean result = gameAccountsService.addAccounts(insertAccounts);
        JSONObject obj = new JSONObject();
        if(result){
            obj.put("code",200);
            obj.put("msg","添加用户成功");
        }else {
            obj.put("code",500);
            obj.put("msg","添加用户失败");
        }
        return obj;
    }

    @PostMapping("/update")
    public JSONObject updateAccounts(@RequestBody UpdateAccounts updateAccounts){
        boolean result = gameAccountsService.updateAccounts(updateAccounts);
        JSONObject obj = new JSONObject();
        if(result){
            obj.put("code",200);
            obj.put("msg","修改用户成功");
        }else {
            obj.put("code",500);
            obj.put("msg","修改用户失败");
        }
        return obj;
    }

    @GetMapping("/delete")
    public JSONObject deleteAccounts(Integer id){
        boolean result = gameAccountsService.deleteAccounts(id);
        JSONObject obj = new JSONObject();
        if(result){
            obj.put("code",200);
            obj.put("msg","修改用户成功");
        }else {
            obj.put("code",500);
            obj.put("msg","修改用户失败");
        }
        return obj;
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<GameAccounts> searchStatus(@PathVariable Integer id) {
        GameAccounts gameAccounts = gameAccountsService.getById(id);
        if (gameAccounts != null) {
            return ResponseEntity.ok(gameAccounts);
        } else {
            // 如果没有找到 GameAccounts 对象，返回一个错误响应
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/pagex")
    public JSONObject pageAccountx(@RequestBody  QueryAccountx queryAccounts) {
        Page<GameAccounts> userPage = gameAccountsService.pageAccountx(queryAccounts);

        JSONObject obj = new JSONObject();
        obj.put("code", 200);
        obj.put("msg", "操作成功");
        obj.put("total", userPage.getTotal());
        obj.put("rows", userPage.getRecords());
        return obj;
    }
}
