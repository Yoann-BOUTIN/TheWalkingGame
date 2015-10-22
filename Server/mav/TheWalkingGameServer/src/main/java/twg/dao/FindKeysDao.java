package twg.dao;

import twg.entities.FindKeys;

/**
 * 
 * @author Kevin
 *
 */
public interface FindKeysDao {

	public FindKeys getDataById(Long id);
	public int updateFindKeys(FindKeys findKeys);
	
}
