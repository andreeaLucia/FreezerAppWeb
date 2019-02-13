package freezer.controller.request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReminderRequest {
	private int idReminder;
	private String message;
	@JsonFormat(pattern="dd-MM-YYYY HH:mm:ss")
	private Date date;
	private int forUser;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getForUser() {
		return forUser;
	}
	public void setForUser(int forUser) {
		this.forUser = forUser;
	}
	public int getIdReminder() {
		return idReminder;
	}
	public void setIdReminder(int idReminder) {
		this.idReminder = idReminder;
	}
	
}
