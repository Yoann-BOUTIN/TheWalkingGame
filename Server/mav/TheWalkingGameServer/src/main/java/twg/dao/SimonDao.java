package twg.dao;

import twg.entities.Simon;

/**
 * 
 * @author Marc
 *
 */
public interface SimonDao {
	
	public Simon getDataById(Long id);
	
	public int updateSimon(Simon simon);
}