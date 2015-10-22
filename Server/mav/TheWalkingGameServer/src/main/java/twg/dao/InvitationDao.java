package twg.dao;

import java.util.List;
import java.util.Map;

import twg.entities.Invitation;

public interface InvitationDao {
	
	public Map<String, List<Invitation>> getInvitationsByInvitePseudo(String invite);
	public void setInvitation(Invitation invitation);
	public void deleteInvitation(int id);
	public void deleteAllInvitationsByUser(String invite);
	public Map<String, Boolean> existInvitation(String inviteur, String invite);
	public void updateInvitation(String inviteur, String invite);

}
