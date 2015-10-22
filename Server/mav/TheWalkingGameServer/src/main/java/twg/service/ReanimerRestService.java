package twg.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import twg.dao.ReanimerDao;
import twg.entities.Reanimer;

@Component
@Path("/reanimer")
public class ReanimerRestService {

	@Autowired
	private ReanimerDao reanimerDao;
	
	/**
	 * Return the data of this prototype
	 * 
	 * @param prototype
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@GET
	@Path("/id")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findById(@QueryParam("id") String id) throws JsonGenerationException, JsonMappingException, IOException {
		long idLong = Long.parseLong(id);
		Reanimer dataById = reanimerDao.getDataById(idLong);
		
		if (dataById != null) {
			return Response
					.status(200)
					.entity(dataById)
					//.entity(scoreTargetByPrototype.getScoreTarget())
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS")
					.build();
		} else {
			return Response
					.status(404)
					.entity("The prototype " + idLong + " does not exist")					
					.build();
		}
	}

	
	/************************************ UPDATE ************************************/
	/**
	 * 
	 * @return
	 */
	@Path("/update")
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response updateReanimerById(Reanimer reanimer) {
		String message;
		int status;
		if (reanimer.getId() != null) {
			reanimerDao.updateReanimer(reanimer);
			status = 200; // OK
			message = "Game Reanimer has been updated";
		} else {
			status = 406; // Not acceptable
			message = "The information you provided is not sufficient to perform either an UPDATE or "
					+ " an INSERTION of the new user resource <br/>"
					+ " If you want to UPDATE please make sure you provide an existent <strong>id</strong> <br/>"
					+ " If you want to insert a new user please provide at least a <strong>title</strong> and the <strong>feed</strong> for the user resource";
		}
		return Response.status(status).entity(message).build();
	}
}
