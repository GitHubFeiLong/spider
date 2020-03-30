package com.spider.dao;

import com.spider.pojo.AllCompanyMessage;
import com.spider.pojo.KeyCall;
import com.spider.pojo.UseFile;

public interface CustomerDao {

    /**
     * 添加 主表
     * @param allCompanyMessage
     * @return
     */
    Integer insertALL_COMPANY_MESSAGE(AllCompanyMessage allCompanyMessage);

    /**
     * 添加附件
     * @param useFile
     * @return
     */
    Integer insertUseFile(UseFile useFile);

    /**
     * 添加轮播图
     * @param keyCall
     * @return
     */
    Integer insertKEY_CALL(KeyCall keyCall);

    /**
     * 根据id删除 use_file
     * @param use_file_id
     */
    void deleteUseFileById(Integer use_file_id);

    /**
     * 根据id删除 KEY_CALL
     * @param key_call_id
     */
    void deleteKeyCallById(Integer key_call_id);

    /**
     * 根据id查询附件
     * @param use_file_id
     * @return
     */
    UseFile selectUseFileById(Integer use_file_id);
}
