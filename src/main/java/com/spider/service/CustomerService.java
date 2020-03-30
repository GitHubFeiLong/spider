package com.spider.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CustomerService {

    /**
     * 添加客户时，保存轮播图
     * @param file
     * @return
     */
    Map<String, Object> addSlideshow(MultipartFile file) throws IOException;

}
