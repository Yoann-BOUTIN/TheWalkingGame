package twg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import twg.dao.UserDao;
import twg.entities.User;

public class UserDaoJPA2Impl implements UserDao {

	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	


	public Map<String, List<User>> getUser() {
		Map<String, List<User>> map = new HashMap<String, List<User>>();
		String qlString = "SELECT u FROM User u";
		TypedQuery<User> query = entityManager.createQuery(qlString, User.class);		
		map.put("users", query.getResultList());
		return map;
	}


	public User getUserById(Long id) {
		try {
			String qlString = "SELECT u FROM User u WHERE u.id = ?1";
			TypedQuery<User> query = entityManager.createQuery(qlString, User.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Long deleteUserById(Long id) {
		User user = entityManager.find(User.class, id);
		entityManager.remove(user);
		
		return 1L;
	}

	
	public Long createUser(User user) {
		entityManager.persist(user);
		entityManager.flush();//force insert to receive the id of the user
		
		return user.getId();
	}

	public int updateUser(User user) {
		
		entityManager.merge(user);
		
		return 1; 
	}

	public void deleteUsers() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE user");		
		query.executeUpdate();		
	}

	public User getUserByPseudo(String pseudo) {
		try {
			String qlString = "SELECT u FROM User u WHERE u.userPseudo = ?1";
			TypedQuery<User> query = entityManager.createQuery(qlString, User.class);		
			query.setParameter(1, pseudo);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Map<String, Boolean> userExist(String pseudo) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			String qlString = "SELECT u FROM User u WHERE u.userPseudo = ?1";
			TypedQuery<User> query = entityManager.createQuery(qlString, User.class);		
			query.setParameter(1, pseudo);

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

}
