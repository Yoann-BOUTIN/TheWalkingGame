package twg.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import twg.dao.GameFinishedDao;
import twg.entities.GameFinished;

public class GameFinishedDaoJPA2Impl implements GameFinishedDao{
	
	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	public GameFinished gameFinishedByPseudo(String pseudo) {
		try {
			String qlString = "SELECT g FROM GameFinished g WHERE g.joueur = ?1";
			TypedQuery<GameFinished> query = entityManager.createQuery(qlString, GameFinished.class);		
			query.setParameter(1, pseudo);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean deleteGameFinishedByPseudo(String pseudo) {
		try {
			Query query = entityManager.createNativeQuery("DELETE FROM GameFinished WHERE joueur = ?1");
			query.setParameter(1, pseudo);
			query.executeUpdate();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	public void createGameFinished(GameFinished gameFinished) {
		entityManager.persist(gameFinished);
		entityManager.flush();
		
	}

	public int updatefinish(GameFinished gameFinished) {
		entityManager.merge(gameFinished);
		
		return 1; 
	}

}
