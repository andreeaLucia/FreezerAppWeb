package freezer.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Authentication {
    private int idAuthentication;
	private String token;
	private int idUserFk;
	private LocalDateTime createdDate;
	private Date modifiedDate;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getIdAuthentication() {
		return idAuthentication;
	}
	public void setIdAuthentication(int idAuthentication) {
		this.idAuthentication = idAuthentication;
	}
	public int getIdUserFk() {
		return idUserFk;
	}
	public void setIdUserFk(int idUserFk) {
		this.idUserFk = idUserFk;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	
}
