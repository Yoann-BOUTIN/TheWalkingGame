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
@Table(name="Team")
public class Team implements Serializable{
	private static final long serialVersionUID = -8039686696076337053L;
	
	/** id of the invitation */
	@Id
	@GeneratedValue
	@Column(name="team_id")
	private Long id;

	/** name of the inviteur **/
	@Column(name="joueur_1")
	private String joueur1;

	/** name of the invite */
	@Column(name="joueur_2")
	private String joueur2;
	
	/** name of the invite **/
	@Column(name="joueur_3")
	private String joueur3;

	/** name of the invite */
	@Column(name="joueur_4")
	private String joueur4;
	
	@Column(name="team_score")
	private int score;
	
	public Team(){};

	public Team(String joueur1, String joueur2, String joueur3, String joueur4){
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
		this.joueur3 = joueur3;
		this.joueur4 = joueur4;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJoueur1() {
		return joueur1;
	}

	public void setJoueur1(String joueur1) {
		this.joueur1 = joueur1;
	}

	public String getJoueur2() {
		return joueur2;
	}

	public void setJoueur2(String joueur2) {
		this.joueur2 = joueur2;
	}

	public String getJoueur3() {
		return joueur3;
	}

	public void setJoueur3(String joueur3) {
		this.joueur3 = joueur3;
	}

	public String getJoueur4() {
		return joueur4;
	}

	public void setJoueur4(String joueur4) {
		this.joueur4 = joueur4;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
