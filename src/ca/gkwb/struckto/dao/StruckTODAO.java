package ca.gkwb.struckto.dao;

import java.sql.Date;

import ca.gkwb.struckto.exception.GenericDBException;
import ca.gkwb.struckto.exception.NoRowFoundException;
import ca.gkwb.struckto.vo.StruckTOIncidentVO;

public interface StruckTODAO {
	
	public StruckTOIncidentVO queryById(String id) throws NoRowFoundException, GenericDBException;
	
	public void update(StruckTOIncidentVO stVO) throws GenericDBException;
	
	public void insert(StruckTOIncidentVO stVO) throws GenericDBException;
	
	public void queryAll(Date startDate, Date endDate, String severity) 
			throws NoRowFoundException, GenericDBException;

}
