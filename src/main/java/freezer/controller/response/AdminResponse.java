package freezer.controller.response;


import java.util.List;

import freezer.model.Users;

public class AdminResponse extends BaseResponse {
	List<Users> usersList;

	public AdminResponse(){	
	}
	
	public AdminResponse(List<Users> usersList){
		this.usersList = usersList;
	}

	public List<Users> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}
	
}
