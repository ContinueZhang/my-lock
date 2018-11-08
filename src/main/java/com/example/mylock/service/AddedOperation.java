package com.example.mylock.service;


import org.springframework.stereotype.Component;

@Component
public class AddedOperation {


    void checkNonNull(Object... object) {
        for (Object o : object) {
            if (null == o) {
                throw new RuntimeException("参数不能为空");
            }
        }
    }

    static void staticRawMethod() {
        System.out.println("原生静态方法");
    }

}
