package ca.gkwb.struckto.location;

import ca.gkwb.struckto.exception.GenericDBException;

public interface LocationDAO {
	
	/**
	 * Inserts a single StruckTOLocationVO based on input object
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param LocationVO
	 * @throws GenericDBException
	 */
	public void insertStruckTOLocation(LocationVO stlVO) throws GenericDBException;;

	/**
	 * Updates a single StruckTOLocationVO based on input object
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param LocationVO
	 * @throws GenericDBException
	 */
	public void updateStruckTOLocation(LocationVO stlVO) throws GenericDBException;;
	
	/**
	 * Queries single StruckTOLocationVO based on input ID
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-03
	 * @param int
	 * @return StruckTOLocationVO
	 * @throws GenericDBException
	 */
	public LocationVO queryOneLocation(int id) throws GenericDBException;

	
	public LocationVO queryNewestLocation() throws GenericDBException;

	public LocationVO queryByLatLng(Double lat, Double lng) throws GenericDBException; 
}
