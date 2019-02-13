package freezer.controller.response;

import java.util.List;
import java.util.Locale.Category;

import freezer.model.Items;

public class CategoryResponse extends BaseResponse{
private List<Category> categories;
	
	public CategoryResponse(){	
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	
	
}
