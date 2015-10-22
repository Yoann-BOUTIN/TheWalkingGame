package twg.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="game")
public class Game implements Serializable{
	private static final long serialVersionUID = -8039686696076337053L;
	
	/** id of the invitation */
	@Id
	@GeneratedValue
	@Column(name="game_id")
	private Long id;

	/** id of the team inviteur **/
	@Column(name="id_team_inviteur")
	private Long idTeamInviteur;

	/** id of the team invite */
	@Column(name="id_team_invite")
	private Long idTeamInvite;
	
	@Column(name="score_team_inviteur")
	private int scoreTeamInviteur;
	
	@Column(name="score_team_invite")
	private int scoreTeamInvite;
	
	public Game(){};

	public Game(Long idTeamInviteur, Long idTeamInvite){
		this.idTeamInviteur = idTeamInviteur;
		this.idTeamInvite = idTeamInvite;
	}

	public Long getIdTeamInviteur() {
		return idTeamInviteur;
	}

	public void setIdTeamInviteur(Long idTeamInviteur) {
		this.idTeamInviteur = idTeamInviteur;
	}

	public Long getIdTeamInvite() {
		return idTeamInvite;
	}

	public void setIdTeamInvite(Long idTeamInvite) {
		this.idTeamInvite = idTeamInvite;
	}

	public int getScoreTeamInviteur() {
		return scoreTeamInviteur;
	}

	public void setScoreTeamInviteur(int scoreTeamInviteur) {
		this.scoreTeamInviteur = scoreTeamInviteur;
	}

	public int getScoreTeamInvite() {
		return scoreTeamInvite;
	}

	public void setScoreTeamInvite(int scoreTeamInvite) {
		this.scoreTeamInvite = scoreTeamInvite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
