package freezer.controller.request;

public class FreezersRequest {
	private int idFreezer;
	private String freezerName;
	private int capacity;
	private String token;
	
	public String getFreezerName() {
		return freezerName;
	}
	public void setFreezerName(String freezerName) {
		this.freezerName = freezerName;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getIdFreezer() {
		return idFreezer;
	}
	public void setIdFreezer(int idFreezer) {
		this.idFreezer = idFreezer;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
