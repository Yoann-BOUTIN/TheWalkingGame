package twg.dao;

import java.util.List;
import java.util.Map;

import twg.entities.User;

/**
 * 
 * @author zizou
 */
public interface UserDao {
	
	public Map<String, List<User>> getUser();
	
	
	/**
	 * Returns a user given its id
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(Long id);
	
	public User getUserByPseudo(String pseudo);
	
	public Map<String, Boolean> userExist(String pseudo);

	public Long deleteUserById(Long id);

	public Long createUser(User user);

	public int updateUser(User user);

	/** removes all users */
	public void deleteUsers();


}
