package twg.dao;

import java.util.Map;

import twg.entities.Team;

public interface TeamDao {

	public Team getTeam(String pseudo);
	
	public void addTeam(Team team);
	
	public Long deleteTeamById(Long id);
	
	public Map<String, Boolean> deleteTeamByPseudo(String pseudo);
	
	public Map<String, Boolean> teamExist(String joueur);
	
	public Map<String, Boolean> updateTeamJoueur2(String joueur2, String joueur1);
	public Map<String, Boolean> updateTeamJoueur3(String joueur3, String joueur1);
	public Map<String, Boolean> updateTeamJoueur4(String joueur4, String joueur1);
}
