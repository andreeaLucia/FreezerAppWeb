package freezer.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

public class CategoryRequest {
	private int idCategory;
	private String token;
	
	@NotEmpty 
	private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setItemName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
