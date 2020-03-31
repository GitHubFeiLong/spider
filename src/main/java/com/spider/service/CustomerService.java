package com.spider.service;


import com.spider.pojo.AllCompanyMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CustomerService {

    /**
     * 添加一条空数据
     * @param customerId 客户主表id
     * @return customerId 客户主表id
     */
    Integer addALL_COMPANY_MESSAGE(Integer customerId);

    /**
     * 添加轮播图
     *
     * @param file 轮播图
     * @param customerId 客户主表id
     * @return map
     */
    Map<String, Object> addSlideshow(MultipartFile file, Integer customerId) throws IOException;

    /**
     * 添加客户时，删除轮播图 删除 use_file all_company_message key_call 三张表数据
     * @param use_file_id
     * @param key_call_id
     */
    void removeSlideshow(Integer use_file_id, Integer key_call_id);

    /**
     * 添加推广信息
     * @param all
     */
    void addCustomer(AllCompanyMessage all);

    /**
     * 修改推广信息
     * @param all
     */
    void updateCustomer(AllCompanyMessage all);
}
