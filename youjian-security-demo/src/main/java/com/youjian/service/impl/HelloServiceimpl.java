package com.youjian.service.impl;

import com.youjian.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author shen youjian
 * @date 12/8/2018 9:22 AM
 */
@Service
public class HelloServiceimpl implements HelloService {
    @Override
    public String helloService(String name) {
        System.out.println("greeting ");
        return "hello " + name;
    }
}
