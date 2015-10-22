package twg.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * HeadUpsideDown entity
 * 
 * @author Marc
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="headupsidedown")
public class HeadUpsideDown implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;
	
	/** id of the headupsidedown game */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="temps_autorise")
	private int tempsAutorise;
	
	public HeadUpsideDown(){}
	
	public HeadUpsideDown(
			int tempsAutorise)
	{
		this.tempsAutorise = tempsAutorise;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getTempsAutorise() {
		return tempsAutorise;
	}

	public void setTempsAutorise(int tempsAutorise) {
		this.tempsAutorise = tempsAutorise;
	}
}
