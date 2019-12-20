package com.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExternalService {

	private Logger logAnalytics = LogManager.getLogger("ExternalServiceFile");
	public  void externalservice(String uuid,int i) {
		// TODO Auto-generated method stub
		logAnalytics.info(uuid+"#Hi am log 2#externalservice#"+i);
		/*
		 * logAnalytics.error(uuid+"#Hi am log 2#"+i);
		 * logAnalytics.warn(uuid+"#Hi am log 2#"+i);
		 * logAnalytics.debug(uuid+"#Hi am log 2#"+i);
		 */
	}
}
