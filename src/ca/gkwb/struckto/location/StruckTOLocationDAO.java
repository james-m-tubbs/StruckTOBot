package ca.gkwb.struckto.location;

import ca.gkwb.struckto.exception.GenericDBException;

public interface StruckTOLocationDAO {
	
	/**
	 * Inserts a single StruckTOLocationVO based on input object
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param StruckTOLocationVO
	 * @throws GenericDBException
	 */
	public void insertStruckTOLocation(StruckTOLocationVO stlVO) throws GenericDBException;;

	/**
	 * Updates a single StruckTOLocationVO based on input object
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param StruckTOLocationVO
	 * @throws GenericDBException
	 */
	public void updateStruckTOLocation(StruckTOLocationVO stlVO) throws GenericDBException;;
	
	/**
	 * Queries single StruckTOLocationVO based on input ID
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param int
	 * @return StruckTOLocationVO
	 * @throws GenericDBException
	 */
	public StruckTOLocationVO queryOneLocation(int id) throws GenericDBException; 
}
