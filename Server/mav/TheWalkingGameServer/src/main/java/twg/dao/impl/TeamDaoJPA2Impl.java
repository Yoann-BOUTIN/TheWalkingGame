package twg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import twg.dao.TeamDao;
import twg.entities.Team;

public class TeamDaoJPA2Impl implements TeamDao {
	
	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	public Team getTeam(String pseudo) {
		String qlString = "SELECT t FROM Team t WHERE t.joueur1=?1 or t.joueur2=?1 or t.joueur3=?1 or t.joueur4=?1";
		TypedQuery<Team> query = entityManager.createQuery(qlString, Team.class);
		query.setParameter(1, pseudo);
		return query.getSingleResult();
	}

	public void addTeam(Team team) {
		entityManager.persist(team);
		entityManager.flush();
		
	}
	
	public Long deleteTeamById(Long id) {
		Team team = entityManager.find(Team.class, id);
		entityManager.remove(team);
		
		return 1L;
	}
	
	public Map<String, Boolean> deleteTeamByPseudo(String pseudo){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			Query query = entityManager.createNativeQuery("DELETE FROM Team WHERE joueur_1 = ?1 or joueur_2 =?1 or joueur_3=?1 or joueur_4=?1");
			query.setParameter(1, pseudo);
			query.executeUpdate();
			map.put("success", true);
			return map;
		} catch (NoResultException e) {
			map.put("success", false);
			return map;
		}
	}
	
	public Map<String, Boolean> teamExist(String joueur){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
		String qlString = "SELECT t FROM Team t WHERE t.joueur1=?1 or t.joueur2=?1 or t.joueur3=?1 or t.joueur4=?1";
		TypedQuery<Team> query = entityManager.createQuery(qlString, Team.class);
		query.setParameter(1, joueur);
		if(query.getSingleResult() == null){
			map.put("exist", false);
		}
		else{
			map.put("exist", true);
		}
		return map;
		} catch (NoResultException e) {
			map.put("exist", false);
			return map;
		}
	
	}

	public Map<String, Boolean> updateTeamJoueur2(String joueur2, String joueur1) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try
		{
		Query query = entityManager.createNativeQuery("UPDATE Team SET joueur_2 = ?1 WHERE joueur_1= ?2");	
		query.setParameter(1, joueur2);
		query.setParameter(2, joueur1);
		query.executeUpdate();
		map.put("success", true);
		return map;
		}catch(Exception e){
			map.put("success", false);
			return map;
		}
		
	}

	public Map<String, Boolean> updateTeamJoueur3(String joueur3, String joueur1) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try
		{
		Query query = entityManager.createNativeQuery("UPDATE Team SET joueur_3 = ?1 WHERE joueur_1= ?2");	
		query.setParameter(1, joueur3);
		query.setParameter(2, joueur1);
		query.executeUpdate();
		map.put("success", true);
		return map;
		}catch(Exception e){
			map.put("success", false);
			return map;
		}
		
	}

	public Map<String, Boolean> updateTeamJoueur4(String joueur4, String joueur1) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try
		{
		Query query = entityManager.createNativeQuery("UPDATE Team SET joueur_4 = ?1 WHERE joueur_1= ?2");	
		query.setParameter(1, joueur4);
		query.setParameter(2, joueur1);
		query.executeUpdate();
		map.put("success", true);
		return map;
		}catch(Exception e){
			map.put("success", false);
			return map;
		}
		
	}

}
