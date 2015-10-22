
package twg.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import twg.dao.FindKeysDao;
import twg.entities.FindKeys;

/**
 * @author kevin
 */
public class FindKeysDaoJPA2Impl implements FindKeysDao{

	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;
	
	public int updateFindKeys(FindKeys findkeys) {
		entityManager.merge(findkeys);
		return 1; 
	}
	
	public FindKeys getDataById(Long id) {
		try {
			String qlString = "SELECT f FROM FindKeys f WHERE f.id = ?1";
			TypedQuery<FindKeys> query = entityManager.createQuery(qlString, FindKeys.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
