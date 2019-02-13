package freezer.model;

public class Items {
	private int idItem;
	private int fk_id_user;
	private String userName;
	private int fk_id_category;
	private String categoryName;
	private int fk_id_freezer;
	private String freezerName;
	private int numberBags;
	private int bagWeight;
	private String createdDate;
	private String lastModifiedDate;
	
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getBagWeight() {
		return bagWeight;
	}
	public void setBagWeight(int bagWeight) {
		this.bagWeight = bagWeight;
	}
	
	public int getNumberBags() {
		return numberBags;
	}
	public void setNumberBags(int numberBags) {
		this.numberBags = numberBags;
	}
	
	public int getIdItem() {
		return idItem;
	}
	public void setIdItems(int idItem) {
		this.idItem = idItem;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getFk_id_category() {
		return fk_id_category;
	}
	public void setFk_id_category(int fk_id_category) {
		this.fk_id_category = fk_id_category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getFreezerName() {
		return freezerName;
	}
	public void setFreezerName(String freezerName) {
		this.freezerName = freezerName;
	}
	
	
	public int getFk_id_user() {
		return fk_id_user;
	}
	public void setFk_id_user(int fk_id_user) {
		this.fk_id_user = fk_id_user;
	}
	public int getFk_id_freezer() {
		return fk_id_freezer;
	}
	public void setFk_id_freezer(int fk_id_freezer) {
		this.fk_id_freezer = fk_id_freezer;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	@Override
	public String toString() { 
        return this.userName + " " + this.categoryName + " " + this.freezerName + " " + this.lastModifiedDate;
     } 
	
	
}
