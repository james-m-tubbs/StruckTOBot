package ca.gkwb.struckto.interaction.bo;

import ca.gkwb.struck.interaction.dao.InteractionVO;
import ca.gkwb.struckto.exception.FatalException;

public interface InteractionBO {
	
	public int processInteractions() throws FatalException;
	
	public int processOneInteraction(InteractionVO iVO) throws FatalException;
	
	public void completeInteraction(InteractionVO iVO) throws FatalException;
	
	public String generateReportByForId(String status) throws FatalException;
	
	public String generateRecentIncidentReprot(String status) throws FatalException;
	
	public String generateLocationReport(String status) throws FatalException;

}
