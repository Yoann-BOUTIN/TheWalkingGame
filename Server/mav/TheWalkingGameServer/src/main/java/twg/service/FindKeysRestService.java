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

import twg.dao.FindKeysDao;
import twg.entities.FindKeys;

@Component
@Path("/findkeys")
public class FindKeysRestService {

	@Autowired
	private FindKeysDao findKeysDao;
	
	/**
	 * Return data with an id given
	 * 
	 * @param id
	 * @return Findkeys data
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@GET
	@Path("/id")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findById(@QueryParam("id") String id) throws JsonGenerationException, JsonMappingException, IOException {
		long idLong = Long.parseLong(id);
		FindKeys dataById = findKeysDao.getDataById(idLong);
		
		if (dataById != null) {
			return Response
					.status(200)
					.entity(dataById)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS")
					.build();
		} else {
			return Response
					.status(404)
					.entity("The id " + idLong + " does not exist")					
					.build();
		}
	}
	
/************************************************************
 * 							UPDATE							*
 ************************************************************/
	@PUT
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public Response updateFindKeysById(FindKeys findKeys) {
		String message;
		int status;
		if (findKeys.getId() != null) {
			findKeysDao.updateFindKeys(findKeys);
			status = 200; // OK
			message = "Game Find Keys has been updated";
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