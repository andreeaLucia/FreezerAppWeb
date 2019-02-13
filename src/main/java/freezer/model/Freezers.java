package freezer.model;

public class Freezers {
	private int idFreezer;
	private String freezerName;
	private int capacity;
	private String createdDate;
	private String lastModifiedDate;

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setIdFreezer(int idFreezer){
		this.idFreezer = idFreezer;
	}
	
	public int getIdFreezer(){
		return idFreezer;
	}

	public String getFreezerName() {
		return freezerName;
	}

	public void setFreezerName(String freezerName) {
		this.freezerName = freezerName;
	}
	@Override
	public String toString() { 
        return this.idFreezer + " "+ this.freezerName+ " "+ this.capacity;
     } 

	
	
}
