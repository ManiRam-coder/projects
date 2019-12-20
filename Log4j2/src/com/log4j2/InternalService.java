package com.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InternalService {


//	private static final Logger logCommon = LogManager.getLogger("commons-log");
	private Logger logCommon = LogManager.getLogger("InternalServiceFile");
    
	
	
	public  void internalservice(String uuid,int i) {
		logCommon.info(uuid+"#Hi am log 1#internalservice#"+i);
		/*
		 * logCommon.debug(uuid+"#Hi am log 1#"+i);
		 * logCommon.error(uuid+"#Hi am log 1#"+i);
		 * logCommon.warn(uuid+"#Hi am log 1#"+i);
		 */
		
	}

}
