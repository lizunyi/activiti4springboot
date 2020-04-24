package com.weaver.inte.activity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.weaver.inte.activity.model.User;

import tk.mybatis.mapper.common.BaseMapper;

@Repository
public interface UserMapper extends BaseMapper<User> {
	@Select("select concat('ROLE(',rolename,')') from role where rid in (select roleid from user_role where userid = #{param1})")
	 public List<String> getRoleNamesByUserId(String userid);
}
