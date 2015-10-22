package twg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import twg.dao.InvitationDao;
import twg.entities.Invitation;

public class InvitationDaoJPA2Impl implements InvitationDao{

	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;
	
	public Map<String, List<Invitation>> getInvitationsByInvitePseudo(String invite) {
		Map<String, List<Invitation>> map = new HashMap<String, List<Invitation>>();
		String qlString = "SELECT i FROM Invitation i WHERE i.invite=?1";
		TypedQuery<Invitation> query = entityManager.createQuery(qlString, Invitation.class);
		query.setParameter(1, invite);
		map.put("invitations", query.getResultList());
		return map;
	}

	public void setInvitation(Invitation invitation) {
		entityManager.persist(invitation);
		entityManager.flush();
		
	}

	public void deleteInvitation(int id) {
		Query query = entityManager.createNativeQuery("DELETE FROM invitation WHERE id = ?1");
		query.setParameter(1, id);
		query.executeUpdate();	
		
	}

	public void deleteAllInvitationsByUser(String invite) {
		Query query = entityManager.createNativeQuery("DELETE FROM invitation WHERE invite = ?1");
		query.setParameter(1, invite);
		query.executeUpdate();	
		
	}

	public Map<String, Boolean> existInvitation(String inviteur, String invite) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			String qlString = "SELECT i FROM Invitation i WHERE i.inviteur = ?1 and i.invite = ?2";
			TypedQuery<Invitation> query = entityManager.createQuery(qlString, Invitation.class);		
			query.setParameter(1, inviteur);
			query.setParameter(2, invite);

			if(query.getSingleResult().toString() != null){
				map.put("exist", true);
			}
			else{
				map.put("exist", false);
			}
			return map;
			
		} catch (NoResultException e) {
			map.put("exist", false);
			return map;
		}
	}

	public void updateInvitation(String ancienInviteur, String newInviteur) {
		Query query = entityManager.createNativeQuery("UPDATE Invitation SET inviteur = ?1 WHERE inviteur= ?2");	
		query.setParameter(1, newInviteur);
		query.setParameter(2, ancienInviteur);
		query.executeUpdate();
		
	}

}
