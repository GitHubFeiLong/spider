package com.spider.service.iml;

import com.spider.dao.CustomerDao;
import com.spider.service.CustomerService;
import com.spider.util.FilePathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author msi
 */
@Service
public class CustomerServiceIml implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 添加客户时，保存轮播图
     *
     * @param file
     * @return
     */
    @Override
    public Map<String, Object> addSlideshow(MultipartFile file) throws IOException {
        Map<String, Object> dataMap = new HashMap<>();
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String fileFullName = file.getOriginalFilename();
//        customerDao
        // 附件信息
        String savePath = FilePathUtil.getSaveUrl(fileFullName);
        savePath = new StringBuffer().append(savePath).insert(savePath.lastIndexOf("."), uuid).toString();

        String readPath = FilePathUtil.getReadUrl(fileFullName);
        readPath = new StringBuffer().append(readPath).insert(readPath.lastIndexOf("."), uuid).toString();

        // 保存文件到磁盘
        File newFile=new File(savePath);
        if(!newFile.exists()){
            newFile.mkdir();
        }
        file.transferTo(newFile);


        dataMap.put("src", readPath);
        dataMap.put("fileName", fileFullName);
        dataMap.put("fileId", "id");
        return dataMap;
    }
}
