package freezer.controller.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ItemsRequest {
	
	private int idItem;
	private int fk_id_user;
	private int fk_id_freezer;
	private int fk_id_category;
	private Integer bagWeight; 
	private int numberBags; 
	private String token;
	
	public int getFk_id_category() {
		return fk_id_category;
	}
	public void setFk_id_category(int fk_id_category) {
		this.fk_id_category = fk_id_category;
	}
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
