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
@Table(name="objective")
public class Objective implements Serializable {
	
	private static final long serialVersionUID = -8039686696076337053L;
	
	/** id of the question */
	@Id
	@GeneratedValue
	@Column(name="objective_id")
	private int id;
	
	/** latitude of the objective */
	@Column(name="latitude")
	private double latitude;
		
	/** longitude of the objective */
	@Column(name="longitude")
	private double longitude;
	
	public Objective(){}
	
	public Objective(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
