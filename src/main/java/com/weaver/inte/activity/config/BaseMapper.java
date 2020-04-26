package com.weaver.inte.activity.config;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;

/**
 * @description 
 * @author lzy
 * @date:2020年4月26日 下午1:09:46
 * @version v1.0
 */
public interface BaseMapper<T> extends Mapper<T>, InsertUseGeneratedKeysMapper<T>, InsertListMapper<T> {

}