package ca.gkwb.struckto.main;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.gkwb.struckto.bo.StruckTOBO;
import ca.gkwb.struckto.exception.FatalException;

public class StruckTOMain {
	
	public static void main(String[] args) {
		
		Logger logger = Logger.getLogger(ca.gkwb.struckto.main.StruckTOMain.class);
		StruckTOBO stBO;
		final String targetAcctProp = "target_acct";
		final String batchSizeProp = "batch_size";
		final String hashtagProp = "hashtags";
		
		try {
			//default values
			int batchSize = 100;
			String targetAcct = "TPSOperations";
			List<String> hashTags = new ArrayList<String>();
			
			logger.debug("Initializing StruckTOBO");
			ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
			stBO = (StruckTOBO)context.getBean("StruckTOBO");
			
			if (args.length > 0) {
				logger.debug("Resolving Properties from: "+args[0]);
				Properties props = new Properties();
				try {
					InputStream is = ca.gkwb.struckto.main.StruckTOMain.class.getResourceAsStream(args[0]);
					props.load(is);
					
					targetAcct = props.getProperty(targetAcctProp);
					batchSize = new Integer(props.getProperty(batchSizeProp));
					//TODO load hashTag arraylist from comma delimited string
					
				} catch (Exception e) {
					e.printStackTrace();
					throw new FatalException();
				}
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
