package com.weaver.inte.activity.sqlprovider;

import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.weaver.inte.activity.utils.SqlTemplete;

/**
 * @description 
 * @author lzy
 * @date:2020年4月26日 上午11:45:36
 * @version v1.0
 */
public class SqlPrivoder {
	
	public String sql(String sqlTemplete,Map map) throws Exception{
		String sql = IOUtils.toString(getClass().getResourceAsStream("/sql/flow/" + sqlTemplete + ".sql"));
		return SqlTemplete.parse(sql, map);
	}
}
