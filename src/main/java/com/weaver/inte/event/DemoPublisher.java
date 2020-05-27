package com.weaver.inte.event;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @description 
 * @author lzy
 * @date:2020年5月27日 上午9:29:42
 * @version v1.0
 */
@Component
public class DemoPublisher {
	@Resource
	private ApplicationContext applicationContext;
	
	public void publish(){
		new Thread(()->{
			applicationContext.publishEvent(new DemoEvent(this,"你好"));
		}).start();
		System.out.println("aaa");
	}
}
