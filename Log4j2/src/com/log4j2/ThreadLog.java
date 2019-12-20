package com.log4j2;

import java.util.UUID;

public class ThreadLog implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub

		String uuid = UUID.randomUUID().toString();
		for (int i = 0; i < 100; i++) {
			InternalService inter = new InternalService();
			inter.internalservice(uuid,i);

		}

		for (int i = 0; i < 100; i++) {
			ExternalService ext = new ExternalService();
			ext.externalservice(uuid,i); 
		}

	}
}
