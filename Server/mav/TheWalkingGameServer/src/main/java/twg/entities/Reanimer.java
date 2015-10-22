package twg.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Reanimer entity
 * 
 * @author Marc
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="reanimer")
public class Reanimer implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;
	
	/** id of the reanimer game */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="score_target")
	private int scoreTarget;
	
	@Column(name="temps_autorise")
	private int tempsAutorise;
	
	public Reanimer(){}
	
	public Reanimer(
			int scoreTarget,
			int tempsAutorise)
	{
		this.scoreTarget = scoreTarget;
		this.tempsAutorise = tempsAutorise;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getScoreTarget() {
		return scoreTarget;
	}

	public void setScoreTarget(int scoreTarget) {
		this.scoreTarget = scoreTarget;
	}
	
	public int getTempsAutorise() {
		return tempsAutorise;
	}

	public void setTempsAutorise(int tempsAutorise) {
		this.tempsAutorise = tempsAutorise;
	}
}
