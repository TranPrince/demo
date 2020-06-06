package com.prince.demo.service.impl;

import com.prince.demo.service.IDemoService;
import com.prince.framework.v2.annotation.Service;

@Service
public class DemoService implements IDemoService {

    @Override
    public String get(String name) {
        return name + "ServiceImpl";
    }
}
