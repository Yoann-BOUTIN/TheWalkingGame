package twg.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="invitation")
public class Invitation implements Serializable{
	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the invitation */
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	/** name of the inviteur **/
	@Column(name="inviteur")
	private String inviteur;

	/** name of the invite */
	@Column(name="invite")
	private String invite;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_invitation")
    public java.util.Date date;
	

	public Invitation(){};

	public Invitation(String inviteur, String invite, java.util.Date date){
		this.inviteur = inviteur;
		this.invite = invite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInviteur() {
		return inviteur;
	}

	public void setInviteur(String inviteur) {
		this.inviteur = inviteur;
	}

	public String getInvite() {
		return invite;
	}

	public void setInvite(String invite) {
		this.invite = invite;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}
	




}
