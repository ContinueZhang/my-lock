package com.example.mylock.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by continue on 2018/11/7.
 */
@Data
@Table(name = "t_icbc_detail")
public class ICBCDetailEntity {

    @Id
    private Integer id;
    private Double amount;
    private Integer icbcId;
    private Integer targetIcbcId;
    private Date time;
}
