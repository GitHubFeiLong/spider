package com.spider.service.iml;

import com.spider.dao.CustomerDao;
import com.spider.pojo.AllCompanyMessage;
import com.spider.pojo.KeyCall;
import com.spider.pojo.UseFile;
import com.spider.service.CustomerService;
import com.spider.util.FilePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author msi
 */
@Service
@Slf4j
public class CustomerServiceIml implements CustomerService {


    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CustomerDao customerDao;


    /**
     * 添加一条空数据
     * @param customerId 客户主表id
     * @return customerId 客户主表id
     */
    @Override
    public Integer addALL_COMPANY_MESSAGE(Integer customerId) {
        // 上传轮播图，添加用户。
        if(customerId <= 0){
            AllCompanyMessage allCompanyMessage = new AllCompanyMessage();
            customerDao.insertALL_COMPANY_MESSAGE(allCompanyMessage);
            return Integer.valueOf(allCompanyMessage.getId().toString());
        }

        return customerId;
    }

    /**
     * 添加轮播图
     *
     * @param file 轮播图
     * @param customerId 客户主表id
     * @return map
     */
    @Override
    @Transactional
    public Map<String, Object> addSlideshow(MultipartFile file, Integer customerId) throws IOException {
        Map<String, Object> dataMap = new HashMap<>();
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String fileFullName = file.getOriginalFilename();

        // 附件信息保存再服务器中
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

        // 插入附件表 use_file
        UseFile useFile = new UseFile();
        useFile.setFileName(fileFullName);
        useFile.setSaveFilePath(savePath);
        useFile.setType("photo");
        useFile.setPath(readPath);

        customerDao.insertUseFile(useFile);

        // 插入主表id ALL_COMPANY_MESSAGE,如果已经当前页面已经插入过，就不插入数据库
        AllCompanyMessage allCompanyMessage = new AllCompanyMessage();

        // 插入主要信息表 KEY_CALL
        KeyCall keyCall = new KeyCall();
        keyCall.setAllId(Long.valueOf(customerId));
        keyCall.setEnclosure(useFile.getId().toString());

        customerDao.insertKEY_CALL(keyCall);

        dataMap.put("src", readPath);
        dataMap.put("fileName", fileFullName);
        // 用户不用提交时，根据zhesangeid删除表数据
        dataMap.put("use_file_id", useFile.getId());
        dataMap.put("all_company_message_id", customerId);
        dataMap.put("key_call_id", keyCall.getId());

        return dataMap;
    }

    /**
     * 添加客户时，删除轮播图 删除 use_file all_company_message key_call 三张表数据
     *
     * @param use_file_id
     * @param key_call_id
     */
    @Override
    @Transactional
    public void removeSlideshow(Integer use_file_id, Integer key_call_id) {

        // 删除服务器上的附件
        UseFile useFile = customerDao.selectUseFileById(use_file_id);
        String saveFilePath = useFile.getSaveFilePath();
        File file = new File(saveFilePath);
        if(file.exists()){
            file.delete();
        }

        // 删除附件表数据
        customerDao.deleteUseFileById(use_file_id);
        // 删除主要信息
        customerDao.deleteKeyCallById(key_call_id);

    }


}
