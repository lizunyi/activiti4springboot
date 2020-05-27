package com.weaver.inte.activity.config;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;

/**
 * @description 
 * @author lzy
 * @date:2020年5月27日 上午10:55:40
 * @version v1.0
 */
public interface MyBaseMapper<T> extends Mapper<T>, InsertUseGeneratedKeysMapper<T>, InsertListMapper<T> {

}
