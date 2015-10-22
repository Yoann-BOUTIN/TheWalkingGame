package twg.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * FindKeys entity
 * 
 * @author kevin
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="findkeys")
public class FindKeys implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;
	
	/** id of the FindKeys game */
	@Id
	@GeneratedValue
	@Column(name="id_find_key")
	private Long id;
	
	@Column(name="limite_time")
	private int timeLimite;
	
	public FindKeys(){}
	
	public FindKeys(int timeLimite){
		this.timeLimite = timeLimite;
		}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTimeLimite() {
		return timeLimite;
	}

	public void setTimeLimite(int timeLimite) {
		this.timeLimite = timeLimite;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
