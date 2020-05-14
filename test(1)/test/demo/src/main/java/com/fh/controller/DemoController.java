package com.fh.controller;

import com.fh.service.FhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private FhService fhService;


    @GetMapping(value = "/init")
    public void init() {
        fhService.deInitSdk();
        fhService.initSdk();
    }

    @GetMapping(value = "/openTermAudVid")
    public void openTermAudVid(){
        fhService.openTermAudVid(1);
    }



}
