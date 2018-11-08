package com.example.mylock.mapper;


import com.example.mylock.entity.ICBCDetailEntity;
import com.example.mylock.entity.ICBCEntity;
import com.example.mylock.provider.BaseUserMapper;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by continue on 2018/11/7.
 */
@NameStyle(Style.camelhumpAndUppercase)
public interface ICBCMapper extends Mapper<ICBCEntity>, MySqlMapper<ICBCEntity> , BaseUserMapper<ICBCEntity> {

}
