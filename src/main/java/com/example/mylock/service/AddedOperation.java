package com.example.mylock.service;


public class AddedOperation {


    public void checkNonNull(Object... object) {
        for (Object o : object) {
            if (null == o) {
                throw new RuntimeException("参数不能为空");
            }
        }
    }

}
