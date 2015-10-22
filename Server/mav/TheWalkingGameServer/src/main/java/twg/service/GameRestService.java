package twg.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import twg.dao.GameDao;
import twg.entities.Game;

@Component
@Path("/game")
public class GameRestService {
	
	@Autowired
	private GameDao gameDao;

	@POST
	@Path("/new")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public Response createGame(Game game) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>salut");
		gameDao.addGame(game);
		return Response.status(201)
				.entity("A new game has been created").build();
	}
	
	@GET
	@Path("/byId")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Game getInvitationByPseudo(@QueryParam("id") Long id) throws JsonGenerationException, JsonMappingException, IOException {
		return gameDao.getGame(id);
	}
	
	@DELETE
	@Path("/delete")
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Transactional
	public Response deleteGameById(@QueryParam("id") Long id) {
		if (gameDao.deleteGameById(id) == 1) {
			return Response.status(204).entity("Game have been successfully removed").build();
		} else {
			return Response
					.status(404)
					.entity("game with the id " + id
							+ " is not present in the database").build();
		}
	}
}