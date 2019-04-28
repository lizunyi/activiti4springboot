package com.example.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;


@Configuration
public class DataSourceConfig {

	/**
	 * Druid 数据库连接池
	 * 
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.mysql")
	public DataSource dataSource() throws SQLException {
		return DataSourceBuilder.create().type(DruidDataSource.class).build();
	}
}