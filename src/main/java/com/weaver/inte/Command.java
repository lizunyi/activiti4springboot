package com.weaver.inte;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.weaver.inte.event.DemoPublisher;

/**
 * @description 
 * @author lzy
 * @date:2020年5月27日 上午9:31:26
 * @version v1.0
 */
@Component
public class Command implements CommandLineRunner{
	
	@Resource
	private DemoPublisher demoPublisher;
	
	
	@Override
	public void run(String... args) throws Exception {
		demoPublisher.publish();
	}
}
