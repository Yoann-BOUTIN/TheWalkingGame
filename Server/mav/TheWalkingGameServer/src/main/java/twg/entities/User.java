package twg.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User entity 
 * 
 * @author zizou
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the user */
	@Id
	@GeneratedValue
	@Column(name="user_id")
	private Long id;
	
	/** pseudo of the user */
	@Column(name="user_pseudo")
	private String userPseudo;
		
	/** score of the user */
	@Column(name="user_score")
	private int userScore;
	
	/** longitude of the user */
	@Column(name="user_pos_longitude")
	private double userPosLongitude;
	
	/** latitude of the user */
	@Column(name="user_pos_latitude")
	private double userPosLatitude; 

	public User(){}
	
	public User(String userPseudo, int userScore, double userPosLongitude,
			double userPosLatitude) {
		
		this.userPseudo = userPseudo;
		this.userScore = userScore;
		this.userPosLongitude = userPosLongitude;
		this.userPosLatitude = userPosLatitude;
		
	}
	
	public User(String userPseudo) {
		
		this.userPseudo = userPseudo;
		this.userScore = 0;
		this.userPosLongitude = 0;
		this.userPosLatitude = 0;
		
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserPseudo() {
		return userPseudo;
	}

	public void setUserPseudo(String userPseudo) {
		this.userPseudo = userPseudo;
	}

	public int getUserScore() {
		return userScore;
	}

	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}

	public double getUserPosLongitude() {
		return userPosLongitude;
	}

	public void setUserPosLongitude(double userPosLongitude) {
		this.userPosLongitude = userPosLongitude;
	}

	public double getUserPosLatitude() {
		return userPosLatitude;
	}

	public void setUserPosLatitude(double userPosLatitude) {
		this.userPosLatitude = userPosLatitude;
	}
	
	
		
}
