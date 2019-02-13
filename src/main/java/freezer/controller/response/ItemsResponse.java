package freezer.controller.response;

import java.util.List;

import freezer.model.Items;

public class ItemsResponse extends BaseResponse {
	
	private List<Items> items;
	
	public ItemsResponse(){	
	}
	
	public ItemsResponse(List<Items> items){
		this.items = items;
	}
	
	public List<Items> getItems() {
		return items;
	}
	
	public void setItems(List<Items> items) {
		this.items = items;
	}
	
}
