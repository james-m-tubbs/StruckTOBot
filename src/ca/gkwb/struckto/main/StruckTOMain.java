package ca.gkwb.struckto.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.gkwb.struckto.bo.StruckTOBO;
import ca.gkwb.struckto.exception.FatalException;

public class StruckTOMain {
	
	public static void main(String[] args) {
		
		Logger logger = Logger.getLogger(ca.gkwb.struckto.main.StruckTOMain.class);
		StruckTOBO stBO;
		
		try {
			//default values
			int batchSize = 100;
			String targetAcct = "TPSOperations";
			List<String> hashTags = new ArrayList<String>();
			
			logger.debug("Initializing StruckTOBO");
			ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
			stBO = (StruckTOBO)context.getBean("StruckTOBO");
			
			if (args.length > 0) {
				logger.debug("Target Acct: "+args[0]);
				logger.debug("Batch Size: "+args[1]);
				logger.debug("Hashtag List: "+args[2]);
				
				targetAcct = args[0];
				batchSize = new Integer(args[1]);
				hashTags = stBO.parseHashtags(args[2]);
			}
				
			logger.debug("Running with params: batchSize="+batchSize+", targetAcct="+targetAcct+", hashTags="+hashTags);
			int results = stBO.queryAndProcess(targetAcct, batchSize, hashTags);
			logger.info("Retweeted "+results+" new incidents");
		} catch (FatalException e) {
			e.printStackTrace();
			logger.error("Processed Crashed with Fatal Error", e);
		}
	}

}
