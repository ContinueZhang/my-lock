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
import java.util.function.BiConsumer;
import java.util.function.Predicate;

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


    private Predicate<UserEntity> userPredicate = user -> {
        if (null == user.getName() || "".equals(user.getName())) {
            throw new RuntimeException("请输入姓名");
        }
        if (null == user.getAge()) {
            throw new RuntimeException("请输入年龄");
        }
        return true;
    };


    private Predicate<ICBCEntity> icbcPredicate = icbc -> {
        if (null == icbc.getUserId()) {
            throw new RuntimeException("请请选择所属用户");
        }
        if (null == icbc.getBalance()) {
            throw new RuntimeException("请输入资金");
        }


        if (0 >= icbc.getBalance()) {
            throw new RuntimeException("资金必须大于0");
        }
        return true;
    };


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

        BiConsumer<AddedOperation, Object> up = AddedOperation::checkNonNull;
        up.accept(null, user);

        if (userPredicate.test(user)) {
            userMapper.insert(user);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addIcbc(ICBCEntity icbc) {


        BiConsumer<AddedOperation, Object> up = AddedOperation::checkNonNull;
        up.accept(null, icbc);

        if (icbcPredicate.test(icbc)) {
            icbcMapper.insert(icbc);
        }
    }

}
