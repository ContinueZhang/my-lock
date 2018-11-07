package com.example.mylock.service;

import com.example.mylock.entity.ICBCDetailEntity;
import com.example.mylock.entity.ICBCEntity;
import com.example.mylock.entity.UserEntity;
import com.example.mylock.mapper.ICBCDetailMapper;
import com.example.mylock.mapper.ICBCMapper;
import com.example.mylock.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by continue on 2018/11/7.
 */
@Service
public class LockService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ICBCMapper icbcMapper;
    @Autowired
    private ICBCDetailMapper icbcDetailMapper;


    public List<UserEntity> selectUserAll() {

        return userMapper.selectAll();
    }

    public List<ICBCEntity> selectIcbcAll() {
        return icbcMapper.selectAll();
    }

    public List<ICBCDetailEntity> selectIcbcDetailAll() {

        return icbcDetailMapper.selectAll();
    }


    public UserEntity selectUserByPrimaryKey(int id) {

        return userMapper.selectByPrimaryKey(id);
    }

    public ICBCEntity selectIcbcByPrimaryKey(int id) {

        return icbcMapper.selectByPrimaryKey(id);
    }

    public ICBCDetailEntity selectIcbcDetailByPrimaryKey(int id) {

        return icbcDetailMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void consume(double amount, int icbcId, int targerIcbcId) {

        ICBCEntity icbc = icbcMapper.selectByPrimaryKey(icbcId);
        Double balance = icbc.getBalance();
        balance = balance - amount;
        if (balance < 0) {
            throw new RuntimeException("余额不足");
        }

        icbc.setBalance(balance);
        icbcMapper.updateByPrimaryKey(icbc);
        ICBCDetailEntity ide = new ICBCDetailEntity();
        ide.setAmount(balance);
        ide.setIcbcId(icbcId);
        ide.setTargetId(targerIcbcId);
        ide.setTime(new Date());
        icbcDetailMapper.insert(ide);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserEntity user) {
        userMapper.insert(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addIcbc(ICBCEntity icbc) {
        icbcMapper.insert(icbc);
    }
}
