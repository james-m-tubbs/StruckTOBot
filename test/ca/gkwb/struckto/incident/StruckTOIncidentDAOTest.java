package ca.gkwb.struckto.incident;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import ca.gkwb.struckto.incident.StruckTOIncidentDAO;
import ca.gkwb.struckto.incident.StruckTOIncidentVO;

public class StruckTOIncidentDAOTest {

	StruckTOIncidentDAO stiDAO;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		System.setProperty("spring.profiles.active", "test1");
		stiDAO = (StruckTOIncidentDAO)context.getBean("struckTOIncidentDAO");
	}	
	
	@Test
	public void testQueryById() {
		try {
			StruckTOIncidentVO stiVO = stiDAO.queryById(1);
			Assert.assertTrue(stiVO.getIncidentId() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	@Transactional
	public void testInsert() {
		try {
			//TODO
			stiDAO.insert(null);
			
			StruckTOIncidentVO stiVO = stiDAO.queryById(1);
			Assert.assertTrue(stiVO.getIncidentId() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}	
}
