package co.facturalo.project.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.facturalo.project.model.pk.AAppObject;

/***
 * Almacena el reporte de un error por parte del usuario y su respuesta por
 * parte del sistema, principalmente orientado a errores de la aplicacion.
 * 
 * @author leon
 *
 */
@Entity
@Table(name = "issue")
public class AppIssue extends AAppObject implements Serializable {

	private static final long serialVersionUID = -2973907272702392841L;

	@Column(length = 150)
	private String path;

	@Column(length = 20)
	private String creatorUser;

	@Column(length = 20)
	private String receiverUser;

	@Temporal(TemporalType.TIME)
	private Date created;

	@Temporal(TemporalType.TIME)
	private Date verified;

	@Column(length = 1000)
	private String report;

	@Column(length = 500)
	private String response;

	public AppIssue() {
		super();
	}

	public AppIssue(String path, String creatorUser, String receiverUser, Date created, Date verified, String report,
			String response) {
		super();
		this.path = path;
		this.creatorUser = creatorUser;
		this.receiverUser = receiverUser;
		this.created = created;
		this.verified = verified;
		this.report = report;
		this.response = response;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(String creatorUser) {
		this.creatorUser = creatorUser;
	}

	public String getReceiverUser() {
		return receiverUser;
	}

	public void setReceiverUser(String receiverUser) {
		this.receiverUser = receiverUser;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getVerified() {
		return verified;
	}

	public void setVerified(Date verified) {
		this.verified = verified;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((creatorUser == null) ? 0 : creatorUser.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((receiverUser == null) ? 0 : receiverUser.hashCode());
		result = prime * result + ((report == null) ? 0 : report.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((verified == null) ? 0 : verified.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppIssue other = (AppIssue) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (creatorUser == null) {
			if (other.creatorUser != null)
				return false;
		} else if (!creatorUser.equals(other.creatorUser))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (receiverUser == null) {
			if (other.receiverUser != null)
				return false;
		} else if (!receiverUser.equals(other.receiverUser))
			return false;
		if (report == null) {
			if (other.report != null)
				return false;
		} else if (!report.equals(other.report))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (verified == null) {
			if (other.verified != null)
				return false;
		} else if (!verified.equals(other.verified))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppError [path=" + path + ", creatorUser=" + creatorUser + ", receiver=" + receiverUser + ", created="
				+ created + ", verified=" + verified + ", report=" + report + ", response=" + response + "]";
	}

}
