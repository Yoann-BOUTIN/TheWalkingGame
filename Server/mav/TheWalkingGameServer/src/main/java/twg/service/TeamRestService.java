package twg.service;

import java.io.IOException;
import java.util.Map;

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

import twg.dao.TeamDao;
import twg.entities.Team;

@Component
@Path("/team")
public class TeamRestService {
	
	@Autowired
	private TeamDao teamDao;

	@POST
	@Path("/new")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public Response createInvitation(Team team) {
		teamDao.addTeam(team);
		return Response.status(201)
				.entity("A new team has been created").build();
	}
	
	@GET
	@Path("/byPseudo")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Team getInvitationByPseudo(@QueryParam("pseudo") String pseudo) throws JsonGenerationException, JsonMappingException, IOException {
		
		return teamDao.getTeam(pseudo);
	}
	
	@DELETE
	@Path("/delete")
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Transactional
	public Response deleteTeamById(@QueryParam("id") Long id) {
		if (teamDao.deleteTeamById(id) == 1) {
			return Response.status(204).entity("Team have been successfully removed").build();
		} else {
			return Response
					.status(404)
					.entity("team with the id " + id
							+ " is not present in the database").build();
		}
	}
	
	@DELETE
	@Path("/deleteByPseudo")
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Transactional
	public Response deleteTeamByPseudo(@QueryParam("pseudo") String pseudo) {
		teamDao.deleteTeamByPseudo(pseudo);
		return Response.status(204).entity("Team have been successfully removed").build();
	}
	
	@GET
	@Path("/exist")
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Transactional
	public Map<String, Boolean> teamExist(@QueryParam("pseudo") String joueur) {
		return teamDao.teamExist(joueur);
	}
	
	@GET
	@Path("/updateJoueur2")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public Map<String, Boolean> updateJoueur2(@QueryParam("joueur2") String joueur2,@QueryParam("joueur1") String joueur1) {
		return teamDao.updateTeamJoueur2(joueur2, joueur1);
	}
	
	@GET
	@Path("/updateJoueur3")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public Map<String, Boolean> updateJoueur3(@QueryParam("joueur3") String joueur3,@QueryParam("joueur1") String joueur1) {
		return teamDao.updateTeamJoueur3(joueur3, joueur1);
	}
	
	@GET
	@Path("/updateJoueur4")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public Map<String, Boolean> updateJoueur4(@QueryParam("joueur4") String joueur4,@QueryParam("joueur1") String joueur1) {
		return teamDao.updateTeamJoueur4(joueur4, joueur1);
	}
	
}
