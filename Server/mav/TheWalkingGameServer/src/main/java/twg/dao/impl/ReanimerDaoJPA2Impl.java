package twg.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import twg.dao.ReanimerDao;
import twg.entities.Reanimer;

public class ReanimerDaoJPA2Impl implements ReanimerDao {

	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;
	
	public int updateReanimer(Reanimer reanimer) {
		entityManager.merge(reanimer);
		
		return 1; 
	}
	
	public Reanimer getDataById(long id) {
		try {
			String qlString = "SELECT r FROM Reanimer r WHERE r.id = ?1";
			TypedQuery<Reanimer> query = entityManager.createQuery(qlString, Reanimer.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
