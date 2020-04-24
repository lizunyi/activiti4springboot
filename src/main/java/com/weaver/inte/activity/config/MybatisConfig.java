package com.weaver.inte.activity.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "com.weaver.inte.*.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisConfig {

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
			throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setVfs(SpringBootVFS.class);
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setTypeAliasesPackage("com.weaver.inte.*.mapper");
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
		sessionFactory.setConfiguration(configuration);
		return sessionFactory.getObject();
	}

}
