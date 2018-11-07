package com.example.mylock.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by continue on 2018/11/7.
 */
@Data
@Table(name = "t_icbc")
public class ICBCEntity {

    @Id
    private Integer id;
    private Double balance;
    private Integer userId;
    @ManyToOne
    @JoinColumn(updatable = false, insertable = false, name = "user_id")
    private UserEntity user;
}
