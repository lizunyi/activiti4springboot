package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.example.model.Demo;

import tk.mybatis.mapper.common.BaseMapper;

public interface DemoMapper extends BaseMapper<Demo> {
	@Select(value = "select * from department ")
	public List<Demo> getByName();
}
