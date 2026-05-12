package com.example.demo.rental.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.rental.dto.AddRental;
import com.example.demo.rental.service.RentalRecordsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/rental")
public class RentalRecordsController {

    @Resource
    private RentalRecordsService rentalRecordsService;
    @PostMapping("/add")
    public JSONObject addRentalRecords(@RequestBody AddRental jsonObject){

        boolean result = rentalRecordsService.addRentalRecords(jsonObject);
        JSONObject obj = new JSONObject();
         if(result){
            obj.put("code",200);
            obj.put("msg","添加租赁记录成功");
        }else {
             obj.put("code",500);
            obj.put("msg","添加租赁记录失败");
         }
        return obj;

    }

}
