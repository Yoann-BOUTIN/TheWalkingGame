package twg.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Simon entity
 * 
 * @author Marc
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="simon")
public class Simon implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;
	
	/** id of the headupsidedown game */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="seq_length")
	private int seqLength;
	
	public Simon(){}
	
	public Simon(
			int seqLength)
	{
		this.seqLength = seqLength;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getSeqLength() {
		return seqLength;
	}

	public void setSeqLength(int seqLength) {
		this.seqLength = seqLength;
	}
}
