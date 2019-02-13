package freezer.model;

public class GetItemsAddedByUser {

	private int idItem;
	private String categoryName;
	private int numberBags;
	private String freezerName;
	private int bagWeight;

	
	
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getNumberBags() {
		return numberBags;
	}
	public void setNumberBags(int numberBags) {
		this.numberBags = numberBags;
	}
	public String getFreezerName() {
		return freezerName;
	}
	public void setFreezerName(String freezerName) {
		this.freezerName = freezerName;
	}
	public int getBagWeight() {
		return bagWeight;
	}
	public void setBagWeight(int bagWeight) {
		this.bagWeight = bagWeight;
	}
	@Override
	public String toString() { 
		return this.idItem + " " + this.categoryName + " " + this.freezerName + " " + this.numberBags + " " + this.bagWeight;
	}

}
