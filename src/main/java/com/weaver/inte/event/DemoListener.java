package com.weaver.inte.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description 
 * @author lzy
 * @date:2020年5月27日 上午9:28:30
 * @version v1.0
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
	
	@Async
	@Override
	public void onApplicationEvent(DemoEvent event) {
		System.out.println("接收到消息"+event.getMsg());
	}

}
