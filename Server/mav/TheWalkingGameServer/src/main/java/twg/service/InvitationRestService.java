package twg.service;

import java.io.IOException;
import java.util.List;
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

import twg.dao.InvitationDao;
import twg.entities.Invitation;

@Component
@Path("/invitation")
public class InvitationRestService {

	@Autowired
	private InvitationDao invitationDao;
	
	@POST
	@Path("/new")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public Response createInvitation(Invitation invitation) {
		invitationDao.setInvitation(invitation);
		return Response.status(201)
				.entity("A new invitation has been created").build();
	}
	
	@GET
	@Path("/byInvite")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Map<String, List<Invitation>> getInvitationByPseudo(@QueryParam("invite") String invite) throws JsonGenerationException, JsonMappingException, IOException {
		
		return invitationDao.getInvitationsByInvitePseudo(invite); 
	}
	
	@GET
	@Path("/exist")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Map<String, Boolean> invitationExist(@QueryParam("inviteur") String inviteur, @QueryParam("invite") String invite) throws JsonGenerationException, JsonMappingException, IOException {
		return invitationDao.existInvitation(inviteur, invite);
	}
	
	@DELETE
	@Path("/delete")
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Transactional
	public Response deleteUserById(@QueryParam("id") int id) {
		invitationDao.deleteInvitation(id);
		return Response.status(201)
				.entity("A invitation has been delated").build();
	}
	
	@DELETE
	@Path("/deleteAllByInvite")
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Transactional
	public Response deleteAllByInvite(@QueryParam("invite") String invite) {
		invitationDao.deleteAllInvitationsByUser(invite);;
		return Response.status(201)
				.entity("A invitation has been delated").build();
	}
	
	@GET
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public void updateInvitation(@QueryParam("ancienInviteur") String ancienInviteur,@QueryParam("newInviteur") String newInviteur) {
		invitationDao.updateInvitation(ancienInviteur, newInviteur);
	}
	
}
