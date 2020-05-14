package com.fh;

import com.fh.callback.OnCtsSdkCallBack;
import com.fh.service.FhService;
import com.fh.service.impl.FhServiceImpl;
import com.fh.utils.fh.CtsSdk;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        CtsSdk commonInstance = CtsSdk.COMMON_INSTANCE;
        System.out.println(commonInstance.TSDK_Req_OpenTermAudVid(1));
    }
}
