package twg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import twg.dao.ObjectiveDao;
import twg.entities.Objective;

public class ObjectiveDaoJPA2Impl implements ObjectiveDao{
	
	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	public Map<String, List<Objective>> getAllObjective() {
		Map<String, List<Objective>> map = new HashMap<String, List<Objective>>();
		try {
			String qlString = "SELECT o FROM Objective o";
			TypedQuery<Objective> query = entityManager.createQuery(qlString, Objective.class);		

			map.put("objectives", query.getResultList());
			return map;
		} catch (NoResultException e) {
			return null;
		}
		
	}

	public Objective getObjectiveById(int id) {
		try {
			String qlString = "SELECT o FROM Objective o WHERE o.id = ?1";
			TypedQuery<Objective> query = entityManager.createQuery(qlString, Objective.class);		
			query.setParameter(1, id);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

}
