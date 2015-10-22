package twg.dao;


import twg.entities.Reanimer;

/**
 * 
 * @author Marc
 *
 */
public interface ReanimerDao {

	public Reanimer getDataById(long id);
	
	public int updateReanimer(Reanimer reanimer);
}