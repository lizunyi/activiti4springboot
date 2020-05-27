package com.weaver.inte.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.weaver.inte.activity.config.MyBaseMapper;
import com.weaver.inte.business.model.User;

public interface UserMapper extends MyBaseMapper<User> {
	@Select("select concat('ROLE(',rolename,')') from role where rid in (select roleid from user_role where userid = #{param1})")
	 public List<String> getRoleNamesByUserId(String userid);
}
