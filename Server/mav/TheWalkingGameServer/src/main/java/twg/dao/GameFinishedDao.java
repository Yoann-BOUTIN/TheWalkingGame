package twg.dao;

import twg.entities.GameFinished;

public interface GameFinishedDao {
	
	public void createGameFinished(GameFinished gameFinished);
	
	public GameFinished gameFinishedByPseudo(String pseudo);
	
	public boolean deleteGameFinishedByPseudo(String pseudo);
	
	public int updatefinish(GameFinished gameFinished);

}
