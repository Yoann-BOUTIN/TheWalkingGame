package twg.dao;

import twg.entities.Game;

public interface GameDao {

	public Game getGame(Long id);
	
	public void addGame(Game game);
	
	public Long deleteGameById(Long id);
}
