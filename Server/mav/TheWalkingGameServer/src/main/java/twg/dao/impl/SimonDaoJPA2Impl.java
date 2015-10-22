package twg.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import twg.dao.SimonDao;
import twg.entities.Simon;

public class SimonDaoJPA2Impl implements SimonDao {

	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	public int updateSimon(Simon simon) {
		entityManager.merge(simon);
		
		return 1; 
	}
	
	public Simon getDataById(Long id) {
		try {
			String qlString = "SELECT s FROM Simon s WHERE s.id = ?1";
			TypedQuery<Simon> query = entityManager.createQuery(qlString, Simon.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}