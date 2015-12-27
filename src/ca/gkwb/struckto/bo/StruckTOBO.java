package ca.gkwb.struckto.bo;


import java.util.List;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import ca.gkwb.struckto.vo.StruckTOIncidentVO;
import ca.gkwb.struckto.vo.StruckTOLocationVO;

public interface StruckTOBO {
	
	public int queryAndProcess(String targetAcct, int size, List<String> hashTags) throws FatalException;
	
	/**
	 * Polls twitter for new incidents and returns a list of struckTOincidentsVOs. Builds struckTOIncidentVOs from
	 * google maps API.
	 * 
	 * @author gingerk1d
	 * @date 2015-12-02
	 * @return
	 * @throws FatalException
	 * @throws WarnException
	 */
	public List<StruckTOIncidentVO> getNewIncidents() throws FatalException, WarnException;
	
	/**
	 * Processes a single incident into the struckTO db and google maps API. Returns true if processed
	 * successfully.
	 * 
	 * @author gingerk1d
	 * @date 2015-12-02
	 * @param stVO
	 * @return boolean 
	 * @throws WarnException
	 * @throws FatalException
	 */
	public boolean processIncident(StruckTOIncidentVO stVO) throws WarnException, FatalException;
	
	/**
	 * Returns a StruckTOLocationVO that matches the input StruckTOIncidentVO.
	 * 
	 * @author tubbsj
	 * @date 2015-12-02
	 * @param stVO
	 * @return StruckTOLocationVO
	 * @throws WarnException
	 * @throws FatalException
	 */
	public StruckTOLocationVO getLocationVOForIncident(StruckTOIncidentVO stVO) throws WarnException, FatalException;
	
	public void sendTwitterUpdate() throws WarnException;
	
	public String buildHashtagString(List<String> htags);

}
