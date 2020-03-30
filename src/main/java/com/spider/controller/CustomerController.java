package com.spider.controller;

import com.spider.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
     * 添加客户id
     * @param customerId
     * @return
     */
    @RequestMapping("/addALL_COMPANY_MESSAGE")
    public Map<String, Object> addALL_COMPANY_MESSAGE(Integer customerId){
        Map<String, Object> map = new HashMap<>();
        try {
            // 获取客户组表的Id
            customerId = customerService.addALL_COMPANY_MESSAGE(customerId);
            map.put("customerId",customerId);
        } catch(Exception e){
            log.error("添加客户失败：",e);
            map.put("customerId",customerId);
        }
        return map;
    }

    /**
     * 上传轮播图 CommonsMultipartFile
     * @param file 轮播图
     * @param customerId 客户主表id
     * @return
     */
    @RequestMapping("/addSlideshow")
    public Map<String, Object> addSlideshow(@RequestParam("file") MultipartFile file, Integer customerId){
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", "成功");
            map.put("code",0);
            map.put("msg","0");
            Map<String, Object> dataMap = customerService.addSlideshow(file, customerId);
            map.put("data",dataMap);
        } catch(Exception e){
            log.error("上传轮播图失败：",e);
        }
        return map;
    }

    /**
     * 删除轮播图附件
     * @param use_file_id 附件表id
     * @param key_call_id 轮播表id
     * @return
     */
    @RequestMapping("/removeSlideshow")
    public Map<String, Object> removeSlideshow(Integer use_file_id, Integer key_call_id){
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", "成功");
            map.put("code",0);
            customerService.removeSlideshow(use_file_id, key_call_id);

        } catch(Exception e){
            log.error("删除轮播图失败：",e);
            map.put("status", "失败");
            map.put("code",500);
        }
        return map;
    }

}
