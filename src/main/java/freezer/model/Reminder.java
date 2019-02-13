package freezer.model;

public class Reminder {
	private int idReminder;
	private String message;
	private String date;
	private String addedDate;
	private String modifiedDate;
	private int forUser;
	private String token;
	
	public int getIdReminder() {
		return idReminder;
	}
	public void setIdReminder(int idReminder) {
		this.idReminder = idReminder;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public int getForUser() {
		return forUser;
	}
	public void setForUser(int forUser) {
		this.forUser = forUser;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() { 
        return this.idReminder + " " + this.message + this.date + " " + this.addedDate + " " + this.modifiedDate+ " " + this.forUser;
     } 
}
