package com.log4j2;

import java.io.File;
import java.net.URISyntaxException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

public class Logwriter {


	public static void main(String[] args) throws  URISyntaxException {
		// static working as expected
		/*File file = new File("D:\\source\\Log4j2\\resources\\log4j2V3.xml");
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		context.setConfigLocation(file.toURI());

		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new ThreadLog());
			thread.start();
		}*/

		//static working as expected ends


		CustomConfigurationFactory factory = new CustomConfigurationFactory();
		factory.getConfiguration(null, "InternalServiceFile", null);

		for (int i = 0; i < 100; i++) {
		String uuid = UUID.randomUUID().toString();
		InternalService inter = new InternalService();
		inter.internalservice(uuid,i);

		ExternalService ext = new ExternalService();
		ext.externalservice(uuid,i); 
		}
	}
}
