package com.prince.demo.service.impl;

import com.prince.demo.service.DemoService;
import com.prince.framework.v2.annotation.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String get(String name) {
        return name + "ServiceImpl";
    }
}
