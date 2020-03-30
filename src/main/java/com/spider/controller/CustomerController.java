package com.spider.controller;

import com.spider.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加客户，编辑客户的控制器
 */
@Slf4j
@RequestMapping("/CustomerController")
@ResponseBody
@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 上传轮播图 CommonsMultipartFile
     * @return
     */
    @RequestMapping("/addSlideshow")
    public Map<String, Object> addSlideshow(@RequestParam("file") MultipartFile file){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        try {
            map.put("status", "成功");
            map.put("code",0);
            map.put("msg","0");
            customerService.addSlideshow(file);

            map.put("data",dataMap);
        } catch(Exception e){
            log.error("上传轮播图失败：",e);
        }
        return map;
    }
}
