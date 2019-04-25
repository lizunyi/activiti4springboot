package com.example.mapper;

import org.springframework.stereotype.Repository;

import com.example.model.Role;

import tk.mybatis.mapper.common.BaseMapper;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
	 
}
