package twg.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import twg.dao.HeadUpsideDownDao;
import twg.entities.HeadUpsideDown;

public class HeadUpsideDownDaoJPA2Impl implements HeadUpsideDownDao {

	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	public int updateHeadUpsideDown(HeadUpsideDown headUpsideDown) {
		entityManager.merge(headUpsideDown);
		
		return 1; 
	}
	
	public HeadUpsideDown getDataById(Long id) {
		try {
			String qlString = "SELECT h FROM HeadUpsideDown h WHERE h.id = ?1";
			TypedQuery<HeadUpsideDown> query = entityManager.createQuery(qlString, HeadUpsideDown.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
