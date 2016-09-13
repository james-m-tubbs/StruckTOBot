package ca.gkwb.struckto.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.gkwb.struckto.bo.StruckTOBO;
import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.interaction.bo.InteractionBO;

public class StruckTOResponderMain {
	
	public static void main(String[] args) {
		
		Logger logger = Logger.getLogger(ca.gkwb.struckto.main.StruckTOMain.class);
		InteractionBO iBO;
		
		try {
			//default values
			int batchSize = 100;
			
			logger.debug("Initializing StruckTOBO Responder");
			ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
			iBO = (InteractionBO)context.getBean("InteractionBO");
			
			if (args.length > 0) {
				logger.debug("Batch Size: "+args[0]);
				
				batchSize = new Integer(args[0]);
			}
				
			logger.debug("Running with params: batchSize="+batchSize);
			int results = iBO.processInteractions(batchSize);
			logger.info("Processed "+results+" new interactions");
		} catch (FatalException e) {
			e.printStackTrace();
			logger.error("Processed Crashed with Fatal Error", e);
		}
	}
}

