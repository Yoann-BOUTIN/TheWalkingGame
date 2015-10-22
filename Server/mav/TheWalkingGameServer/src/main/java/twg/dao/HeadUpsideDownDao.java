package twg.dao;

import twg.entities.HeadUpsideDown;

/**
 * 
 * @author Marc
 *
 */
public interface HeadUpsideDownDao {

	public HeadUpsideDown getDataById(Long id);
	
	public int updateHeadUpsideDown(HeadUpsideDown headUpsideDown);
}
