package ca.gkwb.struckto.interaction.bo.test;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import ca.gkwb.struckto.interaction.bo.InteractionBO;

public class InteractionBOTest {

	private InteractionBO interactionBO;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		interactionBO = (InteractionBO)context.getBean("InteractionBO");	
	}

	@After
	public void tearDown() throws Exception {
	}	
	
	@Test
	public void testSendLocationUpdate() {
		try {
			interactionBO.sendLocationInteractionTweet("Test1 + Test 2", 0, 1, 2, 3, "Gingerk1d");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.state(false);
		}
	}

}

