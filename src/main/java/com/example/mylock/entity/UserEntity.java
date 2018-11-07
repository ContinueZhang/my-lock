package com.example.mylock.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by continue on 2018/11/7.
 */
@Data
@Table(name = "t_user")
public class UserEntity {

    @Id
    private Integer id;
    private String name;
    private Integer age;
    private String hobby;


}
