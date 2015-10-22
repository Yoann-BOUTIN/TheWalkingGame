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
@Table(name="gamefinished")
public class GameFinished implements Serializable{
	private static final long serialVersionUID = -8039686696076337053L;
	
	/** id of the invitation */
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="joueur")
	private String joueur;
	
	@Column(name="finish")
	private int finish;
	
	public GameFinished(){};

	public GameFinished(String joueur, int finish){
		this.joueur = joueur;
		this.finish = finish;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJoueur() {
		return joueur;
	}

	public void setJoueur(String joueur) {
		this.joueur = joueur;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
