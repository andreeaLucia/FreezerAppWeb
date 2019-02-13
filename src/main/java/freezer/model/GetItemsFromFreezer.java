package freezer.model;

public class GetItemsFromFreezer {
	private int idItem;
	private String userName;
	private int numberBags;
	private int bagWeight;
	private String itemName;
	
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getNumberBags() {
		return numberBags;
	}
	public void setNumberBags(int numberBags) {
		this.numberBags = numberBags;
	}
	public int getBagWeight() {
		return bagWeight;
	}
	public void setBagWeight(int bagWeight) {
		this.bagWeight = bagWeight;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Override
	public String toString() { 
        return this.itemName + " " + this.userName + " " + this.numberBags + " " + this.bagWeight + " ";
     }
	
	
	
}
