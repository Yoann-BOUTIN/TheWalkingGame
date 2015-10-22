package twg.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import twg.dao.GameFinishedDao;
import twg.entities.GameFinished;


@Component
@Path("/gameFinished")
public class GameFinishedRestService {
	
	@Autowired
	private GameFinishedDao gameFinishedDao;
	
	@GET
	@Path("/byPseudo")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public GameFinished getGameFinished(@QueryParam("pseudo") String pseudo){
		return gameFinishedDao.gameFinishedByPseudo(pseudo);
	}
	
	@POST
	@Path("/new")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public Response createGameFinish(GameFinished gameFinished) {
		gameFinishedDao.createGameFinished(gameFinished);

		return Response.status(201)
				.entity("A new GameFinished has been created").build();
	}
	
	@DELETE
	@Path("/delete")
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Transactional
	public Response deleteByPseudo(@QueryParam("pseudo") String pseudo) {
		if (gameFinishedDao.deleteGameFinishedByPseudo(pseudo)) {
			return Response.status(204).entity("GameFinished have been successfully removed").build();
		} else {
			return Response
					.status(404)
					.entity("GameFinished with the pseudo " + pseudo
							+ " is not present in the database").build();
		}
	}
	
	@Path("/update")
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response update(GameFinished gameFinished){
		gameFinishedDao.updatefinish(gameFinished);
		return Response.status(204).entity("Updated").build();
	}

}
