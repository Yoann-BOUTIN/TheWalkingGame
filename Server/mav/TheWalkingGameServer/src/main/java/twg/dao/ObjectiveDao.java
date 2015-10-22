package twg.dao;

import java.util.List;
import java.util.Map;

import twg.entities.Objective;

public interface ObjectiveDao {

	public Map<String, List<Objective>> getAllObjective();
	
	public Objective getObjectiveById(int id);
}
