package com.example.mylock.mapper;


import com.example.mylock.entity.UserEntity;
import com.example.mylock.provider.BaseUserMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by continue on 2018/11/7.
 */
public interface UserMapper extends Mapper<UserEntity>, MySqlMapper<UserEntity>, BaseUserMapper<UserEntity> {

}
