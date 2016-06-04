package ca.gkwb.struckto.dao;

import java.util.List;

import ca.gkwb.struckto.exception.GenericDBException;
import ca.gkwb.struckto.exception.NoRowFoundException;
import ca.gkwb.struckto.vo.StruckTOIncidentVO;

public interface StruckTOIncidentDAO {
	
	/**
	 * Queries a single IncidentVO based on input ID. Returns null if no results.
	 *
	 * @author g1ng3rk1d
	 * @date 2016-06-03 
	 * @param id
	 * @return StruckTOIncidentVO
	 * @throws GenericDBException
	 */
	public StruckTOIncidentVO queryById(String id) throws GenericDBException;
	
	/**
	 * Queries a single IncidentVO based on input tweet ID. Returns null if no results.
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param id
	 * @return StruckTOIncidentVO
	 * @throws GenericDBException
	 */
	public StruckTOIncidentVO queryByTwitterId(String id) throws NoRowFoundException, GenericDBException;
	
	/**
	 * Updates a single incident based on the input StruckTOIncidentVO. Requires stVO.id.
	 *
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param stVO
	 * @throws GenericDBException
	 */
	public void update(StruckTOIncidentVO stVO) throws GenericDBException;
	
	/**
	 * Inserts a single struckTincidentOIncidentVO based on the input StruckTOIncidentVO.
	 * Requires stVO.id.
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param stVO
	 * @throws GenericDBException
	 */
	public void insert(StruckTOIncidentVO stVO) throws GenericDBException;
	
	/**
	 * Queries a list of StruckTOIncidentVOs based on the input variable. If fields are null
	 * query matches on field == field. 
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param stVO
	 * @return
	 * @throws NoRowFoundException
	 * @throws GenericDBException
	 */
	public List<StruckTOIncidentVO> queryAll(StruckTOIncidentVO stVO)	 
			throws NoRowFoundException, GenericDBException;

}
