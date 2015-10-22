package twg.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twg.dao.ObjectiveDao;
import twg.entities.Objective;

@Component
@Path("/objective")
public class ObjectiveRestService {
	@Autowired
	private ObjectiveDao objectiveDao;
	
	@GET
	@Path("/id")
	@Produces({ MediaType.APPLICATION_JSON})
	public Objective findObjectiveById(@QueryParam("id") int id) throws JsonGenerationException, JsonMappingException, IOException {

		return objectiveDao.getObjectiveById(id);
		
		}
	
	@GET
	@Path("/allObjective")
	@Produces({ MediaType.APPLICATION_JSON})
	public Map<String, List<Objective>> findAllObjective() throws JsonGenerationException, JsonMappingException, IOException {

		return objectiveDao.getAllObjective();
		
		}

}
