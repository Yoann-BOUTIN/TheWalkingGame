package twg.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import twg.dao.GameDao;
import twg.entities.Game;

public class GameDaoJPA2Impl implements GameDao {
	
	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	public Game getGame(Long id) {
		String qlString = "SELECT g FROM Game g WHERE g.idTeamInviteur=?1 or g.idTeamInvite=?1";
		TypedQuery<Game> query = entityManager.createQuery(qlString, Game.class);
		query.setParameter(1, id);
		return query.getSingleResult();
	}

	public void addGame(Game game) {
		entityManager.persist(game);
		entityManager.flush();
		
	}
	
	public Long deleteGameById(Long id) {
		Game game = entityManager.find(Game.class, id);
		entityManager.remove(game);
		
		return 1L;
	}
}