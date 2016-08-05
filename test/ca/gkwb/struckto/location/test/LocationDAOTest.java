package ca.gkwb.struckto.location.test;

import java.sql.Date;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import ca.gkwb.struckto.location.LocationDAO;
import ca.gkwb.struckto.location.LocationVO;

public class LocationDAOTest {
	LocationDAO stlDAO;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		stlDAO = (LocationDAO)context.getBean("struckTOLocationDAO");	
	}

	@Test
	@Transactional
	public void testInsert() {
		Calendar cal = Calendar.getInstance();
		Date sqlDate = new Date(cal.getTime().getTime());
		
		LocationVO insertVO = new LocationVO(
				-1,
				12.1234,
				43.4321,
				"Toronto",
				"ON",
				"Canada",
				sqlDate,
				"StruckTOBot"
				);	
		try {
		stlDAO.insertStruckTOLocation(insertVO);
		
		LocationVO stlVO = stlDAO.queryNewestLocation();
		Assert.assertTrue(stlVO.getId() > 1);
		} catch (Exception e) {
			logger.error("Failed LocationDAO insert/query", e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testQueryLatLng() {
		try {
			LocationVO stlVO = stlDAO.queryByLatLng(12.124125, 13.131255);
			logger.debug("Returned By Lat/Lng: " + stlVO);
			Assert.assertNotNull(stlVO);
		} catch (Exception e) {
			logger.error("Failed LocationDAO insert/query", e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testQueryNewest() {
		try {
			LocationVO stlVO = stlDAO.queryNewestLocation();
			logger.debug("Returned By Newest: " + stlVO);
			Assert.assertNotNull(stlVO);
		} catch (Exception e) {
			logger.error("Failed LocationDAO queryByNewest", e);
			Assert.fail(e.getMessage());
		}
	}	
}
