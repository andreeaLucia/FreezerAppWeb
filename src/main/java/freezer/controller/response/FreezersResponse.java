package freezer.controller.response;

import java.util.ArrayList;
import java.util.List;

import freezer.model.Freezers;

public class FreezersResponse extends BaseResponse{
	List<Freezers> freezers = new ArrayList<>();
	
	public FreezersResponse(){	
	}
	
	public FreezersResponse(List<Freezers> freezers){
		this.freezers = freezers;
	}

	public List<Freezers> getFreezers() {
		return freezers;
	}

	public void setFreezers(List<Freezers> list) {
		this.freezers = list;
	}
	
}
